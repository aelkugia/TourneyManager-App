package edu.gatech.seclass.tourneymanager.ui.manager;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.tourneymanager.R;
import edu.gatech.seclass.tourneymanager.core.capabilities.ManagerCapabilityFacade;

/**
 * Created by aelkugia3 on 3/1/17.
 */

public class HouseProfitsActivity extends Activity {
    private ListView houseProfitsListView;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_profits);

        houseProfitsListView = (ListView) findViewById(R.id.houseProfitsList);
        ManagerCapabilityFacade managerCapabilityFacade = new ManagerCapabilityFacade();
        List<Integer> houseProfits = managerCapabilityFacade.getHouseWinnings();

        // Instanciating an array list (you don't need to do this,
        // you already have yours).
        List<String> profitsArr = new ArrayList<String>();

        for(Integer profit: houseProfits){
            profitsArr.add(profit.toString());
        }

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                profitsArr );

        houseProfitsListView.setAdapter(arrayAdapter);
    }
}


