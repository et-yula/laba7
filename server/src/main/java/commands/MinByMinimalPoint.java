package commands;

import managers.CollectionManager;
import models.LabWork;
import utility.Response;
import utility.User;

/**
 * выводит любой объект из коллекции, значение поля minimalPoint которого является минимальным
 */
public class MinByMinimalPoint extends Command {
    private final CollectionManager collectionManager;
    public MinByMinimalPoint(CollectionManager collectionManager){
        super("min_by_minimal_point", "вывести любой объект из коллекции, значение поля minimalPoint которого является минимальным");
        this.collectionManager = collectionManager;
    }
    /**
     * выполняет команду
     * @param arguments - аргументы команды
     * @return успешность выполнения команды
     */
    @Override
    public Response execute(String[] arguments, Object obj, User user) {
        if (!arguments[1].isEmpty()) {
            return new Response(400, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }
        LabWork labWorkWithMinimalPoint = null;
        for (var labWork: collectionManager.getCollection()){
            if (labWorkWithMinimalPoint == null || labWorkWithMinimalPoint.getMinimalPoint() > labWork.getMinimalPoint()) {
                labWorkWithMinimalPoint = labWork;
            }
        }
        if (labWorkWithMinimalPoint == null) {
            return new Response(400,"Коллекция пуста!");
        }
        return new Response(labWorkWithMinimalPoint.toString());
    }
}

