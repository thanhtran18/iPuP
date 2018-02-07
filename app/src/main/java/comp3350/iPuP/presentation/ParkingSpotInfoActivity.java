package comp3350.iPuP.presentation;

/**
 * Created by ThanhTran on 2018-01-30.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import comp3350.iPuP.R;
import comp3350.iPuP.application.Main;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.ReservationTime;


import static comp3350.iPuP.presentation.AvailableParkingSpots.KEY_ADDRESS;
import static comp3350.iPuP.presentation.AvailableParkingSpots.KEY_EMAIL;
import static comp3350.iPuP.presentation.AvailableParkingSpots.KEY_NAME;
import static comp3350.iPuP.presentation.AvailableParkingSpots.KEY_RESERVATION;
import static comp3350.iPuP.presentation.AvailableParkingSpots.KEY_PHONE;
import static comp3350.iPuP.presentation.AvailableParkingSpots.KEY_RATE;

public class ParkingSpotInfoActivity extends AppCompatActivity {
    public ArrayList<ParkingSpot> fakeParkingSpots = new ArrayList<>();
    Button bookThisSpot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Main.startUp();

        setContentView(R.layout.activity_parking_spot_info);

        bookThisSpot = (Button) findViewById(R.id.buttonBookThisSpot);
        bookThisSpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBookingResult();
            }
        });

        //**** load info
        String hostName = "";
        String reservation = "";
        String hostPhone = "";
        String hostEmail = "";
        String hostAddress = "";
        String spotRate = "";
        Intent intent = getIntent();
        if (null != intent) {
            hostName = intent.getStringExtra(KEY_NAME);
            reservation = intent.getStringExtra(KEY_RESERVATION);
            hostPhone = intent.getStringExtra(KEY_PHONE);
            hostEmail = intent.getStringExtra(KEY_EMAIL);
            hostAddress = intent.getStringExtra(KEY_ADDRESS);
            spotRate = intent.getStringExtra(KEY_RATE);
        }

        TextView hostNameTxt = (TextView) findViewById(R.id.hostNameText);
        hostNameTxt.setText(hostName);

        TextView hostPhoneTxt = (TextView) findViewById(R.id.hostPhoneText);
        hostPhoneTxt.setText(hostPhone);

        TextView reservationTxt = (TextView) findViewById(R.id.startTimeText);
        reservationTxt.setText(reservation);

        TextView hostEmailTxt = (TextView) findViewById(R.id.hostEmailText);
        hostEmailTxt.setText(hostEmail);

        TextView hostAddressTxt = (TextView) findViewById(R.id.addressText);
        hostAddressTxt.setText(hostAddress);

        TextView spotRateTxt = (TextView) findViewById(R.id.endTimeText);
        spotRateTxt.setText(spotRate);

    }

    public void showBookingResult() {
        Context context = getApplicationContext();
        CharSequence text = "Booked successfully!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public void addFakeSpots() {
        ReservationTime newReservationTime = new ReservationTime(2018, 02, 18, 8, 00, 05, 00);
        ParkingSpot tempSpot;
        String address = "20 place ave";
        String name="this dude";
        String phone="the number";
        String email="theguy@host.com";
        double rate = 0.10;
        tempSpot= new ParkingSpot(newReservationTime, address, name, phone, email,rate);
        fakeParkingSpots.add(tempSpot);
    }
}





