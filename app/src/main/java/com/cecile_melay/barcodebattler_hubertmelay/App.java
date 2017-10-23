package com.cecile_melay.barcodebattler_hubertmelay;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by Utilisateur on 23/10/2017.
 */

public class App {

    //Ici on stockera toutes les variables globales de l'app
    private static App instance;
    private Context context;
    private Activity currentActivity;
    private Fragment currentFragment;

    public static App getSingleton() {
        if(instance == null) {
            instance = new App();
        }

        return instance;
    }

    private App() {

    }

    public Context getContext() {
        return this.context;
    }

    public void setContext (Context c) {
        this.context = c;
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    public void setCurrentFragment(Fragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }
}
