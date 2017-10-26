package com.cecile_melay.barcodebattler_hubertmelay.fragments.views;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cecile_melay.barcodebattler_hubertmelay.R;
import com.cecile_melay.barcodebattler_hubertmelay.database.dao.CreatureDAO;
import com.cecile_melay.barcodebattler_hubertmelay.database.dao.EquipmentDAO;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Creature;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Equipment;
import com.cecile_melay.barcodebattler_hubertmelay.fragments.MyFragment;

import java.util.List;

/**
 * Created by Utilisateur on 25/10/2017.
 */

public class DisplayEquipmentAndPotions extends MyFragment {

    ListView listBag;

    @Override
    protected int getLayoutId() {
        return R.layout.display_equipment_and_potions;
    }

    @Override
    protected String getTitle() {
        return null;
    }

    @Override
    protected void init() {
        listBag = (ListView)contentView.findViewById(R.id.bagContent);
        EquipmentDAO equipmentDAO = new EquipmentDAO(this.getContext());
        equipmentDAO.open();
        List<Equipment> equipmentsFromBDD = equipmentDAO.getAllEquipment();
        if(equipmentsFromBDD == null) {
            Toast.makeText(this.getContext(), "No creatures", Toast.LENGTH_LONG).show();
        }
        else {
            List<String> equipmentFromBDDString = equipmentDAO.equipmentToString(equipmentsFromBDD);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this.getContext(),
                    android.R.layout.simple_list_item_1,
                    equipmentFromBDDString);

            listBag.setAdapter(arrayAdapter);
        }
    }

}