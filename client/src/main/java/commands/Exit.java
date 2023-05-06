package commands;

import utility.Console;

public class Exit extends Command {
    private final Console console;
    public Exit(Console console){
        super("exit", "завершить программу (без сохранения в файл)");
        this.console = console;
    }

    /**
     * выполняет команду
     * @param arguments - аргументы команды
     * @return успешность выполнения команды
     */
    @Override
    public boolean execute(String[] arguments) {
        if (arguments[1].isEmpty()) {
            console.println("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
            return false;
        }

        console.println("Завершение выполнения...");
        return true;
    }

}
