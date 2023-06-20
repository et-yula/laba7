package models;

import utility.Validatable;

import java.io.Serializable;
import java.util.Objects;

public class Coordinates implements Validatable, Serializable {
    /**
     * класс координат
     */
    private Integer x; //Поле не может быть null
    private Long y; //Поле не может быть null
    public Coordinates(Integer x, Long y){
        this.x=x;
        this.y=y;
    }

    public Integer getX() {
        return x;
    }

    public Long getY() {
        return y;
    }

    public Coordinates(String s){
        try {
            try { this.x = Integer.parseInt(s.split(";")[0]); } catch (NumberFormatException e) { }
            try { this.y = Long.parseLong(s.split(";")[1]); } catch (NumberFormatException e) { }
        } catch (ArrayIndexOutOfBoundsException e) {}
    }
    /**
     * @return true, если поля удовлетворяют условиям, иначе false
     */
    public boolean validate(){
        if (x==null){
            return false;
        }
        return y!=null;
    }
    @Override
    public String toString(){
        return x+";"+y;
    }
    @Override
    public int hashCode(){
        return Objects.hash(x,y);
    }
    @Override
    public boolean equals(Object o){
        if (this==o){
            return true;
        }
        if ((o==null)|(this.getClass()!=o.getClass())){
            return false;
        }
        Coordinates that=(Coordinates) o;
        return Objects.equals(x, that.x)&&Objects.equals(y,that.y);
    }
}


