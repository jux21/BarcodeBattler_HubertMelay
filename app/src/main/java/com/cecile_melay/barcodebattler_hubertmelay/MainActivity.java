package com.cecile_melay.barcodebattler_hubertmelay;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cecile_melay.barcodebattler_hubertmelay.database.dao.CreatureDAO;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Creature;
import com.cecile_melay.barcodebattler_hubertmelay.fragments.MyFragment;
import com.cecile_melay.barcodebattler_hubertmelay.fragments.views.DisplayCreatures;
import com.cecile_melay.barcodebattler_hubertmelay.fragments.views.DisplayEquipmentAndPotions;
import com.cecile_melay.barcodebattler_hubertmelay.fragments.views.Home;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    private List<MyFragment> fragments = new ArrayList<>();
    private MyFragment homeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Insertion de données tests en BD
        CreatureDAO creatureDAO = new CreatureDAO(this);
        Creature creature1 = new Creature("Jux",120,"air",5,80,50,30);
        creatureDAO.open();
        creatureDAO.insertCreature(creature1);
        Creature creatureFromBDD = creatureDAO.getCreatureWithName("Jux");
        if(creatureFromBDD != null) {
            //On affiche les infos de la Creature dans un Toast
            Toast.makeText(this, creatureFromBDD.toString(), Toast.LENGTH_LONG).show();
        }



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            startFragment(Home.class);

        } else if (id == R.id.nav_entity_catch) {

            // Handle the camera action
            Intent entityCatch = new Intent(this, BarCodeScanner.class);
            startActivity(entityCatch);

        } else if (id == R.id.nav_display_creatures) {

            startFragment(DisplayCreatures.class);

        } else if (id == R.id.nav_display_equipment_and_potions) {

            startFragment(DisplayEquipmentAndPotions.class);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setNavTitle(String title) {
        this.toolbar.setTitle(title);
    }

    public void closeCurrentFragment(Fragment fragmentToClose, boolean restoreMap) {
        fragments.remove(fragmentToClose);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.remove(fragmentToClose);

        ft.commit();
    }

    public void startFragment(final Class<? extends MyFragment> fragmentClass) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    MyFragment fragment = fragmentClass.newInstance();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_frame, fragment);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.addToBackStack(null);
                    ft.commit();

                    if (homeFragment == null) {
                        homeFragment = fragment;
                    } else {
                        fragments.add(fragment);
                    }

                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
