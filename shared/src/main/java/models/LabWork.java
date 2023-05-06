package models;

import utility.Element;
import utility.Validatable;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * основной класс лабораторной работы
 */
public class LabWork implements Validatable, Serializable {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long minimalPoint; //Поле не может быть null, Значение поля должно быть больше 0
    private Difficulty difficulty; //Поле может быть null
    private Person author; //Поле может быть null

    public LabWork(int id, String name, Coordinates coordinates, Long minimalPoint, Difficulty difficulty, Person author) {
        this.id = id;
        this.name = name;
        this.coordinates=coordinates;
        this.creationDate = LocalDate.now();
        this.minimalPoint = minimalPoint;
        this.difficulty = difficulty;
        this.author = author;
    }
    public LabWork(int id, String name, Coordinates coordinates, LocalDate creationDate, Long minimalPoint, Difficulty difficulty, Person author) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.minimalPoint = minimalPoint;
        this.difficulty = difficulty;
        this.author = author;
    }

    /**
     * @return true, если поля удовлетворяют условиям, иначе false
     */
    @Override
    public boolean validate() {
        if (id <= 0) { return false; }
        if (name == null || name.isEmpty()) {
            return false;
        }
        if (coordinates == null || !coordinates.validate()) {
            return false;
        }
        if (creationDate == null) {
            return false;
        }
        if (minimalPoint == null || minimalPoint <= 0) {
            return false;
        }
        if (author != null && !author.validate()) {
            return false;
        }
        return true;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Long getMinimalPoint() {
        return minimalPoint;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Person getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabWork labWork = (LabWork) o;
        return id == labWork.id && name.equals(labWork.name) && coordinates.equals(labWork.coordinates) && creationDate.equals(labWork.creationDate) && minimalPoint.equals(labWork.minimalPoint) && difficulty == labWork.difficulty && Objects.equals(author, labWork.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, minimalPoint, difficulty, author);
    }

    @Override
    public String toString(){
        String info = "";
        info += " ID: " + id;
        info += "\n Название работы: " + name;
        info += "\n Координаты: (" + coordinates +")";
        info += "\n Время создания: "+ creationDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        info += "\n Минимальный балл: "+ minimalPoint;
        info += "\n Сложность: "+((difficulty == null) ? "null" : difficulty);
        info += "\n Автор работы: "+ ((author == null) ? "null" : author);
        return info;
    }
    public static String[] toArray(LabWork e) {
        var list = new ArrayList<String>();
        list.add(String.valueOf(e.getId()));
        list.add(e.getName());
        list.add(e.getCoordinates().toString());
        list.add(e.getCreationDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        list.add(e.getMinimalPoint().toString());
        list.add(e.getDifficulty() == null ? "null" : e.getDifficulty().toString());
        list.add(e.getAuthor() == null ? "null" : e.getAuthor().toString());
        return list.toArray(new String[0]);
    }

    public static LabWork fromArray(String[] a) {
        int id;
        String name;
        Coordinates coordinates;
        LocalDate creationDate;
        Long minimalPoint;
        Difficulty difficulty;
        Person author;
        try {
            try { id = Integer.parseInt(a[0]); } catch (NumberFormatException e) { id = 0;  }
            name = a[1];
            coordinates = new Coordinates(a[2]);
            try { creationDate = LocalDate.parse(a[3], DateTimeFormatter.ISO_LOCAL_DATE); } catch (
                    DateTimeParseException e) { creationDate = null; };
            try { minimalPoint = Long.parseLong(a[4]); } catch (NumberFormatException e) { minimalPoint = null;}
            try { difficulty = (a[5].equals("null") ? null: Difficulty.valueOf(a[5])); } catch (NullPointerException | IllegalArgumentException  e) { difficulty = null; }
            author = (a[6].equals("null") ? null : new Person(a[6]));
            return new LabWork(id, name, coordinates, creationDate, minimalPoint, difficulty, author);
        } catch (ArrayIndexOutOfBoundsException e) {}
        return null;
    }
    public int compareTo(Element element) {
        return (int)(this.id - element.getId());
    }
}
