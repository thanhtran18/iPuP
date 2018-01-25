package comp3350.iPuP.presentation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.EditText;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import comp3350.iPuP.R;

public class HostActivity extends Activity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
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
        DatePicker datePicker =  (DatePicker) findViewById(R.id.datePickerDate);
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();
        TimePicker timePickerStart =  (TimePicker) findViewById(R.id.timePickerStart);
        int hour = timePickerStart.getHour();
        int minute = timePickerStart.getMinute();
        LocalDateTime start = LocalDateTime.of(year, month+1, day+1, hour, minute);
        TimePicker timePickerEnd =  (TimePicker) findViewById(R.id.timePickerEnd);
        hour = timePickerEnd.getHour();
        minute = timePickerEnd.getMinute();
        LocalDateTime end = LocalDateTime.of(year, month+1, day+1, hour, minute);
        finish();
    }
}
