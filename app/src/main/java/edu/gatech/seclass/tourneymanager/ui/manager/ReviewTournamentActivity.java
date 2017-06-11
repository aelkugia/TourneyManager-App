package edu.gatech.seclass.tourneymanager.ui.manager;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.tourneymanager.R;
import edu.gatech.seclass.tourneymanager.core.capabilities.ManagerCapabilityFacade;
import edu.gatech.seclass.tourneymanager.core.models.Tournament;
import edu.gatech.seclass.tourneymanager.core.models.TournamentPlayer;
import edu.gatech.seclass.tourneymanager.core.models.TournamentResult;
import edu.gatech.seclass.tourneymanager.ui.packages.ErrorDialog;

/**
 * Created by aelkugia3 on 3/1/17.
 */

public class ReviewTournamentActivity extends Activity {
    private TextView houseCut;
    private TextView firstPrize;
    private TextView secondPrize;
    private TextView thirdPrize;
    private ListView players;
    private long tourneyId;
    private ArrayAdapter<String> arrayAdapter;
    Tournament tournament;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_tournament);
        try {
            Intent i = getIntent();
            tourneyId = (long) i.getSerializableExtra("tournament");
            tournament = Tournament.find(Tournament.class, "id = ?", String.valueOf(tourneyId)).get(0);
            TournamentResult results = (TournamentResult) i.getSerializableExtra("results");

            houseCut = (TextView) findViewById(R.id.reviewHouseCut);
            firstPrize = (TextView) findViewById(R.id.review1stPrize);
            secondPrize = (TextView) findViewById(R.id.review2ndPrize);
            thirdPrize = (TextView) findViewById(R.id.review3rdPrize);
            players = (ListView) findViewById(R.id.reviewPlayers);

            houseCut.setText(String.valueOf(results.getProfit()));
            firstPrize.setText(String.valueOf(results.getFirstPrize()));
            secondPrize.setText(String.valueOf(results.getSecondPrize()));
            thirdPrize.setText(String.valueOf(results.getThirdPrize()));

            // Instanciating an array list (you don't need to do this,
            // you already have yours).
            List<String> playerArr = new ArrayList<String>();

            for(TournamentPlayer player: tournament.getPlayers()){
                playerArr.add(player.getUsername());
            }

            // This is the array adapter, it takes the context of the activity as a
            // first parameter, the type of list view as a second parameter and your
            // array as a third parameter.
            arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    playerArr );

            players.setAdapter(arrayAdapter);
        } catch(Exception e) {
            FragmentManager fm = this.getFragmentManager();
            ErrorDialog dialog = new ErrorDialog();
            dialog.setMessage(e.getMessage());
            dialog.show(fm, "Error");
        }
    }

    public void matchList(View view) {
        try {
            Intent intent = new Intent(ReviewTournamentActivity.this, edu.gatech.seclass.tourneymanager.ui.manager.MatchListActivity.class);
            ManagerCapabilityFacade mgmtFacade = new ManagerCapabilityFacade();
            mgmtFacade.runTournament(tournament);
            startActivity(intent);
        } catch(Exception e) {
            FragmentManager fm = this.getFragmentManager();
            ErrorDialog dialog = new ErrorDialog();
            dialog.setMessage(e.getMessage());
            dialog.show(fm, "Error");
        }

    }

}
