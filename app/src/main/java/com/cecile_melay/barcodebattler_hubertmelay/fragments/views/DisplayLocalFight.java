package com.cecile_melay.barcodebattler_hubertmelay.fragments.views;

import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cecile_melay.barcodebattler_hubertmelay.MainActivity;
import com.cecile_melay.barcodebattler_hubertmelay.R;
import com.cecile_melay.barcodebattler_hubertmelay.database.dao.CreatureDAO;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Creature;
import com.cecile_melay.barcodebattler_hubertmelay.fragments.MyFragment;

import java.util.Random;

/**
 * Created by Utilisateur on 26/10/2017.
 */

public class DisplayLocalFight extends MyFragment {

    Button btnAttack1;
    Button btnPotion1;
    Button btnAttack2;
    Button btnPotion2;


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

        if (creature1 != null) {
            //display Creature 1 data
            TextView cartTitle1 = (TextView) this.contentView.findViewById(R.id.card_title1);
            cartTitle1.setText(creature1.getName());
            ImageView cardImage1 = (ImageView) this.contentView.findViewById(R.id.card_image1);
            cardImage1.setImageResource(creature1.getImagePath());
            TextView creatureDetails1 = (TextView) this.contentView.findViewById(R.id.card_text1);
            creatureDetails1.setText("PV : " + creature1.getHp()
                    + " T : " + creature1.getType()
                    + " D : " + creature1.getDefense()
                    + " R : " + creature1.getSpeed()
                    + " F : " + creature1.getStrength());
        }

        if (creature2 != null) {
            //display Creature 2 data
            TextView cartTitle = (TextView) this.contentView.findViewById(R.id.card_title);
            cartTitle.setText(creature2.getName());
            ImageView cardImage = (ImageView) this.contentView.findViewById(R.id.card_image);
            cardImage.setImageResource(creature2.getImagePath());
            TextView creatureDetails = (TextView) this.contentView.findViewById(R.id.card_text);
            creatureDetails.setText("PV : " + creature2.getHp()
                    + " T : " + creature2.getType()
                    + " D : " + creature2.getDefense()
                    + " R : " + creature2.getSpeed()
                    + " F : " + creature2.getStrength());
        }

        btnAttack1 = (Button) contentView.findViewById(R.id.potion1);
        btnPotion1 = (Button) contentView.findViewById(R.id.attack1);
        btnAttack2 = (Button) contentView.findViewById(R.id.potion);
        btnPotion2 = (Button) contentView.findViewById(R.id.attack);

        //Si la rapidité de la creature 1 est supérieur à la rapidité de la creature 2
        //La creature 1 commence
        if (creature1.getSpeed() > creature2.getSpeed()) {
            btnAttack2.setEnabled(false);
            btnPotion2.setEnabled(false);
        //Si la rapidité de la creature 2 est supérieur à la rapidité de la creature 1
        //La creature 2 commence
        } else if(creature1.getSpeed() < creature2.getSpeed()) {
            btnAttack1.setEnabled(false);
            btnPotion1.setEnabled(false);
        //Si les 2 creatures ont la mémé rapidité
        //Le premier à pouvoir jouer est choisi aléatoirement
        } else {
            int min = 0;
            int max = 1;
            Random r = new Random();
            int i1 = r.nextInt(max - min + 1) + min;
            if (i1 == 0) {
                btnAttack2.setEnabled(false);
                btnPotion2.setEnabled(false);
            } else {
                btnAttack1.setEnabled(false);
                btnPotion1.setEnabled(false);
            }
        }

    }

    //https://codelabs.developers.google.com/codelabs/material-design-style/index.html?index=..%2F..%2Findex#4
}
