package comp3350.iPuP.presentation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.EditText;
import android.widget.Toast;

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

        ParkingSpot newParkingSpot = new ParkingSpot(reservationTime,address,name,phone,email,rate);
        String rtn = accessParkingSpots.insertParkingSpot(newParkingSpot);

        if (rtn == null)
        {
            Toast.makeText(this, "New advertisement created!", Toast.LENGTH_LONG).show();
        } else
        {
            Toast.makeText(this, "Failed to create new advertisement!", Toast.LENGTH_LONG).show();
        }

        finish();
    }
}
