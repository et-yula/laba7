package commands;

import managers.CollectionManager;
import models.LabWork;
import utility.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * выводит элементы коллекции в порядке возрастания
 */
public class PrintAscending extends Command {
    private final CollectionManager collectionManager;
    public PrintAscending(CollectionManager collectionManager){
        super("print_ascending", "вывести элементы коллекции в порядке возрастания");
        this.collectionManager = collectionManager;
    }
    /**
     * выполняет команду
     * @param arguments - аргументы команды
     * @return успешность выполнения команды
     */
    @Override
    public Response execute(String[] arguments,Object obj) {
        if (!arguments[1].isEmpty()) {
            return new Response(400, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }
        List<LabWork> allLabWorks = new ArrayList<>(collectionManager.getCollection());
        if (allLabWorks.isEmpty()) {
            return new Response(400,"Коллекция пуста!");
        }
        allLabWorks.sort((o1, o2) -> {
            if (o1.getMinimalPoint() > o2.getMinimalPoint()) {
                return 1;
            }
            if (o1.getMinimalPoint() < o2.getMinimalPoint()) {
                return -1;
            }
            return 0;
        });
        StringBuilder res= new StringBuilder("Элементы коллекции в порядке возрастания:\n");
        for (var labWork:allLabWorks){
            res.append(labWork.toString()+"\n");
        }
        return new Response(res.toString());
    }
}

