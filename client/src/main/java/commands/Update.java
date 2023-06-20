package commands;


import managers.TCPManager;
import utility.Ask;
import utility.Console;

/**
 * обновляет элемент коллекции
 */
public class Update extends Command {
    private final Console console;
    private final TCPManager tcpManager;

    public Update(Console console, TCPManager tcpManager) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID");
        this.console = console;
        this.tcpManager = tcpManager;
    }

    /**
     * выполняет команду
     * @return успешность выполнения команды
     */
    @Override
    public boolean execute(String[] arguments) {
        try {
            if (arguments[1].isEmpty()) {
                console.println("Неправильное количество аргументов!");
                console.println("Использование: '" + getName() + "'");
                return false;
            }
            long id = -1;
            try { id = Long.parseLong(arguments[1].trim()); } catch (NumberFormatException e) { console.println("ID не распознан"); return false; }

            if (!tcpManager.sendAndGetMessage("update "+ id).equals("OK")) {
                console.println("Несуществующий ID");
                return false;
            }

            console.println("* Создание новой лабораторной работы:");
            var labWork = Ask.askLabWork(console);
            if (labWork != null && labWork.validate()) {
                console.println(tcpManager.sendAndGetMessage("update "+ id,labWork));
                return true;
            } else {
                console.println("Поля лабораторной работы не валидны! Лабораторная работа не создана!");
                return false;
            }
        } catch (Ask.AskBreak e) {
            console.println("Отмена...");
            return false;
        }
    }
}
