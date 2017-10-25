package com.cecile_melay.barcodebattler_hubertmelay.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jux on 23/10/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // Stucture des tables
    public static final String CREATURE_TABLE_NAME = "creature",
            CREATURE_ID = "id",
            CREATURE_NAME = "name",
            CREATURE_HP = "hp",
            CREATURE_TYPE = "type",
            CREATURE_INVENTORY_MAX_SIZE = "inventory_max_size",
            CREATURE_SIZE = "size",
            CREATURE_WEIGHT = "weight",
            CREATURE_SPEED = "speed",
            CREATURE_STRENGTH = "strength",
            CREATURE_DEFENSE = "defense";

    public static final String EQUIPMENT_TABLE_NAME = "equipment",
            EQUIPMENT_ID = "id",
            EQUIPMENT_NAME = "name",
            EQUIPMENT_TYPE = "type",
            EQUIPMENT_BONUS = "bonus";


    public static final String EQUIPMENT_PER_CREATURE_TABLE_NAME = "equipment_per_creature",
            EQUIPMENT_PER_CREATURE_CREATURE_ID = "id_creature",
            EQUIPMENT_PER_CREATURE_EQUIPMENT_ID = "id_equipment";


    public static final String POTION_TABLE_NAME = "potion",
            POTION_ID = "id",
            POTION_NAME = "name",
            POTION_TYPE = "type",
            POTION_BONUS = "bonus";

    /*public static final String BAG_TABLE_NAME = "potion",
            BAG_POTION_ID = "id_potion",
            BAG_EQUIPMENT_ID = "id_equipment";*/


    public static final String PLAYER_TABLE_NAME = "player",
            PLAYER_ID = "id",
            PLAYER_NAME = "name",
            PLAYER_NB_WINS = "nb_wins",
            PLAYER_NB_LOSSES = "nb_losses",
            PLAYER_INVENTORY_MAX_SIZE = "inventory_max_size",
            PLAYER_CREATURE_MAX_SIZE = "creatures_max_size";


    // Script de création/suppression des tables
    public static final String CREATURE_TABLE_CREATE =
            "CREATE TABLE " + CREATURE_TABLE_NAME + " (" +
                    CREATURE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CREATURE_NAME + " TEXT, " +
                    CREATURE_HP + " INTEGER, " +
                    CREATURE_TYPE + " TEXT, " +
                    CREATURE_INVENTORY_MAX_SIZE + " INTEGER, " +
                    CREATURE_SIZE + " INTEGER, " +
                    CREATURE_WEIGHT + " INTEGER, " +
                    CREATURE_SPEED + " INTEGER, " +
                    CREATURE_STRENGTH + " INTEGER, " +
                    CREATURE_DEFENSE + " INTEGER );";
    public static final String CREATURE_TABLE_DROP = "DROP TABLE IF EXISTS " + CREATURE_TABLE_NAME + ";";

    public static final String EQUIPMENT_TABLE_CREATE =
            "CREATE TABLE " + EQUIPMENT_TABLE_NAME + " (" +
                    EQUIPMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    EQUIPMENT_NAME + " TEXT, " +
                    EQUIPMENT_TYPE + " TEXT, " +
                    EQUIPMENT_BONUS + " INTEGER );";
    public static final String EQUIPMENT_TABLE_DROP = "DROP TABLE IF EXISTS " + EQUIPMENT_TABLE_NAME + ";";

    public static final String EQUIPMENT_PER_CREATURE_TABLE_CREATE =
            "CREATE TABLE " + EQUIPMENT_PER_CREATURE_TABLE_NAME + " (" +
                    EQUIPMENT_PER_CREATURE_CREATURE_ID + " INTEGER, " +
                    EQUIPMENT_PER_CREATURE_EQUIPMENT_ID + " INTEGER, "+
                    "FOREIGN KEY ("+EQUIPMENT_PER_CREATURE_CREATURE_ID+") REFERENCES "+CREATURE_TABLE_NAME+"("+CREATURE_ID+")," +
                    "FOREIGN KEY ("+EQUIPMENT_PER_CREATURE_EQUIPMENT_ID+") REFERENCES "+EQUIPMENT_TABLE_NAME+"("+EQUIPMENT_ID+"));";
    public static final String EQUIPMENT_PER_CREATURE_TABLE_DROP = "DROP TABLE IF EXISTS " + EQUIPMENT_PER_CREATURE_TABLE_NAME + ";";

    public static final String POTION_TABLE_CREATE =
            "CREATE TABLE " + POTION_TABLE_NAME + " (" +
                    POTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    POTION_NAME + " TEXT, " +
                    POTION_TYPE + " TEXT, " +
                    POTION_BONUS + " INTEGER );";
    public static final String POTION_TABLE_DROP = "DROP TABLE IF EXISTS " + POTION_TABLE_NAME + ";";

    public static final String PLAYER_TABLE_CREATE =
            "CREATE TABLE " + PLAYER_TABLE_NAME + " (" +
                    PLAYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PLAYER_NAME + " TEXT, " +
                    PLAYER_NB_WINS + " INTEGER, " +
                    PLAYER_NB_LOSSES + " INTEGER, "+
                    PLAYER_INVENTORY_MAX_SIZE + "INTEGER, " +
                    PLAYER_CREATURE_MAX_SIZE + "INTEGER );";
    public static final String PLAYER_TABLE_DROP = "DROP TABLE IF EXISTS " + PLAYER_TABLE_NAME + ";";



    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATURE_TABLE_CREATE);
        db.execSQL(EQUIPMENT_TABLE_CREATE);
        db.execSQL(EQUIPMENT_PER_CREATURE_TABLE_CREATE);
        db.execSQL(POTION_TABLE_CREATE);
        db.execSQL(PLAYER_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CREATURE_TABLE_DROP);
        db.execSQL(EQUIPMENT_TABLE_DROP);
        db.execSQL(EQUIPMENT_PER_CREATURE_TABLE_DROP);
        db.execSQL(POTION_TABLE_DROP);
        db.execSQL(PLAYER_TABLE_DROP);
        onCreate(db);
    }
}

