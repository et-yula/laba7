package commands;

import managers.TCPManager;
import utility.Console;

/**
 * очищает коллекцию
 */
public class Clear extends Command {
    private final Console console;
    private final TCPManager tcpManager;

    public Clear(Console console, TCPManager tcpManager) {
        super("clear", "очистить коллекцию");
        this.console = console;
        this.tcpManager = tcpManager;
    }
    /**
     * выполняет команду
     * @return успешность выполнения команды
     */
    @Override
    public boolean execute(String[] arguments) {
        if (!arguments[1].isEmpty()) {
            console.println("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
            return false;
        }
        console.println(tcpManager.sendAndGetMessage("clear"));
        return true;
    }
}
