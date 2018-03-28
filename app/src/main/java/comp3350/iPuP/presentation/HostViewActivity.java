package comp3350.iPuP.presentation;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;

import comp3350.iPuP.R;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.objects.ParkingSpot;

public class HostViewActivity extends ListActivity
{
    private AccessParkingSpots accessParkingSpots;
    SpotAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_view);

        accessParkingSpots = new AccessParkingSpots();

        populateList();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
            populateList();
    }

    private void populateList()
    {
        String name = getIntent().getStringExtra(getResources().getString(R.string.extra_name));
        try
        {
            ArrayList<ParkingSpot> parkingSpots = accessParkingSpots.getMyHostedSpots(name);
            adapter = new SpotAdapter(this, parkingSpots);
            setListAdapter(adapter);
        }
        catch (DAOException daoe)
        {
            Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}