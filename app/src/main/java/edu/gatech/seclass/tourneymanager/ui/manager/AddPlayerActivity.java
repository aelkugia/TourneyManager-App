package edu.gatech.seclass.tourneymanager.ui.manager;


import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import edu.gatech.seclass.tourneymanager.R;
import edu.gatech.seclass.tourneymanager.core.capabilities.ManagerCapabilityFacade;
import edu.gatech.seclass.tourneymanager.core.models.DeckType;
import edu.gatech.seclass.tourneymanager.core.models.Player;
import edu.gatech.seclass.tourneymanager.ui.packages.ErrorDialog;

/**
 * Created by aelkugia3 on 3/1/17.
 */

public class AddPlayerActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private EditText nameET;
    private EditText usernameET;
    private EditText phoneNumberET;

    Spinner spinner;

    public static final String TAG = "AddPlayerActivity";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        nameET = (EditText)findViewById(R.id.nameET);
        usernameET = (EditText)findViewById(R.id.usernameET);
        phoneNumberET = (EditText) findViewById(R.id.phoneNumberET);

        spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setAdapter(new ArrayAdapter<DeckType>(this, android.R.layout.simple_spinner_item, DeckType.values()));

        spinner.setOnItemSelectedListener(this);

    }

    public void AddPlayerToDb(View view) throws Exception {

        Intent intent = new Intent(AddPlayerActivity.this, ManagerControlsActivity.class);

        ManagerCapabilityFacade MCF = new ManagerCapabilityFacade();

        String name = nameET.getText().toString();
        String username = usernameET.getText().toString();
        String phoneNumber = phoneNumberET.getText().toString();
        //String deckChoice = deckChoiceET.getText().toString();

        Player player = new Player(name,username,phoneNumber, DeckType.values()[5]);
        player.save();

        try {
            MCF.addPlayerToSystem(player);
        } catch (Exception e) {
            FragmentManager fm = this.getFragmentManager();
            ErrorDialog dialog = new ErrorDialog();
            dialog.setMessage("Error creating player.");
            dialog.show(fm, "Error");
        }

        startActivity(intent);

    }

    private void dataVariable() {

        nameET = (EditText) findViewById(R.id.nameET);

        nameET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    Log.d(TAG, "nameET focused");
                else {

                }

            }
        });

        usernameET = (EditText) findViewById(R.id.usernameET);

        usernameET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    Log.d(TAG, "usernameET focused");
                else {

                }

            }
        });

        phoneNumberET = (EditText) findViewById(R.id.phoneNumberET);

        phoneNumberET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    Log.d(TAG, "phoneNumberET focused");
                else {

                }

            }
        });

        //deckChoiceET = (EditText) findViewById(R.id.deckChoiceET);

        /*deckChoiceET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    Log.d(TAG, "deckChoiceET focused");
                else {

                }

            }
        });*/


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //TextView myText = (TextView) view;
        //Toast.makeText(this,"You Selected "+myText.getText(), Toast.LENGTH_SHORT) .show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("AddPlayer Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    */
}
