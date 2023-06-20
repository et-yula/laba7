package commands;

import managers.CollectionManager;
import utility.Response;
import utility.User;

/**
 * удаляет элемент из коллекции по его id
 */
public class RemoveById extends Command {
    private final CollectionManager collectionManager;

    public RemoveById (CollectionManager collectionManager) {
        super("remove_by_id <ID>", "удалить элемент из коллекции по ID");
        this.collectionManager = collectionManager;
    }

    /**
     * выполняет команду
     * @param arguments - аргументы команды
     * @return успешность выполнения команды
     */
    @Override
    public Response execute(String[] arguments, Object obj, User user) {
        if (arguments[1].isEmpty()) {
            return new Response(400, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }
        int id = -1;
        try {
            id = Integer.parseInt(arguments[1].trim());
        } catch (NumberFormatException e) {
            return new Response(400,"ID не распознан"); }

        if (collectionManager.byId(id) == null) {
            return new Response(400,"Несуществующий ID");
        }
        if (collectionManager.remove(id,user)) {
            return new Response("Лабораторная работа успешно удалёна!");
        } else {
            return new Response(403,"Access denied");
        }

    }
}

