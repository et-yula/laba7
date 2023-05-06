package commands;

import managers.CollectionManager;
import models.LabWork;
import utility.Response;

import java.util.ArrayList;

/**
 * удаляет из коллекции все элементы, превышающие заданный
 */
public class RemoveGreater extends Command {
    private final CollectionManager collectionManager;
    public RemoveGreater(CollectionManager collectionManager){
        super("remove_greater {element}", "удалить из коллекции все элементы, превышающие заданный");
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
        LabWork receivedLabWork = (LabWork) obj;
        if (receivedLabWork != null && receivedLabWork.validate()) {
            int i = 0;
            var needRemove = new ArrayList<LabWork>();
            for (var labWork : collectionManager.getCollection()) {
                if (labWork.getMinimalPoint() > receivedLabWork.getMinimalPoint()){
                    needRemove.add(labWork);
                    i++;
                }
            }
            for (var labWork:needRemove)
                collectionManager.getCollection().remove(labWork);
            return new Response("Удалено "+i+" лабораторных работ");
        } else {
            return new Response(400,"Поля лабораторной работы не валидны! Лабораторная работа не сравнима!");
        }
    }
}

