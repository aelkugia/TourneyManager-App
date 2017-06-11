package edu.gatech.seclass.tourneymanager.ui.player;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.gatech.seclass.tourneymanager.R;
import edu.gatech.seclass.tourneymanager.core.capabilities.ManagerCapabilityFacade;
import edu.gatech.seclass.tourneymanager.core.models.Match;
import edu.gatech.seclass.tourneymanager.core.models.Tournament;
import edu.gatech.seclass.tourneymanager.ui.packages.ErrorDialog;

/**
 * Created by aelkugia3 on 3/1/17.
 */

public class PlayerTourneyActivity extends Activity {
    private ListView matchList;
    private List<Match> matches;
    private ManagerCapabilityFacade mgmtFacade;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_tourney);
        try {
            matchList = (ListView) findViewById(R.id.playerMatchList);
            mgmtFacade = new ManagerCapabilityFacade();
            List<Tournament> tournaments = Tournament.find(Tournament.class, "active = 1");

            matches = mgmtFacade.getReadyMatches();

            List<Map<String, String>> data = new ArrayList<Map<String, String>>();

            for(Match match: matches){
                Map<String, String> datum = new HashMap<String, String>(2);
                datum.put("First Line", match.getPlayer1().getUsername() + " vs " + match.getPlayer2().getUsername());
                String secondLine = "";
                if(match.getWinnerId() > 0) {
                    secondLine = match.getWinner().getUsername() + " won";
                } else if(match.isStarted()) {
                    secondLine = "Started";
                } else {
                    secondLine = "Not Started";
                }

                if(match.isFinalMatch()) {
                    secondLine = "First Place Match - " + secondLine;
                } else if(match.isThirdPlaceMatch()) {
                    secondLine = "Third Place Match - " + secondLine;
                }

                datum.put("Second Line",secondLine);
                data.add(datum);
            }

            // This is the array adapter, it takes the context of the activity as a
            // first parameter, the type of list view as a second parameter and your
            // array as a third parameter.
            adapter = new SimpleAdapter(this, data,
                    android.R.layout.simple_list_item_2,
                    new String[] {"First Line", "Second Line" },
                    new int[] {android.R.id.text1, android.R.id.text2 });

            matchList.setAdapter(adapter);
        } catch(Exception e) {
            FragmentManager fm = this.getFragmentManager();
            ErrorDialog dialog = new ErrorDialog();
            dialog.setMessage(e.getMessage());
            dialog.show(fm, "Error");
        }
    }

}
