package commands;

import managers.TCPManager;
import models.LabWork;
import utility.Ask;
import utility.Console;

/**
 * добавляет новый элемент в коллекцию
 */
public class Add extends Command {
    private final Console console;
    private final TCPManager tcpManager;

    public Add(Console console, TCPManager tcpManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
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
                console.println(tcpManager.sendAndGetMessage("add", labWork));
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

