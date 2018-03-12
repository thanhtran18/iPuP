package comp3350.iPuP.presentation;

import comp3350.iPuP.R;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.Booking;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.objects.DateFormatter;
import comp3350.iPuP.objects.ParkingSpot;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.util.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Victory on 2018-03-01.
 */

public class ParkerSearchActivity extends ListActivity{
    private DateFormatter setDate;//for setting the date

    private AccessParkingSpots accessParkingSpots;
    ArrayAdapter<ParkingSpot> adapter;
    ArrayList<ParkingSpot> parkingSpots = new ArrayList<>();
    ArrayList<ParkingSpot> arrayList = new ArrayList<>();
    String dayTime;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parker_search);

        Calendar c = Calendar.getInstance();
        setDate = new DateFormatter();

        TextView tv = (TextView)findViewById(R.id.editDate);
        tv.setText(setDate.getDateFormat().format(c.getTime()));
        dayTime = tv.getText().toString();
        populateScreen(null,dayTime);


    }


    public void onDateClick(View v) {
        DialogFragment dateFragment = DatePickerFragment.newInstance(R.id.editDate);
        dateFragment.show(getFragmentManager(), "DatePicker");
    }
    public void startSearch(View v)
    {
        setDate = new DateFormatter();
        TextView tv = (TextView)findViewById(R.id.editDate);
        SearchView streetName = (SearchView)findViewById(R.id.showSearchIcon);
        CharSequence charName = streetName.getQuery();
        String searchText = charName.toString();
        if(searchText.length() == 0)
        {
            searchText = null;
        }
        dayTime = tv.getText().toString();
        populateScreen(searchText,dayTime);
    }

    public void populateScreen( String name, String dayTime)
    {
        accessParkingSpots = new AccessParkingSpots();
        ListView list = findViewById(android.R.id.list);
        //String name = ((SearchView)findViewById(R.id.showSearchIcon)).getQuery().toString();
        try
        {
            arrayList.clear();
            SimpleDateFormat currForm = new SimpleDateFormat("E, dd MMM yyyy");
            // parse the string into Date object
            Date date = currForm.parse(dayTime);
            // create SimpleDateFormat object with desired date format
            SimpleDateFormat newForm = new SimpleDateFormat("yyyy-MM-dd");
            // parse the date into another format
            dayTime = newForm.format(date);
            Date today= new Date(setDate.getSqlDateFormat().parse(dayTime).getTime());
            //TODO: Victory, replace null with address search string
            parkingSpots = accessParkingSpots.getDailySpots(name, today);
            //ArrayList<ParkingSpot> parkingSpots = accessParkingSpots.getAllSpots();
            for (final ParkingSpot spot : parkingSpots)
            {
                arrayList.add(spot);
            }

            adapter = new ArrayAdapter<ParkingSpot>(this, android.R.layout.simple_list_item_1, arrayList);
            //setListAdapter(adapter);
            list.setAdapter(adapter);
//            int first = list.getFirstVisiblePosition();
//            for (int i : disabledIndices)
//                list.getChildAt(0).setEnabled(false);


            registerForContextMenu(list);
        } catch (ParseException pe)
        {
            Toast.makeText(this, pe.getMessage(), Toast.LENGTH_LONG).show();
        } catch (DAOException daoe)
        {
            Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void prevDayClick(View v)
    {}
    public void nextDayClick(View v)
    {}

}