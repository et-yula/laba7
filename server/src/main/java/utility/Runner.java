package utility;

import managers.CommandManager;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    private Console console;
    private final CommandManager commandManager;
    private final List<String> scriptStack = new ArrayList<>();
    private int lengthRecursion = -1;

    public Runner(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Launchs the command.
     * @param userCommand Команда для запуска
     * @return Код завершения.
     */
    private Response launchCommand(String[] userCommand, Object obj) {
        if (userCommand[0].equals("")) return new Response("OK");
        var command = commandManager.getCommands().get(userCommand[0]);

        if (command == null || userCommand[0].equals("save") || userCommand[0].equals("load"))
            return new Response(400, "Команда '" + userCommand[0] + "' не найдена. Наберите 'help' для справки");//error

        var resp = command.execute(userCommand, obj);
        if (resp == null) return new Response(503, "503");
        return resp;
    }

    public Object executeCommand(String s, Object obj) {
        String[] userCommand = {"", ""};
        userCommand = (s.replace('\n',' ').replace('\r',' ') + " ").split(" ", 2);
        userCommand[1] = userCommand[1].trim();
        System.out.println("$ "+userCommand[0]);

        commandManager.addToHistory(userCommand[0]);

        return launchCommand(userCommand, obj);
    }
}
