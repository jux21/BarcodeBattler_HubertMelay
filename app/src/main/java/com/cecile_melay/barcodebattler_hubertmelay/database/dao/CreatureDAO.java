package com.cecile_melay.barcodebattler_hubertmelay.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cecile_melay.barcodebattler_hubertmelay.database.DatabaseHandler;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Creature;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jux on 24/10/2017.
 */

public class CreatureDAO {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "creature.db";

    private static final String CREATURE_TABLE_NAME = DatabaseHandler.CREATURE_TABLE_NAME;
    private static final String COL_CREATURE_ID = DatabaseHandler.CREATURE_ID;
    private static final int NUM_COL_CREATURE_ID = 0;
    private static final String COL_CREATURE_NAME = DatabaseHandler.CREATURE_NAME;
    private static final int NUM_COL_CREATURE_NAME = 1;
    private static final String COL_CREATURE_HP = DatabaseHandler.CREATURE_HP;
    private static final int NUM_COL_CREATURE_HP = 2;
    private static final String COL_CREATURE_TYPE = DatabaseHandler.CREATURE_TYPE;
    private static final int NUM_COL_CREATURE_TYPE = 3;
    private static final String COL_CREATURE_INVENTORY_MAX_SIZE = DatabaseHandler.CREATURE_INVENTORY_MAX_SIZE;
    private static final int NUM_COL_CREATURE_INVENTORY_MAX_SIZE = 4;
    private static final String COL_CREATURE_SIZE = DatabaseHandler.CREATURE_SIZE;
    private static final int NUM_COL_CREATURE_SIZE = 5;
    private static final String COL_CREATURE_WEIGHT = DatabaseHandler.CREATURE_WEIGHT;
    private static final int NUM_COL_CREATURE_WEIGHT = 6;
    private static final String COL_CREATURE_SPEED = DatabaseHandler.CREATURE_SPEED;
    private static final int NUM_COL_CREATURE_SPEED = 7;
    private static final String COL_CREATURE_STRENGTH = DatabaseHandler.CREATURE_STRENGTH;
    private static final int NUM_COL_CREATURE_STRENGTH = 8;
    private static final String COL_CREATURE_DEFENSE = DatabaseHandler.CREATURE_DEFENSE;
    private static final int NUM_COL_CREATURE_DEFENSE = 9;

    private SQLiteDatabase bdd;
    private DatabaseHandler mySQLiteBase;

    public CreatureDAO(Context context){
        //On crée la BDD et sa table
        mySQLiteBase = new DatabaseHandler(context, DB_NAME, null, DB_VERSION);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = mySQLiteBase.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertCreature(Creature creature){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_CREATURE_NAME, creature.getName());
        values.put(COL_CREATURE_HP, creature.getHp());
        values.put(COL_CREATURE_TYPE, creature.getType());
        values.put(COL_CREATURE_INVENTORY_MAX_SIZE, creature.getInventory_max_size());
        values.put(COL_CREATURE_SIZE, creature.getSize());
        values.put(COL_CREATURE_WEIGHT, creature.getWeight());
        values.put(COL_CREATURE_SPEED, creature.getSpeed());
        values.put(COL_CREATURE_STRENGTH, creature.getStrength());
        values.put(COL_CREATURE_DEFENSE, creature.getDefense());

        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(CREATURE_TABLE_NAME, null, values);
    }

    public int updateCreature(int id, Creature creature){
        //La mise à jour d'une creature dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quelle créature on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_CREATURE_NAME, creature.getName());
        values.put(COL_CREATURE_HP, creature.getHp());
        values.put(COL_CREATURE_TYPE, creature.getType());
        values.put(COL_CREATURE_INVENTORY_MAX_SIZE, creature.getInventory_max_size());
        values.put(COL_CREATURE_SIZE, creature.getSize());
        values.put(COL_CREATURE_WEIGHT, creature.getWeight());
        values.put(COL_CREATURE_SPEED, creature.getSpeed());
        values.put(COL_CREATURE_STRENGTH, creature.getStrength());
        values.put(COL_CREATURE_DEFENSE, creature.getDefense());

        return bdd.update(CREATURE_TABLE_NAME, values, COL_CREATURE_ID + " = " +id, null);
    }

