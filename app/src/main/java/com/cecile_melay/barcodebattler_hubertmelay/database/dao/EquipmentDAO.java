package com.cecile_melay.barcodebattler_hubertmelay.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cecile_melay.barcodebattler_hubertmelay.database.DatabaseHandler;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Equipment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jux on 26/10/2017.
 */

public class EquipmentDAO {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "equipment.db";

    private static final String EQUIPMENT_TABLE_NAME = DatabaseHandler.EQUIPMENT_TABLE_NAME;
    private static final String COL_EQUIPMENT_ID = DatabaseHandler.EQUIPMENT_ID;
    private static final int NUM_COL_EQUIPMENT_ID = 0;
    private static final String COL_EQUIPMENT_NAME = DatabaseHandler.EQUIPMENT_NAME;
    private static final int NUM_COL_EQUIPMENT_NAME = 1;
    private static final String COL_EQUIPMENT_TYPE = DatabaseHandler.EQUIPMENT_TYPE;
    private static final int NUM_COL_EQUIPMENT_TYPE = 2;
    private static final String COL_EQUIPMENT_BONUS = DatabaseHandler.EQUIPMENT_BONUS;
    private static final int NUM_COL_EQUIPMENT_BONUS = 3;

    private SQLiteDatabase bdd;
    private DatabaseHandler mySQLiteBase;

    public EquipmentDAO(Context context){
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

    public long insertEquipment(Equipment equipment){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_EQUIPMENT_NAME, equipment.getName());
        values.put(COL_EQUIPMENT_TYPE, equipment.getType());
        values.put(COL_EQUIPMENT_BONUS, equipment.getBonus());

        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(EQUIPMENT_TABLE_NAME, null, values);
    }

    public int updateEquipment(int id, Equipment equipment){
        //La mise à jour d'un equipment dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel equipment on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_EQUIPMENT_NAME, equipment.getName());
        values.put(COL_EQUIPMENT_TYPE, equipment.getType());
        values.put(COL_EQUIPMENT_BONUS, equipment.getBonus());

        return bdd.update(EQUIPMENT_TABLE_NAME, values, COL_EQUIPMENT_ID + " = " +id, null);
    }

    public int removeEquipmentWithID(int id){
        //Suppression d'un equipment de la BDD grâce à l'ID
        return bdd.delete(EQUIPMENT_TABLE_NAME, COL_EQUIPMENT_ID + " = " +id, null);
    }

    public Equipment getEquipmentWithName(String name){
        //Récupère dans un Cursor les valeurs correspondant à un equipment contenu dans la BDD
        Cursor c = bdd.query(EQUIPMENT_TABLE_NAME,
                new String[] {COL_EQUIPMENT_ID, COL_EQUIPMENT_NAME, COL_EQUIPMENT_TYPE,COL_EQUIPMENT_BONUS},
                COL_EQUIPMENT_NAME + " LIKE \"" + name +"\"", null, null, null, null);
        return cursorToEquipment(c);
    }

    public List<Equipment> getAllEquipment(){
        Cursor cursor = bdd.rawQuery("select * from "+EQUIPMENT_TABLE_NAME,null);
        return cursorToEquipments(cursor);
    }

    public ArrayList<String> equipmentToString(List<Equipment> equipments) {
        ArrayList<String> equipmentString = new ArrayList<String>();
        for (int i = 0; i < equipments.size(); i++) {
            equipmentString.add(equipments.get(i).toString());
        }
        return equipmentString;
    }

    //Cette méthode permet de convertir un cursor en un equipment
    private Equipment cursorToEquipment(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un equipment
        Equipment equipment = new Equipment();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        equipment.setId(c.getInt(NUM_COL_EQUIPMENT_ID));
        equipment.setName(c.getString(NUM_COL_EQUIPMENT_NAME));
        equipment.setType(c.getString(NUM_COL_EQUIPMENT_TYPE));
        equipment.setBonus(c.getInt(NUM_COL_EQUIPMENT_BONUS));

        //On ferme le cursor
        c.close();

        //On retourne le livre
        return equipment;
    }

    private List<Equipment> cursorToEquipments(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        List<Equipment> listEquipments = new ArrayList<Equipment>();
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                //On créé un equipment
                Equipment equipment = new Equipment();
                //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
                equipment.setId(c.getInt(NUM_COL_EQUIPMENT_ID));
                equipment.setName(c.getString(NUM_COL_EQUIPMENT_NAME));
                equipment.setType(c.getString(NUM_COL_EQUIPMENT_TYPE));
                equipment.setBonus(c.getInt(NUM_COL_EQUIPMENT_BONUS));

                listEquipments.add(equipment);
                c.moveToNext();
            }
        }

        //On ferme le cursor
        c.close();

        //On retourne le livre
        return listEquipments;
    }

}
