package com.cecile_melay.barcodebattler_hubertmelay.entities;

import com.cecile_melay.barcodebattler_hubertmelay.R;

/**
 * Created by Utilisateur on 24/10/2017.
 */

public class Creature {

    private int id;
    private String name;
    private Double hp;
    private String type;
    private int inventory_max_size;
    private int size;
    private int weight;
    private int speed;  // Calculated with size - weight
    private int strength;  // Calculated with (size + weight) / 2
    private int defense;
    private Double nbWin;
    private Double nbLoss;
    private int imagePath;



    public Creature() {}

    public Creature(String name, Double hp, String type, int inventory_max_size, int size, int weight, int defense) {
        this.name = name;
        this.hp = hp;
        this.type = type;
        this.inventory_max_size = inventory_max_size;
        this.size = size;
        this.weight = weight;
        this.defense = defense;
        this.speed = this.size - this.weight;
        this.strength = (this.size + this.weight) / 2;
        this.nbWin = 0.0;
        this.nbLoss = 0.0;
        this.imagePath = pickImage(this.type);
    }

    public int pickImage(String type) {
        int imagePath;
        switch(type) {
            case "feu":
                imagePath = R.mipmap.creature_fire_1;
                break;
            case "glace":
                imagePath = R.mipmap.creature_ice_1;
                break;
            case "électrique":
                imagePath = R.mipmap.creature_elec_1;
                break;
            case "roche":
                imagePath = R.mipmap.creature_rock_1;
                break;
            case "plante":
                imagePath = R.mipmap.creature_grass_1;;
                break;
            case "air":
                imagePath = R.mipmap.creature_cat;
                break;
            case "dragon":
                imagePath = R.mipmap.creature_dragon_1;
                break;
            case "insecte":
                imagePath = R.mipmap.creature_insect_1;
                break;
            case "terre":
                imagePath = R.mipmap.creature_earth_1;
                break;
            case "eau":
                imagePath = R.mipmap.creature_water_1;
                break;
            default:
                imagePath = R.mipmap.creature_cat;
                break;

        }
        return imagePath;
    }

    //}

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

    public Double getHp() {
        return hp;
    }

    public void setHp(Double hp) {
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

    public Double getNbWin() {
        return nbWin;
    }

    public void setNbWin(Double nbWin) {
        this.nbWin = nbWin;
    }

    public Double getNbLoss() {
        return nbLoss;
    }

    public void setNbLoss(Double nbLoss) {
        this.nbLoss = nbLoss;
    }

    public String toString() {
        return "ID : " + id + "\nName : " + name + "\nHeath point : " + hp + "\nType : " + type + "\nInventory max size : " +
                inventory_max_size + "\nSize : " + size + "\nWeight : " + weight + "\nSpeed : " + speed + "\nType : "
                + type + "\nStrength: " + strength + "\nDefense: " + defense + "\nnbWin: " + nbWin + "\nnbLoss: " + nbLoss;
    }

    public int getImagePath() {
        return pickImage(this.type);
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }
}
