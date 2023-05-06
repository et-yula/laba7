package commands;

import utility.Response;

/**
 * интерфейс для всех выполняемых команд.
 */
public interface Executable {
    /**
     * выполнить что-либо.
     * params:arguments – аргумент для выполнения
     * returns: результат выполнения
     */
    Response execute(String[] arguments, Object obj);
}

