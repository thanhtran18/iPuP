package comp3350.iPuP.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import comp3350.iPuP.R;
import comp3350.iPuP.objects.TimeSlot;

public class BookTimeSlotsActivity extends AppCompatActivity {

    ArrayList<TimeSlot> timesToShow=new ArrayList<TimeSlot>();
    ArrayList<TimeSlot> bookedSlots=new ArrayList<TimeSlot>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_time_slots);

        Date startDate=new Date();
        Date endDate=new Date(2018,5,16);
        TimeSlot temp=new TimeSlot(startDate,endDate);
        timesToShow.add(temp);
        startDate=new Date();
        endDate=new Date(2019,5,16);
        temp=new TimeSlot(startDate,endDate);
        timesToShow.add(temp);
        startDate=new Date(2017,6,20);
        endDate=new Date(2020,5,16);
        temp=new TimeSlot(startDate,endDate);
        timesToShow.add(temp);

        ListView tSlots=(ListView)findViewById(R.id.timeSlotsList);
        tSlots.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<TimeSlot> timeListAdapter=new ArrayAdapter<TimeSlot>(this,
                R.layout.time_slot_item_layout,R.id.timeSlotCheckItem,timesToShow);
        tSlots.setAdapter(timeListAdapter);
        tSlots.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TimeSlot currSlot=timesToShow.get(i);
                if (bookedSlots.contains(currSlot)){
                    bookedSlots.remove(currSlot);
                }else{
                    bookedSlots.add(currSlot);
                }

            }
        });

    }

    public void buttonPerform(View v){
        bookSelectedSlotsInDB(bookedSlots);
    }

    public boolean bookSelectedSlotsInDB(ArrayList<TimeSlot> theArray){
        //TODO: Add the full functionality for this function!
        if(theArray.size()>0) {
            Toast.makeText(this, theArray.get(0).toString(), Toast.LENGTH_LONG).show();
        }
        return true;
    }
}
