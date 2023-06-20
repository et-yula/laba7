package utility;

import models.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

public class Ask {
    public static class AskBreak extends Exception {}

    public static LabWork askLabWork(Console console) throws AskBreak {
		/*
            private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
            private String name; //Поле не может быть null, Строка не может быть пустой
            private Coordinates coordinates; //Поле не может быть null
            private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
            private Long minimalPoint; //Поле не может быть null, Значение поля должно быть больше 0
            private Difficulty difficulty; //Поле может быть null
            private Person author; //Поле может быть null
        */
        try {
            console.print("name: ");
            String name;
            while (true) {
                name = console.readln().trim();
                if (name.equals("exit")) throw new AskBreak();
                if (!name.equals("")) break;
                console.print("name: ");
            }
            var coordinates = askCoordinates(console);
            console.print("minimal point: ");
            Long minimalPoint;
            while (true) {
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try { minimalPoint = Long.parseLong(line); if (minimalPoint>0) break; } catch (NumberFormatException e) { }
                console.print("minimal point: ");
            }
            var difficulty= askDifficulty(console);
            var author=askPerson(console);
            return new LabWork(1000000000, name, coordinates, minimalPoint, difficulty, author);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static Coordinates askCoordinates(Console console) throws AskBreak {
        try {
            /*
            private Integer x; //Поле не может быть null
            private Long y; //Поле не может быть null
             */
            console.print("coordinates.x: ");
            Integer x;
            while (true) {
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try { x = Integer.parseInt(line); break; } catch (NumberFormatException e) { }
                console.print("coordinates.x: ");
            }
            console.print("coordinates.y: ");
            Long y;
            while (true) {
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try { y = Long.parseLong(line); break; } catch (NumberFormatException e) { }
                console.print("coordinates.y: ");
            }

            return new Coordinates(x, y);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static Location askLocation(Console console) throws AskBreak {
        try {
            /*
            private Integer x; //Поле не может быть null
            private int y;
            private Integer z; //Поле не может быть null
            private String name; //Поле может быть null
             */
            console.print("person.location.x (empty for null-location): ");
            Integer x;
            while (true) {
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.equals("")) return null;
                try { x = Integer.parseInt(line); break; } catch (NumberFormatException e) { }
                console.print("person.location.x: ");
            }
            console.print("person.location.y: ");
            int y;
            while (true) {
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try { y = Integer.parseInt(line); break; } catch (NumberFormatException e) { }
                console.print("person.location.y: ");
            }
            console.print("person.location.z: ");
            Integer z;
            while (true) {
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try { z = Integer.parseInt(line); break; } catch (NumberFormatException e) { }
                console.print("person.location.z: ");
            }
            console.print("person.location.name: ");
            String name;
            while (true) {
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.equals("")) { name = null; break; }
                try { name = line; break; } catch (NullPointerException e) { }
                console.print("person.location.name: ");
            }
            return new Location(x, y, z, name);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static Difficulty askDifficulty(Console console) throws AskBreak {
        try {
            console.print("Difficulty ("+Difficulty.names()+"): ");
            Difficulty r;
            while (true) {
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.equals("")) { r = null; break; }
                try { r = Difficulty.valueOf(line); break; } catch (NullPointerException | IllegalArgumentException  e) { }
                console.print("Difficulty: ");
            }
            return r;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static Person askPerson(Console console) throws AskBreak{
        try {
            /*
            private String name; //Поле не может быть null, Строка не может быть пустой
            private java.time.LocalDate birthday; //Поле не может быть null
            private String passportID; //Строка не может быть пустой, Длина строки не должна быть больше 49, Длина строки должна быть не меньше 6, Поле может быть null
            private Location location; //Поле может быть null
            */
            console.print("person.name (empty for null-person): ");
            String name;
            name = console.readln().trim();
            if (name.equals("exit")) throw new AskBreak();
            if (name.equals("")) return null;

            console.print("person.birthday (Example: "+LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)+"): ");
            LocalDate birthday;
            while (true) {
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try { birthday = LocalDate.parse(line, DateTimeFormatter.ISO_LOCAL_DATE); break; } catch (DateTimeParseException e) { }
                try { birthday = LocalDate.parse(line+"T00:00:00.0000", DateTimeFormatter.ISO_DATE_TIME); break; } catch (DateTimeParseException e) { }
                console.print("person.birthday: ");
            }
            console.print("person.passportID: ");
            String passportID;
            while (true) {
                passportID = console.readln().trim();
                if (passportID.equals("exit")) throw new AskBreak();
                if (passportID.equals("")) { passportID = null; break; }
                if (passportID.length() >= 6 && passportID.length() <= 49) break;
                console.print("person.passportID: ");
            }
            Location location = askLocation(console);
            return new Person(name, birthday, passportID,location);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
}

