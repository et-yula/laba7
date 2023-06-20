package utility;

public class User {
    private long id;
    private final String login;
    private final String password;

    public User(long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public long getID() {
        return id;
    }
    public void setID(long id) {
        this.id = id;
    }
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
}
