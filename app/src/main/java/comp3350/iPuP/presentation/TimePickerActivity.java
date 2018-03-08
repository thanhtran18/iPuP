package comp3350.iPuP.presentation;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Spinner;
import android.widget.TextView;

import comp3350.iPuP.R;

public class TimePickerActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_time_picker);
        //TODO: Remove this after
        ((Spinner)findViewById(R.id.spinnerHour)).setSelection(9);
        ((Spinner)findViewById(R.id.spinnerMinute)).setSelection(1);
    }

    public void buttonCancelOnClick(View v)
    {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    public void buttonOkOnClick(View v)
    {
        String hour = ((Spinner)findViewById(R.id.spinnerHour)).getSelectedItem().toString();
        String minute = ((Spinner)findViewById(R.id.spinnerMinute)).getSelectedItem().toString();
        String amPm = ((Spinner)findViewById(R.id.spinnerAMPM)).getSelectedItem().toString();
        String ret = hour + ":" + minute + " " + amPm;
        Intent resultIntent = new Intent();
        resultIntent.putExtra("time", ret);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
