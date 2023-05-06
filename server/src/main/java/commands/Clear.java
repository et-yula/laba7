package commands;

import managers.CollectionManager;
import utility.Response;

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
    public Response execute(String[] arguments, Object obj) {
        if (!arguments[1].isEmpty()){
            return new Response(400, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }
       collectionManager.getCollection().clear();
        return new Response("Коллекция очищена!");
    }
}
