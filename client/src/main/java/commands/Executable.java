package commands;

public interface Executable {
    /**
     * @param args - аргументы команды
     */
    boolean execute(String[] args);
}
