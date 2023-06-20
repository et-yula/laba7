package commands;

import managers.TCPManager;
import utility.Console;

/**
 *  выводит сумму значений поля minimalPoint для всех элементов коллекции
 */
public class SumOfMinimalPoint extends Command {
    private final Console console;
    private final TCPManager tcpManager;
    public SumOfMinimalPoint(Console console, TCPManager tcpManager){
        super("sum_of_minimal_point", "вывести сумму значений поля minimalPoint для всех элементов коллекции");
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
        console.println(tcpManager.sendAndGetMessage("sum_of_minimal_point"));
        return true;
    }
}

