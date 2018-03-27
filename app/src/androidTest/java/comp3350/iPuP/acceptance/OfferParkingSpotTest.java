package comp3350.iPuP.acceptance;

/**
 * Created by Victory on 2018-03-26.
 */
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextSwitcher;
import android.widget.TextView;

import junit.framework.Assert;

import com.robotium.solo.Solo;

import java.util.Date;
import java.util.regex.Pattern;

import comp3350.iPuP.R;
import comp3350.iPuP.presentation.HomeActivity;

public class OfferParkingSpotTest extends ActivityInstrumentationTestCase2<HomeActivity> {

    private Solo solo;

    public OfferParkingSpotTest()
    {
        super(HomeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() throws Exception
    {
        solo.finishOpenedActivities();
    }

    public void testValidCreateParkingSpot()
    {

        solo.waitForActivity("HomeActivity");
        solo.enterText((EditText) solo.getView(R.id.editTextName), "marker");
        solo.assertCurrentActivity("Expected activity Home Activity", "HomeActivity");

        solo.clickOnButton("I have available parking to advertise");
        solo.waitForActivity("HostMenuActivity");
        solo.assertCurrentActivity("Expected activity HostMenuActivity", "HostMenuActivity");

        solo.clickOnButton("Create a new parking spot");
        solo.waitForActivity("HostActivity");
        solo.assertCurrentActivity("Expected activity Host  Activity", "HostActivity");



        solo.enterText((EditText) solo.getView(R.id.editTextAddress), "325 Author V. Mauro");
        solo.waitForText("325 Author V. Mauro");
        solo.enterText((EditText) solo.getView(R.id.editTextPhone), "204-234-5678");
        solo.waitForText("204-234-5678");
        solo.enterText((EditText) solo.getView(R.id.editTextEmail), "manlikerodney@gmail.com");
        solo.waitForText("manlikerodney@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.editTextRate), "2");
        solo.waitForText("2");

        solo.clickOnView((TextView) solo.getView(R.id.textViewToDate));
        solo.setDatePicker(0, 2018, 2, 27);
        solo.clickOnText("OK");
        solo.clickOnButton("Cancel");


        solo.clickOnButton("Create a new parking spot");
        solo.waitForActivity("HostActivity");
        solo.assertCurrentActivity("Expected activity Host  Activity", "HostActivity");

        solo.clearEditText((EditText) solo.getView(R.id.editTextAddress));
        solo.clearEditText((EditText) solo.getView(R.id.editTextPhone));
        solo.clearEditText((EditText) solo.getView(R.id.editTextEmail));
        solo.clearEditText((EditText) solo.getView(R.id.editTextRate));


        solo.enterText((EditText) solo.getView(R.id.editTextAddress), "325 Author V. Mauro");
        solo.waitForText("325 Author V. Mauro");
        solo.enterText((EditText) solo.getView(R.id.editTextPhone), "204-234-5678");
        solo.waitForText("204-234-5678");
        solo.enterText((EditText) solo.getView(R.id.editTextEmail), "manlikerodney@gmail.com");
        solo.waitForText("manlikerodney@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.editTextRate), "2");
        solo.waitForText("2");


        solo.clickOnView((TextView) solo.getView(R.id.textViewToDate));
        solo.setDatePicker(0, 2018, 2, 28);
        solo.clickOnText("OK");


        solo.clickOnToggleButton("Does not repeat");
        solo.waitForActivity("RepeatActivity");
        solo.assertCurrentActivity("Expected activity Repeat  Activity", "RepeatActivity");

        solo.clearEditText((EditText) solo.getView(R.id.editTextPeriod));
        solo.enterText((EditText) solo.getView(R.id.editTextPeriod), "4");

        solo.clickOnButton("Confirm");
        solo.goBackToActivity("HostActivity");
        solo.clickOnButton("Confirm");
        solo.goBackToActivity("HostMenuActivity");

        solo.clickOnButton("View your parking spots");
        solo.waitForActivity("HostViewActivity");
        solo.assertCurrentActivity("Expected activity Host View Activity", "HostViewActivity");

        solo.waitForText("Address: 325 Author V. Mauro");
        Assert.assertTrue(solo.searchText("Address: 325 Author V. Mauro"));
        solo.clickOnText("Address: 325 Author V. Mauro");




    }

    public void testInvalidCreateParkingSpot()
    {
        solo.waitForActivity("HomeActivity");
        solo.enterText((EditText) solo.getView(R.id.editTextName), "marker");
        solo.assertCurrentActivity("Expected activity Home Activity", "HomeActivity");

        solo.clickOnButton("I have available parking to advertise");
        solo.waitForActivity("HostMenuActivity");
        solo.assertCurrentActivity("Expected activity HostMenuActivity", "HostMenuActivity");
        solo.sleep(3000);

        solo.clickOnButton("Create a new parking spot");
        solo.waitForActivity("HostActivity");
        solo.assertCurrentActivity("Expected activity Host  Activity", "HostActivity");


        solo.clickOnButton("Confirm");

        Assert.assertTrue(solo.searchText("Address"));
        Assert.assertTrue(solo.searchText("Invalid phone number"));
        Assert.assertTrue(solo.searchText("Email Address"));
        Assert.assertTrue(solo.searchText(Pattern.quote("Rate ($/h)")));

        solo.enterText((EditText) solo.getView(R.id.editTextAddress), "325 Author V. Mauro");
        solo.waitForText("325 Author V. Mauro");
        solo.clickOnButton("Confirm");
        Assert.assertTrue(solo.searchText("325 Author V. Mauro"));
        Assert.assertTrue(solo.searchText("Invalid phone number"));
        Assert.assertTrue(solo.searchText("Email Address"));
        Assert.assertTrue(solo.searchText(Pattern.quote("Rate ($/h)")));

        solo.clearEditText((EditText) solo.getView(R.id.editTextAddress));
        solo.clearEditText((EditText) solo.getView(R.id.editTextPhone));
        solo.enterText((EditText) solo.getView(R.id.editTextPhone), "2042345678");
        solo.clickOnButton("Confirm");
        Assert.assertTrue(solo.searchText("Address"));
        Assert.assertTrue(solo.searchText("Invalid phone number"));
        Assert.assertTrue(solo.searchText("Email Address"));
        Assert.assertTrue(solo.searchText(Pattern.quote("Rate ($/h)")));


        solo.clearEditText((EditText) solo.getView(R.id.editTextPhone));
        solo.enterText((EditText) solo.getView(R.id.editTextPhone), "204-234-5678");
        solo.clickOnButton("Confirm");
        Assert.assertTrue(solo.searchText("Address"));
        Assert.assertTrue(solo.searchText("204-234-5678"));
        Assert.assertTrue(solo.searchText("Email Address"));
        Assert.assertTrue(solo.searchText(Pattern.quote("Rate ($/h)")));


        solo.clearEditText((EditText) solo.getView(R.id.editTextPhone));
        solo.enterText((EditText) solo.getView(R.id.editTextEmail), "manlikerodney.com");
        solo.clickOnButton("Confirm");
        Assert.assertTrue(solo.searchText("Address"));
        Assert.assertTrue(solo.searchText("Invalid phone number"));
        Assert.assertTrue(solo.searchText("Invalid Email address"));
        Assert.assertTrue(solo.searchText(Pattern.quote("Rate ($/h)")));

        solo.clearEditText((EditText) solo.getView(R.id.editTextEmail));
        solo.enterText((EditText) solo.getView(R.id.editTextEmail), "manlikerodney@gmail.com");
        solo.clickOnButton("Confirm");
        Assert.assertTrue(solo.searchText("Address"));
        Assert.assertTrue(solo.searchText("Invalid phone number"));
        Assert.assertTrue(solo.searchText("manlikerodney@gmail.com"));
        Assert.assertTrue(solo.searchText(Pattern.quote("Rate ($/h)")));

        solo.clearEditText((EditText) solo.getView(R.id.editTextEmail));
        solo.enterText((EditText) solo.getView(R.id.editTextRate), "2");
        solo.clickOnButton("Confirm");
        Assert.assertTrue(solo.searchText("Address"));
        Assert.assertTrue(solo.searchText("Invalid phone number"));
        Assert.assertTrue(solo.searchText("Email Address"));
        Assert.assertTrue(solo.searchText("2"));
        //todo warning colors





    }
}

