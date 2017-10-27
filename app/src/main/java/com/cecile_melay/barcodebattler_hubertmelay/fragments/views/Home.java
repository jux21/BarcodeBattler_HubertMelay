package com.cecile_melay.barcodebattler_hubertmelay.fragments.views;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.cecile_melay.barcodebattler_hubertmelay.EntityCatch;
import com.cecile_melay.barcodebattler_hubertmelay.R;
import com.cecile_melay.barcodebattler_hubertmelay.fragments.MyFragment;

/**
 * Created by Utilisateur on 23/10/2017.
 */

public class Home extends MyFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.home;
    }

    @Override
    protected String getTitle() {
        return null;
    }

    public void init() {
        FloatingActionButton fab = (FloatingActionButton) contentView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the camera action
                Intent entityCatch = new Intent(getActivity(), EntityCatch.class);
                startActivity(entityCatch);
            }
        });
    }
}
