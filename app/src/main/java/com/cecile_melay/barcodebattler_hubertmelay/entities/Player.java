package com.cecile_melay.barcodebattler_hubertmelay.entities;

/**
 * Created by Utilisateur on 23/10/2017.
 */

public class Player {

    private int id;
    private String name;
    private int nb_wins;
    private int nb_losses;
    private int inventory_max_size;
    private int creature_max_size;

    public Player() {}

    public Player(String name, int nb_wins, int nb_losses, int inventory_max_size, int creature_max_size) {
        this.name = name;
        this.nb_wins = nb_wins;
        this.nb_losses = nb_losses;
        this.inventory_max_size = inventory_max_size;
        this.creature_max_size = creature_max_size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNbWin(int nb_wins) {
        this.nb_wins = nb_wins;
    }

    public void setNbLosses(int nb_losses) {
        this.nb_losses = nb_losses;
    }

    public void setInventoryMaxSize(int inventory_max_size) {
        this.inventory_max_size = inventory_max_size;
    }

    public void setCreatureMaxSize(int creature_max_size) {
        this.creature_max_size = creature_max_size;
    }

    public int getNbWin() {
        return nb_wins;
    }

    public int getNbLosses() {
        return nb_losses;
    }

    public int getInventoryMaxSize() {
        return inventory_max_size;
    }

    public int getCreatureMaxSize() {
        return creature_max_size;
    }

    public String getName() {
        return name;
    }

    /*public int getLevel() {
        return level;
    }*/

    public void setName(String name) {
        this.name = name;
    }

    /*public void setLevel(int level) {
        this.level = level;
    }*/
}
