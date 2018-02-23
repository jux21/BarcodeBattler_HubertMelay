package com.cecile_melay.barcodebattler_hubertmelay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.cecile_melay.barcodebattler_hubertmelay.database.dao.CreatureDAO;
import com.cecile_melay.barcodebattler_hubertmelay.database.dao.EquipmentDAO;
import com.cecile_melay.barcodebattler_hubertmelay.database.dao.PotionDAO;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Creature;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Equipment;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Potion;
import com.cecile_melay.barcodebattler_hubertmelay.fragments.views.DisplayCreature;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Utilisateur on 24/10/2017.
 */

public class EntityCatch extends Activity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView ScannerView;
    static Creature creature;
    static Potion potion;
    static Equipment equipment;
    private static List<String> creatureNames = new ArrayList<String>(Arrays.asList("Ushug","Bugdul","Klughig","Bernard","Seepig","Djent","Ako","Anubis","Wided","Rachid,","Garman","Pestana","Heishman","Frankum","Tamiko","Mowbray","Taketa","Crossman","Ora Yun"));
    private static List<String> creatureTitre = new ArrayList<String>(Arrays.asList(" l'impétueux"," le sage"," le diplomate"," le destructeur"," le repenti"," le charmeur"," le serviteur"," le traitre"," le développeur Web"," le Data Scientist"," du MBDS"," le conquérant"," l'incroyable"," du royaume"," le puant"," le terrible"," le fort"," le grand"," le magnifique"," le turbulent"));
    /* elixir + */ private static List<String> potionNames = new ArrayList<String>(Arrays.asList("de victoire","de resurgence","de vitalité","maudit","des dieux","du prophète","de resurrection"));
    private static List<String> equipmentNames = new ArrayList<String>(Arrays.asList("ceinture des cieux","cape de feu","bouclier du soleil","armure des abysses","demacia","épée de feu","faux de la mort","couteau esprit","bonbon enchanté","caramel doré"));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entity_catch);
        QRCScanner(this.ScannerView);
    }

    public void QRCScanner (View view){
        ScannerView = new ZXingScannerView(this);   //  initialise le scanner view
        setContentView(ScannerView);
        ScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        ScannerView.startCamera();         // Start camera
    }

    @Override
    public void onPause() {
        super.onPause();
        ScannerView.startCamera();
    }
    @Override
    public void onResume() {
        super.onResume();
        ScannerView.startCamera();
    }

    @Override
    public void handleResult(Result rawResult) {

        String barCodeFormat = rawResult.getBarcodeFormat().toString();
        String result = rawResult.getText();

        String message = createEntity(barCodeFormat, result);

        if(message != "Oups, vous n'avez rien capturé") {
            displayAlertDialog(message, barCodeFormat, result);
        }
        else {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }

        ScannerView.resumeCameraPreview(this);
    }

    private void displayAlertDialog(String message, String barCodeFormat, String result) {
        ScannerView.stopCamera();
        String messageTextBox;
        if(message == "Félicitation, créature capturée !") {
            messageTextBox = creature.getName()+"\nPV : "+creature.getHp()+"\nType : "+creature.getType()+
            "\n\nCapturer d'autres entités ?";
        }
        else if (message == "Félicitation, potion capturée !")
        {
            messageTextBox = potion.getName()+"\nType : "+potion.getType()+"\nBonus : "+potion.getBonus()+
                    "\n\nCapturer d'autres entités ?";
        }
        else
        {
            messageTextBox = equipment.getName()+"\nType : "+equipment.getType()+"\nBonus : "+equipment.getBonus()+
                    "\n\nCapturer d'autres entités ?";
        }

        AlertDialog alterDialog = new AlertDialog.Builder(this)
            .setTitle(message)
            .setMessage(messageTextBox)
            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    ScannerView.startCamera();
                }
            })
            .setNegativeButton("Non",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            })
            .create();
        alterDialog.show();
    }

    private String createEntity(String barCodeFormat, String result) {
        String message;
        if (result != null) {

            switch (barCodeFormat.toUpperCase()) {

                //code bar utilisé dans le commerce et l'industrie
                case "EAN":
                    createCreature(result);
                    message = "Félicitation, créature capturée !";
                    break;
                case "EAN_8":
                    createCreature(result);
                    message = "Félicitation, créature capturée !";
                    break;
                case "EAN_13":
                    createCreature(result);
                    message = "Félicitation, créature capturée !";
                    break;

                //code bar utilisé en pharmacie
                case "CODE_39":
                    createPotion(result);
                    message = "Félicitation, potion capturée !";
                    break;
                case "CODE_93":
                    createPotion(result);
                    message = "Félicitation, potion capturée !";
                    break;

                //code bar QR Code
                case "CODE_QR":
                    message = "Félicitation, équipement capturé !";
                    createEquipment(result);
                    break;

                //code bar USA
                case "UPC_A":
                    message = "Félicitation, équipement capturé !";
                    createEquipment(result);
                    break;

                //code bar transport/distribution
                case "CODE_128":
                    message = "Félicitation, équipement capturé !";
                    createEquipment(result);
                    break;

                //le format du code n'est pas reconnu
                default:
                    message = "Oups, vous n'avez rien capturé";
                    break;
            }
        } else {
            message = "Oups, vous n'avez rien capturé";
        }
        return message;
    }

    public String pickRandomName(int type) {
        Random r = new Random();
        String retour = "The nameless";
        if(type == 0) // creature
        {
            String name = creatureNames.get(r.nextInt(((creatureNames.size()-1) - 0) + 1) + 0);
            String titre = creatureTitre.get(r.nextInt(((creatureTitre.size()-1) - 0) + 1) + 0);
            retour = name+titre;
        }
        else if (type == 1) // potion
        {
            String name = potionNames.get(r.nextInt(((potionNames.size()-1) - 0) + 1) + 0);
            retour = name;
        }
        else // equipment
        {
            String name = equipmentNames.get(r.nextInt(((equipmentNames.size()-1) - 0) + 1) + 0);
            retour = name;
        }
        Log.d("nameCreature",retour);
        return retour;
    }

    /*public double normalize(double x) {
        return ((x - 1) / (30 - 1)) * (normalizedHigh - normalizedLow) + normalizedLow;
    }*/

    private void createEquipment(String result) {
        String name = pickRandomName(2);
        String type = "";
        int bonus = 0;
        char[] array = result.toCharArray();

        //for (int i = 0; i < array.length; i++) {
            //name = String.valueOf(array[0] + array[1] + array[2]+array[3]);
            type = getPotionEquipment(array[array.length-2]);
            bonus = array[array.length-1];
        //}
        Log.d("infoEquipment",name+" "+type+" "+bonus);
        if (name != "" &&  type != "" &&  bonus != 0) {
            equipment = new Equipment(name, type, bonus);
            persistEquipment(equipment);
        }
        else {
            Toast.makeText(this, "code barre non valide", Toast.LENGTH_LONG).show();
        }

    }

    private void createPotion(String result) {
        String name = "Elixir "+pickRandomName(1);
        String type = "";
        int bonus = 0;
        char[] array = result.toCharArray();

        //for (int i = 0; i < array.length; i++) {
            //name = String.valueOf(array[0] + array[1] + array[2]+array[3]);
            type = getPotionEquipment(array[array.length-2]);
            bonus = array[array.length-1];
        //}
        Log.d("infoPotion",name+" "+type+" "+bonus);
        if (name != "" &&  type != "" &&  bonus != 0) {
            potion = new Potion(name, type, bonus);
            persistPotion(potion);
        }
        else {
            Toast.makeText(this, "code barre non valide", Toast.LENGTH_LONG).show();
        }

    }

    private void createCreature(String result) {
        String name = pickRandomName(0);
        int hp = 0;
        String type = "";
        int inventory_max_size = 0;
        int size = 0;
        int weight = 0;
        int defense = 0;
        char[] array = result.toCharArray();

        //for (int i = 0; i < array.length; i++) {
            //name = String.valueOf(array[0] + array[1] + array[2]+array[3]);
            hp = array[6];
            hp += array[7];
            type = getType(array[array.length-1]);
            inventory_max_size = array[array.length-2];
            size = array[array.length-3];
            weight = array[array.length-4];
            defense = array[array.length-5];
        //}

        Log.d("infoCreature",name+" "+hp+" "+type+" "+inventory_max_size+" "+size+" "+weight+" "+defense);

        if (name != "" &&  hp != 0 &&  type != "" &&  inventory_max_size != 0 && size != 0  && weight != 0  && defense  != 0) {
            creature = new Creature(name, hp, type, inventory_max_size, size, weight, defense);
            persistCreature(creature);
        } else {
            Toast.makeText(this, "code barre non valide", Toast.LENGTH_LONG).show();
        }
    }

    private String getType(char c) {
        String type = "";
        switch(Character.getNumericValue(c)) {
            case 0:
                type = "feu";
                break;
            case 1:
                type = "glace";
                break;
            case 2:
                type = "électrique";
                break;
            case 3:
                type = "roche";
                break;
            case 4:
                type = "plante";
                break;
            case 5:
                type = "air";
                break;
            case 6:
                type = "dragon";
                break;
            case 7:
                type = "insecte";
                break;
            case 8:
                type = "terre";
                break;
            case 9:
                type = "eau";
                break;
        }
        return type;
    }

    private String getPotionEquipment(char c) {
        String type = "";
        switch(Character.getNumericValue(c)) {
            case 0:
                type = "hp";
                break;
            case 1:
                type = "size";
                break;
            case 2:
                type = "weight";
                break;
            case 3:
                type = "speed";
                break;
            case 4:
                type = "strength";
                break;
            case 5:
                type = "defense";
                break;
            case 6:
                type = "hp";
                break;
            case 7:
                type = "strength";
                break;
            case 8:
                type = "defense";
                break;
            case 9:
                type = "speed";
                break;
        }
        return type;
    }




    private void persistCreature(Creature creature) {
        CreatureDAO creatureDAO = new CreatureDAO(this);
        creatureDAO.open();
        creatureDAO.insertCreature(creature);
        creatureDAO.close();
    }

    private void persistPotion(Potion potion) {
        PotionDAO potionDAO = new PotionDAO(this);
        potionDAO.open();
        potionDAO.insertPotion(potion);
        potionDAO.close();
    }

    private void persistEquipment(Equipment equipment) {
        EquipmentDAO equipmentDAO = new EquipmentDAO(this);
        equipmentDAO.open();
        equipmentDAO.insertEquipment(equipment);
        equipmentDAO.close();
    }
}
