package com.cecile_melay.barcodebattler_hubertmelay.fragments.views;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cecile_melay.barcodebattler_hubertmelay.MainActivity;
import com.cecile_melay.barcodebattler_hubertmelay.R;
import com.cecile_melay.barcodebattler_hubertmelay.database.dao.CreatureDAO;
import com.cecile_melay.barcodebattler_hubertmelay.entities.Creature;
import com.cecile_melay.barcodebattler_hubertmelay.fragments.MyFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Utilisateur on 25/10/2017.
 */

public class DisplayCreatures extends MyFragment {

    ListView listCreatures;
    private TextView numberOfCreatures;

    //Creature data
    private String name;
    private int hp;
    private String type;
    private  int id;
    private int imagePath;

    //Detect wich action to launch (local fight, nfc fight, or show creature details)
    String actionOnClickItem;

    //Creature choosen for local fight
    String creatureID1 = "";
    String creatureID2 = "";

    @Override
    protected int getLayoutId() {return R.layout.display_creatures;}

    @Override
    protected String getTitle() {
        return null;
    }

    @Override
    protected void init() {
        actionOnClickItem = ((MainActivity) getActivity()).getParams();
        listCreatures = (ListView)contentView.findViewById(R.id.listCreatures);
        numberOfCreatures = (TextView)contentView.findViewById(R.id.textView2);
        CreatureDAO creatureDAO = new CreatureDAO(this.getContext());
        creatureDAO.open();
        List<Creature> creaturesFromBDD = creatureDAO.getAllCreature();
        if(creaturesFromBDD == null) {
            Toast.makeText(this.getContext(), "No creatures", Toast.LENGTH_LONG).show();
            numberOfCreatures.setText("No creatures");
        }
        else {
            List<String> creaturesFromBDDString = creatureDAO.creaturesToString(creaturesFromBDD);
            numberOfCreatures.setText(creaturesFromBDD.size()+" creatures");

            //Création de la ArrayList qui nous permettra de remplire la listView
            ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

            //On déclare la HashMap qui contiendra les informations pour un item
            HashMap<String, String> map;

            for (Creature creature : creaturesFromBDD) {
                id = creature.getId();
                name = creature.getName();
                hp = creature.getHp();
                type = creature.getType();
                imagePath = creature.getImagePath();
                //Création d'une HashMap pour insérer les informations du premier item de notre listView
                map = new HashMap<String, String>();
                //on insère un élément titre que l'on récupérera dans le textView titre créé dans le fichier affichageitem.xml
                map.put("id", ""+id);
                map.put("titre", name);

                //on insère la référence à l'image (convertit en String car normalement c'est un int) que l'on récupérera dans l'imageView créé dans le fichier affichageitem.xml
                //map.put("img", String.valueOf(R.mipmap.creature_alien_1));
                map.put("img", String.valueOf(imagePath));

                /*ImageView img=(ImageView)findViewById(R.id.imageView1);
                String[] imageArray = {"1", "2"};
                Random rand = new Random();

                int rndInt = rand.nextInt(1) + 1;
                int resID = getResources().getIdentifier(imageArray[rndInt], "drawable",  getContext().getPackageName());
                img.setImageResource(resID);*/


                //on insère un élément description que l'on récupérera dans le textView description créé dans le fichier affichageitem.xml
                map.put("description", "PV :"+ hp + " Type :" + type);
                //enfin on ajoute cette hashMap dans la arrayList
                listItem.add(map);
            }

            //Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (listItem) dans la vue affichageitem
            SimpleAdapter mSchedule = new SimpleAdapter (this.getActivity().getBaseContext(), listItem, R.layout.display_entities_in_list,
                    new String[] {"img", "titre", "description"}, new int[] {R.id.img, R.id.titre, R.id.description});

            //On attribut à notre listView l'adapter que l'on vient de créer
            listCreatures.setAdapter(mSchedule);

            //Enfin on met un écouteur d'évènement sur notre listView
            listCreatures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                @SuppressWarnings("unchecked")
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                    //on récupère la HashMap contenant les infos de notre item (titre, description, img)
                    HashMap<String, String> map = (HashMap<String, String>) listCreatures.getItemAtPosition(position);
                    switch(actionOnClickItem) {
                        case "onClickShowCreatureDetails":
                            ((MainActivity) getActivity()).startDisplayCreatureFragment(DisplayCreature.class, map.get("id"));
                            break;
                        case "OnClickLaunchLocalFight":
                            if (creatureID1 == "") {
                                creatureID1 = map.get("id");
                            } else if(creatureID2 == "") {
                                creatureID2 = map.get("id");
                                ((MainActivity) getActivity()).startDisplayLocalFightFragment(DisplayLocalFight.class, creatureID1, creatureID2);
                            }
                            break;
                        case "OnClickLaunchNFCFight":
                            //todo code launch your activity fight
                            break;
                        default:
                            break;
                    }

                }
            });

        }
    }
}