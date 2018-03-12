package comp3350.iPuP.presentation;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.ParseException;

import comp3350.iPuP.R;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.TimeSlot;

public class HostModifyActivity extends AppCompatActivity
{
    protected AccessParkingSpots accessParkingSpots;
    private long spotID;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_host_modify);

        accessParkingSpots = new AccessParkingSpots();

        spotID = getIntent().getLongExtra("spotid",-1);
        if (spotID != -1)
        {
            try
            {
                ParkingSpot ps = accessParkingSpots.getParkingSpot(spotID);
                EditText editText = findViewById(R.id.editAddress);
                editText.setText(ps.getAddress());
                editText = findViewById(R.id.editRate);
                editText.setText(String.valueOf(ps.getRate()));
                editText = findViewById(R.id.editEmail);
                editText.setText(ps.getEmail());
                editText = findViewById(R.id.editPhone);
                editText.setText(ps.getPhone());
            }
            catch (DAOException daoe)
            {
                Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        }
    }

    public void buttonCancelOnClick(View view)
    {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    public void buttonConfirmOnClick(View view)
    {
        EditText edit = findViewById(R.id.editAddress);
        String address = edit.getText().toString();
        edit = findViewById(R.id.editEmail);
        String email = edit.getText().toString();
        edit = findViewById(R.id.editPhone);
        String phone = edit.getText().toString();
        edit = findViewById(R.id.editRate);
        String rateStr = edit.getText().toString();
        Double rate = Double.parseDouble(rateStr.equals("") ? "0": rateStr);

        boolean valid = true;

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

        if (valid)
        {
            try
            {
                accessParkingSpots.modifyParkingSpot(spotID, address, phone, email, rate);

                Toast.makeText(this, "Advertisement updated created!", Toast.LENGTH_LONG).show();
            }
            catch (DAOException daoe)
            {
                Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
            }

            finish();
        }
    }
}
