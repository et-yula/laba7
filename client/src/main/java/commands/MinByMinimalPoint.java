package commands;

import managers.TCPManager;
import utility.Console;

/**
 * выводит любой объект из коллекции, значение поля minimalPoint которого является минимальным
 */
public class MinByMinimalPoint extends Command {
    private final Console console;
    private final TCPManager tcpManager;
    public MinByMinimalPoint(Console console, TCPManager tcpManager){
        super("min_by_minimal_point", "вывести любой объект из коллекции, значение поля minimalPoint которого является минимальным");
        this.console = console;
        this.tcpManager = tcpManager;
    }
    /**
     * выполняет команду
     * @param arguments - аргументы команды
     * @return успешность выполнения команды
     */
    @Override
    public boolean execute(String[] arguments) {
        if (!arguments[1].isEmpty()) {
            console.println("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
            return false;
        }
        console.println(tcpManager.sendAndGetMessage("min_by_minimal_point"));
        return true;
    }
}
