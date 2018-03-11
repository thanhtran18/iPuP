package comp3350.iPuP.presentation;

import android.app.Activity;
import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import comp3350.iPuP.R;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.Booking;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.objects.ParkingSpot;

/**
 * Created by ThanhTran on 2018-03-05.
 */

public class ParkerLogViewActivity extends ListActivity
{
    private AccessParkingSpots accessParkingSpots;
    ArrayAdapter<Booking> adapter;
    ArrayList<Booking> bookingSpots = new ArrayList<>();
    ArrayList<Booking> arrayList = new ArrayList<>();
    ArrayList<Integer> disabledIndices = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        String name;
        //String name = getIntent().getExtras().getString("name");
        //String name = getIntent().getStringExtra("name");
        //String name = getIntent().getExtras();
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            name = null;
        } else {
            name = extras.getString("name");
        }
        setContentView(R.layout.activity_parker_log_view);

        accessParkingSpots = new AccessParkingSpots();

        ListView list = findViewById(android.R.id.list);

        try
        {
            bookingSpots = accessParkingSpots.getMyBookedSpots(name);
            //ArrayList<ParkingSpot> parkingSpots = accessParkingSpots.getAllSpots();
            for (final Booking spot : bookingSpots)
            {
//                if (!spot.isCancelled())

                arrayList.add(spot);
                SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
                //Date now = new Date();
                //Date bookedTime = spot.getStartTime();
                //int difference = now.compareTo(formatter.parse(bookedTime.toString()));
//                if (spot.getStartTime().before(new Date())) {
//                    list.setEnabled(false);
//                    //list.setOnItemClickListener(null);
//                    disabledIndices.add(parkingSpots.indexOf(spot));
//                }


//                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Date now = new Date();
//                        Date bookedTime = spot.getStartTime();
//                        if (spot.getStartTime().before(new Date()))
//                            disabledIndices.add(parkingSpots.indexOf(spot));
//                    }
//                });



            }

            adapter = new ArrayAdapter<Booking>(this, android.R.layout.simple_list_item_1, arrayList) {
                @Override
                public boolean isEnabled(int position) {
                    if (arrayList.get(position).getStart().before(new Date())) {
                        return false;
                    }
                    return true;
                }

                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View row = super.getView(position, convertView, parent);
                    if (arrayList.get(position).getStart().before(new Date()))
                        row.setBackgroundColor(Color.parseColor("#E0E0E0"));

                    return row;
                }
            };
            //setListAdapter(adapter);
            //list.getChildAt(0).setEnabled(false);
            list.setAdapter(adapter);

//            int first = list.getFirstVisiblePosition();
//            for (int i : disabledIndices)
//                list.getChildAt(0).setEnabled(false);


            registerForContextMenu(list);
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_context_delete, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo obj = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();


        switch (item.getItemId())
        {
            case R.id.delete:
                //accessParkingSpots.cancelThisSpot(name, .getTimeSlotId());
                //parkingSpots.get(obj.position).setCancelled(true);
                try {
                    accessParkingSpots.cancelThisSpot(arrayList.get(obj.position).getUsername(), arrayList.get(obj.position).getTimeSlotId());
                } catch (DAOException daoe)
                {
                    Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
                }
                arrayList.remove(obj.position);

                adapter.notifyDataSetChanged();
                break;
        }

        return super.onContextItemSelected(item);
    }



}
