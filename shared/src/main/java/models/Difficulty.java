package models;

import java.io.Serializable;

public enum Difficulty implements Serializable {
    /**
     * Перечисление степеней трудности
     */
    VERY_EASY,
    NORMAL,
    HOPELESS,
    TERRIBLE;

    /**
     * @return строка со всеми элементами enum через запятую
     */
    public static String names(){
        StringBuilder nameList = new StringBuilder();
        for (var Difficulty : values()) {
            nameList.append(Difficulty.name()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }
}
