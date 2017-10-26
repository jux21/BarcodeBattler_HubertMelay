package com.cecile_melay.barcodebattler_hubertmelay.entities;

/**
 * Created by Utilisateur on 23/10/2017.
 */

public class Player {

    private  String name;
    private int level;
    private int nbWin;
    private int nbLosses;
    private int inventoryMaxSize;
    private int creatureMaxSize;

    public Player() {}

    public Player(String name, int nbWin, int nbLosses, int inventoryMaxSize, int creatureMaxSize) {
        this.name = name;
        this.nbWin = nbWin;
        this.nbLosses = nbLosses;
        this.inventoryMaxSize = inventoryMaxSize;
        this.creatureMaxSize = creatureMaxSize;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNbWin(int nbWin) {
        this.nbWin = nbWin;
    }

    public void setNbLosses(int nbLosses) {
        this.nbLosses = nbLosses;
    }

    public void setInventoryMaxSize(int inventoryMaxSize) {
        this.inventoryMaxSize = inventoryMaxSize;
    }

    public void setCreatureMaxSize(int creatureMaxSize) {
        this.creatureMaxSize = creatureMaxSize;
    }

    public int getNbWin() {
        return nbWin;
    }

    public int getNbLosses() {
        return nbLosses;
    }

    public int getInventoryMaxSize() {
        return inventoryMaxSize;
    }

    public int getCreatureMaxSize() {
        return creatureMaxSize;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
