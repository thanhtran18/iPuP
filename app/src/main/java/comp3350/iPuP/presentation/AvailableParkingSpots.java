package comp3350.iPuP.presentation;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import comp3350.iPuP.R;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.ReservationTime;

public class AvailableParkingSpots extends ListActivity
{

    public static final String KEY_RESERVATION_START = "spot_reservation_start";
    public static final String KEY_RESERVATION_END = "spot_reservation_end";
    public static final String KEY_ADDRESS = "spot_address";
    public static final String KEY_NAME = "host_name";
    public static final String KEY_PHONE = "host_phone";
    public static final String KEY_EMAIL = "host_email";
    public static final String KEY_RATE = "spot_rate";
    public static final String ID_OF_SPOT = "spot_id";

    private ArrayList<ParkingSpot>fakeSpots=new ArrayList<ParkingSpot>();
    private SimpleDateFormat date;
    private SimpleDateFormat time;

    private AccessParkingSpots accessParkingSpots;

    ArrayAdapter<ParkingSpot> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_parking_spots);
        accessParkingSpots = new AccessParkingSpots();
        fakeSpots = accessParkingSpots.getAvailableSpots();
        adapter = new ArrayAdapter<ParkingSpot>(this,
                android.R.layout.simple_list_item_1,
                fakeSpots);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);

        String nameOfTheParkingHost = fakeSpots.get(position).getName();
        String addressOfTheSpot = fakeSpots.get(position).getAddress();
        String emailOfTheHost = fakeSpots.get(position).getEmail();
        String phoneNumberOfTheHost = fakeSpots.get(position).getPhone();
        String rateDescription = "$" + fakeSpots.get(position).getRate() + "/hr";
        String reservationTime = fakeSpots.get(position).getStartTime().toString();
        String message = nameOfTheParkingHost + " with email: " + emailOfTheHost +
                " and phone number: " + phoneNumberOfTheHost +
                " is offering " + addressOfTheSpot + " at " + rateDescription+
                " at " + reservationTime;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        date = new SimpleDateFormat("EEE, d MMM yyyy");
        ParkingSpot item = (ParkingSpot) fakeSpots.get(position);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Intent intent = new Intent(getApplicationContext(), ParkingSpotInfoActivity.class);
        intent.putExtra(KEY_RESERVATION_START, item.getStartTime().toString());
        intent.putExtra(KEY_RESERVATION_END, item.getEndTime().toString());
        intent.putExtra(KEY_NAME, item.getName());
        intent.putExtra(KEY_ADDRESS, item.getAddress());
        intent.putExtra(KEY_PHONE, item.getPhone());
        intent.putExtra(KEY_EMAIL, item.getEmail());
        intent.putExtra(KEY_RATE, "$" + Double.toString(item.getRate()));

        intent.putExtra(ID_OF_SPOT, item.getId());

        startActivity(intent);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        adapter.clear();
        fakeSpots = accessParkingSpots.getAvailableSpots();
        adapter = new ArrayAdapter<ParkingSpot>(this,
                android.R.layout.simple_list_item_1,
                fakeSpots);
        setListAdapter(adapter);
    }
}
