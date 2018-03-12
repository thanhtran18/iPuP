package comp3350.iPuP.presentation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.app.DialogFragment;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import comp3350.iPuP.R;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.objects.DateFormatter;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.TimeSlot;

public class HostActivity extends Activity
{
    private AccessParkingSpots accessParkingSpots;
    private String repetitionInfo;
    private String name;
    private DateFormatter df;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        accessParkingSpots = new AccessParkingSpots();

        repetitionInfo = "";

        name = getIntent().getStringExtra("name");

        Calendar c = Calendar.getInstance();
        df = new DateFormatter();

        if (c.get(Calendar.MINUTE) > 30)
            c.set(Calendar.MINUTE, 30);
        else c.set(Calendar.MINUTE, 0);

        TextView tv = (TextView)findViewById(R.id.editFromDate);
        tv.setText(df.getDateFormat().format(c.getTime()));

        tv = (TextView)findViewById(R.id.editFromTime);
        tv.setText(df.getTimeFormat().format(c.getTime()));

        c.add(Calendar.HOUR_OF_DAY,1);
        tv = (TextView)findViewById(R.id.editToDate);
        tv.setText(df.getDateFormat().format(c.getTime()));

        tv = (TextView)findViewById(R.id.editToTime);
        tv.setText(df.getTimeFormat().format(c.getTime()));
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
        EditText edit =  (EditText) findViewById(R.id.editAddress);
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
            EditText text = findViewById(R.id.editAddress);
            text.setBackgroundColor(getResources().getColor(R.color.colorWarning));
        }
        else
        {
            EditText text = findViewById(R.id.editAddress);
            text.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        }

        if (rate == 0)
        {
            valid = false;
            EditText text = findViewById(R.id.editRate);
            text.setBackgroundColor(getResources().getColor(R.color.colorWarning));
        }
        else
        {
            EditText text = findViewById(R.id.editRate);
            text.setBackgroundColor(getResources().getColor(R.color.colorLightGrey));
        }

        if (email.equals("") && phone.equals(""))
        {
            valid = false;
            EditText text = findViewById(R.id.editPhone);
            text.setHint("Enter either phone");
            text.setBackgroundColor(getResources().getColor(R.color.colorWarning));
            text = findViewById(R.id.editEmail);
            text.setHint("or email");
            text.setBackgroundColor(getResources().getColor(R.color.colorWarning));
        }
        else
        {
            EditText text = findViewById(R.id.editPhone);
            text.setHint(getResources().getString(R.string.host_phone));
            text.setBackgroundColor(getResources().getColor(R.color.colorLightGrey));
            text = findViewById(R.id.editEmail);
            text.setHint(getResources().getString(R.string.host_email));
            text.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        }

        if (timeSlot.getStart().compareTo(timeSlot.getEnd()) >= 0)
        {
            valid = false;
            TextView text = findViewById(R.id.editFromTime);
            text.setBackgroundColor(getResources().getColor(R.color.colorWarning));
            text = findViewById(R.id.editFromDate);
            text.setBackgroundColor(getResources().getColor(R.color.colorWarning));
        }
        else
        {
            TextView text = findViewById(R.id.editFromTime);
            text.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            text = findViewById(R.id.editFromDate);
            text.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        }

        if (valid)
        {
            try {
                boolean rtn = accessParkingSpots.insertParkingSpot(name, timeSlot, repetitionInfo, address, name, phone, email, rate);

                if (rtn) {
                    Toast.makeText(this, "New advertisement created!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Failed to create new advertisement!", Toast.LENGTH_LONG).show();
                }
            } catch (DAOException daoe)
            {
                Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
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
                if (resultCode == Activity.RESULT_OK)
                {
                    ret = data.getStringExtra("time");
                    ((TextView) findViewById(R.id.editFromTime)).setText(ret.toString());
                }
                break;
            case(2):
                if (resultCode == Activity.RESULT_OK)
                {
                    ret = data.getStringExtra("time");
                    TextView textView = ((TextView) findViewById(R.id.editToTime));
                    textView.setText(ret.toString());
                }
                break;
        }
    }

    public void buttonCancelOnClick(View view)
    {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
