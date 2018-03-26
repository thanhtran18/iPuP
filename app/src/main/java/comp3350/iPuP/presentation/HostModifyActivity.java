package comp3350.iPuP.presentation;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import comp3350.iPuP.R;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.objects.ParkingSpot;

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

        spotID = getIntent().getLongExtra(getResources().getString(R.string.extra_spotID), -1);
        if (spotID != -1)
        {
            try
            {
                ParkingSpot ps = accessParkingSpots.getParkingSpot(spotID);
                EditText editText = findViewById(R.id.editTextAddress);
                editText.setText(ps.getAddress());
                editText = findViewById(R.id.editTextRate);
                editText.setText(String.valueOf(ps.getRate()));
                editText = findViewById(R.id.editTextEmail);
                editText.setText(ps.getEmail());
                editText = findViewById(R.id.editTextPhone);
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
        EditText edit = findViewById(R.id.editTextAddress);
        String address = edit.getText().toString();
        edit = findViewById(R.id.editTextEmail);
        String email = edit.getText().toString();
        edit = findViewById(R.id.editTextPhone);
        String phone = edit.getText().toString();
        edit = findViewById(R.id.editTextRate);
        String rateStr = edit.getText().toString();
        Double rate = Double.parseDouble(rateStr.equals("") ? "0": rateStr);

        boolean valid = true;

        if (address.equals(""))
        {
            valid = false;
            EditText text = findViewById(R.id.editTextAddress);
            text.setBackgroundColor(getResources().getColor(R.color.colorWarning));
        }
        else
        {
            EditText text = findViewById(R.id.editTextAddress);
            text.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        }

        if (rate == 0)
        {
            valid = false;
            EditText text = findViewById(R.id.editTextRate);
            text.setBackgroundColor(getResources().getColor(R.color.colorWarning));
        }
        else
        {
            EditText text = findViewById(R.id.editTextRate);
            text.setBackgroundColor(getResources().getColor(R.color.colorLightGrey));
        }

        if (email.equals("") && phone.equals(""))
        {
            valid = false;
            EditText text = findViewById(R.id.editTextPhone);
            text.setHint("Enter either phone");
            text.setBackgroundColor(getResources().getColor(R.color.colorWarning));
            text = findViewById(R.id.editTextEmail);
            text.setHint("or email");
            text.setBackgroundColor(getResources().getColor(R.color.colorWarning));
        }
        else
        {
            EditText text = findViewById(R.id.editTextPhone);
            text.setHint(getResources().getString(R.string.host_phone));
            text.setBackgroundColor(getResources().getColor(R.color.colorLightGrey));
            text = findViewById(R.id.editTextEmail);
            text.setHint(getResources().getString(R.string.host_email));
            text.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        }

        if (valid)
        {
            try
            {
                accessParkingSpots.modifyParkingSpot(spotID, address, phone, email, rate);

                Toast.makeText(this, "Advertisement updated!", Toast.LENGTH_LONG).show();
            }
            catch (DAOException daoe)
            {
                Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
            }

            finish();
        }
    }
}
