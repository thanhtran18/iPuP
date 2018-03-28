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
            Assert.assertTrue(solo.searchText("Address: 325 Author V. Mauro"));


            //solo.clickOnButton("Modify");
            //todo modify test

            solo.clickOnText("Address: 88 Plaza Drive");
            solo.waitForText("Start: Tue, 12 Jun 2018, 2:30 PM");
            solo.clickOnText("Start: Tue, 12 Jun 2018, 2:30 PM");
            solo.waitForText("Start: Tue, 12 Jun 2018, 3:00 PM");
            //solo.clickOnText("Delete", 0);

            solo.goBack();

            //solo.waitForText("Start: Mon, 11 Jun 2018, 10:30 AM");
            //solo.clickOnText("Delete", 0);

            solo.waitForText("Start: Tue, 12 Jun 2018, 2:30 AM");
            solo.clickOnButton("Delete");



        }
}
