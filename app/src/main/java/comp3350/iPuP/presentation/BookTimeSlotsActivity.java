package comp3350.iPuP.presentation;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import comp3350.iPuP.R;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.TimeSlot;

public class BookTimeSlotsActivity extends AppCompatActivity{
    long spotIDFromSearchScreen;
    String userBookingSpot;
    ParkingSpot currSpot;
    private AccessParkingSpots accessParkingSpots = new AccessParkingSpots();
    ArrayList<TimeSlot> timesToShow;
    ArrayList<TimeSlot> timesToBook = new ArrayList<TimeSlot>();
    ArrayAdapter<TimeSlot> timeListAdapter;
    ListView tSlots;
    TextView emptyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_time_slots);

        tSlots = (ListView)findViewById(R.id.timeSlotsList);
        tSlots.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        emptyList=(TextView)findViewById(R.id.emptySlotList);

        Intent intent = getIntent();
        if (null != intent)
        {
            spotIDFromSearchScreen = intent.getLongExtra(
                    getResources().getString(R.string.selected_spot),-1);
            userBookingSpot=intent.getStringExtra("name");
        }

        if (spotIDFromSearchScreen != -1) {
            try {
                currSpot = accessParkingSpots.getSpotBYID(spotIDFromSearchScreen);
                refreshScreen();
            } catch (Exception exd) {
                Toast.makeText(this, exd.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else
        {
            Toast.makeText(this, "Unable to retrieve spotID!", Toast.LENGTH_LONG).show();
        }

        TextView hostName = (TextView)findViewById(R.id.parkingSpotHostName);
        hostName.setText("HOST NAME: "+currSpot.getName());
        TextView spotAddress = (TextView)findViewById(R.id.parkingSpotAddress);
        spotAddress.setText("ADDRESS: "+currSpot.getAddress());
        TextView hostPhoneNumber = (TextView)findViewById(R.id.parkingSpotPhoneNumber);
        hostPhoneNumber.setText("PHONE NUMBER: "+currSpot.getPhone());
        TextView hostEmailAddress = (TextView)findViewById(R.id.parkingSpotHostEmail);
        hostEmailAddress.setText("E-MAIL: "+currSpot.getEmail());
        TextView rate = (TextView)findViewById(R.id.parkingSpotChargeRate);
        rate.setText("CHARGE RATE: $"+Double.toString(+currSpot.getRate()));
        final TextView currentPrice=(TextView)findViewById(R.id.currentRateView);
        currentPrice.setText("$"+Double.toString(0.00));

        tSlots.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TimeSlot currSlot = timesToShow.get(i);
                if (timesToBook.contains(currSlot)){
                    timesToBook.remove(currSlot);
                }else{
                    timesToBook.add(currSlot);
                }
                currentPrice.setText("$"+Double.toString(currSpot.getRate()* timesToBook.size()));
            }
        });
    }


    public void bookSlots(View v){
        bookSelectedSlotsInDB(timesToBook);
        timesToBook.clear();
    }


    public boolean bookSelectedSlotsInDB(ArrayList<TimeSlot> theArray){
        boolean allSpotsBooked=false;
        if(theArray.size() > 0) {
            try {
                 allSpotsBooked = accessParkingSpots.bookTimeSlots(theArray, userBookingSpot,
                         spotIDFromSearchScreen);
            }catch (Exception ex){
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            if(allSpotsBooked) {
                Toast.makeText(this, "SuccessFully Booked Timeslots", Toast.LENGTH_LONG).show();
            }
            timeListAdapter.clear();
            refreshScreen();
        }else{

        }
        return true;
    }

    private void refreshScreen(){
        //This will set the screen as it needs to be each time
        try
        {
            timesToShow = accessParkingSpots.getFreeTimeSlotsByID(spotIDFromSearchScreen);
            if(timesToShow.size()==0){
                emptyList.setText(getResources().getString(R.string.no_available_times_to_book));
            }
        }catch (DAOException exd){
            Toast.makeText(this, exd.getMessage(), Toast.LENGTH_LONG).show();
        }
        timeListAdapter = new ArrayAdapter<TimeSlot>(this,
                R.layout.time_slot_item_layout,R.id.timeSlotCheckItem, timesToShow);
        tSlots.setAdapter(timeListAdapter);
    }
}
