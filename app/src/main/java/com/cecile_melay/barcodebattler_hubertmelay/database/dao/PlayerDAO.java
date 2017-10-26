package com.cecile_melay.barcodebattler_hubertmelay.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cecile_melay.barcodebattler_hubertmelay.database.DatabaseHandler;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Creature;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Utilisateur on 26/10/2017.
 */

public class PlayerDAO {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "player.db";

    private static final String PLAYER_TABLE_NAME = DatabaseHandler.PLAYER_TABLE_NAME;
    private static final String COL_PLAYER_ID = DatabaseHandler.PLAYER_ID;
    private static final int NUM_COL_PLAYER_ID = 0;
    private static final String COL_PLAYER_NAME = DatabaseHandler.PLAYER_NAME;
    private static final int NUM_COL_PLAYER_NAME = 1;
    private static final String COL_PLAYER_NB_WINS = DatabaseHandler.PLAYER_NB_WINS;
    private static final int NUM_COL_PLAYER_WINS = 2;
    private static final String COL_PLAYER_NB_LOSSES = DatabaseHandler.PLAYER_NB_LOSSES;
    private static final int NUM_COL_PLAYER_LOSSES = 3;
    private static final String COL_PLAYER_INVENTORY_MAX_SIZE = DatabaseHandler.PLAYER_INVENTORY_MAX_SIZE;
    private static final int NUM_COL_PLAYER_INVENTORY_MAX_SIZE = 4;
    private static final String COL_PLAYER_CREATURE_MAX_SIZE = DatabaseHandler.PLAYER_CREATURE_MAX_SIZE;
    private static final int NUM_COL_PLAYER_CREATURE_MAX_SIZE = 5;

    private SQLiteDatabase bdd;
    private DatabaseHandler mySQLiteBase;

    public PlayerDAO(Context context){
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

    public long insertPlayer(Player player){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_PLAYER_NAME, player.getName());
        values.put(COL_PLAYER_NB_WINS, player.getNbWin());
        values.put(COL_PLAYER_NB_LOSSES, player.getNbLosses());
        values.put(COL_PLAYER_INVENTORY_MAX_SIZE, player.getInventoryMaxSize());
        values.put(COL_PLAYER_CREATURE_MAX_SIZE, player.getCreatureMaxSize());

        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(PLAYER_TABLE_NAME, null, values);
    }

    public int updatePlayer(int id, Player player){
        //La mise à jour d'une creature dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quelle créature on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_PLAYER_NAME, player.getName());
        values.put(COL_PLAYER_NB_WINS, player.getNbWin());
        values.put(COL_PLAYER_NB_LOSSES, player.getNbLosses());
        values.put(COL_PLAYER_INVENTORY_MAX_SIZE, player.getInventoryMaxSize());
        values.put(COL_PLAYER_CREATURE_MAX_SIZE, player.getCreatureMaxSize());

        return bdd.update(PLAYER_TABLE_NAME, values, COL_PLAYER_ID + " = " +id, null);
    }

    public int removePlayerWithID(int id){
        //Suppression d'une créature de la BDD grâce à l'ID
        return bdd.delete(PLAYER_TABLE_NAME, COL_PLAYER_ID + " = " +id, null);
    }

    public List<Player> getAllPlayers(){
        Cursor cursor = bdd.rawQuery("select * from "+PLAYER_TABLE_NAME,null);
        return cursorToPlayers(cursor);
    }


    private List<Player> cursorToPlayers(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        List<Player> listPlayers = new ArrayList<Player>();
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                Player player = new Player();
                //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
                player.setId(c.getInt(NUM_COL_PLAYER_ID));
                player.setName(c.getString(NUM_COL_PLAYER_NAME));
                player.setNbWin(c.getInt(NUM_COL_PLAYER_WINS));
                player.setNbLosses(c.getInt(NUM_COL_PLAYER_LOSSES));
                player.setCreatureMaxSize(c.getInt(NUM_COL_PLAYER_CREATURE_MAX_SIZE));
                player.setInventoryMaxSize(c.getInt(NUM_COL_PLAYER_INVENTORY_MAX_SIZE));
                listPlayers.add(player);
                c.moveToNext();
            }
        }

        //On ferme le cursor
        c.close();

        //On retourne le livre
        return listPlayers;
    }

    public ArrayList<String> playersToString(List<Player> players) {
        ArrayList<String> creaturesString = new ArrayList<String>();
        for (int i = 0; i < players.size(); i++) {
            creaturesString.add(players.get(i).toString());
        }
        return creaturesString;
    }



}
