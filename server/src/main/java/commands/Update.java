package commands;

import models.LabWork;
import utility.Response;
import managers.CollectionManager;
import utility.User;

/**
 * обновляет значение элемента коллекции, id которого равен заданному
 */
public class Update extends Command {
    private final CollectionManager collectionManager;
    public Update(CollectionManager collectionManager) {
        super("update <id> {element}", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String[] arguments,Object obj, User user) {
        if (arguments[1].isEmpty()) {
            return new Response(400, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }
        int id = -1;
        try {
            id = Integer.parseInt(arguments[1].trim());
        } catch (NumberFormatException e) {
            return new Response(400,"ID не распознан");
        }
        if (collectionManager.byId(id) == null) {
            return new Response(400,"Несуществующий ID");
        } else if (obj == null)
            return new Response("OK");

        LabWork receivedLabWork = (LabWork) obj;
        if (receivedLabWork.validate()) {
            receivedLabWork.setId(id);
            if (collectionManager.update(receivedLabWork,user)){
                return new Response("Лабораторная работа успешно изменёна!");
            } else {
                return new Response(403,"Access denied");
            }
        } else {
            return new Response(400,"Поля лабораторной работы не валидны! Лабораторная работа не создана!");
        }
    }
}
