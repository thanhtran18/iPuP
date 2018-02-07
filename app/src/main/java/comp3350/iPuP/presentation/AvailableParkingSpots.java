package comp3350.iPuP.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import comp3350.iPuP.R;

public class AvailableParkingSpots extends AppCompatActivity {

    Button toInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_parking_spots);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toInfoButton=(Button) findViewById(R.id.buttonToParkingInfo);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonToSpotInfo();
            }
        });
    }

    public void buttonToSpotInfo() {
        //Go the Parker screen
        Intent toSpotInfo = new Intent(AvailableParkingSpots.this, ParkingSpotInfoActivity.class);
        AvailableParkingSpots.this.startActivity(toSpotInfo);
    }
}
