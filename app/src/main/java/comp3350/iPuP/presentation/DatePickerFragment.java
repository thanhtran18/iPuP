package comp3350.iPuP.presentation;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.app.DialogFragment;
import android.app.Dialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import comp3350.iPuP.R;
import comp3350.iPuP.objects.DateFormatter;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
{
    private DateFormatter df;

    public static DatePickerFragment newInstance(int id)
    {
        Bundle b = new Bundle();
        b.putInt("id", id);
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(b);
        return newFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final Calendar c = Calendar.getInstance();
        int day = 10;//c.get(Calendar.DAY_OF_MONTH);
        int month = 5;//c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day)
    {
        TextView tv = (TextView) getActivity().findViewById(getArguments().getInt("id"));
        Calendar c = new GregorianCalendar(year, month, day, 1, 1);
        SimpleDateFormat time = new SimpleDateFormat("EEE, d MMM yyyy");
        tv.setText(time.format(c.getTime()));
    }
}
