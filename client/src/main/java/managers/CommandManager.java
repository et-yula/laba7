package managers;

import commands.Command;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {
    private final Map<String, Command> commands = new LinkedHashMap<>();
    private final List<String> commandHistory = new ArrayList<>();

    /**
     * @param commandName - название команды как будущий ключ
     * @param command - экземпляр команды
     */
    public void register (String commandName, Command command) {
        commands.put(commandName, command);
    }

    /**
     * @return словарь команд
     */

    public Map<String, Command> getCommands() {
        return commands;
    }

    /**
     * добавляет команду в историю
     * @param commandName - название команды
     */
    public void addToHistory(String commandName){
        commandHistory.add(commandName);
    }

    /**
     * @return историю команд
     */

    public List<String> getCommandHistory() {
        return commandHistory;
    }
}
