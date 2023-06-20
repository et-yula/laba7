package commands;

import managers.CollectionManager;
import utility.Response;
import utility.User;

import java.time.LocalDateTime;

/**
 * выводит информацию о коллекции
 */
public class Info extends Command {
    private final CollectionManager collectionManager;

    public Info(CollectionManager collectionManager) {
        super("info", "вывести информацию о коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * выполняет команду
     * @return успешность выполнения команды
     */
    @Override
    public Response execute(String[] arguments, Object obj, User user) {
        if (!arguments[1].isEmpty()){
            return new Response(400, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }
        var s="Сведения о коллекции:\n";
        s+=" Тип: " + collectionManager.getCollection().getClass().toString()+"\n";
        s+=" Количество элементов: " + collectionManager.getCollection().size()+"\n";
        return new Response(s);
    }
}
