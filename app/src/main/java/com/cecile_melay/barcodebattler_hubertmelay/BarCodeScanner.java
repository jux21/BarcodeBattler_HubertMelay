package com.cecile_melay.barcodebattler_hubertmelay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Utilisateur on 24/10/2017.
 */

public class BarCodeScanner extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView ScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        QRCScanner(this.ScannerView) ;
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
        ScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {

        String barCodeFormat = rawResult.getBarcodeFormat().toString();
        String result = rawResult.getText();
        // afficher le résultat dans une dialog box.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Félicitation, entité capturée !");
        builder.setMessage(result+  " - " + barCodeFormat);

        builder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alerte = builder.create();
        alerte.show();
        // Pour redémarrer le scanner.
        ScannerView.resumeCameraPreview(this);

    }
}