package commands;

import managers.CollectionManager;
import utility.Response;

/**
 * сохраняет коллекцию в файл.
 */
public class Save extends Command {
    private final CollectionManager collectionManager;

    public Save(CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
    }

    /**
     * выполняет команду
     * @return успешность выполнения команды.
     */
    @Override
    public Response execute(String[] arguments, Object obj) {
        System.out.println("$ save");
        collectionManager.saveCollection();
        return new Response("OK");
    }
}