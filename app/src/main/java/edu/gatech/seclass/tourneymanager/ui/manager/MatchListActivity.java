package edu.gatech.seclass.tourneymanager.ui.manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.gatech.seclass.tourneymanager.R;
import edu.gatech.seclass.tourneymanager.core.capabilities.ManagerCapabilityFacade;
import edu.gatech.seclass.tourneymanager.core.models.Match;
import edu.gatech.seclass.tourneymanager.core.models.Player;
import edu.gatech.seclass.tourneymanager.core.models.Tournament;
import edu.gatech.seclass.tourneymanager.core.models.TournamentResult;
import edu.gatech.seclass.tourneymanager.ui.packages.ErrorDialog;

/**
 * Created by aelkugia3 on 3/1/17.
 */

public class MatchListActivity extends Activity {
    private ListView matchList;
    private List<Match> matches;
    private ManagerCapabilityFacade mgmtFacade;
    private SimpleAdapter adapter;
    private Match selectedMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list);
        try {
            matchList = (ListView) findViewById(R.id.mgmtMatchList);
            matchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,int position, long id)
                {
                    selectedMatch = matches.get(position);
                    String title = selectedMatch.getPlayer1().getUsername() + " vs " + selectedMatch.getPlayer2().getUsername();
                    Toast.makeText(getApplicationContext(),
                            String.format("Selected match \"%s\"", title),
                            Toast.LENGTH_SHORT).show();
                }});
            mgmtFacade = new ManagerCapabilityFacade();
            List<Tournament> tournaments = Tournament.find(Tournament.class, "active = 1");
            if(tournaments.size() <= 0) {
                FragmentManager fm = this.getFragmentManager();
                ErrorDialog dialog = new ErrorDialog();
                dialog.setMessage("No active tournament.");
                dialog.show(fm, "Nope");
                return;
            }
            mgmtFacade.startNextRoundIfPossible();
            matches = mgmtFacade.getReadyMatches();

            List<Map<String, String>> data = new ArrayList<Map<String, String>>();

            for(Match match: matches){
                Map<String, String> datum = new HashMap<String, String>(2);
                datum.put("First Line", match.getPlayer1().getUsername() + " vs " + match.getPlayer2().getUsername());
                String secondLine = "";
                if(match.getWinnerId() > 0) {
                    secondLine = "Ended";
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

    public void managerControl(View view) {
        Intent intent = new Intent(MatchListActivity.this, edu.gatech.seclass.tourneymanager.ui.manager.ManagerControlsActivity.class);

        startActivity(intent);
    }

    public void startMatch(View view) {
        try {
            if(selectedMatch == null) {
                throw new Exception("A match must be selected");
            }
            mgmtFacade.startMatch(selectedMatch);
            matches = mgmtFacade.getReadyMatches();

            List<Map<String, String>> data = new ArrayList<Map<String, String>>();

            for(Match match: matches){
                Map<String, String> datum = new HashMap<String, String>(2);
                datum.put("First Line", match.getPlayer1().getUsername() + " vs " + match.getPlayer2().getUsername());
                String secondLine = "";
                if(match.getWinnerId() > 0) {
                    secondLine = "Ended";
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

    public void endMatch(View view) {
        final FragmentManager fm = this.getFragmentManager();
        try {
            if(selectedMatch == null) {
                throw new Exception("A match must be selected");
            }
            final Player p1 = selectedMatch.getPlayer1();
            final Player p2 = selectedMatch.getPlayer2();
            final AlertDialog.Builder b = new AlertDialog.Builder(this);
            final Context context = this;

            b.setTitle("Who won?");
            String[] types = {p1.getUsername(), p2.getUsername()};
            b.setItems(types, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        dialog.dismiss();
                        switch (which) {
                            case 0:
                                mgmtFacade.endMatch(selectedMatch, p1);
                                break;
                            case 1:
                                mgmtFacade.endMatch(selectedMatch, p2);
                                break;
                        }
                        if(mgmtFacade.startNextRoundIfPossible()) {
                            ErrorDialog dialogErr = new ErrorDialog();
                            dialogErr.setMessage("The next round of matches has started!");
                            dialogErr.show(fm, "Awesome!");
                        }
                        matches = mgmtFacade.getReadyMatches();

                        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

                        for(Match match: matches){
                            Map<String, String> datum = new HashMap<String, String>(2);
                            datum.put("First Line", match.getPlayer1().getUsername() + " vs " + match.getPlayer2().getUsername());
                            String secondLine = "";
                            if(match.getWinnerId() > 0) {
                                secondLine = "Ended";
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
                        adapter = new SimpleAdapter(context, data,
                                android.R.layout.simple_list_item_2,
                                new String[] {"First Line", "Second Line" },
                                new int[] {android.R.id.text1, android.R.id.text2 });
                        matchList.setAdapter(adapter);
                    } catch(Exception e) {
                        ErrorDialog dialogErr = new ErrorDialog();
                        dialogErr.setMessage(e.getMessage());
                        dialogErr.show(fm, "Error");
                    }
                }

            });

            b.show();

        } catch(Exception e) {

            ErrorDialog dialog = new ErrorDialog();
            dialog.setMessage(e.getMessage());
            dialog.show(fm, "Error");
        }
    }

    public void endTournament(View view) {
        try{
            TournamentResult results = mgmtFacade.endTournament();
            Intent intent = new Intent(MatchListActivity.this, ManagerControlsActivity.class);

            if(results == null) {
                FragmentManager fm = this.getFragmentManager();
                ErrorDialog dialog = new ErrorDialog();
                dialog.setMessage("Tournament ended before all matches were completed, refund issued.");
                dialog.show(fm, "Tournament Ended");
            } else {
                FragmentManager fm = this.getFragmentManager();
                ErrorDialog dialog = new ErrorDialog();
                dialog.setMessage("Tournament ended results and prizes saved to database.");
                dialog.show(fm, "Tournament Ended");
            }

            startActivity(intent);
        } catch(Exception e) {
            FragmentManager fm = this.getFragmentManager();
            ErrorDialog dialog = new ErrorDialog();
            dialog.setMessage(e.getMessage());
            dialog.show(fm, "Error");
        }

    }
}
