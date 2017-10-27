package com.cecile_melay.barcodebattler_hubertmelay.fragments.views;

import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cecile_melay.barcodebattler_hubertmelay.R;
import com.cecile_melay.barcodebattler_hubertmelay.database.dao.CreatureDAO;
import com.cecile_melay.barcodebattler_hubertmelay.database.dao.EquipmentDAO;
import com.cecile_melay.barcodebattler_hubertmelay.database.dao.PotionDAO;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Creature;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Equipment;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Potion;
import com.cecile_melay.barcodebattler_hubertmelay.fragments.MyFragment;

import java.util.List;

/**
 * Created by Utilisateur on 25/10/2017.
 */

public class DisplayEquipmentAndPotions extends MyFragment {

    ListView listBag;
    List<String> listEquipment;
    List<String> listPotion;
    ArrayAdapter arrayAdapterBag;

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

        getActivity().setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        listBag = (ListView)contentView.findViewById(R.id.bagContent);

        EquipmentDAO equipmentDAO = new EquipmentDAO(this.getContext());
        equipmentDAO.open();
        List<Equipment> equipmentsFromBDD = equipmentDAO.getAllEquipment();
        if(equipmentsFromBDD == null) {
            Toast.makeText(this.getContext(), "No equipment", Toast.LENGTH_LONG).show();
        }
        else {
            listEquipment = equipmentDAO.equipmentToString(equipmentsFromBDD);
        }

        PotionDAO potionDAO = new PotionDAO(this.getContext());
        potionDAO.open();
        List<Potion> potionsFromBDD = potionDAO.getAllPotion();
        if(potionsFromBDD == null) {
            Toast.makeText(this.getContext(), "No potion", Toast.LENGTH_LONG).show();
        }
        else {
            listPotion = potionDAO.potionToString(potionsFromBDD);
        }

        // On combine les arrays et on les affiche dans la listView
        listEquipment.addAll(listPotion);
        arrayAdapterBag = new ArrayAdapter<String>(
                this.getContext(),
                android.R.layout.simple_list_item_1,
                listEquipment);
        listBag.setAdapter(arrayAdapterBag);
    }

}