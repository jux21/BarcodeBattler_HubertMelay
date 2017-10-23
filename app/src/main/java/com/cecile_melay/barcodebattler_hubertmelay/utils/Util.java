package com.cecile_melay.barcodebattler_hubertmelay.utils;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Path;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.cecile_melay.barcodebattler_hubertmelay.App;
import com.cecile_melay.barcodebattler_hubertmelay.MainActivity;
import com.cecile_melay.barcodebattler_hubertmelay.entities.User;
import com.cecile_melay.barcodebattler_hubertmelay.fragments.MyFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Utilisateur on 23/10/2017.
 */

//Ici on mettra toutes les fonctions usuelles nécessaires à différents endroits du code
public class Util {
    private static MainActivity mainActivity;
    private static MyFragment currentFragment;
    private static User currentUser;
    private static Path currentPath;
    private static List<Path> paths = new ArrayList<>();

    public static void createDialog(String message)
    {
        Activity activity = App.getSingleton().getCurrentActivity();
        if(activity == null)
            return;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.show();
    }

    public static void createToast(String message)
    {
        Activity activity = App.getSingleton().getCurrentActivity();
        if(activity == null)
            return;

        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public static void checkPrivileges(Activity AC, int MY_PERMISSIONS_REQUEST_GEOLOCATION_FINE, int MY_PERMISSIONS_REQUEST_GEOLOCATION_COARSE)
    {
        // Boite de dialogue pour demander les permissions GPS
        if (ActivityCompat.checkSelfPermission(AC, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AC, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AC,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_GEOLOCATION_FINE);
            ActivityCompat.requestPermissions(AC,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_GEOLOCATION_COARSE);
        }
    }

    public static void writeInPrefs(String key, String value) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(App.getSingleton().getContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String readFromPrefs(String key) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(App.getSingleton().getContext());
        return settings.getString(key, null);
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public static void setMainActivity(MainActivity activity) {
        mainActivity = activity;
    }

    public static void setCurrentUser(User currentUser) {
        Util.currentUser = currentUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static MyFragment getCurrentFragment() {
        return currentFragment;
    }

    public static void setCurrentFragment(MyFragment currentFragment) {
        Util.currentFragment = currentFragment;
    }

    public static void hideSoftKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) mainActivity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(mainActivity.getCurrentFocus() == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(
                mainActivity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void setCurrentPath(Path currentPath) {
        Util.currentPath = currentPath;
    }

    public static Path getCurrentPath() {
        return currentPath;
    }

    public static List<Path> getPaths() {
        return paths;
    }

    public static void addPath(Path path) {
        Util.paths.add(path);
    }



}
