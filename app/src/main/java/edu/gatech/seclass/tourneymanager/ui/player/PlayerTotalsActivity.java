package edu.gatech.seclass.tourneymanager.ui.player;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.gatech.seclass.tourneymanager.R;
import edu.gatech.seclass.tourneymanager.core.capabilities.PlayerCapabilityFacade;
import edu.gatech.seclass.tourneymanager.core.models.Player;

/**
 * Created by aelkugia3 on 3/1/17.
 */

public class PlayerTotalsActivity extends Activity {
    private ListView playerTotalsListView;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_totals);

        playerTotalsListView = (ListView) findViewById(R.id.allPlayerTotals);
        PlayerCapabilityFacade playerCapabilityFacade = new PlayerCapabilityFacade();
        List<Player> players = playerCapabilityFacade.getAllPlayerTotals();

        // Instanciating an array list (you don't need to do this,
        // you already have yours).
        List<String> playerArr = new ArrayList<String>();
        List<PlayerTotal> totals = new ArrayList<>();

        for(Player player: players){
            int total = 0;
            if(player.getPrizes() != null) {
                for(int prize: player.getPrizes()) {
                    total += prize;
                }
            }
            totals.add(new PlayerTotal(total, player.getUsername()));
        }

        Collections.sort(totals);

        for(PlayerTotal player: totals) {
            playerArr.add(player.getTotal() + " - " + player.getUsername());
        }

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                playerArr );

        playerTotalsListView.setAdapter(arrayAdapter);
    }

    public class PlayerTotal implements Comparable<PlayerTotal> {
        private int total;
        private String username;

        public PlayerTotal(int total, String username) {
            this.total = total;
            this.username = username;
        }

        public int getTotal() {
            return total;
        }

        public String getUsername() {
            return username;
        }



        @Override
        public int compareTo(PlayerTotal another) {
            return another.getTotal() - this.getTotal();
        }
    }
}
