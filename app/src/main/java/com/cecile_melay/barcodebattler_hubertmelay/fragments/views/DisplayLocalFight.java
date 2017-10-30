package com.cecile_melay.barcodebattler_hubertmelay.fragments.views;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.cecile_melay.barcodebattler_hubertmelay.MainActivity;
import com.cecile_melay.barcodebattler_hubertmelay.R;
import com.cecile_melay.barcodebattler_hubertmelay.database.dao.CreatureDAO;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Creature;
import com.cecile_melay.barcodebattler_hubertmelay.fragments.MyFragment;

/**
 * Created by Utilisateur on 26/10/2017.
 */

public class DisplayLocalFight extends MyFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.display_local_fight;
    }

    @Override
    protected String getTitle() {
        return null;
    }

    @Override
    protected void init() {

        //Get creature 1 and 2 data
        String id1 = ((MainActivity) getActivity()).getParam1();
        String id2 = ((MainActivity) getActivity()).getParam2();

        CreatureDAO creatureDAO = new CreatureDAO(getActivity());
        creatureDAO.open();
        Creature creature1 = creatureDAO.getCreatureWithID(id1);
        creatureDAO.close();

        CreatureDAO creatureDAO2 = new CreatureDAO(getActivity());
        creatureDAO2.open();
        Creature creature2 = creatureDAO2.getCreatureWithID(id2);
        creatureDAO2.close();

        Log.d("YOLOO1", id1);
        Log.d("YOLOO2", id2);
        //Log.d("YOLOO1", String.valueOf(creature1));
        //Log.d("YOLOO2", String.valueOf(creature2));
        if (creature1 != null) {
            //display Creature 1 data
            TextView cartTitle1 = (TextView) this.contentView.findViewById(R.id.card_title1);
            cartTitle1.setText(creature1.getName());
            ImageView cardImage1 = (ImageView) this.contentView.findViewById(R.id.card_image1);
            cardImage1.setImageResource(creature1.getImagePath());
            TextView creatureDetails1 = (TextView) this.contentView.findViewById(R.id.card_text1);
            creatureDetails1.setText("PV : " + creature1.getHp());
        }

        if (creature2 != null) {
            //display Creature 2 data
            TextView cartTitle = (TextView) this.contentView.findViewById(R.id.card_title);
            cartTitle.setText(creature2.getName());
            ImageView cardImage = (ImageView) this.contentView.findViewById(R.id.card_image);
            cardImage.setImageResource(creature2.getImagePath());
            TextView creatureDetails = (TextView) this.contentView.findViewById(R.id.card_text);
            creatureDetails.setText("PV : " + creature2.getHp());
        }

    }

    //https://codelabs.developers.google.com/codelabs/material-design-style/index.html?index=..%2F..%2Findex#4
}
