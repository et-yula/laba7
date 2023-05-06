package commands;

import managers.TCPManager;
import utility.Console;

/**
 * выводит информацию о коллекции
 */
public class Info extends Command {
    private final Console console;
    private final TCPManager tcpManager;

    public Info(Console console, TCPManager tcpManager) {
        super("info", "вывести информацию о коллекции");
        this.console = console;
        this.tcpManager = tcpManager;
    }
    /**
     * Выполняет команду
     * @return успешность выполнения команды
     */
    @Override
    public boolean execute(String[] arguments) {
        if (!arguments[1].isEmpty()) {
            console.println("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
            return false;
        }
        console.println(tcpManager.sendAndGetMassage("info"));
        return true;
    }
}