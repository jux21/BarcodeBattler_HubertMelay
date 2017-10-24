package com.cecile_melay.barcodebattler_hubertmelay.entities;

/**
 * Created by Utilisateur on 24/10/2017.
 */

public class Creature {

    private int id;
    private String name;
    private int hp;
    private String type;
    private int inventory_max_size;
    private int size;
    private int weight;
    private int speed;  // Calculated with size - weight
    private int strength;  // Calculated with size and weight
    private int defense;


    public Creature() {}

    public Creature(String name, int hp, String type, int inventory_max_size, int size, int weight, int defense ) {
        this.name = name;
        this.hp = hp;
        this.type = type;
        this.inventory_max_size = inventory_max_size;
        this.size = size;
        this.weight = weight;
        this.defense = defense;
        this.speed = this.size - this.weight;
        this.strength = this.size - this.weight;
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getInventory_max_size() {
        return inventory_max_size;
    }

    public void setInventory_max_size(int inventory_max_size) {
        this.inventory_max_size = inventory_max_size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public String toString() {
        return "ID : " + id + "\nName : " + name + "\nHeath point : " + hp + "\nType : " + type + "\nInventory max size : " +
                inventory_max_size + "\nSize : " + size + "\nWeight : " + weight + "\nSpeed : " + speed + "\nType : "
                + type + "\nStrength: " + strength + "\nDefense: " + defense;
    }


}
