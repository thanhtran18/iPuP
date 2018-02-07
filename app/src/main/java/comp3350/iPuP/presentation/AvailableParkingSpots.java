package comp3350.iPuP.presentation;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

import comp3350.iPuP.R;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.ReservationTime;

public class AvailableParkingSpots extends ListActivity {

    private ArrayList<ParkingSpot>fakeSpots=new ArrayList<ParkingSpot>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_parking_spots);
        fakeSpots=returnDataStub();
        //ParkingSpot=new ParkingSpot(reservationTime, );
        ArrayAdapter<ParkingSpot> adapter=new ArrayAdapter<ParkingSpot>(this,
                android.R.layout.simple_list_item_1,
                fakeSpots);
        setListAdapter(adapter);
    }

    private ArrayList<ParkingSpot> returnDataStub(){
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


}
