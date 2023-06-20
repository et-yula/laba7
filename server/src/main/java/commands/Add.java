package commands;

import managers.CollectionManager;
import models.LabWork;
import utility.Response;
import utility.User;

/**
 * добавляет новый элемент в коллекцию
 */
public class Add extends Command {
    private final CollectionManager collectionManager;

    public Add(CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
    }

    /**
     * выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response execute(String[] arguments, Object obj, User user) {
        if (!arguments[1].isEmpty()) {
            return new Response(400, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }
        LabWork labWork = (LabWork) obj;
        if (labWork != null && labWork.validate()) {
            if (collectionManager.add(labWork, user))
                return new Response("Лабораторная работа успешно добавлена!");
            else
                return new Response(500, "Access error");
        } else {
            return new Response(400,"Поля лабораторной работы не валидны! Лабораторная работа не создана!");
        }
    }
}
