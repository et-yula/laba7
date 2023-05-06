package commands;

import managers.CollectionManager;
import utility.Response;

/**
 *  выводит сумму значений поля minimalPoint для всех элементов коллекции
 */
public class SumOfMinimalPoint extends Command {
    private final CollectionManager collectionManager;
    public SumOfMinimalPoint(CollectionManager collectionManager){
        super("sum_of_minimal_point", "вывести сумму значений поля minimalPoint для всех элементов коллекции");
        this.collectionManager = collectionManager;
    }
    /**
     * выполняет команду
     * @param arguments - аргументы команды
     * @return успешность выполнения команды
     */
    @Override
    public Response execute(String[] arguments, Object obj) {
        if (!arguments[1].isEmpty()) {
            return new Response(400, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }
        Long sumOfMinimalPoint = 0L;
        for (var labWork: collectionManager.getCollection()){
            sumOfMinimalPoint = sumOfMinimalPoint + labWork.getMinimalPoint();
        }
        if (sumOfMinimalPoint == 0L) {
            return new Response(400,"Коллекция пуста!");
        }
        return new Response(sumOfMinimalPoint.toString());
    }
}

