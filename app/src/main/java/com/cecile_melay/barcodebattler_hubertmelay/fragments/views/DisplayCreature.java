package com.cecile_melay.barcodebattler_hubertmelay.fragments.views;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cecile_melay.barcodebattler_hubertmelay.MainActivity;
import com.cecile_melay.barcodebattler_hubertmelay.R;
import com.cecile_melay.barcodebattler_hubertmelay.database.dao.CreatureDAO;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Creature;
import com.cecile_melay.barcodebattler_hubertmelay.fragments.MyFragment;
import com.cecile_melay.barcodebattler_hubertmelay.utils.Util;

import java.io.Console;

/**
 * Created by Utilisateur on 25/10/2017.
 */

public class DisplayCreature extends MyFragment {

    private int id;
    private String name;
    private int hp;
    private String type;
    private int inventory_max_size;
    private int size;
    private int weight;
    private int speed;  // Calculated with size - weight
    private int strength;  // Calculated with (size + weight) / 2
    private int defense;

    private Button freeButton;
    private Button EquipButton;


    @Override
    public void init() {

        final String id = ((MainActivity) getActivity()).getParams();

        CreatureDAO creatureDAO = new CreatureDAO(getActivity());
        creatureDAO.open();
        final Creature creature = creatureDAO.getCreatureWithID(id);
        creatureDAO.close();

        if(creature==null) {
            Home newFragment = new Home();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_frame, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else
        {

            final TextView title = (TextView) this.contentView.findViewById(R.id.creature_name);
            title.setText("Infos sur " + creature.getName());

            TextView cartTitle = (TextView) this.contentView.findViewById(R.id.creature_title);
            cartTitle.setText(creature.getName());
            cartTitle.setTextColor(Color.RED);

            ImageView cardImage = (ImageView) this.contentView.findViewById(R.id.card_image);
            cardImage.setImageResource(creature.getImagePath());

            TextView creatureDetails = (TextView) this.contentView.findViewById(R.id.creature_details);
            creatureDetails.setText("PV : " + creature.getHp()
                    + "\nTaille : " + creature.getSize()
                    + "\nType : " + creature.getType()
                    + "\nInventaire : " + creature.getInventory_max_size()
                    + "\nDéfense : " + creature.getDefense()
                    + "\nRapidité : " + creature.getSpeed()
                    + "\nForce : " + creature.getStrength()
                    + "\nPoid : " + creature.getWeight());

            // Suppression de la créature
            freeButton = (Button) this.contentView.findViewById(R.id.action_free);
            freeButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CreatureDAO creatureDAO = new CreatureDAO(getActivity());
                    creatureDAO.open();
                    int creatureDelete = creatureDAO.removeCreatureWithID(Integer.parseInt(id));
                    creatureDAO.close();
                    if (creatureDelete == 1) {
                        Toast.makeText(getActivity(), creature.getName() + " supprimé", Toast.LENGTH_LONG).show();

                        EquipButton = (Button) getActivity().findViewById(R.id.action_add_equip);

                        DisplayCreatures newFragment = new DisplayCreatures();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_frame, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                    } else {
                        Toast.makeText(getActivity(), "Impossible de supprimer " + creature.getName(), Toast.LENGTH_LONG).show();
                    }


                }
            });
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.display_creature;
    }

    @Override
    protected String getTitle() {
        return null;
    }


}

