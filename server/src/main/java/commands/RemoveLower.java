package commands;

import managers.CollectionManager;
import models.LabWork;
import utility.Response;
import utility.User;

import java.util.ArrayList;

/**
 *  удаляет из коллекции все элементы, меньшие, чем заданный
 */
public class RemoveLower extends Command {
    private final CollectionManager collectionManager;
    public RemoveLower( CollectionManager collectionManager){
        super("remove_lower {element}", " удалить из коллекции все элементы, меньшие, чем заданный");
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
            var needRemove = new ArrayList<LabWork>();
            for (var labWork : collectionManager.getCollection())
                if (labWork.getMinimalPoint() < receivedLabWork.getMinimalPoint()){
                    needRemove.add(labWork);
                }
            int j =0;
            for (var labWork:needRemove)
                if (collectionManager.remove(labWork.getId(),user))
                    j++;
            return new Response("Удалено " + j + " лабораторных работ");
        } else {
            return new Response(400,"Поля лабораторной работы не валидны! Лабораторная работа не сравнима!");
        }
    }
}

