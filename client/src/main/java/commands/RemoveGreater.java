package commands;

import managers.TCPManager;
import models.LabWork;
import utility.Ask;
import utility.Console;

/**
 * удаляет из коллекции все элементы, превышающие заданный
 */
public class RemoveGreater extends Command {
    private final Console console;
    private final TCPManager tcpManager;
    public RemoveGreater(Console console, TCPManager tcpManager){
        super("remove_greater {element}", "удалить из коллекции все элементы, превышающие заданный");
        this.console = console;
        this.tcpManager = tcpManager;
    }
    /**
     * выполняет команду
     * @param arguments - аргументы команды
     * @return успешность выполнения команды
     */
    @Override
    public boolean execute(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) {
                console.println("Неправильное количество аргументов!");
                console.println("Использование: '" + getName() + "'");
                return false;
            }
            console.println("* Введите лабораторную работу для сравнения:");
            LabWork receivedLabWork = Ask.askLabWork(console);
            if (receivedLabWork != null && receivedLabWork.validate()) {
                console.println(tcpManager.sendAndGetMessage("remove_greater", receivedLabWork));
                return true;
            } else {
                console.println("Поля лабораторной работы не валидны! Лабораторная работа не сравнима!");
                return false;
            }
        } catch (Ask.AskBreak e) {
            console.println("Отмена...");
            return false;
        }
    }
}

