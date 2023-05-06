package models;

import utility.Validatable;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class Person implements Validatable, Serializable {
    /**
     * класс человека
     */
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.time.LocalDate birthday; //Поле не может быть null
    private String passportID; //Строка не может быть пустой, Длина строки не должна быть больше 49, Длина строки должна быть не меньше 6, Поле может быть null
    private Location location; //Поле может быть null
    public Person(String name,java.time.LocalDate birthday,String passportID,Location location) {
        this.name = name;
        this.birthday = birthday;
        this.passportID = passportID;
        this.location = location;
    }
    public Person(String s) {
        try {
            this.name = s.split(" ; ")[0];
            try { this.birthday = LocalDate.parse(s.split(" ; ")[1], DateTimeFormatter.ISO_LOCAL_DATE); } catch (
                    DateTimeParseException e) { this.birthday = null; };
            this.passportID = s.split(" ; ")[2].equals("null") ? null : s.split(" ; ")[2];
            this.location=s.split(" ; ")[3].equals("null") ? null : new Location(s.split(" ; ")[3]);
        } catch (ArrayIndexOutOfBoundsException e) {}
    }
    /**
     * @return true, если поля удовлетворяют условиям, иначе false
     */
    public boolean validate(){
        if (name == null || name.isEmpty()) { return false; }
        if (birthday == null) { return false; }
        if (passportID != null) {
            if (passportID.length() > 49 || passportID.length() < 6) { return false;}
        }
        if (location != null && !location.validate()) { return false; }
        return true;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getPassportID() {
        return passportID;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name) && birthday.equals(person.birthday) && Objects.equals(passportID, person.passportID) && location.equals(person.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthday, passportID, location);
    }

    @Override
    public String toString() {
        return name+" ; "+ birthday.format(DateTimeFormatter.ISO_LOCAL_DATE)+" ; "+((passportID == null) ? "null" :passportID)+" ; "+((location == null) ? "null" : location.toString());
    }
}

