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
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.util.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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


        //todo get current date from onclick listen
        //todo change button and screen colors
        //todo try implementing without the start seacrh button
        //todo write test
        final Calendar c = Calendar.getInstance();
        setDate = new DateFormatter();

        TextView tv = (TextView)findViewById(R.id.editDate);
        tv.setText(setDate.getDateFormat().format(c.getTime()));
        dayTime = tv.getText().toString();
        populateScreen(null,dayTime);
        final Button prev = findViewById(R.id.leftButton);
        prev.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                c.add(Calendar.DATE, -1);
                TextView tv = (TextView)findViewById(R.id.editDate);
                tv.setText(setDate.getDateFormat().format(c.getTime()));
            }
        });


        final Button next = findViewById(R.id.rightButton);
        next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                c.add(Calendar.DATE, +1);
                TextView tv = (TextView)findViewById(R.id.editDate);
                tv.setText(setDate.getDateFormat().format(c.getTime()));
            }
        });

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
        if(searchText.trim().length() == 0 )
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

}