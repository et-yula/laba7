package commands;

import managers.TCPManager;
import utility.Console;

/**
 * удаляет элемент из коллекции по его id
 *
 */
public class RemoveById extends Command {
    private final Console console;
    private final TCPManager tcpManager;

    public RemoveById(Console console, TCPManager tcpManager) {
        super("remove_by_id <ID>", "удалить элемент из коллекции по ID");
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
        if (arguments[1].isEmpty()) {
            console.println("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
            return false;
        }
        long id = -1;
        try { id = Long.parseLong(arguments[1].trim()); } catch (NumberFormatException e) { console.println("ID не распознан"); return false; }

        console.println(tcpManager.sendAndGetMassage("remove_by_id "+String.valueOf(id)));
        return true;
    }
}
