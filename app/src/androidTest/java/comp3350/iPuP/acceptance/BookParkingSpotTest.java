package comp3350.iPuP.acceptance;

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

        solo.clickOnButton("View your booked spots");
        solo.waitForActivity("ParkerLogViewActivity");
        solo.assertCurrentActivity("Expected activity ParkerLogViewActivity", "ParkerLogViewActivity");
        Assert.assertFalse(solo.searchText("1000 St. Mary's Rd (hold to cancel this booking"));
        Assert.assertTrue(solo.searchText("1000 St. Mary's Rd"));
        Assert.assertTrue(solo.searchText("91 Dalhousie Drive (hold to cancel this booking"));
        Assert.assertTrue(solo.searchText("1 Pembina Hwy (hold to cancel this booking"));
        Assert.assertTrue(solo.searchText("1338 Chancellor Drive (hold to cancel this booking"));
        solo.goBack();
        solo.waitForActivity("ParkerMenuActivity");
        solo.assertCurrentActivity("Expected activity Parker Menu Activity", "ParkerMenuActivity");

        solo.clickOnButton("Search for available parking spots");
        solo.waitForActivity("ParkerSearchActivity");
        solo.assertCurrentActivity("Expected activity ParkerSearchActivity", "ParkerSearchActivity");
        solo.clickOnView(((TextView) solo.getView(R.id.textViewDate)));
        Date currDate = new Date();
        solo.sleep(2000);
        solo.setDatePicker(0, 2018, 5, 11);
        solo.clickOnText("OK");

        solo.scrollToBottom();
        solo.scrollToTop();



        solo.enterText(0, "St. ");
        solo.clickOnButton("Next");

        Assert.assertTrue(solo.searchText("29 St. Mary's Rd"));

        solo.clickInList(0);

        Assert.assertTrue(solo.searchText("Name: Mary Watson"));
        Assert.assertTrue(solo.searchText("Address: 29 St. Mary's Rd"));
        Assert.assertTrue(solo.searchText("Phone number: 204-242-2255"));
        Assert.assertTrue(solo.searchText("Email: sherlock101@gmail.com"));
        Assert.assertTrue(solo.searchText(Pattern.quote("Rate: $4.50")));
        Assert.assertTrue(solo.searchText(Pattern.quote("Current price: $0.00")));

        solo.clickInList(1);
        Assert.assertTrue(solo.searchText(Pattern.quote("Current price: $2.25")));
        solo.clickInList(2);
        solo.clickInList(3);
        Assert.assertTrue(solo.searchText(Pattern.quote("Current price: $6.75")));
        solo.clickInList(4);
        Assert.assertTrue(solo.searchText(Pattern.quote("Current price: $9.00")));
        Assert.assertTrue(solo.searchText("Mon, 11 Jun 2018, 9:00"));
        Assert.assertTrue(solo.searchText("Mon, 11 Jun 2018, 9:30"));
        Assert.assertTrue(solo.searchText("Mon, 11 Jun 2018, 10:00"));
        Assert.assertTrue(solo.searchText("Mon, 11 Jun 2018, 10:30"));

        solo.clickOnButton("Confirm booking");
        Assert.assertFalse(solo.searchText("Mon, 11 Jun 2018, 9:00"));
        Assert.assertFalse(solo.searchText("Mon, 11 Jun 2018, 9:30"));
        Assert.assertFalse(solo.searchText("Mon, 11 Jun 2018, 10:00"));

        solo.goBackToActivity("ParkerMenuActivity");
        solo.waitForActivity("ParkerMenuActivity");
        solo.assertCurrentActivity("Expected activity Parker Menu Activity", "ParkerMenuActivity");

        solo.clickOnButton("View your booked spots");
        Assert.assertFalse(solo.searchText("1000 St. Mary's Rd (hold to cancel this booking"));
        Assert.assertTrue(solo.searchText("1000 St. Mary's Rd"));
        Assert.assertTrue(solo.searchText("91 Dalhousie Drive (hold to cancel this booking"));
        Assert.assertTrue(solo.searchText("1 Pembina Hwy (hold to cancel this booking"));
        Assert.assertTrue(solo.searchText("1338 Chancellor Drive (hold to cancel this booking"));
        Assert.assertTrue(solo.searchText("29 St. Mary's Rd (hold to cancel this booking"));
        Assert.assertTrue(solo.searchText("Mon, 11 Jun 2018, 10:00"));
        Assert.assertTrue(solo.searchText("Mon, 11 Jun 2018, 10:30"));

        solo.clickLongInList(1);
        Assert.assertTrue(solo.searchText("1000 St. Mary's Rd"));
        solo.clickLongInList(2);
        Assert.assertTrue(solo.searchText("Delete this reservation"));
        solo.clickOnText("Delete this reservation");
        Assert.assertFalse(solo.searchText("91 Dalhousie Drive (hold to cancel this booking"));
        solo.clickLongInList(3);
        Assert.assertFalse(solo.searchText("1338 Chancellor Drive (hold to cancel this booking"));

    }


}
