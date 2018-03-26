package comp3350.iPuP.presentation;

import comp3350.iPuP.R;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.objects.DateFormatter;
import comp3350.iPuP.objects.ParkingSpot;

import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.util.Date;

import java.util.ArrayList;
import java.util.Calendar;

public class ParkerSearchActivity extends ListActivity implements DateFragmentObserver
{
    private DateFormatter df;
    Calendar current;

    private AccessParkingSpots accessParkingSpots;
    ArrayAdapter<ParkingSpot> adapter;
    ArrayList<ParkingSpot> parkingSpots = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parker_search);

        current = Calendar.getInstance();
        df = new DateFormatter();

        TextView tv = findViewById(R.id.textViewDate);
        tv.setText(df.getDateFormat().format(current.getTime()));

        populateScreen();

        SearchView sv = findViewById(R.id.searchViewAddress);
        sv.setQueryHint("Search by street name");
        sv.setSubmitButtonEnabled(true);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {

            @Override
            public boolean onQueryTextSubmit(String s)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                    populateScreen();
                return false;
            }
        });

    }


    public void onPrevClick(View v)
    {

        current.add(Calendar.DATE, -1);
        TextView tv = findViewById(R.id.textViewDate);
        tv.setText(df.getDateFormat().format(current.getTime()));
        populateScreen();
    }

    public void onNextClick(View v)
    {
        current.add(Calendar.DATE, +1);
        TextView tv = findViewById(R.id.textViewDate);
        tv.setText(df.getDateFormat().format(current.getTime()));
        populateScreen();
    }

    public void onDateClick(View v)
    {
        DialogFragment dateFragment = DatePickerFragment.newInstance();
        dateFragment.show(getFragmentManager(), "DatePicker");
    }

    public String getSearchText()
    {
        SearchView streetName = findViewById(R.id.searchViewAddress);
        CharSequence charName = streetName.getQuery();
        String searchText = charName.toString();
        if(searchText.trim().length() == 0 )
        {
            return null;
        }
        return searchText;
    }


    public void populateScreen()
    {
        accessParkingSpots = new AccessParkingSpots();
        ListView list = findViewById(android.R.id.list);

        try
        {
            parkingSpots.clear();

            parkingSpots = accessParkingSpots.getDailySpots(getSearchText(), current.getTime());

            adapter = new ArrayAdapter<ParkingSpot>(this, android.R.layout.simple_list_item_1, parkingSpots);
            list.setAdapter(adapter);

            registerForContextMenu(list);
        }
        catch (DAOException daoe)
        {
            Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        String name;
        Bundle extras = getIntent().getExtras();
        if(extras == null)
        {
            name = null;
        }
        else
        {
            name = extras.getString(getResources().getString(R.string.extra_name));
        }

        super.onListItemClick(l, v, position, id);
        ParkingSpot currItem = parkingSpots.get(position);

        Intent intent = new Intent(getApplicationContext(), BookTimeSlotsActivity.class);
        intent.putExtra(getResources().getString(R.string.extra_spotID), currItem.getSpotID());
        intent.putExtra(getResources().getString(R.string.extra_name), name);
        startActivity(intent);
    }

    @Override
    public void update(Date date)
    {
        TextView tv = findViewById(R.id.textViewDate);
        tv.setText(df.getDateFormat().format(date));
        current.setTime(date);
        populateScreen();
    }

    public void onMapClick(View view)
    {
        Intent mapIntent = new Intent(ParkerSearchActivity.this, ParkerMapActivity.class);
        ParkerSearchActivity.this.startActivity(mapIntent);
    }
}