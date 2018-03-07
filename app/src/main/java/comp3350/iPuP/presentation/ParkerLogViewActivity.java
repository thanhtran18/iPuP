package comp3350.iPuP.presentation;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    ArrayList<ParkingSpot> parkingSpots = new ArrayList<>();
    ArrayList<ParkingSpot> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        String name;
        //String name = getIntent().getExtras().getString("name");
        //String name = getIntent().getStringExtra("name");
        //String name = getIntent().getExtras();
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            name = null;
        } else {
            name = extras.getString("name");
        }
        setContentView(R.layout.activity_parker_log_view);

        accessParkingSpots = new AccessParkingSpots();

        ListView list = findViewById(android.R.id.list);

        try
        {
            parkingSpots = accessParkingSpots.getMySpots(name);
            //ArrayList<ParkingSpot> parkingSpots = accessParkingSpots.getAllSpots();
            for (ParkingSpot spot : parkingSpots)
            {
                arrayList.add(spot);
            }

            adapter = new ArrayAdapter<ParkingSpot>(this, android.R.layout.simple_list_item_1, arrayList);
            //setListAdapter(adapter);
            list.setAdapter(adapter);

            registerForContextMenu(list);
        }
        catch (ParseException pe)
        {
            System.out.print(pe.getMessage());
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_context_delete, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo obj = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();


        switch (item.getItemId())
        {
            case R.id.delete:
                arrayList.remove(obj.position);
                adapter.notifyDataSetChanged();
                break;
        }

        return super.onContextItemSelected(item);
    }
}

