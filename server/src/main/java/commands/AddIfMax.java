package commands;

import managers.CollectionManager;
import models.LabWork;
import utility.Response;
import utility.User;

/**
 *  добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
 */
public class AddIfMax extends Command {
    private final CollectionManager collectionManager;
    public AddIfMax(CollectionManager collectionManager) {
        super("add_if_max {element}", " добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
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
        LabWork receivedLabWork = (LabWork) obj;
        if (receivedLabWork != null && receivedLabWork.validate()) {
            Long maxMinimalPoint = 0L;

            for (var labWork : collectionManager.getCollection()) {
                if (maxMinimalPoint < labWork.getMinimalPoint()) {
                    maxMinimalPoint = labWork.getMinimalPoint();
                }
            }
            if (maxMinimalPoint == 0L) {
                return new Response(400,"Коллекция пуста!");
            }
            if (receivedLabWork.getMinimalPoint() > maxMinimalPoint) {
                if (collectionManager.add(receivedLabWork, user)){
                    return new Response("Лабораторная работа успешно добавлена!");
                } else {
                    return new Response(500, "Access error");
                }
            } else {
                return new Response(400,"Лабораторная работа не добавлена, так как ее значение не превышает значение наибольшего элемента этой коллекции");
            }
        }
        return new Response(400,"Поля лабораторной работы не валидны! Лаюораторная работа не создана!");
    }
}

