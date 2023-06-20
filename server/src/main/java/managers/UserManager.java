package managers;

import utility.User;

import java.security.MessageDigest;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * использует файл для сохранения и загрузки конфигурации
 */
public class UserManager {
    private final DumpManager dumpManager;
    private ConcurrentLinkedDeque<User> users = new ConcurrentLinkedDeque<>();

    public UserManager(DumpManager dumpManager) {
        this.dumpManager = dumpManager;
    }

    public boolean initialize() {
        users.clear();
        for (var user: dumpManager.selectUser())
            users.addLast(user);
        return true;
    }

    public User getUser(String login) {
        for (var e: users)
            if (e.getLogin().equals(login))
                return e;
        return null;
    }

    public User[] getUsers() {
        return users.toArray(new User[]{});
    }

    public boolean addUser(String login, String password) {
        MessageDigest messageDigest=null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-224");
        } catch (Exception e) {}
        messageDigest.update(password.getBytes());
        byte[] digest = messageDigest.digest();
        String passHash = java.util.HexFormat.of().formatHex(digest);
        var user = new User(0, login, passHash);
        if (dumpManager.insertUser(user)){
            users.addLast(user);
        } else {
            return false;
        }
        return true;
    }
}
