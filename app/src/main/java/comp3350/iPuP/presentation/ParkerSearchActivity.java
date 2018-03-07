package comp3350.iPuP.presentation;

import comp3350.iPuP.R;
import comp3350.iPuP.objects.DateFormatter;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Victory on 2018-03-01.
 */

public class ParkerSearchActivity extends Activity {
    private DateFormatter setDate;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parker_search);

        Calendar c = Calendar.getInstance();
        setDate = new DateFormatter();

        if (c.get(Calendar.MINUTE) > 30)
            c.set(Calendar.MINUTE, 30);
        else c.set(Calendar.MINUTE, 0);

        TextView tv = (TextView)findViewById(R.id.editDate);
        tv.setText(setDate.getDateFormat().format(c.getTime()));

    }


    public void onDateClick(View v) {
        DialogFragment dateFragment = DatePickerFragment.newInstance(R.id.editDate);
        dateFragment.show(getFragmentManager(), "DatePicker");
    }
}