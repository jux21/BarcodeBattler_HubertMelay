package com.cecile_melay.barcodebattler_hubertmelay.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cecile_melay.barcodebattler_hubertmelay.database.DatabaseHandler;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Potion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jux on 26/10/2017.
 */

public class PotionDAO {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "potion.db";

    private static final String POTION_TABLE_NAME = DatabaseHandler.POTION_TABLE_NAME;
    private static final String COL_POTION_ID = DatabaseHandler.POTION_ID;
    private static final int NUM_COL_POTION_ID = 0;
    private static final String COL_POTION_NAME = DatabaseHandler.POTION_NAME;
    private static final int NUM_COL_POTION_NAME = 1;
    private static final String COL_POTION_TYPE = DatabaseHandler.POTION_TYPE;
    private static final int NUM_COL_POTION_TYPE = 2;
    private static final String COL_POTION_BONUS = DatabaseHandler.POTION_BONUS;
    private static final int NUM_COL_POTION_BONUS = 3;

    private SQLiteDatabase bdd;
    private DatabaseHandler mySQLiteBase;

    public PotionDAO(Context context){
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

    public long insertPotion(Potion potion){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_POTION_NAME, potion.getName());
        values.put(COL_POTION_TYPE, potion.getType());
        values.put(COL_POTION_BONUS, potion.getBonus());

        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(POTION_TABLE_NAME, null, values);
    }

    public int updatePotion(int id, Potion potion){
        //La mise à jour d'une potion dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quelle potion on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_POTION_NAME, potion.getName());
        values.put(COL_POTION_TYPE, potion.getType());
        values.put(COL_POTION_BONUS, potion.getBonus());

        return bdd.update(POTION_TABLE_NAME, values, COL_POTION_ID + " = " +id, null);
    }

    public int removePotionWithID(int id){
        //Suppression d'une potion de la BDD grâce à l'ID
        return bdd.delete(POTION_TABLE_NAME, COL_POTION_ID + " = " +id, null);
    }

    public Potion getPotionWithName(String name){
        //Récupère dans un Cursor les valeurs correspondant à une potion contenue dans la BDD
        Cursor c = bdd.query(POTION_TABLE_NAME,
                new String[] {COL_POTION_ID, COL_POTION_NAME, COL_POTION_TYPE,COL_POTION_BONUS},
                COL_POTION_NAME + " LIKE \"" + name +"\"", null, null, null, null);
        return cursorToPotion(c);
    }

    public List<Potion> getAllPotion(){
        Cursor cursor = bdd.rawQuery("select * from "+POTION_TABLE_NAME,null);
        return cursorToPotions(cursor);
    }

    public ArrayList<String> potionToString(List<Potion> potions) {
        ArrayList<String> potionString = new ArrayList<String>();
        for (int i = 0; i < potions.size(); i++) {
            potionString.add(potions.get(i).toString());
        }
        return potionString;
    }

    //Cette méthode permet de convertir un cursor en une potion
    private Potion cursorToPotion(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé une potion
        Potion potion = new Potion();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        potion.setId(c.getInt(NUM_COL_POTION_ID));
        potion.setName(c.getString(NUM_COL_POTION_NAME));
        potion.setType(c.getString(NUM_COL_POTION_TYPE));
        potion.setBonus(c.getInt(NUM_COL_POTION_BONUS));

        //On ferme le cursor
        c.close();

        //On retourne le livre
        return potion;
    }

    private List<Potion> cursorToPotions(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        List<Potion> listPotions = new ArrayList<Potion>();
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                //On créé un equipment
                Potion equipment = new Potion();
                //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
                equipment.setId(c.getInt(NUM_COL_POTION_ID));
                equipment.setName(c.getString(NUM_COL_POTION_NAME));
                equipment.setType(c.getString(NUM_COL_POTION_TYPE));
                equipment.setBonus(c.getInt(NUM_COL_POTION_BONUS));

                listPotions.add(equipment);
                c.moveToNext();
            }
        }

        //On ferme le cursor
        c.close();

        //On retourne le livre
        return listPotions;
    }
}
