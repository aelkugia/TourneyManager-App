package edu.gatech.seclass.tourneymanager.ui.manager;

import android.app.Activity;
<<<<<<< HEAD
import android.content.Intent;
=======
import android.app.FragmentManager;
import android.content.Context;
>>>>>>> 7f6d433bc3b1cc9f99dc1934d24214b179ef5da6
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
<<<<<<< HEAD

    ListView removePlayerOptions;
    Button deleteBtn;
    List<String> playerArr = new ArrayList<String>();
    List<String> username;
    ArrayAdapter<String> adapter;

=======
    private ListView removePlayerOptions;
    private List<Player> players;
    private ArrayAdapter<String> arrayAdapter;
>>>>>>> 7f6d433bc3b1cc9f99dc1934d24214b179ef5da6

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_player);
        Context context = this;

        removePlayerOptions = (ListView) findViewById(R.id.removePlayerOptions);
<<<<<<< HEAD
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        username = new ArrayList<>();


=======
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
>>>>>>> 7f6d433bc3b1cc9f99dc1934d24214b179ef5da6

        ManagerCapabilityFacade MCF = new ManagerCapabilityFacade();

        List<Player> players = MCF.getPlayersFromSystem();

        for(Player player: players){
            playerArr.add(player.getUsername());
        }

<<<<<<< HEAD

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, playerArr );
=======
        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        arrayAdapter = new ArrayAdapter<String>(
                context,
                android.R.layout.simple_list_item_1,
                playerArr );
>>>>>>> 7f6d433bc3b1cc9f99dc1934d24214b179ef5da6

        removePlayerOptions.setAdapter(arrayAdapter);

        removePlayerOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int pos, long id) {
                //username.setText(username.get(pos));
                //username.get(pos);
                //String player = (removePlayerOptions.getItemAtPosition(pos).getName();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        /*try {
            MCF.removePlayerFromSystem(player);
        } catch (Exception e) {
            Log.e(TAG,"Remove player fail exception");
        }*/

    }

    private void delete(){

        int pos = removePlayerOptions.getCheckedItemPosition();

        if(pos > -1) {

            adapter.remove(username.get(pos));

            adapter.notifyDataSetChanged();

        }

    }

    public void RemovePlayer(View view) throws Exception{

        //Intent intent = new Intent(RemovePlayerActivity.this, ManagerControlsActivity.class);


        //startActivity(intent);

    }

}
