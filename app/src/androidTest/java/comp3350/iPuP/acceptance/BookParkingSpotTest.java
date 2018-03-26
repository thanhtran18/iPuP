package comp3350.iPuP.acceptance;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextSwitcher;
import android.widget.TextView;

import junit.framework.Assert;

import com.robotium.solo.Solo;

import java.util.Date;

import comp3350.iPuP.R;
import comp3350.iPuP.presentation.HomeActivity;

/**
 * Created by ThanhTran on 2018-03-26.
 */

public class BookParkingSpotTest extends ActivityInstrumentationTestCase2<HomeActivity>
{
    private Solo solo;

    public BookParkingSpotTest()
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

    public void testValidParker()
    {
        solo.waitForActivity("HomeActivity");
        solo.enterText((EditText) solo.getView(R.id.editTextName), "marker");
        solo.assertCurrentActivity("Expected activity Home Activity", "HomeActivity");

        solo.clickOnButton("I am looking for parking");
        solo.waitForActivity("ParkerMenuActivity");
        solo.assertCurrentActivity("Expected activity Parker Menu Activity", "ParkerMenuActivity");

        solo.clickOnButton("Search for available parking spots");
        solo.waitForActivity("ParkerSearchActivity");
        solo.assertCurrentActivity("Expected activity ParkerSearchActivity", "ParkerSearchActivity");
        solo.clickOnView(((TextView) solo.getView(R.id.textViewDate)));
        Date currDate = new Date();
        if (currDate.getMonth() == 3) //if current date is in April
        {
            solo.clickOnButton(">");
            solo.clickOnButton(">"); //in June now
        }
        else if (currDate.getMonth() == 2) //if current Date is in March
        {
            solo.clickOnButton(">");
            solo.clickOnButton(">");
            solo.clickOnButton(">");
        }
        solo.clickOnButton("11");
    }


}
