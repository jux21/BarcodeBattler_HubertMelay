package com.cecile_melay.barcodebattler_hubertmelay.fragments.views;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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

    ProgressBar progressBar1;
    ProgressBar progressBar2;

    LinearLayout.LayoutParams params;

    Double originalHPcreature1;
    Double originalHPcreature2;

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
            cartTitle1.setTextColor(Color.WHITE);
            cardImage1 = (ImageView) this.contentView.findViewById(R.id.card_image1);
            cardImage1.setImageResource(creature1.getImagePath());
            creatureDetails1 = (TextView) this.contentView.findViewById(R.id.card_text1);
            String text = "PV : " + creature1.getHp()
                    + "  - Armure : " + creature1.getDefense()
                    + "\nAttaque : " + creature1.getStrength()
                    + " - Vitesse : " + creature1.getSpeed()
                    + "\nType : " + creature1.getType();
            creatureDetails1.setText(text.replace("\n", System.getProperty("line.separator")));
            progressBar1 = (ProgressBar) this.contentView.findViewById(R.id.life_bar1);
            originalHPcreature1 = creature1.getHp();
            Log.d("originalHPcreature1", String.valueOf(originalHPcreature1));
        }

        if (creature2 != null) {
            //display Creature 2 data
            cartTitle = (TextView) this.contentView.findViewById(R.id.card_title);
            cartTitle.setText(creature2.getName());
            cartTitle.setTextColor(Color.WHITE);
            cardImage = (ImageView) this.contentView.findViewById(R.id.card_image);
            cardImage.setImageResource(creature2.getImagePath());
            creatureDetails = (TextView) this.contentView.findViewById(R.id.card_text);
            String text = "PV : " + creature2.getHp()
                    + "  - Armure : " + creature2.getDefense()
                    + "\nAttaque : " + creature2.getStrength()
                    + " - Vitesse : " + creature2.getSpeed()
                    + "\nType : " + creature2.getType();
            creatureDetails.setText(text.replace("\n", System.getProperty("line.separator")));
            progressBar2 = (ProgressBar) this.contentView.findViewById(R.id.life_bar);
            originalHPcreature2 = creature2.getHp();
            Log.d("originalHPcreature2", String.valueOf(originalHPcreature2));
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
        //Si les 2 creatures ont la même rapidité
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
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                creature1.setHp(creature1.getHp() - ((creature2.getStrength()+creature1.getDefense())/4));
                progressBar1.setProgress((int) ((creature1.getHp()/originalHPcreature1)*100),true);
                String text = "PV : " + creature1.getHp()
                        + "  - Armure : " + creature1.getDefense()
                        + "\nAttaque : " + creature1.getStrength()
                        + " - Vitesse : " + creature1.getSpeed()
                        + "\nType : " + creature1.getType();
                creatureDetails1.setText(text.replace("\n", System.getProperty("line.separator")));

                btnAttack2.setEnabled(false);
                btnPotion2.setEnabled(false);
                btnAttack2.setTextColor(Color.parseColor("#aaaaaa"));
                btnPotion2.setTextColor(Color.parseColor("#aaaaaa"));
                btnAttack1.setEnabled(true);
                btnPotion1.setEnabled(true);
                btnAttack1.setTextColor(Color.parseColor("#3F51B5"));
                btnPotion1.setTextColor(Color.parseColor("#3F51B5"));

                if(creature1.getHp() <= 0) {
                    params =  (LinearLayout.LayoutParams) cardView1.getLayoutParams();
                    params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, params.bottomMargin); // left, top, right, bottom
                    cardView2.setLayoutParams(params);
                    cartTitle.setTextColor(Color.GREEN);
                    cardView1.setVisibility(View.GONE);
                   //cardView2.setPadding(0,35,0,0);
                }
            }
        });

        btnAttack1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                creature2.setHp(creature2.getHp() - ((creature1.getStrength()+creature2.getDefense())/4));
                progressBar2.setProgress((int) ((creature2.getHp()/originalHPcreature2)*100),true);
                String text = "PV : " + creature2.getHp()
                        + "  - Armure : " + creature2.getDefense()
                        + "\nAttaque : " + creature2.getStrength()
                        + " - Vitesse : " + creature2.getSpeed()
                        + "\nType : " + creature2.getType();
                creatureDetails.setText(text.replace("\n", System.getProperty("line.separator")));
                btnAttack1.setEnabled(false);
                btnPotion1.setEnabled(false);
                btnAttack1.setTextColor(Color.parseColor("#aaaaaa"));
                btnPotion1.setTextColor(Color.parseColor("#aaaaaa"));
                btnAttack2.setEnabled(true);
                btnPotion2.setEnabled(true);
                btnAttack2.setTextColor(Color.parseColor("#3F51B5"));
                btnPotion2.setTextColor(Color.parseColor("#3F51B5"));

                if(creature2.getHp() <= 0) {
                    params =  (LinearLayout.LayoutParams) cardView1.getLayoutParams();
                    params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, params.bottomMargin); // left, top, right, bottom
                    cardView1.setLayoutParams(params);
                    cartTitle1.setTextColor(Color.GREEN);
                    cardView2.setVisibility(View.GONE);
                }
            }
        });



    }

    //https://codelabs.developers.google.com/codelabs/material-design-style/index.html?index=..%2F..%2Findex#4
}
