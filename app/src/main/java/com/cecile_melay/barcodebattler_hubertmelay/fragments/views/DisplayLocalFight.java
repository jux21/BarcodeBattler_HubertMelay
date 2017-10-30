package com.cecile_melay.barcodebattler_hubertmelay.fragments.views;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    CardView cardView1;
    CardView cardView2;

    Button btnAttack1;
    Button btnPotion1;
    Button btnAttack2;
    Button btnPotion2;

    TextView cartTitle1;
    ImageView cardImage1;
    TextView creatureDetails1;

    TextView cartTitle;
    ImageView cardImage;
    TextView creatureDetails;

    LinearLayout.LayoutParams params;



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

        cardView1 = (CardView) this.contentView.findViewById(R.id.card_view1);
        cardView2 = (CardView) this.contentView.findViewById(R.id.card_view);

        params = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;

        //Get creature 1 and 2 data
        String id1 = ((MainActivity) getActivity()).getParam1();
        String id2 = ((MainActivity) getActivity()).getParam2();
        CreatureDAO creatureDAO = new CreatureDAO(getActivity());
        creatureDAO.open();
        final Creature creature1 = creatureDAO.getCreatureWithID(id1);
        creatureDAO.close();
        CreatureDAO creatureDAO2 = new CreatureDAO(getActivity());
        creatureDAO2.open();
        final Creature creature2 = creatureDAO2.getCreatureWithID(id2);
        creatureDAO2.close();

        if (creature1 != null) {
            //display Creature 1 data
            cartTitle1 = (TextView) this.contentView.findViewById(R.id.card_title1);
            cartTitle1.setText(creature1.getName());
            cardImage1 = (ImageView) this.contentView.findViewById(R.id.card_image1);
            cardImage1.setImageResource(creature1.getImagePath());
            creatureDetails1 = (TextView) this.contentView.findViewById(R.id.card_text1);
            creatureDetails1.setText("PV : " + creature1.getHp()
                    + " T : " + creature1.getType()
                    + " D : " + creature1.getDefense()
                    + " R : " + creature1.getSpeed()
                    + " F : " + creature1.getStrength());
        }

        if (creature2 != null) {
            //display Creature 2 data
            cartTitle = (TextView) this.contentView.findViewById(R.id.card_title);
            cartTitle.setText(creature2.getName());
            cardImage = (ImageView) this.contentView.findViewById(R.id.card_image);
            cardImage.setImageResource(creature2.getImagePath());
            creatureDetails = (TextView) this.contentView.findViewById(R.id.card_text);
            creatureDetails.setText("PV : " + creature2.getHp()
                    + " T : " + creature2.getType()
                    + " D : " + creature2.getDefense()
                    + " R : " + creature2.getSpeed()
                    + " F : " + creature2.getStrength());
        }

        btnPotion1 = (Button) contentView.findViewById(R.id.potion1);
        btnAttack1 = (Button) contentView.findViewById(R.id.attack1);
        btnPotion2 = (Button) contentView.findViewById(R.id.potion);
        btnAttack2 = (Button) contentView.findViewById(R.id.attack);

        //Si la rapidité de la creature 1 est supérieur à la rapidité de la creature 2
        //La creature 1 commence
        if (creature1.getSpeed() > creature2.getSpeed()) {
            btnAttack2.setEnabled(false);
            btnPotion2.setEnabled(false);
            btnAttack2.setTextColor(Color.parseColor("#aaaaaa"));
            btnPotion2.setTextColor(Color.parseColor("#aaaaaa"));

        //Si la rapidité de la creature 2 est supérieur à la rapidité de la creature 1
        //La creature 2 commence
        } else if(creature1.getSpeed() < creature2.getSpeed()) {
            btnAttack1.setEnabled(false);
            btnPotion1.setEnabled(false);
            btnAttack1.setTextColor(Color.parseColor("#aaaaaa"));
            btnPotion1.setTextColor(Color.parseColor("#aaaaaa"));
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
                btnAttack2.setTextColor(Color.parseColor("#aaaaaa"));
                btnPotion2.setTextColor(Color.parseColor("#aaaaaa"));
            } else {
                btnAttack1.setEnabled(false);
                btnPotion1.setEnabled(false);
                btnAttack1.setTextColor(Color.parseColor("#aaaaaa"));
                btnPotion1.setTextColor(Color.parseColor("#aaaaaa"));
            }
        }

        btnAttack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creature1.setHp(creature1.getHp() - ((creature2.getStrength()+creature1.getDefense())/4));
                creatureDetails1.setText("PV : " + creature1.getHp()
                    + " T : " + creature1.getType()
                    + " D : " + creature1.getDefense()
                    + " R : " + creature1.getSpeed()
                    + " F : " + creature1.getStrength());
                btnAttack2.setEnabled(false);
                btnPotion2.setEnabled(false);
                btnAttack2.setTextColor(Color.parseColor("#aaaaaa"));
                btnPotion2.setTextColor(Color.parseColor("#aaaaaa"));
                btnAttack1.setEnabled(true);
                btnPotion1.setEnabled(true);
                btnAttack1.setTextColor(Color.parseColor("#3F51B5"));
                btnPotion1.setTextColor(Color.parseColor("#3F51B5"));

                if(creature1.getHp() <= 0) {
                    cardView1.setVisibility(View.GONE);
                   //cardView2.setPadding(0,35,0,0);
                }
            }
        });

        btnAttack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creature2.setHp(creature2.getHp() - ((creature1.getStrength()+creature2.getDefense())/4));
                creatureDetails.setText("PV : " + creature2.getHp()
                        + " T : " + creature2.getType()
                        + " D : " + creature2.getDefense()
                        + " R : " + creature2.getSpeed()
                        + " F : " + creature2.getStrength());
                btnAttack1.setEnabled(false);
                btnPotion1.setEnabled(false);
                btnAttack1.setTextColor(Color.parseColor("#aaaaaa"));
                btnPotion1.setTextColor(Color.parseColor("#aaaaaa"));
                btnAttack2.setEnabled(true);
                btnPotion2.setEnabled(true);
                btnAttack2.setTextColor(Color.parseColor("#3F51B5"));
                btnPotion2.setTextColor(Color.parseColor("#3F51B5"));




                if(creature2.getHp() <= 0) {
                    cardView1.setVisibility(View.GONE);
                    //cardView2.setLayout(0,35,0,0);
                }
            }
        });



    }

    //https://codelabs.developers.google.com/codelabs/material-design-style/index.html?index=..%2F..%2Findex#4
}
