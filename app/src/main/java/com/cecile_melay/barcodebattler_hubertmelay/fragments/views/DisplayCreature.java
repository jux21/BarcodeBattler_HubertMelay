package com.cecile_melay.barcodebattler_hubertmelay.fragments.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cecile_melay.barcodebattler_hubertmelay.MainActivity;
import com.cecile_melay.barcodebattler_hubertmelay.R;
import com.cecile_melay.barcodebattler_hubertmelay.database.dao.CreatureDAO;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Creature;
import com.cecile_melay.barcodebattler_hubertmelay.fragments.MyFragment;
import com.cecile_melay.barcodebattler_hubertmelay.utils.Util;

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

    @Override
    public void init() {

        String id = ((MainActivity) getActivity()).getParams();

        CreatureDAO creatureDAO = new CreatureDAO(getActivity());
        creatureDAO.open();
        Creature creature = creatureDAO.getCreatureWithID(id);
        creatureDAO.close();

        TextView creatureName = (TextView) this.contentView.findViewById(R.id.creature_name);
        creatureName.setText(creature.getName());

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

