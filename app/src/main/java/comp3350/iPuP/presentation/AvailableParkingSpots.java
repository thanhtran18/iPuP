package comp3350.iPuP.presentation;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import comp3350.iPuP.R;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.ReservationTime;

public class AvailableParkingSpots extends ListActivity {
    //**** constants added by Kev
    public static final String KEY_RESERVATION_START = "spot_reservation_start";
    public static final String KEY_RESERVATION_END = "spot_reservation_end";
    public static final String KEY_ADDRESS = "spot_address";
    public static final String KEY_NAME = "host_name";
    public static final String KEY_PHONE = "host_phone";
    public static final String KEY_EMAIL = "host_email";
    public static final String KEY_RATE = "spot_rate";
    ////
    private ArrayList<ParkingSpot>fakeSpots=new ArrayList<ParkingSpot>();
    //ArrayAdapter<ParkingSpot> adapter;
    private SimpleDateFormat date;
    private SimpleDateFormat time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_parking_spots);
        fakeSpots = returnDataStub();
        ArrayAdapter<ParkingSpot> adapter = new ArrayAdapter<ParkingSpot>(this,
                android.R.layout.simple_list_item_1,
                fakeSpots);
        setListAdapter(adapter);
    }

    //***ADDED BY KEV
    //ListView spotList = (ListView) findViewById(android.R.id.list);
    //spotList.setAdapter(adapter);
    //spotList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /*@Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long offset) {
                ParkingSpot item = (ParkingSpot) adapter.getItem(position);
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Intent intent = new Intent(getApplicationContext(), ParkingSpotInfoActivity.class);
                intent.putExtra(KEY_RESERVATION_START, item.getStartTime());
                intent.putExtra(KEY_RESERVATION_END, item.getEndTime());
                intent.putExtra(KEY_NAME, item.getName());
                intent.putExtra(KEY_ADDRESS, item.getAddress());
                intent.putExtra(KEY_PHONE, item.getPhone());
                intent.putExtra(KEY_EMAIL, item.getEmail());
                intent.putExtra(KEY_RATE, "$" + Double.toString(item.getRate()));

                startActivity(intent);
            }
        });
    }*/



    private ArrayList<ParkingSpot> returnDataStub() {
        ArrayList<ParkingSpot>fakeSpots=new ArrayList<ParkingSpot>();
        ParkingSpot tempSpot;
        ReservationTime time;
        String address;
        String name;
        String phone;
        String email;
        double rate;

        time = new ReservationTime(2018, 6, 11, 10, 30, 12, 30);
        address = "20 place ave";
        name="this dude";
        phone="the number";
        email="theguy@host.com";
        rate=0.10;
        tempSpot= new ParkingSpot(time, address, name, phone, email,rate);
        fakeSpots.add(tempSpot);

        time = new ReservationTime(2018, 6, 11, 10, 30, 14, 45);
        address = "21 place ave";
        name="this dude 1";
        phone="the number 1";
        email="theguy@host.com";
        rate=0.10;
        tempSpot= new ParkingSpot(time,address, name, phone, email,rate);
        fakeSpots.add(tempSpot);

        time = new ReservationTime(2018, 6, 11, 10, 30, 11, 20);
        address = "22 place ave";
        name="this dude 2";
        phone="the number 2";
        email="theguy@host.com";
        rate=0.10;
        tempSpot= new ParkingSpot(time,address, name, phone, email,rate);
        fakeSpots.add(tempSpot);

        time = new ReservationTime(2018, 6, 11, 10, 30, 11, 0);
        address = "23 place ave";
        name="this dude 3";
        phone="the number 3";
        email="theguy@host.com";
        rate=0.10;
        tempSpot= new ParkingSpot(time , address, name, phone, email,rate);
        fakeSpots.add(tempSpot);

        time = new ReservationTime(2018, 6, 11, 10, 30, 16, 45);
        address = "24 place ave";
        name="this dude 4";
        phone="the number 4";
        email="theguy@host.com";
        rate=0.10;
        tempSpot= new ParkingSpot(time, address, name, phone, email,rate);
        fakeSpots.add(tempSpot);

        time = new ReservationTime(2018, 6, 11, 10, 30, 20, 0);
        address = "25 place ave";
        name="this dude 5";
        phone="the number 5";
        email="theguy@host.com";
        rate=0.10;
        tempSpot= new ParkingSpot(time,address, name, phone, email,rate);
        fakeSpots.add(tempSpot);

        time = new ReservationTime(2018, 6, 11, 10, 30, 13,30);
        address = "26 place ave";
        name="this dude 6";
        phone="the number 6";
        email="theguy@host.com";
        rate=0.10;
        tempSpot= new ParkingSpot(time, address, name, phone, email,rate);
        fakeSpots.add(tempSpot);

        time = new ReservationTime(2018, 6, 11, 10, 30, 15, 0);
        address = "27 place ave";
        name="this dude 7";
        phone="the number 7";
        email="theguy@host.com";
        rate=0.10;
        tempSpot= new ParkingSpot(time, address, name, phone, email,rate);
        fakeSpots.add(tempSpot);

        time = new ReservationTime(2018, 6, 11, 10, 30, 14, 30);
        address = "28 place ave";
        name="this dude 8";
        phone="the number 8";
        email="theguy@host.com";
        rate=0.10;
        tempSpot= new ParkingSpot(time, address, name, phone, email,rate);
        fakeSpots.add(tempSpot);

        time = new ReservationTime(2018, 6, 11, 10, 30, 16, 0);
        address = "29 place ave";
        name="this dude 9";
        phone="the number 9";
        email="theguy@host.com";
        rate=0.10;
        tempSpot= new ParkingSpot(time, address, name, phone, email,rate);
        fakeSpots.add(tempSpot);

        time = new ReservationTime(2018, 6, 11, 10, 30, 16, 0);
        address = "30 place ave";
        name="this dude 10";
        phone="the number 10";
        email="theguy@host.com";
        rate=0.10;
        tempSpot= new ParkingSpot(time, address, name, phone, email,rate);
        fakeSpots.add(tempSpot);
        return fakeSpots;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String nameOfTheParkingHost=fakeSpots.get(position).getName();
        String addressOfTheSpot=fakeSpots.get(position).getAddress();
        String emailOfTheHost=fakeSpots.get(position).getEmail();
        String phoneNumberOfTheHost=fakeSpots.get(position).getPhone();
        String rateDescription="$"+fakeSpots.get(position).getRate()+"/hr";
        String reservationTime=fakeSpots.get(position).getStartTime().toString();
        String message=nameOfTheParkingHost+" with email: "+emailOfTheHost+
                " and phone number: "+phoneNumberOfTheHost+
                " is offering "+addressOfTheSpot+" at "+rateDescription+
                " at "+reservationTime;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        date = new SimpleDateFormat("EEE, d MMM yyyy");
        ParkingSpot item = (ParkingSpot) fakeSpots.get(position);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Intent intent = new Intent(getApplicationContext(), ParkingSpotInfoActivity.class);
        intent.putExtra(KEY_RESERVATION_START, item.getStartTime().toString());
        //intent.putExtra(KEY_RESERVATION_END, item.getEndTime());
        intent.putExtra(KEY_RESERVATION_END, item.getEndTime().toString());
        intent.putExtra(KEY_NAME, item.getName());
        intent.putExtra(KEY_ADDRESS, item.getAddress());
        intent.putExtra(KEY_PHONE, item.getPhone());
        intent.putExtra(KEY_EMAIL, item.getEmail());
        intent.putExtra(KEY_RATE, "$" + Double.toString(item.getRate()));

        startActivity(intent);
    }
}
