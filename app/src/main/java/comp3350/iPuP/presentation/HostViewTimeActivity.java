package comp3350.iPuP.presentation;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import comp3350.iPuP.R;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.TimeSlot;

public class HostViewTimeActivity extends ListActivity
{
    AccessParkingSpots accessParkingSpots;
    TimeSlotAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_view_time);

        long spotID = getIntent().getLongExtra("spotID",0);
        setContentView(R.layout.activity_host_view);

        accessParkingSpots = new AccessParkingSpots();

        ListView list = getListView();
        try
        {
            ParkingSpot spot = null;// accessParkingSpots.getParkingSpot(spotID);

            TextView tv = findViewById(R.id.addressText);
            tv.setText(spot.getAddress());
            tv = findViewById(R.id.textViewRate);
            tv.setText(String.valueOf(spot.getRate()));

            ArrayList<TimeSlot> timeSlots = accessParkingSpots.getTimeSlots(spotID);
            adapter = new TimeSlotAdapter(this, timeSlots);
            setListAdapter(adapter);
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
