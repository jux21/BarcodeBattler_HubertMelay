package com.cecile_melay.barcodebattler_hubertmelay.entities;

/**
 * Created by Utilisateur on 24/10/2017.
 */

public class Potion {

    private int id;
    private String name;
    private String type;
    private int bonus;

    public Potion() {}

    public Potion(String name, String type, int bonus) {
        this.name = name;
        this.type = type;
        this.bonus = bonus;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public String toString() {
        return "ID : " + id + "\nName : " + name + "\nType : " + type + "\nBonus : " + bonus;
    }
}
