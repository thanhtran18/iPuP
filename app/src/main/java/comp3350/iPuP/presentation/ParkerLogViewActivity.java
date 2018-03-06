package comp3350.iPuP.presentation;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.ParseException;
import java.util.ArrayList;

import comp3350.iPuP.R;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.ParkingSpot;

/**
 * Created by ThanhTran on 2018-03-05.
 */

public class ParkerLogViewActivity extends ListActivity
{
    private AccessParkingSpots accessParkingSpots;
    ArrayAdapter<ParkingSpot> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        String name = getIntent().getStringExtra("name");
        setContentView(R.layout.activity_parker_log_view);

        accessParkingSpots = new AccessParkingSpots();

        ListView list = findViewById(android.R.id.list);

        try {
            ArrayList<ParkingSpot> parkingSpots = accessParkingSpots.getMySpots(name);

            adapter = new ArrayAdapter<ParkingSpot>(this, android.R.layout.simple_list_item_1, parkingSpots);
            setListAdapter(adapter);
        }
        catch (ParseException pe)
        {
            System.out.print(pe.getMessage());
        }
    }
}

