package utility;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Для ввода команд и вывода результата
 */
public class ClientConsole implements Console {
    private static final String P = "$ ";
    private static StringBuilder out = new StringBuilder();

    /**
     * Выводит obj.toString() в консоль
     * @param obj объект для печати
     */
    public void print(Object obj) {
        out.append(obj==null?"null":obj.toString());
    }

    /**
     * Выводит obj.toString() + \n в консоль
     * @param obj объект для печати
     */
    public void println(Object obj) {
        out.append(obj==null?"null\n":obj.toString()+"\n");
    }

    /**
     * Выводит ошибка: obj.toString() в консоль
     * @param obj ошибка для печати
     */
    public void printError(Object obj) {
        out.append("Error(err):"+(obj==null?"null\n":obj.toString()+"\n"));
    }

    public String readln() throws NoSuchElementException, IllegalStateException { return null; }

    public boolean isCanReadln() throws IllegalStateException { return false; }

    /**
     * Выводит таблицу из 2 колонок
     * @param elementLeft левый элемент колонки.
     * @param elementRight правый элемент колонки.
     */
    public void printTable(Object elementLeft, Object elementRight) {
        out.append(String.format(" %-35s%-1s%n", elementLeft, elementRight));
    }

    /**
     * Выводит prompt текущей консоли
     */
    public void prompt() {
        print(P);
    }

    /**
     * @return prompt текущей консоли
     */
    public String getPrompt() {
        return P;
    }

    public void selectFileScanner(Scanner scanner) {}

    public void selectConsoleScanner() {}

    public void clearOut() {out.setLength(0);}
    public String getOut() {return out.toString();}
}