    public int removeCreatureWithID(int id){
        //Suppression d'une créature de la BDD grâce à l'ID
        return bdd.delete(CREATURE_TABLE_NAME, COL_CREATURE_ID + " = " +id, null);
    }

    public Creature getCreatureWithName(String name){
        //Récupère dans un Cursor les valeurs correspondant à une créature contenue dans la BDD
        Cursor c = bdd.query(CREATURE_TABLE_NAME,
                new String[] {COL_CREATURE_ID, COL_CREATURE_NAME, COL_CREATURE_HP,COL_CREATURE_TYPE, COL_CREATURE_INVENTORY_MAX_SIZE,
                COL_CREATURE_SIZE,COL_CREATURE_WEIGHT, COL_CREATURE_SPEED, COL_CREATURE_STRENGTH, COL_CREATURE_DEFENSE},
                COL_CREATURE_NAME + " LIKE \"" + name +"\"", null, null, null, null);
        return cursorToCreature(c);
    }

    public List<Creature> getAllCreature(){
        Cursor cursor = bdd.rawQuery("select * from "+CREATURE_TABLE_NAME,null);
        return cursorToCreatures(cursor);
    }

    public ArrayList<String> creaturesToString(List<Creature> creatures) {
        ArrayList<String> creaturesString = new ArrayList<String>();
        for (int i = 0; i < creatures.size(); i++) {
            creaturesString.add(creatures.get(i).toString());
        }
        return creaturesString;
    }

    //Cette méthode permet de convertir un cursor en un Creature
    private Creature cursorToCreature(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé une Creature
        Creature creature = new Creature();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        creature.setId(c.getInt(NUM_COL_CREATURE_ID));
        creature.setName(c.getString(NUM_COL_CREATURE_NAME));
        creature.setHp(c.getInt(NUM_COL_CREATURE_HP));
        creature.setName(c.getString(NUM_COL_CREATURE_NAME));
        creature.setType(c.getString(NUM_COL_CREATURE_TYPE));
        creature.setInventory_max_size(c.getInt(NUM_COL_CREATURE_INVENTORY_MAX_SIZE));
        creature.setSize(c.getInt(NUM_COL_CREATURE_SIZE));
        creature.setWeight(c.getInt(NUM_COL_CREATURE_WEIGHT));
        creature.setSpeed(c.getInt(NUM_COL_CREATURE_SPEED));
        creature.setStrength(c.getInt(NUM_COL_CREATURE_STRENGTH));
        creature.setDefense(c.getInt(NUM_COL_CREATURE_DEFENSE));

        //On ferme le cursor
        c.close();

        //On retourne le livre
        return creature;
    }

    private List<Creature> cursorToCreatures(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        List<Creature> listCreatures = new ArrayList<Creature>();
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                //On créé une Creature
                Creature creature = new Creature();
                //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
                creature.setId(c.getInt(NUM_COL_CREATURE_ID));
                creature.setName(c.getString(NUM_COL_CREATURE_NAME));
                creature.setHp(c.getInt(NUM_COL_CREATURE_HP));
                creature.setName(c.getString(NUM_COL_CREATURE_NAME));
                creature.setType(c.getString(NUM_COL_CREATURE_TYPE));
                creature.setInventory_max_size(c.getInt(NUM_COL_CREATURE_INVENTORY_MAX_SIZE));
                creature.setSize(c.getInt(NUM_COL_CREATURE_SIZE));
                creature.setWeight(c.getInt(NUM_COL_CREATURE_WEIGHT));
                creature.setSpeed(c.getInt(NUM_COL_CREATURE_SPEED));
                creature.setStrength(c.getInt(NUM_COL_CREATURE_STRENGTH));
                creature.setDefense(c.getInt(NUM_COL_CREATURE_DEFENSE));

                listCreatures.add(creature);
                c.moveToNext();
            }
        }

        //On ferme le cursor
        c.close();

        //On retourne le livre
        return listCreatures;
    }

}
