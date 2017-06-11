package edu.gatech.seclass.tourneymanager.ui.manager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.tourneymanager.R;
import edu.gatech.seclass.tourneymanager.core.capabilities.ManagerCapabilityFacade;
import edu.gatech.seclass.tourneymanager.core.models.Player;

/**
 * Created by aelkugia3 on 3/1/17.
 */

public class RemovePlayerActivity extends Activity {
    private ListView removePlayerOptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_player);

        removePlayerOptions = (ListView) findViewById(R.id.removePlayerOptions);
        ManagerCapabilityFacade mgmtFacade = new ManagerCapabilityFacade();

        List<Player> players = mgmtFacade.getPlayersFromSystem();

        // Instanciating an array list (you don't need to do this,
        // you already have yours).
        List<String> playerArr = new ArrayList<String>();

        for(Player player: players){
            playerArr.add(player.getUsername());
        }

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                playerArr );

        removePlayerOptions.setAdapter(arrayAdapter);
    }

}
