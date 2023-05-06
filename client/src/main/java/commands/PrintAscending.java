package commands;

import managers.TCPManager;
import utility.Console;

/**
 * выводит элементы коллекции в порядке возрастания
 */
public class PrintAscending extends Command {
    private final Console console;
    private final TCPManager tcpManager;
    public PrintAscending(Console console, TCPManager tcpManager){
        super("print_ascending", "вывести элементы коллекции в порядке возрастания");
        this.console = console;
        this.tcpManager=tcpManager;
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
        console.println(tcpManager.sendAndGetMassage("print_ascending"));
        return true;
    }
}

