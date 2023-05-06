package commands;

import java.util.Objects;
/**
 * абстрактный класс для всех команд
 */
public abstract class Command implements Executable, Describable {
    private final String name;
    private final String description;
    public Command(String name, String description){
        this.name=name;
        this.description=description;
    }
    /**
     * @return название команды
     */
    public String getName(){
        return name;
    }
    /**
     * @return предназначение команды
     */
    public String getDescription(){
        return description;
    }
    @Override
    public String toString(){
        return "Command{" + "name='" + name + '\'' + ", description='" + description + '\'' + '}';
    }
    @Override
    public int hashCode(){
        return Objects.hash(name,description);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return Objects.equals(this.name, command.name) && Objects.equals(this.description, command.description);
    }
}

