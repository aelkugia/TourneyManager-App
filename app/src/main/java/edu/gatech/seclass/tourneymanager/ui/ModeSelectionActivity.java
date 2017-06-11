package edu.gatech.seclass.tourneymanager.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.gatech.seclass.tourneymanager.R;
import edu.gatech.seclass.tourneymanager.core.capabilities.PlayerCapabilityFacade;
import edu.gatech.seclass.tourneymanager.ui.manager.ManagerControlsActivity;
import edu.gatech.seclass.tourneymanager.ui.manager.MatchListActivity;
import edu.gatech.seclass.tourneymanager.ui.player.PlayerTotalsActivity;
import edu.gatech.seclass.tourneymanager.ui.player.PlayerTourneyActivity;

/**
 * Created by aelkugia3 on 3/1/17.
 */

public class ModeSelectionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mode);
    }

    //if no ongoing tournament
    public void playerView(View view) {
        PlayerCapabilityFacade pcf = new PlayerCapabilityFacade();
        Intent intent;
        if(pcf.isTournamentActive()) {
            intent = new Intent(ModeSelectionActivity.this, PlayerTourneyActivity.class);
        } else {
            intent = new Intent(ModeSelectionActivity.this, PlayerTotalsActivity.class);
        }

        startActivity(intent);

    //else onclick go to PlayerTourneyActivity
    }

    public void gotoManager(View view) {
        PlayerCapabilityFacade pcf = new PlayerCapabilityFacade();
        Intent intent;
        if(pcf.isTournamentActive()) {
            intent = new Intent(ModeSelectionActivity.this, MatchListActivity.class);
        } else {
            intent = new Intent(ModeSelectionActivity.this, ManagerControlsActivity.class);
        }

        startActivity(intent);

    }
}
