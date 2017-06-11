package edu.gatech.seclass.tourneymanager.ui.manager;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.content.Intent;

import edu.gatech.seclass.tourneymanager.R;
import edu.gatech.seclass.tourneymanager.ui.player.PlayerTotalsActivity;

/**
 * Created by aelkugia3 on 3/1/17.
 */

public class ManagerControlsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_controls);
    }

    public void addPlayer(View view) {
        Intent intent = new Intent(ManagerControlsActivity.this, edu.gatech.seclass.tourneymanager.ui.manager.AddPlayerActivity.class);

        startActivity(intent);
    }

    public void removePlayer(View view) {
        Intent intent = new Intent(ManagerControlsActivity.this, RemovePlayerActivity.class);

        startActivity(intent);
    }

    public void startTournament(View view) {
        Intent intent = new Intent(ManagerControlsActivity.this, StartTournamentActivity.class);

        startActivity(intent);
    }

    public void playerTotals(View view) {
        Intent intent = new Intent(ManagerControlsActivity.this, PlayerTotalsActivity.class);

        startActivity(intent);
    }

    public void houseProfits(View view) {
        Intent intent = new Intent(ManagerControlsActivity.this, edu.gatech.seclass.tourneymanager.ui.manager.HouseProfitsActivity.class);

        startActivity(intent);
    }

    public void matchList(View view) {
        Intent intent = new Intent(ManagerControlsActivity.this, MatchListActivity.class);

        startActivity(intent);
    }

    public void playerTotalsList(View view) {
        Intent intent = new Intent(ManagerControlsActivity.this, ManagerPlayerTotals.class);

        startActivity(intent);
    }


}
