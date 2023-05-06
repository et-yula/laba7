package commands;

public interface Describable {
    /**
     * @return описание команды
     */
    String getDescription();

    /**
     * @return название команды
     */
    String getName();
}
