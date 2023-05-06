package commands;

import managers.TCPManager;
import models.LabWork;
import utility.Console;

import java.util.HashSet;
import java.util.stream.Collectors;

public class Show extends Command {
    private final Console console;
    private final TCPManager tcpManager;

    public Show(Console console, TCPManager tcpManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.console = console;
        this.tcpManager = tcpManager;
    }

    /**
     * выполняет команду
     * @return успешность выполнения команды.
     */
    @Override
    public boolean execute(String[] arguments) {
        if (!arguments[1].isEmpty()) {
            console.println("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
            return false;
        }
        var s=((HashSet<LabWork>)tcpManager.send("show").getResponseObj()).stream().map(LabWork::toString).collect(Collectors.joining("\n"));
        if (s.isEmpty())
            console.println("Коллекция пуста!");
        else
            console.println(s);
        return true;
    }
}
