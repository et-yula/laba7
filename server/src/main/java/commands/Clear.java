package commands;

import managers.CollectionManager;
import models.LabWork;
import utility.Response;
import utility.User;

/**
 * очищает коллекцию
 */
public class Clear extends Command {
    private final CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    /**
     * выполняет команду
     * @return успешность выполнения команды.
     */
    @Override
    public Response execute(String[] arguments, Object obj, User user) {
        if (!arguments[1].isEmpty()){
            return new Response(400, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }
        for (var labWork: collectionManager.getCollection()) {
            collectionManager.remove(labWork.getId(), user);
        }
        return new Response("Коллекция очищена!");
    }
}
