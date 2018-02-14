package comp3350.iPuP.presentation;

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
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.ParkingSpot;


import static comp3350.iPuP.presentation.AvailableParkingSpots.KEY_ADDRESS;
import static comp3350.iPuP.presentation.AvailableParkingSpots.KEY_EMAIL;
import static comp3350.iPuP.presentation.AvailableParkingSpots.KEY_NAME;
import static comp3350.iPuP.presentation.AvailableParkingSpots.KEY_RESERVATION_START;
import static comp3350.iPuP.presentation.AvailableParkingSpots.KEY_RESERVATION_END;
import static comp3350.iPuP.presentation.AvailableParkingSpots.KEY_PHONE;
import static comp3350.iPuP.presentation.AvailableParkingSpots.KEY_RATE;
import static comp3350.iPuP.presentation.AvailableParkingSpots.ID_OF_SPOT;

public class ParkingSpotInfoActivity extends AppCompatActivity {
    public ArrayList<ParkingSpot> fakeParkingSpots = new ArrayList<>();
    Button bookThisSpot;

    private AccessParkingSpots accessParkingSpots;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Main.startUp();

        setContentView(R.layout.activity_parking_spot_info);

        bookThisSpot = (Button) findViewById(R.id.buttonBookThisSpot);
        accessParkingSpots=new AccessParkingSpots();

        //**** load info
        String hostName = "";
        String reservationStart = "";
        String reservationEnd = "";
        String hostPhone = "";
        String hostEmail = "";
        String hostAddress = "";
        String spotRate = "";
        final Intent intent = getIntent();
        if (null != intent)
        {
            hostName = intent.getStringExtra(KEY_NAME);
            reservationStart = intent.getStringExtra(KEY_RESERVATION_START);
            reservationEnd = intent.getStringExtra(KEY_RESERVATION_END);
            hostPhone = intent.getStringExtra(KEY_PHONE);
            hostEmail = intent.getStringExtra(KEY_EMAIL);
            hostAddress = intent.getStringExtra(KEY_ADDRESS);
            spotRate = intent.getStringExtra(KEY_RATE);
        }

        TextView hostNameTxt = (TextView) findViewById(R.id.hostNameText);
        hostNameTxt.setText(hostName);

        TextView hostPhoneTxt = (TextView) findViewById(R.id.hostPhoneText);
        hostPhoneTxt.setText(hostPhone);

        TextView reservationStartTxt = (TextView) findViewById(R.id.startTimeText);
        reservationStartTxt.setText(reservationStart);

        TextView reservationEndTxt = (TextView) findViewById(R.id.endTimeText);
        reservationEndTxt.setText(reservationEnd);

        TextView hostEmailTxt = (TextView) findViewById(R.id.hostEmailText);
        hostEmailTxt.setText(hostEmail);

        TextView hostAddressTxt = (TextView) findViewById(R.id.addressText);
        hostAddressTxt.setText(hostAddress);

        TextView spotRateTxt = (TextView) findViewById(R.id.spotRateText);
        spotRateTxt.setText(spotRate);

        bookThisSpot.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                showBookingResult();
                String spotId=intent.getStringExtra(ID_OF_SPOT);
                accessParkingSpots.bookSpot(spotId);
                finish();
            }
        });


    }

    public void showBookingResult()
    {
        Context context = getApplicationContext();
        CharSequence text = "Booked successfully!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public void buttonBackToMain(View v)
    {
        Intent backIntent = new Intent(ParkingSpotInfoActivity.this, HomeActivity.class);
        ParkingSpotInfoActivity.this.startActivity(backIntent);
    }
}

