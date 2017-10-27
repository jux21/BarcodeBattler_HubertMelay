package com.cecile_melay.barcodebattler_hubertmelay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.cecile_melay.barcodebattler_hubertmelay.database.dao.CreatureDAO;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Creature;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Utilisateur on 24/10/2017.
 */

public class EntityCatch extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView ScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entity_catch);
        QRCScanner(this.ScannerView);
    }

    public  void QRCScanner (View view){
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

        displayAlertDialog(message, barCodeFormat, result);

        ScannerView.resumeCameraPreview(this);
    }

    private void displayAlertDialog(String message, String barCodeFormat, String result) {

        AlertDialog alterDialog = new AlertDialog.Builder(this)
            .setTitle(message)
            .setMessage(result+  " - " + barCodeFormat +
                    "\n\nSouhaitez vous capturer d'autres entités ?")
            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            })
            .setNegativeButton("Non",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
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
                    createCreature8(result);
                    message = "Félicitation, créature capturée !";
                    break;
                case "EAN_13":
                    createCreature13(result);
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

    private void createEquipment(String result) {

    }

    private void createCreature8(String result) {

    }

    private void createCreature13(String result) {
        String name ="";
        int hp = 0;
        String type = "";
        int inventory_max_size = 0;
        int size = 0;
        int weight = 0;
        int defense = 0;

        String test = "0123456789012";
        char[] array = result.toCharArray();

        for (int i = 0; i < array.length; i++) {

            name = String.valueOf(array[0] + array[1] + array[2]+array[3]);

            hp = array[6];
            hp += array[7];

            type = getType(array[8]);

            inventory_max_size = array[9];

            size = array[10];

            weight = array[11];

            defense = array[12];
        }

        if (name != "" &&  hp != 0 &&  type != "" &&  inventory_max_size != 0 && size != 0  && weight != 0  && defense  != 0) {
            Creature creature13 = new Creature(name, hp, type, inventory_max_size, size, weight, defense);
            persistCreature(creature13);
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

    private void createPotion(String result) {

    }

    private void createCreature(String result) {


    }

    private void persistCreature(Creature creature) {
        CreatureDAO creatureDAO = new CreatureDAO(this);
        creatureDAO.open();
        creatureDAO.insertCreature(creature);
        creatureDAO.close();
    }
}