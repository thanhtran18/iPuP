package comp3350.iPuP.presentation;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import comp3350.iPuP.R;
import comp3350.iPuP.objects.DateFormatter;

public class RepeatActivity extends AppCompatActivity
{
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setFinishOnTouchOutside(false);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_repeat);

        DateFormatter df = new DateFormatter();
        String dateStr = getIntent().getStringExtra("date");
        calendar = new GregorianCalendar();

        try
        {
            Date date = df.getDateFormat().parse(dateStr);
            calendar.setTime(date);
        }
        catch (ParseException e)
        {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }


        TextView tv = findViewById(R.id.textViewMonthDate);
        String ending = "th";
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (day % 10 == 1 && day != 11)
            ending = "st";
        else if (day % 10 == 2 && day != 12)
            ending = "nd";
        else if (day % 10 == 3 && day != 13)
            ending = "rd";
        else if (day % 10 == 3 && day != 13)
            ending = "rd";

        tv.setText(String.format(getString(R.string.repeat_onthe), day, ending));

        ToggleButton btn = null;
        switch (calendar.get(Calendar.DAY_OF_WEEK))
        {
            case 1: btn = (ToggleButton)findViewById(R.id.toggleButtonSun); break;
            case 2: btn = (ToggleButton)findViewById(R.id.toggleButtonMon); break;
            case 3: btn = (ToggleButton)findViewById(R.id.toggleButtonTue); break;
            case 4: btn = (ToggleButton)findViewById(R.id.toggleButtonWed); break;
            case 5: btn = (ToggleButton)findViewById(R.id.toggleButtonThu); break;
            case 6: btn = (ToggleButton)findViewById(R.id.toggleButtonFri); break;
            case 7: btn = (ToggleButton)findViewById(R.id.toggleButtonSat); break;
        }
        btn.setChecked(true);
        btn.setClickable(false);

        Spinner s = findViewById(R.id.spinnerPeriod);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Spinner s = (Spinner)parent;
                if (s.getSelectedItem().toString().equals("Weeks"))
                {
                    RelativeLayout layout = findViewById(R.id.layoutWeekly);
                    layout.setVisibility(View.VISIBLE);
                    layout = findViewById(R.id.layoutMonthly);
                    layout.setVisibility(View.INVISIBLE);
                }
                else if (s.getSelectedItem().toString().equals("Months"))
                {
                    RelativeLayout layout = findViewById(R.id.layoutWeekly);
                    layout.setVisibility(View.INVISIBLE);
                    layout = findViewById(R.id.layoutMonthly);
                    layout.setVisibility(View.VISIBLE);
                }
                else
                {
                    RelativeLayout layout = findViewById(R.id.layoutWeekly);
                    layout.setVisibility(View.INVISIBLE);
                    layout = findViewById(R.id.layoutMonthly);
                    layout.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    public static char toChar(final Boolean b)
    {
        return b == null ? '?' : b ? '1' : '0';
    }

    public void buttonConfirmOnClick(View v)
    {
        Intent resultIntent = new Intent();
        String ret = ((Spinner)findViewById(R.id.spinnerPeriod)).getSelectedItem().toString();

        ret += " " + ((EditText)findViewById(R.id.editTextPeriod)).getText();
        ret += " " + ((EditText)findViewById(R.id.editTextNumReps)).getText() + " ";

        if (ret.startsWith("Weeks"))
        {

            ret += toChar(((ToggleButton)findViewById(R.id.toggleButtonSun)).isChecked());
            ret += toChar(((ToggleButton)findViewById(R.id.toggleButtonMon)).isChecked());
            ret += toChar(((ToggleButton)findViewById(R.id.toggleButtonTue)).isChecked());
            ret += toChar(((ToggleButton)findViewById(R.id.toggleButtonWed)).isChecked());
            ret += toChar(((ToggleButton)findViewById(R.id.toggleButtonThu)).isChecked());
            ret += toChar(((ToggleButton)findViewById(R.id.toggleButtonFri)).isChecked());
            ret += toChar(((ToggleButton)findViewById(R.id.toggleButtonSat)).isChecked());
        }

        resultIntent.putExtra(getResources().getString(R.string.extra_repetitionInfo), ret);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    public void buttonCancelOnClick(View view)
    {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
