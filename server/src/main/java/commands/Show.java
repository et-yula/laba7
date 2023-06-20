package commands;

import managers.CollectionManager;
import utility.Response;
import utility.User;

/**
 * выводит все элементы коллекции.
 */
public class Show extends Command {
    private final CollectionManager collectionManager;

    public Show(CollectionManager collectionManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
    }

    /**
     * выполняет команду
     * @return успешность выполнения команды.
     */
    @Override
    public Response execute(String[] arguments, Object obj, User user) {
        if (!arguments[1].isEmpty()) {
            return new Response(400, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }
        return new Response("OK",collectionManager.getCollection());
    }
}
