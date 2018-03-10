package comp3350.iPuP.presentation;

import comp3350.iPuP.R;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.Booking;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.objects.DateFormatter;
import comp3350.iPuP.objects.ParkingSpot;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Victory on 2018-03-01.
 */

public class ParkerSearchActivity extends Activity {
    private DateFormatter setDate;//for setting the date

    private AccessParkingSpots accessParkingSpots;
    ArrayAdapter<ParkingSpot> adapter;
    ArrayList<ParkingSpot> parkingSpots = new ArrayList<>();
    ArrayList<ParkingSpot> arrayList = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parker_search);

        Calendar c = Calendar.getInstance();
        setDate = new DateFormatter();

        TextView tv = (TextView)findViewById(R.id.editDate);
        tv.setText(setDate.getDateFormat().format(c.getTime()));

        accessParkingSpots = new AccessParkingSpots();
        ListView list = findViewById(R.id.dailySpot);
        try
        {
            Date today= new Date(setDate.getSqlDateFormat().parse("2018-06-11").getTime());
            //TODO: Victory, replace null with address search string
            parkingSpots = accessParkingSpots.getDailySpots(null, today);
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
    
    
    public void onDateClick(View v) {
        DialogFragment dateFragment = DatePickerFragment.newInstance(R.id.editDate);
        dateFragment.show(getFragmentManager(), "DatePicker");
    }

    public void prevDayClick(View v)
    {}
    public void nextDayClick(View v)
    {}

}