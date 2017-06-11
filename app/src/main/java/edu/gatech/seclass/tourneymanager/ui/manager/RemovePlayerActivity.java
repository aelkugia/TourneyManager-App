package edu.gatech.seclass.tourneymanager.ui.manager;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.tourneymanager.R;
import edu.gatech.seclass.tourneymanager.core.capabilities.ManagerCapabilityFacade;
import edu.gatech.seclass.tourneymanager.core.models.Player;
import edu.gatech.seclass.tourneymanager.ui.packages.ErrorDialog;

/**
 * Created by aelkugia3 on 3/1/17.
 */

public class RemovePlayerActivity extends Activity {
    private ListView removePlayerOptions;
    private List<Player> players;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_player);
        Context context = this;

        removePlayerOptions = (ListView) findViewById(R.id.removePlayerOptions);
        final ManagerCapabilityFacade mgmtFacade = new ManagerCapabilityFacade();

        players = mgmtFacade.getPlayersFromSystem();
        final FragmentManager fm = this.getFragmentManager();

        removePlayerOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   try {
                       Player player = players.get(position);
                       mgmtFacade.removePlayerFromSystem(player);
                       String user = arrayAdapter.getItem(position);
                       arrayAdapter.remove(user);
                       Toast.makeText(getApplicationContext(),
                               String.format("Removed \"%s\".", player.getUsername()),
                               Toast.LENGTH_SHORT).show();
                   } catch (Exception e) {
                       ErrorDialog dialog = new ErrorDialog();
                       dialog.setMessage(e.getMessage());
                       dialog.show(fm, "Error");
                   }
               }
           });

        // Instanciating an array list (you don't need to do this,
        // you already have yours).
        List<String> playerArr = new ArrayList<String>();

        for(Player player: players){
            playerArr.add(player.getUsername());
        }

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        arrayAdapter = new ArrayAdapter<String>(
                context,
                android.R.layout.simple_list_item_1,
                playerArr );

        removePlayerOptions.setAdapter(arrayAdapter);
    }

}
