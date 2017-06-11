package edu.gatech.seclass.tourneymanager.ui.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    ListView removePlayerOptions;
    Button deleteBtn;
    List<String> playerArr = new ArrayList<String>();
    List<String> username;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_player);

        removePlayerOptions = (ListView) findViewById(R.id.removePlayerOptions);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        username = new ArrayList<>();



        ManagerCapabilityFacade MCF = new ManagerCapabilityFacade();

        List<Player> players = MCF.getPlayersFromSystem();

        for(Player player: players){
            playerArr.add(player.getUsername());
        }


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, playerArr );

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
