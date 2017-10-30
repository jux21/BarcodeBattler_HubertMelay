package com.cecile_melay.barcodebattler_hubertmelay.fragments.views;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cecile_melay.barcodebattler_hubertmelay.EntityCatch;
import com.cecile_melay.barcodebattler_hubertmelay.MainActivity;
import com.cecile_melay.barcodebattler_hubertmelay.R;
import com.cecile_melay.barcodebattler_hubertmelay.database.dao.CreatureDAO;
import com.cecile_melay.barcodebattler_hubertmelay.database.dao.EquipmentDAO;
import com.cecile_melay.barcodebattler_hubertmelay.database.dao.PlayerDAO;
import com.cecile_melay.barcodebattler_hubertmelay.database.dao.PotionDAO;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Creature;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Equipment;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Player;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Potion;
import com.cecile_melay.barcodebattler_hubertmelay.fragments.MyFragment;

import java.util.List;

/**
 * Created by Utilisateur on 23/10/2017.
 */

public class Home extends MyFragment {

    //Display Home
    TextView playerResults;
    TextView displayPlayerName;
    Button actionFight;
    Button actionLocalFight;
    Button displayCreatures;
    Button displayEquipmentAndPotions;
    Button actionEntityCatch;

    int nbCreature = 0;
    int nbPotion = 0;
    int nbEquipment = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.home;
    }

    @Override
    protected String getTitle() {
        return null;
    }

    public void init() {

        PlayerDAO playerDAO = new PlayerDAO(getActivity());
        playerDAO.open();
        List<Player> playersFromBDD = playerDAO.getAllPlayers();

        displayPlayerName = (TextView) contentView.findViewById(R.id.player_name_title);
        displayPlayerName.setText(playersFromBDD.get(0).getName());

        CreatureDAO creatureDAO = new CreatureDAO(this.getContext());
        creatureDAO.open();
        List<Creature> creaturesFromBDD = creatureDAO.getAllCreature();
        if(creaturesFromBDD == null) {
            nbCreature = 0;
        } else {
            nbCreature = creaturesFromBDD.size();
        }

        PotionDAO potionDAO = new PotionDAO(this.getContext());
        potionDAO.open();
        List<Potion> potionsFromBDD = potionDAO.getAllPotion();
        if(potionsFromBDD == null) {
            nbPotion = 0;
        } else {
            nbPotion = potionsFromBDD.size();
        }

        EquipmentDAO equipmentDAO = new EquipmentDAO(this.getContext());
        equipmentDAO.open();
        List<Equipment> equipmentFromBDD = equipmentDAO.getAllEquipment();
        if(equipmentFromBDD == null) {
            nbEquipment = 0;
        } else {
            nbEquipment = equipmentFromBDD.size();
        }


        playerResults = (TextView) contentView.findViewById(R.id.player_details);
        playerResults.setText("Résultats \nCréatures : "+nbCreature+" \nPotions : "+nbPotion+" - Equipements : "+nbEquipment+" \nVictoires : "+playersFromBDD.get(0).getNbWin()+" - Défaite : "+playersFromBDD.get(0).getNbLosses());

        actionEntityCatch = (Button) contentView.findViewById(R.id.action_entity_catch);
        actionEntityCatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the camera action
                Intent entityCatch = new Intent(getActivity(), EntityCatch.class);
                startActivity(entityCatch);
            }
        });

        actionLocalFight = (Button) contentView.findViewById(R.id.action_local_fight);
        actionLocalFight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).startFragment(ChooseCreatureForLocalFight.class);
            }
        });

        actionFight = (Button) contentView.findViewById(R.id.action_fight);
        actionFight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).startFragment(ChooseCreatureForNFCFight.class);
            }
        });

        displayCreatures = (Button) contentView.findViewById(R.id.display_creatures);
        displayCreatures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).startFragment(DisplayCreatures.class);
            }
        });

        displayEquipmentAndPotions = (Button) contentView.findViewById(R.id.display_equipment_and_potions);
        displayEquipmentAndPotions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).startFragment(DisplayEquipmentAndPotions.class);
            }
        });
    }
}
