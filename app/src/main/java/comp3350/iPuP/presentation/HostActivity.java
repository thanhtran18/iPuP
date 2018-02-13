package comp3350.iPuP.presentation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import comp3350.iPuP.R;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.ReservationTime;

public class HostActivity extends Activity
{
    private AccessParkingSpots accessParkingSpots;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        accessParkingSpots = new AccessParkingSpots();
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void buttonConfirmOnClick(View v)
    {
        EditText edit =  (EditText) findViewById(R.id.editName);
        String name = edit.getText().toString();
        edit =  (EditText) findViewById(R.id.editAddress);
        String address = edit.getText().toString();
        edit =  (EditText) findViewById(R.id.editEmail);
        String email = edit.getText().toString();
        edit =  (EditText) findViewById(R.id.editPhone);
        String phone = edit.getText().toString();
        edit = (EditText) findViewById(R.id.editRate);
        String rateStr = edit.getText().toString();
        Double rate = Double.parseDouble(rateStr.equals("") ? "0": rateStr);

        DatePicker datePicker =  (DatePicker) findViewById(R.id.datePickerDate);
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        TimePicker timePickerStart =  (TimePicker) findViewById(R.id.timePickerStart);
        int startHour = timePickerStart.getHour();
        int startMinute = timePickerStart.getMinute();

        TimePicker timePickerEnd =  (TimePicker) findViewById(R.id.timePickerEnd);
        int endHour = timePickerEnd.getHour();
        int endMinute = timePickerEnd.getMinute();

        ReservationTime reservationTime = new ReservationTime(year,month,day,startHour,startMinute,endHour,endMinute);

        boolean valid = true;
        if (address.equals(""))
        {
            valid = false;
            TextView text = findViewById(R.id.textAddress);
            text.setTextColor(Color.RED);
        }
        else
        {
            TextView text = findViewById(R.id.textAddress);
            text.setTextColor(Color.BLACK);
        }

        if (name.equals(""))
        {
            valid = false;
            TextView text = findViewById(R.id.textName);
            text.setTextColor(Color.RED);
        }
        else
        {
            TextView text = findViewById(R.id.textName);
            text.setTextColor(Color.BLACK);
        }

        if (rate == 0)
        {
            valid = false;
            TextView text = findViewById(R.id.textRate);
            text.setTextColor(Color.RED);
        }
        else
        {
            TextView text = findViewById(R.id.textRate);
            text.setTextColor(Color.BLACK);
        }

        if (email.equals("") && phone.equals(""))
        {
            valid = false;
            TextView text = findViewById(R.id.textPhone);
            text.setTextColor(Color.RED);
            text.setText("" + text.getText() + "\nYou must enter either phone or email");
            text = findViewById(R.id.textEmail);
            text.setTextColor(Color.RED);
        }

        if (reservationTime.getStart().compareTo(reservationTime.getEnd()) >= 0)
        {
            valid = false;
            TextView text = findViewById(R.id.textTime);
            text.setTextColor(Color.RED);
            text.setText("" + text.getText() + ".\nYour ending time must be after your starting time");
        }
        else
        {
            TextView text = findViewById(R.id.textTime);
            text.setTextColor(Color.BLACK);
        }

        if (valid)
        {
            ParkingSpot newParkingSpot = new ParkingSpot(reservationTime, address, name, phone, email, rate);
            String rtn = accessParkingSpots.insertParkingSpot(newParkingSpot);

            if (rtn == null)
            {
                Toast.makeText(this, "New advertisement created!", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this, "Failed to create new advertisement!", Toast.LENGTH_LONG).show();
            }

            finish();
        }
    }
}
