package com.cecile_melay.barcodebattler_hubertmelay.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jux on 23/10/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

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

    public static final String BAG_TABLE_NAME = "potion",
            BAG_POTION_ID = "id_potion",
            BAG_EQUIPMENT_ID = "id_equipment";


    public static final String PLAYER_TABLE_NAME = "player",
            PLAYER_ID = "id",
            PLAYER_NAME = "name",
            PLAYER_NB_WINS = "nb_wins",
            PLAYER_NB_LOSSES = "nb_losses",
            PLAYER_INVENTORY_MAX_SIZE = "inventory_max_size",
            PLAYER_CREATURE_MAX_SIZE = "creatures_max_size";

    



    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

