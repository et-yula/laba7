package utility;

import managers.CommandManager;
import managers.UserManager;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class Runner {
    private Console console;
    private final CommandManager commandManager;
    private final UserManager userManager;
    private final List<String> scriptStack = new ArrayList<>();
    private int lengthRecursion = -1;

    public Runner(Console console, CommandManager commandManager, UserManager userManager) {
        this.console = console;
        this.commandManager = commandManager;
        this.userManager = userManager;
    }

    /**
     * Launchs the command.
     * @param userCommand - команда для запуска
     * @return код завершения
     */
    private Response launchCommand(String[] userCommand, Object obj, String login, String pass) {
        //if (userCommand[0].equals("")) {
            //return new Response("OK");
        //}
        var command = commandManager.getCommands().get(userCommand[0]);

        if (command == null)
            return new Response(400, "Команда '" + userCommand[0] + "' не найдена. Наберите 'help' для справки");

        var user = userManager.getUser(login);
        if (user != null) {
            MessageDigest md=null;
            try{
                md = MessageDigest.getInstance("SHA-224");
            } catch (Exception ignored) {}
            assert md != null;
            md.update(pass.getBytes());
            byte[] digest = md.digest();
            String passHash = java.util.HexFormat.of().formatHex(digest);
            if (!passHash.equals(user.getPassword())) {
                return new Response(503, "login or password is invalid");
            }
        } else if (!userCommand[0].equals("create_user")) {
            return new Response(503, "User is null");
        } else {
            user = new User(0,"","");
        }
        var resp = command.execute(userCommand, obj, user);
        if (resp == null) return new Response(503, "503");
        return resp;
    }

    public Object executeCommand(String s, Object obj, String login, String pass) {
        String[] userCommand = {"", ""};
        userCommand = (s.replace('\n',' ').replace('\r',' ') + " ").split(" ", 2);
        userCommand[1] = userCommand[1].trim();
        System.out.println("$ "+userCommand[0]);

        commandManager.addToHistory(userCommand[0]);

        return launchCommand(userCommand, obj, login, pass);
    }
}