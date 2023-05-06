package models;

import utility.Validatable;

import java.io.Serializable;
import java.util.Objects;

public class Location implements Validatable, Serializable {
    /**
     * класс локации
     */
    private Integer x; //Поле не может быть null
    private int y;
    private Integer z; //Поле не может быть null
    private String name; //Поле может быть null
    public Location(Integer x, int y, Integer z, String name){
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }
    public Location(String s){
        try {
            try { this.x = Integer.parseInt(s.split(";")[0]); } catch (NumberFormatException e) { }
            try { this.y = Integer.parseInt(s.split(";")[1]); } catch (NumberFormatException e) { }
            try { this.z = Integer.parseInt(s.split(";")[2]); } catch (NumberFormatException e) { }
            try { this.name = s.split(";")[3].equals("null") ? null : s.split(";")[3]; } catch (NullPointerException e) { }
        } catch (ArrayIndexOutOfBoundsException e) {}
    }
    /**
     * @return true, если поля удовлетворяют условиям, иначе false
     */
    public boolean validate(){
        if(x == null || z == null) {
            return false;
        }
        return true;
    }

    public Integer getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Integer getZ() {
        return z;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return x+";"+y+";"+z+";"+(name==null?"null":name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return y == location.y && x.equals(location.x) && z.equals(location.z) && name.equals(location.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, name);
    }
}
