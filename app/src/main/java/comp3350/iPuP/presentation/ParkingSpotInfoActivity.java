package comp3350.iPuP.presentation;

/**
 * Created by ThanhTran on 2018-01-30.
 */

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.time.LocalDateTime;
import java.util.ArrayList;

import comp3350.iPuP.R;
import comp3350.iPuP.application.Main;
import comp3350.iPuP.objects.ParkingSpot;

public class ParkingSpotInfoActivity extends AppCompatActivity {


    public ArrayList<ParkingSpot> fakeParkingSpots = new ArrayList<>();

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFakeSpots(); //DELETE THIS WHEN DONE WITH FAKE SPOT
        setContentView(R.layout.activity_parking_spot_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Main.startUp();

        setContentView(R.layout.activity_home);
    }


    public void addFakeSpots() {
        ParkingSpot tempSpot;
        String start = "2018/06/11; 10:30";
        String end = "2018/01/01; 01:01";
        String address = "20 place ave";
        String name="this dude";
        String phone="the number";
        String email="theguy@host.com";
        double rate = 0.10;
        tempSpot= new ParkingSpot(start, end, address, name, phone, email,rate);
        fakeParkingSpots.add(tempSpot);
    }
}