package comp3350.iPuP.presentation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.app.DialogFragment;
import android.widget.ToggleButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import comp3350.iPuP.R;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.TimeSlot;

public class HostActivity extends Activity
{
    private AccessParkingSpots accessParkingSpots;
    private String repetitionInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        accessParkingSpots = new AccessParkingSpots();
        repetitionInfo = "";

        Calendar c = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("EEE, d MMM yyyy");
        SimpleDateFormat time = new SimpleDateFormat("h:mm a");

        if (c.get(Calendar.MINUTE) > 30)
            c.set(Calendar.MINUTE, 30);
        else c.set(Calendar.MINUTE, 0);

        TextView tv = (TextView)findViewById(R.id.editFromDate);
        tv.setText(date.format(c.getTime()));

        tv = (TextView)findViewById(R.id.editFromTime);
        tv.setText(time.format(c.getTime()));

        c.add(Calendar.HOUR_OF_DAY,1);
        tv = (TextView)findViewById(R.id.editToDate);
        tv.setText(date.format(c.getTime()));

        tv = (TextView)findViewById(R.id.editToTime);
        tv.setText(time.format(c.getTime()));
    }

    public void onFromDateClick(View v)
    {
        DialogFragment dateFragment = DatePickerFragment.newInstance(R.id.editFromDate);
        dateFragment.show(getFragmentManager(),"DatePicker");
    }

    public void onToDateClick(View v)
    {
        DialogFragment dateFragment = DatePickerFragment.newInstance(R.id.editToDate);
        dateFragment.show(getFragmentManager(),"DatePicker");
    }

    public void onFromTimeClick(View v)
    {
        //DialogFragment timeFragment = TimePickerFragment.newInstance(R.id.editFromTime);
        //timeFragment.show(getFragmentManager(),"TimePicker");
        Intent fromTimeIntent = new Intent(HostActivity.this, TimePickerActivity.class);
        HostActivity.this.startActivityForResult(fromTimeIntent, 1);
    }

    public void onToTimeClick(View v)
    {
        //DialogFragment timeFragment = TimePickerFragment.newInstance(R.id.editToTime);
        //timeFragment.show(getFragmentManager(),"TimePicker");
        Intent toTimeIntent = new Intent(HostActivity.this, TimePickerActivity.class);
        HostActivity.this.startActivityForResult(toTimeIntent, 2);
    }

    public void onRepeatClick(View v)
    {
        if (((ToggleButton)v).isChecked()) {
            Intent repeatIntent = new Intent(HostActivity.this, RepeatActivity.class);
            TextView dateFrom = (TextView) findViewById(R.id.editFromDate);
            repeatIntent.putExtra("date", dateFrom.getText());
            HostActivity.this.startActivityForResult(repeatIntent, 0);
        }
        else
        {
            repetitionInfo = "";
        }
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


        boolean valid = true;

        TextView tv;
        tv = (TextView) findViewById(R.id.editFromDate);
        String fromStr = tv.getText().toString();
        tv = (TextView) findViewById(R.id.editFromTime);
        fromStr += ", " + tv.getText().toString();
        tv = (TextView) findViewById(R.id.editToDate);
        String toStr = tv.getText().toString();
        tv = (TextView) findViewById(R.id.editToTime);
        toStr += ", " + tv.getText().toString();
        TimeSlot timeSlot = null;
        try
        {
            timeSlot = TimeSlot.parseString(fromStr + " - " + toStr);
        }
        catch (ParseException e)
        {
            valid = false;
        }
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

        if (timeSlot.getStart().compareTo(timeSlot.getEnd()) >= 0)
        {
            valid = false;
            TextView text = findViewById(R.id.textDateTime);
            text.setTextColor(Color.RED);
            text.setText("Your ending time must be after your starting time");
        }
        else
        {
            TextView text = findViewById(R.id.textFrom);
            text.setTextColor(Color.BLACK);
        }

        if (valid)
        {
            String rtn = accessParkingSpots.insertParkingSpots(timeSlot, repetitionInfo, address, name, phone, email, rate);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        String ret;
        switch(requestCode)
        {
            case (0) :
                if (resultCode == Activity.RESULT_OK)
                    repetitionInfo = data.getStringExtra("repetitionInfo");
                else
                {
                    repetitionInfo = "";
                    ((ToggleButton)findViewById(R.id.toggleButtonRepeat)).setChecked(false);
                }
                break;
            case(1):
                ret = data.getStringExtra("time");
                ((TextView)findViewById(R.id.editFromTime)).setText(ret);
                break;
            case(2):
                ret = data.getStringExtra("time");
                ((TextView)findViewById(R.id.editToTime)).setText(ret);
                break;
        }
    }
}
