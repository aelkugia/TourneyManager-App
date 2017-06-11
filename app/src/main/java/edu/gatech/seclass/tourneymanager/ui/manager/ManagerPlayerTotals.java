package edu.gatech.seclass.tourneymanager.ui.manager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import edu.gatech.seclass.tourneymanager.R;
import edu.gatech.seclass.tourneymanager.core.capabilities.PlayerCapabilityFacade;
import edu.gatech.seclass.tourneymanager.core.models.Player;

public class ManagerPlayerTotals extends Activity {
    private ListView playersListView;
    private ListView playerDetails;
    private ArrayAdapter<String> arrayAdapter1;
    private ArrayAdapter<String> arrayAdapter2;
    private List<PlayerTotal2> playerList;
    private PlayerTotal2 playerSel;
    private HashMap<String, List<Integer>> playerMaps;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_player_totals);
        playerDetails = (ListView) findViewById(R.id.individualPlayers);
        playersListView = (ListView) findViewById(R.id.playerTotals);
        context = this;

        playersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                playerSel = playerList.get(position);
                String title = playerSel.getUsername() + " selected";
                List<String> prizes = new ArrayList<String>();
                if(playerMaps.containsKey(playerSel.getUsername()) &&
                        playerMaps.get(playerSel.getUsername()) != null) {
                    for(int prize: playerMaps.get(playerSel.getUsername())) {
                        prizes.add(String.valueOf(prize));
                    }
                }

                if(prizes.size() == 0) {
                    prizes.add("");
                }

                // This is the array adapter, it takes the context of the activity as a
                // first parameter, the type of list view as a second parameter and your
                // array as a third parameter.
                arrayAdapter2 = new ArrayAdapter<String>(
                        context,
                        android.R.layout.simple_list_item_1,
                        prizes );

                playerDetails.setAdapter(arrayAdapter2);

                Toast.makeText(getApplicationContext(),
                        title,
                        Toast.LENGTH_SHORT).show();
            }});

        PlayerCapabilityFacade playerCapabilityFacade = new PlayerCapabilityFacade();
        List<Player> players = playerCapabilityFacade.getAllPlayerTotals();

        // Instanciating an array list (you don't need to do this,
        // you already have yours).
        List<String> playerArr = new ArrayList<String>();
        playerList = new ArrayList<>();
        playerMaps = new HashMap<>();

        for(Player player: players){
            int total = 0;
            playerMaps.put(player.getUsername(), player.getPrizes());
            if(player.getPrizes() != null) {
                for(int prize: player.getPrizes()) {
                    total += prize;
                }
            }
            playerList.add(new PlayerTotal2(total, player.getUsername()));
        }

        Collections.sort(playerList);

        for(PlayerTotal2 player: playerList) {
            playerArr.add(player.getTotal() + " - " + player.getUsername());
        }

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        arrayAdapter1 = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                playerArr );

        playersListView.setAdapter(arrayAdapter1);
    }

    public class PlayerTotal2 implements Comparable<PlayerTotal2> {
        private int total;
        private String username;

        public PlayerTotal2(int total, String username) {
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
        public int compareTo(PlayerTotal2 another) {
            return another.getTotal() - this.getTotal();
        }
    }
}
