package comp3350.iPuP.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import comp3350.iPuP.R;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.TimeSlot;

import static comp3350.iPuP.presentation.ParkerSearchActivity.SELECTED_SPOT;

public class BookTimeSlotsActivity extends AppCompatActivity {
    long testSPOTIDFORSCREEN; //TODO: Make the ID come from previous screen instead
    String userBookingSpot="marker";
    ParkingSpot currSpot; //TODO this parking spot object should come from the prevoius screen instead
    private AccessParkingSpots accessParkingSpots = new AccessParkingSpots();
    ArrayList<TimeSlot> timesToShow;
    ArrayList<TimeSlot> bookedSlots = new ArrayList<TimeSlot>();
    ArrayAdapter<TimeSlot> timeListAdapter;
    ListView tSlots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_time_slots);

        final Intent intent = getIntent();
        if (null != intent)
        {
            testSPOTIDFORSCREEN = intent.getLongExtra(SELECTED_SPOT,-1);
        }

        if (testSPOTIDFORSCREEN != -1) {
            try {
                currSpot = accessParkingSpots.getSpotBYID(testSPOTIDFORSCREEN);
                timesToShow = accessParkingSpots.getFreeTimeSlotsByID(testSPOTIDFORSCREEN);
            } catch (DAOException exd) {
                Toast.makeText(this, exd.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else
        {
            Toast.makeText(this, "Unable to retrieve spotID!", Toast.LENGTH_LONG).show();
        }

        TextView hostName = (TextView)findViewById(R.id.parkingSpotHostName);
        hostName.setText(currSpot.getName());
        TextView spotAddress = (TextView)findViewById(R.id.parkingSpotAddress);
        spotAddress.setText(currSpot.getAddress());
        TextView hostPhoneNumber = (TextView)findViewById(R.id.parkingSpotPhoneNumber);
        hostPhoneNumber.setText(currSpot.getPhone());
        TextView hostEmailAddress = (TextView)findViewById(R.id.parkingSpotHostEmail);
        hostEmailAddress.setText(currSpot.getEmail());
        TextView rate = (TextView)findViewById(R.id.parkingSpotChargeRate);
        rate.setText(Double.toString(currSpot.getRate()));
        final TextView currentPrice=(TextView)findViewById(R.id.currentRateView);
        currentPrice.setText(Double.toString(0.00));

        tSlots = (ListView)findViewById(R.id.timeSlotsList);
        tSlots.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        timeListAdapter = new ArrayAdapter<TimeSlot>(this,
                R.layout.time_slot_item_layout,R.id.timeSlotCheckItem, timesToShow);
        tSlots.setAdapter(timeListAdapter);
        tSlots.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TimeSlot currSlot = timesToShow.get(i);
                if (bookedSlots.contains(currSlot)){
                    bookedSlots.remove(currSlot);
                }else{
                    bookedSlots.add(currSlot);
                }
                currentPrice.setText(Double.toString(currSpot.getRate()*bookedSlots.size()));
            }
        });
    }


    public void buttonPerform(View v){
        bookSelectedSlotsInDB(bookedSlots);
        bookedSlots.clear();
    }


    public boolean bookSelectedSlotsInDB(ArrayList<TimeSlot> theArray){
        //TODO: Add the full functionality for this function!
        if(theArray.size() > 0) {
            try {
                boolean allsPotsBooked = accessParkingSpots.bookTimeSlots(theArray, userBookingSpot,
                        testSPOTIDFORSCREEN);
            }catch (Exception ex){
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            Toast.makeText(this, theArray.get(0).toString(), Toast.LENGTH_LONG).show();

            timeListAdapter.clear();
            try
            {
                currSpot = accessParkingSpots.getSpotBYID(testSPOTIDFORSCREEN);
                timesToShow = accessParkingSpots.getFreeTimeSlotsByID(testSPOTIDFORSCREEN);
            }catch (DAOException exd){
                Toast.makeText(this, exd.getMessage(), Toast.LENGTH_LONG).show();
            }
            timeListAdapter = new ArrayAdapter<TimeSlot>(this,
                    R.layout.time_slot_item_layout,R.id.timeSlotCheckItem, timesToShow);
            tSlots.setAdapter(timeListAdapter);
        }
        return true;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        timeListAdapter.clear();
        try
        {
            currSpot = accessParkingSpots.getSpotBYID(testSPOTIDFORSCREEN);
            timesToShow = accessParkingSpots.getFreeTimeSlotsByID(testSPOTIDFORSCREEN);
        }catch (DAOException exd){
            Toast.makeText(this, exd.getMessage(), Toast.LENGTH_LONG).show();
        }
        timeListAdapter = new ArrayAdapter<TimeSlot>(this,
                R.layout.time_slot_item_layout,R.id.timeSlotCheckItem, timesToShow);
        tSlots.setAdapter(timeListAdapter);
    }

}
