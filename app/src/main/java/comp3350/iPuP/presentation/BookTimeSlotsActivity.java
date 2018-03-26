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

import java.util.ArrayList;

import comp3350.iPuP.R;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.TimeSlot;

public class BookTimeSlotsActivity extends AppCompatActivity
{
    long spotID;
    String name;
    ParkingSpot currentSpot;
    private AccessParkingSpots accessParkingSpots = new AccessParkingSpots();
    ArrayList<TimeSlot> timesToShow;
    ArrayList<TimeSlot> timesToBook = new ArrayList<TimeSlot>();
    ArrayAdapter<TimeSlot> timeSlotAdapter;
    ListView timeSlotList;
    TextView emptyList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_time_slots);

        timeSlotList =  findViewById(R.id.timeSlotsList);
        timeSlotList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        emptyList = findViewById(R.id.emptySlotList);

        Intent intent = getIntent();
        if (null != intent)
        {
            spotID = intent.getLongExtra(getResources().getString(R.string.extra_spotID),-1);
            name = intent.getStringExtra(getResources().getString(R.string.extra_name));
        }

        if (spotID != -1)
        {
            try
            {
                currentSpot = accessParkingSpots.getSpotByID(spotID);
                refreshScreen();
            }
            catch (Exception e)
            {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this, "Unable to retrieve spotID!", Toast.LENGTH_LONG).show();
        }

        TextView hostName = findViewById(R.id.textViewName);
        hostName.setText(String.format(getResources().getString(R.string.info_name), currentSpot.getName()));
        TextView spotAddress = (TextView)findViewById(R.id.textViewAddress);
        spotAddress.setText(String.format(getResources().getString(R.string.info_address), currentSpot.getAddress()));
        TextView hostPhoneNumber = (TextView)findViewById(R.id.textViewPhone);
        hostPhoneNumber.setText(String.format(getResources().getString(R.string.info_phone), currentSpot.getPhone()));
        TextView hostEmailAddress = (TextView)findViewById(R.id.textViewEmail);
        hostEmailAddress.setText(String.format(getResources().getString(R.string.info_email), currentSpot.getEmail()));
        TextView rate = (TextView)findViewById(R.id.textViewRate);
        rate.setText(String.format(getResources().getString(R.string.info_rate), currentSpot.getRate()));
        final TextView currentPrice=(TextView)findViewById(R.id.textViewCurrentRate);
        currentPrice.setText(String.format(getString(R.string.info_current_price), 0.0));

        timeSlotList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                TimeSlot currSlot = timesToShow.get(i);
                if (timesToBook.contains(currSlot))
                {
                    timesToBook.remove(currSlot);
                }
                else
                {
                    timesToBook.add(currSlot);
                }
                currentPrice.setText(String.format(getString(R.string.info_current_price), currentSpot.getRate() * timesToBook.size()/2));
            }
        });
    }


    public void bookSlots(View v)
    {
        bookSelectedSlotsInDB(timesToBook);
        timesToBook.clear();
    }


    public void bookSelectedSlotsInDB(ArrayList<TimeSlot> theArray)
    {
        boolean allSpotsBooked=false;
        if(theArray.size() > 0)
        {
            try
            {
                 allSpotsBooked = accessParkingSpots.bookTimeSlots(theArray, name,
                         spotID);
            }
            catch (Exception ex)
            {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            if(allSpotsBooked)
            {
                Toast.makeText(this, "SuccessFully Booked Timeslots", Toast.LENGTH_LONG).show();
            }
            timeSlotAdapter.clear();
            refreshScreen();
        }
    }

    private void refreshScreen()
    {
        try
        {
            timesToShow = accessParkingSpots.getFreeTimeSlotsByID(spotID);
            if(timesToShow.size()==0)
            {
                emptyList.setText(getResources().getString(R.string.book_no_times));
            }
        }
        catch (DAOException daoe)
        {
            Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
        }
        timeSlotAdapter = new ArrayAdapter<TimeSlot>(this,
                R.layout.time_slot_item_layout,R.id.timeSlotCheckItem, timesToShow);
        timeSlotList.setAdapter(timeSlotAdapter);
    }
}
