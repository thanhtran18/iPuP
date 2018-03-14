package comp3350.iPuP.presentation;

import comp3350.iPuP.R;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.objects.DateFormatter;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.TimeSlot;

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

import java.text.ParseException;
import java.util.Date;

import java.util.ArrayList;
import java.util.Calendar;

public class ParkerSearchActivity extends ListActivity implements DateFragmentObserver
{
    private DateFormatter df;//for setting the date

    private AccessParkingSpots accessParkingSpots;
    ArrayAdapter<ParkingSpot> adapter;
    ArrayList<ParkingSpot> parkingSpots = new ArrayList<>();
    ArrayList<TimeSlot> newTime = new ArrayList<>();
    ArrayList<TimeSlot> finalTimeSlot = new ArrayList<>();
    TimeSlot timeSlots;

    Calendar current;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parker_search);
        //todo get current date from onclick listen
        //todo change button and screen colors
        //todo try implementing without the start seach button
        //todo write test
        current = Calendar.getInstance();
        df = new DateFormatter();

        TextView tv = (TextView)findViewById(R.id.editDate);
        tv.setText(df.getDateFormat().format(current.getTime()));

        populateScreen();

        final Button prev = findViewById(R.id.leftButton);
        prev.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {

                current.add(Calendar.DATE, -1);
                TextView tv = findViewById(R.id.editDate);
                tv.setText(df.getDateFormat().format(current.getTime()));
                populateScreen();
            }
        });

        SearchView sv = findViewById(R.id.showSearchIcon);
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

        final Button next = findViewById(R.id.rightButton);
        next.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                current.add(Calendar.DATE, +1);
                TextView tv = findViewById(R.id.editDate);
                tv.setText(df.getDateFormat().format(current.getTime()));
                populateScreen();
            }
        });

    }

    public void onDateClick(View v)
    {
        DialogFragment dateFragment = DatePickerFragment.newInstance();
        dateFragment.show(getFragmentManager(), "DatePicker");
    }

    public String getSearchText()
    {
        SearchView streetName = findViewById(R.id.showSearchIcon);
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
        //String name = ((SearchView)findViewById(R.id.showSearchIcon)).getQuery().toString();
        try
        {
            parkingSpots.clear();
            newTime.clear();
            //TODO: Victory, replace null with address search string
            parkingSpots = accessParkingSpots.getDailySpots(getSearchText(), current.getTime());
            for(int i = 0; i < parkingSpots.size(); i++)
            {
                newTime = accessParkingSpots.getTimeSlotForParkingSpots(parkingSpots.get(i).getSpotID());
                TimeSlot newTimeSlot = new TimeSlot(newTime.get(0).getStart(), newTime.get(newTime.size() - 1).getEnd());
                finalTimeSlot.add(newTimeSlot);

            }
            // timeSlots = accessParkingSpots.getTimeSlotForParkingSpots();
            //ArrayList<ParkingSpot> parkingSpots = accessParkingSpots.getAllSpots();

            adapter = new ArrayAdapter<ParkingSpot>(this, android.R.layout.simple_list_item_1, parkingSpots);
            //setListAdapter(adapter);
            list.setAdapter(adapter);
//            int first = list.getFirstVisiblePosition();
//            for (int i : disabledIndices)
//                list.getChildAt(0).setEnabled(false);


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
        if(extras == null) {
            name = null;
        } else {
            name = extras.getString("name");
        }

        super.onListItemClick(l, v, position, id);
        ParkingSpot currItem = parkingSpots.get(position);

        Intent intent = new Intent(getApplicationContext(), BookTimeSlotsActivity.class);
        intent.putExtra(getResources().getString(R.string.selected_spot), currItem.getSpotID());
        intent.putExtra("name", name);
        startActivity(intent);
    }

    @Override
    public void update(Date date)
    {
        TextView tv = findViewById(R.id.editDate);
        tv.setText(df.getDateFormat().format(date));
        current.setTime(date);
        populateScreen();
    }
}