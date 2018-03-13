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

    public static DatePickerFragment newInstance()
    {
        DatePickerFragment newFragment = new DatePickerFragment();
        return newFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day)
    {
        Calendar c = new GregorianCalendar(year, month, day, 1, 1);
        ((DateFragmentObserver)getActivity()).update(c.getTime());
    }
}
