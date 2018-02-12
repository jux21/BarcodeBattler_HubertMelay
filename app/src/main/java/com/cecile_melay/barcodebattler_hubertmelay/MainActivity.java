package com.cecile_melay.barcodebattler_hubertmelay;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuPresenter;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cecile_melay.barcodebattler_hubertmelay.database.dao.CreatureDAO;
import com.cecile_melay.barcodebattler_hubertmelay.database.dao.EquipmentDAO;
import com.cecile_melay.barcodebattler_hubertmelay.database.dao.PotionDAO;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Creature;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Equipment;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Potion;
import com.cecile_melay.barcodebattler_hubertmelay.database.dao.PlayerDAO;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Creature;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Equipment;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Player;
import com.cecile_melay.barcodebattler_hubertmelay.fragments.MyFragment;
import com.cecile_melay.barcodebattler_hubertmelay.fragments.views.*;
import com.cecile_melay.barcodebattler_hubertmelay.fragments.views.DisplayCreature;
import com.cecile_melay.barcodebattler_hubertmelay.fragments.views.DisplayCreatures;
import com.cecile_melay.barcodebattler_hubertmelay.fragments.views.DisplayEquipmentAndPotions;
import com.cecile_melay.barcodebattler_hubertmelay.fragments.views.Home;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Activity Main View
    Toolbar toolbar;
    TextView playerNameTextView;
    TextView playerDetailsTextView;
    String playerName = "";

    //Fragment Home View
    //Display Create New Player
    Button btnCreateNewPlayer;
    AutoCompleteTextView editText;

    //Fragments
    private List<MyFragment> fragments = new ArrayList<>();
    private MyFragment homeFragment;

    //ID number of the creature selected in the display creatures list
    String params = "";

    //ID number of the 2 creatures selected in the display creatures list for a local fight
    String param1 = "";
    String param2 = "";

    //Permissions
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 0;
    private static final int MY_PERMISSIONS_NFC = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Boite de dialogue pour demander la permission camera et NFC
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.NFC) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.NFC}, MY_PERMISSIONS_NFC);
        }

        PlayerDAO playerDAO = new PlayerDAO(this);
        playerDAO.open();
        List<Player> playersFromBDD = playerDAO.getAllPlayers();

        if (playersFromBDD == null) {
            setContentView(R.layout.create_new_player);
            btnCreateNewPlayer = (Button) this.findViewById(R.id.create_new_player);
            editText = (AutoCompleteTextView) this.findViewById(R.id.player_name);
            btnCreateNewPlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                playerName = editText.getText().toString();
                createNewPlayer(playerName);
                }
            });
        } else {
            launchGame();
        }

        //Creature creatureFromBDD = creatureDAO.getCreatureWithName("Jux");
        //if(creatureFromBDD != null) {
        //On affiche les infos de la Creature dans un Toast
        //Toast.makeText(this, creatureFromBDD.toString(), Toast.LENGTH_LONG).show();
        //}
    }

    private void createNewPlayer(String playerName) {
        PlayerDAO playerDAO = new PlayerDAO(this);
        playerDAO.open();
        Player player = new Player(playerName, 0, 0, 10, 10);
        playerDAO.insertPlayer(player);
        launchGame();
    }

    private void launchGame() {
        setContentView(R.layout.activity_main);

        startFragment(Home.class);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        PlayerDAO playerDAO = new PlayerDAO(this);
        playerDAO.open();
        List<Player> playersFromBDD = playerDAO.getAllPlayers();

        View headerView = navigationView.getHeaderView(0);
        playerNameTextView = (TextView) headerView.findViewById(R.id.display_player_name);
        playerNameTextView.setText(playersFromBDD.get(0).getName());

        playerDetailsTextView = (TextView) headerView.findViewById(R.id.display_player_details);
        playerDetailsTextView.setText("Niveau : "+(playersFromBDD.get(0).getNbLosses() - playersFromBDD.get(0).getNbWin()));

        createEntities();

       /* Intent iin= getIntent();
        Bundle b = iin.getExtras();

        //Si entitycatch start this mainactivity, display the details of the creature captured
        if(b!=null) {
            String j = String.valueOf(b.get("position"));
            Log.d("YOLOOO", j);
            //startDisplayCreatureFragment(DisplayCreature.class, j);
        }*/
    }

    private void createEntities() {
        // Insertion de données tests en BD
        CreatureDAO creatureDAO = new CreatureDAO(this);
        EquipmentDAO equipmentDAO = new EquipmentDAO(this);
        PotionDAO potionDAO = new PotionDAO(this);

        //Creature creature1 = new Creature("Jux",120,"air",5,80,50,30);
        //Creature creature2 = new Creature("Cecile",140,"feu",3,50,20,10);
        //Creature creature3 = new Creature("Mad",120,"eau",8,80,100,300);
        //Creature creature4 = new Creature("Max",120,"terre",6,90,75,160);
        //Equipment equipment1 = new Equipment("Cape de feu","defense",10);
        //Equipment equipment2 = new Equipment("Épée du dragon","attaque",10);
        //Equipment equipment3 = new Equipment("Basket Adidas","vitesse",5);
        //Potion potion1 = new Potion("potion revigorante","vie",10);
        //Potion potion2 = new Potion("potion de vitesse","vitesse",20);
        //Potion potion3 = new Potion("potion de force","attaque",25);

        creatureDAO.open();
        //creatureDAO.insertCreature(creature1);
        //creatureDAO.insertCreature(creature2);
        //creatureDAO.insertCreature(creature3);
        //creatureDAO.insertCreature(creature4);
        creatureDAO.close();
        equipmentDAO.open();
        //equipmentDAO.insertEquipment(equipment1);
        //equipmentDAO.insertEquipment(equipment2);
        //equipmentDAO.insertEquipment(equipment3);
        equipmentDAO.close();
        potionDAO.open();
        //potionDAO.insertPotion(potion1);
        //potionDAO.insertPotion(potion2);
        //potionDAO.insertPotion(potion3);
        potionDAO.close();

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
            Intent entityCatch = new Intent(this, EntityCatch.class);
            startActivity(entityCatch);

        } else if (id == R.id.nav_display_creatures) {

            startDisplayCreatureFragment(DisplayCreatures.class, "onClickShowCreatureDetails");

        } else if (id == R.id.nav_display_equipment_and_potions) {

            startFragment(DisplayEquipmentAndPotions.class);

        } else if (id == R.id.nav_local_fight) {

            startDisplayCreatureFragment(DisplayCreatures.class, "OnClickLaunchLocalFight");

        } else if (id == R.id.nav_nfc_fight) {

            //todo code use startDisplayCreatureFragment(DisplayCreatures.class, "OnClickLaunchNFCFight");
            Intent entityCatch = new Intent(this, NFCFight.class);
            startActivity(entityCatch);
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

    public void startDisplayCreatureFragment(final Class<? extends MyFragment> fragmentClass, String id) {
        params = id;
        startFragment(fragmentClass);
    }
    public void startDisplayLocalFightFragment(final Class<? extends MyFragment> fragmentClass, String id1, String id2) {
        param1 = id1;
        param2 = id2;
        startFragment(fragmentClass);
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

}
