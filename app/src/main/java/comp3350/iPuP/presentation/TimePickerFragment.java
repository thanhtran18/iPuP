package comp3350.iPuP.presentation;

import android.app.TimePickerDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TextView;
import android.app.DialogFragment;
import android.app.Dialog;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.widget.TimePicker;
import comp3350.iPuP.R;

/**
 * Created by kram1 on 2/23/2018.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener
{
    public static TimePickerFragment newInstance(int id)
    {
        Bundle b = new Bundle();
        b.putInt("id", id);
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setArguments(b);
        return newFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
        TextView tv = (TextView) getActivity().findViewById(getArguments().getInt("id"));
        Calendar c = new GregorianCalendar(2000, 1, 1, hourOfDay, minute);
        SimpleDateFormat time = new SimpleDateFormat("h:mm a");
        tv.setText(time.format(c.getTime()));
    }
}
