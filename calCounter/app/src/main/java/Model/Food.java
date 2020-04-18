package Model;

import java.io.Serializable;

public class Food implements Serializable {
    private static final long serialVersionUID = 10L;
    private int id;
    private String name;
    private int calories;
    private String dateAdded;

    public Food(int id, String name, int calories, String dateAdded) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.dateAdded = dateAdded;
    }

    public Food(String name, int calories, String dateAdded) {
        this.name = name;
        this.calories = calories;
        this.dateAdded = dateAdded;
    }

    public Food() {
    }

    public static long getSerialisationVer() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}
