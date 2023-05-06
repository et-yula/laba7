package commands;

import managers.CollectionManager;
import models.LabWork;
import utility.Response;

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
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response execute(String[] arguments, Object obj) {
        if (!arguments[1].isEmpty()) {
            return new Response(400, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }
        LabWork labWork = (LabWork) obj;
        if (labWork != null && labWork.validate()) {
            labWork.setId(collectionManager.getFreeId());
            collectionManager.add(labWork);
            return new Response("Лабораторная работа успешно добавлена!");
        } else {
            return new Response(400,"Поля лабораторной работы не валидны! Лабораторная работа не создана!");
        }
    }
}
