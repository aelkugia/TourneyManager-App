package edu.gatech.seclass.tourneymanager.ui.manager;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.tourneymanager.R;
import edu.gatech.seclass.tourneymanager.core.capabilities.ManagerCapabilityFacade;
import edu.gatech.seclass.tourneymanager.core.models.Player;
import edu.gatech.seclass.tourneymanager.core.models.Tournament;
import edu.gatech.seclass.tourneymanager.core.models.TournamentResult;
import edu.gatech.seclass.tourneymanager.ui.packages.ErrorDialog;

/**
 * Created by aelkugia3 on 3/1/17.
 */

public class StartTournamentActivity extends Activity {
    private EditText entryFee;
    private EditText houseCut;
    private ListView players;
    private List<String> usernames;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_tournament);

        entryFee = (EditText) findViewById(R.id.entranceFee);
        houseCut = (EditText) findViewById(R.id.houseCut);
        players = (ListView) findViewById(R.id.tournamentPlayersListView);
        usernames = new ArrayList<>();

        players.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {
                String username = players.getItemAtPosition(position).toString();
                usernames.add(username);
                String user = arrayAdapter.getItem(position);
                arrayAdapter.remove(user);
                Toast.makeText(getApplicationContext(),
                        String.format("Added \"%s\"", username),
                        Toast.LENGTH_SHORT).show();
            }});

        ManagerCapabilityFacade mgmtFacade = new ManagerCapabilityFacade();

        List<Player> systemPlayers = mgmtFacade.getPlayersFromSystem();

        // Instanciating an array list (you don't need to do this,
        // you already have yours).
        List<String> playerArr = new ArrayList<String>();

        for(Player player: systemPlayers){
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
    }

    public void goToReview(View view) {
        Intent intent = new Intent(StartTournamentActivity.this, edu.gatech.seclass.tourneymanager.ui.manager.ReviewTournamentActivity.class);
        try {
            int entryFeeVal = Integer.parseInt(entryFee.getText().toString());
            int houseCutVal = Integer.parseInt(houseCut.getText().toString());
            Tournament tournament = new Tournament(houseCutVal, entryFeeVal, false, 0);
            for(String username: usernames) {
                Player player = Player.find(Player.class, "username = ?", username).get(0);
                tournament.addPlayer(player);
            }
            if(tournament.getPlayers().size() != 8 && tournament.getPlayers().size() != 16) {
                throw new Exception(String.format("Tournament must have either 8 or 16 players, %s were provided.", tournament.getPlayers().size()));
            }

            ManagerCapabilityFacade mgmtFacade = new ManagerCapabilityFacade();
            TournamentResult results = mgmtFacade.simulateTournamentResult(tournament);
            intent.putExtra("results", results);
            long id = mgmtFacade.saveTournament(tournament);
            intent.putExtra("tournament", id);
            startActivity(intent);
        } catch(Exception e) {
            FragmentManager fm = this.getFragmentManager();
            ErrorDialog dialog = new ErrorDialog();
            dialog.setMessage(e.getMessage());
            dialog.show(fm, "Error");
        }

    }

}
