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
public class ModifyParkingSpotTest extends ActivityInstrumentationTestCase2<HomeActivity>
{

        private Solo solo;

        public ModifyParkingSpotTest()
        {
            super(HomeActivity.class);
        }

        @Override
        protected void setUp() throws Exception
        {
            solo = new Solo(getInstrumentation(), getActivity());
        }

        @Override
        protected void tearDown() throws Exception
        {
            solo.finishOpenedActivities();
        }

        public void testModifyParking()
        {
            solo.waitForActivity("HomeActivity");
            solo.enterText((EditText) solo.getView(R.id.editTextName), "marker");
            solo.assertCurrentActivity("Expected activity Home Activity", "HomeActivity");

            solo.clickOnButton("I have available parking to advertise");
            solo.waitForActivity("HostMenuActivity");
            solo.assertCurrentActivity("Expected activity HostMenuActivity", "HostMenuActivity");

            solo.clickOnButton("View your parking spots");
            solo.waitForActivity("HostViewActivity");
            solo.assertCurrentActivity("Expected activity Host View Activity", "HostViewActivity");

            solo.waitForText("Address: 88 Plaza Drive");
            Assert.assertTrue(solo.searchText("Address: 88 Plaza Drive"));

            solo.clickOnButton("Modify");
            Assert.assertTrue(solo.searchText("theBestMarker@gmail.com"));

            solo.clearEditText((EditText) solo.getView(R.id.editTextAddress));
            solo.sleep(2000);
            solo.enterText((EditText) solo.getView(R.id.editTextAddress), "1000 Plaza Drive");
            solo.clearEditText((EditText) solo.getView(R.id.editTextRate));
            solo.sleep(2000);
            solo.enterText((EditText) solo.getView(R.id.editTextRate), "4.5");

            solo.clickOnButton("Confirm");

            solo.clickLongInList(1);
            Assert.assertTrue(solo.searchText("Address: 1000 Plaza Drive"));
            Assert.assertTrue(solo.searchText(Pattern.quote("Rate: $4.50")));
            Assert.assertTrue(solo.searchText("Start: Mon, 11 Jun 2018, 10:30 AM"));
            Assert.assertTrue(solo.searchText("End: Tue, 12 Jun 2018, 4:30 PM"));

            solo.clickOnText("End: Mon, 11 Jun 2018, 12:30 AM");
            Assert.assertTrue(solo.searchText("Address: 1000 Plaza Drive"));
            Assert.assertTrue(solo.searchText(Pattern.quote("Rate: $4.50")));
            Assert.assertTrue(solo.searchText("Start: Mon, 11 Jun 2018, 10:30 AM"));
            Assert.assertTrue(solo.searchText("End: Mon, 11 Jun 2018, 11:00 AM"));
            Assert.assertTrue(solo.searchText("Start: Mon, 11 Jun 2018, 11:00 AM"));
            Assert.assertTrue(solo.searchText("Start: Mon, 11 Jun 2018, 12:00 PM"));
            Assert.assertTrue(solo.searchText("End: Mon, 11 Jun 2018, 11:30 AM"));
            Assert.assertTrue(solo.searchText("End: Mon, 11 Jun 2018, 12:00 PM"));

            solo.clickOnText("Delete");

        }
}
