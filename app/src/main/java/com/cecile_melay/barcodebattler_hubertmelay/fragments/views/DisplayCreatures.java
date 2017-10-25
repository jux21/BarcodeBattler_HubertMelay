package com.cecile_melay.barcodebattler_hubertmelay.fragments.views;

import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cecile_melay.barcodebattler_hubertmelay.R;
import com.cecile_melay.barcodebattler_hubertmelay.database.dao.CreatureDAO;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Creature;
import com.cecile_melay.barcodebattler_hubertmelay.fragments.MyFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Utilisateur on 25/10/2017.
 */

public class DisplayCreatures extends MyFragment {

    ListView listCreatures;

    @Override
    protected int getLayoutId() {return R.layout.display_creatures;}

    @Override
    protected String getTitle() {
        return null;
    }

    @Override
    protected void init() {
        listCreatures = (ListView)contentView.findViewById(R.id.listCreatures);
        CreatureDAO creatureDAO = new CreatureDAO(this.getContext());
        creatureDAO.open();
        List<Creature> creaturesFromBDD = creatureDAO.getAllCreature();
        if(creaturesFromBDD == null) {
            Toast.makeText(this.getContext(), "No creatures", Toast.LENGTH_LONG).show();
        }
        else {
            List<String> creaturesFromBDDString = creatureDAO.creaturesToString(creaturesFromBDD);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this.getContext(),
                    android.R.layout.simple_list_item_1,
                    creaturesFromBDDString);

            listCreatures.setAdapter(arrayAdapter);
        }




    }
}