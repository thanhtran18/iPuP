package comp3350.iPuP.presentation;

import android.app.ListActivity;
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

import java.util.ArrayList;
import java.util.Date;

import comp3350.iPuP.R;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.Booking;
import comp3350.iPuP.objects.DAOException;

public class ParkerLogViewActivity extends ListActivity
{
    private AccessParkingSpots accessParkingSpots;
    ArrayAdapter<Booking> adapter;
    ArrayList<Booking> bookings = new ArrayList<>();
    ArrayList<Booking> displayBookings = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        String name;
        Bundle extras = getIntent().getExtras();
        if(extras == null)
            name = null;
        else
            name = extras.getString(getResources().getString(R.string.extra_name));

        setContentView(R.layout.activity_parker_log_view);

        accessParkingSpots = new AccessParkingSpots();

        ListView list = findViewById(android.R.id.list);

        try
        {
            bookings = accessParkingSpots.getMyBookedSpots(name);

            for (final Booking spot : bookings)
            {
                displayBookings.add(spot);
            }

            adapter = new ArrayAdapter<Booking>(this, android.R.layout.simple_list_item_1, displayBookings)
            {
                @Override
                public boolean isEnabled(int position)
                {
                    if (displayBookings.get(position).getStart().before(new Date()))
                    {
                        return false;
                    }
                    return true;
                }

                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
                {
                    View row = super.getView(position, convertView, parent);
                    Date currentDate = new Date();
                    if (displayBookings.get(position).getStart().before(currentDate))
                        row.setBackgroundResource(R.color.colorTextHint);

                    return row;
                }
            };
            list.setAdapter(adapter);



            registerForContextMenu(list);
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_context_delete, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo obj = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();


        switch (item.getItemId())
        {
            case R.id.delete:
                try
                {
                    accessParkingSpots.cancelThisSpot(displayBookings.get(obj.position).getUsername(), displayBookings.get(obj.position).getTimeSlotId());
                }
                catch (DAOException daoe)
                {
                    Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
                }
                displayBookings.remove(obj.position);

                adapter.notifyDataSetChanged();
                break;
        }

        return super.onContextItemSelected(item);
    }

}
