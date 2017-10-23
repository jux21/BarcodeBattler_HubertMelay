package com.cecile_melay.barcodebattler_hubertmelay.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


//import com.google.android.gms.location.DetectedActivity;

import com.cecile_melay.barcodebattler_hubertmelay.App;
import com.cecile_melay.barcodebattler_hubertmelay.R;
import com.cecile_melay.barcodebattler_hubertmelay.utils.MyResources;

import java.util.List;

/**
 * Created by Utilisateur on 23/10/2017.
 */

public class RecognitionActivityFragment extends MyFragment {
    private TextView textActivity;
    private TextView textVehicule;
    private TextView textBicycle;
    private TextView textOnfoot;
    private TextView textRunning;
    private TextView textWalking;
    private TextView textStill;
    private TextView textTitling;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_view;
    }

    @Override
    protected String getTitle(){
        return MyResources.RECOGNIZE_ACTIVITY;
    }

    @Override
    protected void init() {
        this.textActivity = (TextView) this.contentView.findViewById(R.id.activity);
        this.textVehicule = (TextView) this.contentView.findViewById(R.id.vehicule);
        this.textBicycle = (TextView) this.contentView.findViewById(R.id.bicycle);
        this.textOnfoot = (TextView) this.contentView.findViewById(R.id.onfoot);
        this.textRunning = (TextView) this.contentView.findViewById(R.id.running);
        this.textWalking = (TextView) this.contentView.findViewById(R.id.walking);
        this.textStill = (TextView) this.contentView.findViewById(R.id.still);
        this.textTitling = (TextView) this.contentView.findViewById(R.id.titling);
    }

    @Override
    public View getView() {
        return this.contentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        App.getSingleton().getSingleton().setCurrentFragment(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void handleServiceConnected() {
        this.textActivity.setText("Service connecté");
    }



    public void handleServiceSuspended() {
        this.textActivity.setText("Service suspendu");
    }

    public void handleServiceConnectionFailed() {
        this.textActivity.setText("Service injoignable");
    }
}
