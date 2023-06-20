package commands;

import managers.TCPManager;
import models.LabWork;
import utility.Ask;
import utility.Console;
/**
 *  добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
 */
public class AddIfMax extends Command{
    private final Console console;
    private final TCPManager tcpManager;

    public AddIfMax(Console console, TCPManager tcpManager) {
        super("AddIfMax {element}", "добавить новый элемент в коллекцию");
        this.console = console;
        this.tcpManager = tcpManager;
    }

    /**
     * выполняет команду
     * @return успешность выполнения команды.
     */
    @Override
    public boolean execute(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) {
                console.println("Неправильное количество аргументов!");
                console.println("Использование: '" + getName() + "'");
                return false;
            }

            console.println("* Создание новой лабораторной работы:");
            LabWork labWork = Ask.askLabWork(console);

            if (labWork != null && labWork.validate()) {
                console.println(tcpManager.sendAndGetMessage("add_if_max", labWork));
                return true;
            }
            console.println("Поля лабораторной работы не валидны! Лабораторная работа не создана!");
            return false;
        } catch (Ask.AskBreak e) {
            console.println("Отмена...");
            return false;
        }
    }
}
