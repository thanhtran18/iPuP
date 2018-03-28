package comp3350.iPuP.acceptance;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextSwitcher;
import android.widget.TextView;

import junit.framework.Assert;

import com.robotium.solo.Solo;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.regex.Pattern;

import comp3350.iPuP.R;
import comp3350.iPuP.application.Services;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.persistence.DataAccess;
import comp3350.iPuP.persistence.DataAccessObject;
import comp3350.iPuP.presentation.HomeActivity;

import static comp3350.iPuP.application.Main.dbName;
import static comp3350.iPuP.application.Main.getDBPathName;

public class BookParkingSpotTest extends ActivityInstrumentationTestCase2<HomeActivity>
{
    private final String DB_PATH = "db";
    private final String DB_FILE_NAME = "SC.script";
    private AssetManager assetManager;
    private ContextWrapper c;
    private DataAccess newDataAccess;
    private Solo solo;
    private String dbAsset = null;

    public BookParkingSpotTest()
    {
        super(HomeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        c = getActivity().getHomeContext();
        assetManager = c.getAssets();
        String[] assetNames = assetManager.list(DB_PATH);

        for(int i = 0; i < assetNames.length; i++)
        {
            if (assetNames[i].equals(DB_FILE_NAME))
            {
                dbAsset = DB_PATH + "/" + assetNames[i];
                break;
            }
        }

        if (dbAsset == null)
        {
            System.err.println("HSQL Database (SC.script) does not exist in assets!");
            System.exit(1);
        }

        System.out.println("\nStarting Acceptance test BookParkingSpot (using default DB)");
    }

    @Override
    protected void tearDown() throws Exception
    {
        solo.finishOpenedActivities();

        replaceDbWithDefault();

        System.out.println("\nFinished Acceptance test BookParkingSpot (using default DB)");
    }

    private void replaceDbWithDefault() throws DAOException
    {
        Services.closeDataAccess();

        try {
            File dataDirectory = c.getDir(DB_PATH, Context.MODE_PRIVATE);
            String[] components = dbAsset.split("/");
            String copyPath = dataDirectory + "/" + components[components.length - 1];

            File outFile = new File(copyPath);

            if (outFile.exists()) {
                InputStream in = assetManager.open(dbAsset);
                FileUtils.copyInputStreamToFile(in, outFile);
                in.close();
            } else {
                throw new DAOException("Error in locating database file in assets!");
            }

        } catch (IOException ioe) {
            throw new DAOException("Unable to access database: ", ioe);
        }

        Services.createDataAccess(dbName);

//        newDataAccess = new DataAccessObject(dbName);
//        Services.createDataAccess(newDataAccess);
    }

    public void testExistingParker()
    {
        try
        {
            replaceDbWithDefault();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        solo.waitForActivity("HomeActivity");
        solo.enterText((EditText) solo.getView(R.id.editTextName), "marker");
        solo.assertCurrentActivity("Expected activity Home Activity", "HomeActivity");

        solo.clickOnButton("I am looking for parking");
        solo.waitForActivity("ParkerMenuActivity");
        solo.assertCurrentActivity("Expected activity Parker Menu Activity", "ParkerMenuActivity");
        solo.sleep(3000);

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
        solo.sleep(3000);
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
        solo.waitForActivity("ParkerLogViewActivity");
        solo.assertCurrentActivity("Expected activity Parker Log View Activity", "ParkerLogViewActivity");
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
        solo.clickOnText("Delete this reservation");
        Assert.assertFalse(solo.searchText("1338 Chancellor Drive (hold to cancel this booking"));

        solo.goBackToActivity("HomeActivity");
        solo.assertCurrentActivity("Expected activity HomeActivity", "HomeActivity");

    }

    public void testNewParker()
    {
        solo.waitForActivity("HomeActivity");
        solo.enterText((EditText) solo.getView(R.id.editTextName), "new_user");
        solo.assertCurrentActivity("Expected activity Home Activity", "HomeActivity");

        solo.clickOnButton("I am looking for parking");
        solo.waitForActivity("ParkerMenuActivity");
        solo.assertCurrentActivity("Expected activity Parker Menu Activity", "ParkerMenuActivity");
        solo.sleep(3000);

        solo.clickOnButton("View your booked spots");
        solo.waitForActivity("ParkerLogViewActivity");
        solo.assertCurrentActivity("Expected activity ParkerLogViewActivity", "ParkerLogViewActivity");
        Assert.assertFalse(solo.searchText("1000 St. Mary's Rd"));
        Assert.assertFalse(solo.searchText("1338 Chancellor Drive"));
        Assert.assertTrue(solo.searchText("There are no available spots to display"));

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
        solo.sleep(3000);
        solo.clickOnButton("Next");

        Assert.assertTrue(solo.searchText("29 St. Mary's Rd"));

        solo.clickInList(0);

        Assert.assertTrue(solo.searchText("Name: Mary Watson"));
        Assert.assertTrue(solo.searchText("Address: 29 St. Mary's Rd"));
        Assert.assertTrue(solo.searchText("Phone number: 204-242-2255"));
        Assert.assertTrue(solo.searchText("Email: sherlock101@gmail.com"));
        Assert.assertTrue(solo.searchText(Pattern.quote("Rate: $4.50")));
        Assert.assertTrue(solo.searchText(Pattern.quote("Current price: $0.00")));

        solo.clickOnText("Tue, 12 Jun 2018, 12:00 AM - Tue, 12 Jun 2018, 12:30 AM");
        Assert.assertTrue(solo.searchText(Pattern.quote("Current price: $2.25")));
        solo.clickOnText("Tue, 12 Jun 2018, 12:30 AM - Tue, 12 Jun 2018, 1:00 AM");
        solo.clickOnText("Tue, 12 Jun 2018, 1:00 AM - Tue, 12 Jun 2018, 1:30 AM");
        Assert.assertTrue(solo.searchText(Pattern.quote("Current price: $6.75")));
        solo.clickOnText("Tue, 12 Jun 2018, 1:30 AM - Tue, 12 Jun 2018, 2:00 AM");
        Assert.assertTrue(solo.searchText(Pattern.quote("Current price: $9.00")));

        Assert.assertTrue(solo.searchText("Tue, 12 Jun 2018, 12:00"));
        Assert.assertTrue(solo.searchText("Tue, 12 Jun 2018, 12:30"));
        Assert.assertTrue(solo.searchText("Tue, 12 Jun 2018, 1:00"));
        Assert.assertTrue(solo.searchText("Tue, 12 Jun 2018, 1:30"));

        solo.clickOnButton("Confirm booking");
        Assert.assertFalse(solo.searchText("Tue, 12 Jun 2018, 12:30"));
        Assert.assertFalse(solo.searchText("Tue, 12 Jun 2018, 1:00"));
        Assert.assertFalse(solo.searchText("Tue, 12 Jun 2018, 1:30"));

        solo.goBackToActivity("ParkerMenuActivity");
        solo.waitForActivity("ParkerMenuActivity");
        solo.assertCurrentActivity("Expected activity Parker Menu Activity", "ParkerMenuActivity");

        solo.clickOnButton("View your booked spots");
        solo.waitForActivity("ParkerLogViewActivity");
        solo.assertCurrentActivity("Expected activity Parker Log View Activity", "ParkerLogViewActivity");
        Assert.assertFalse(solo.searchText("1000 St. Mary's Rd (hold to cancel this booking"));
        Assert.assertFalse(solo.searchText("1000 St. Mary's Rd"));
        Assert.assertFalse(solo.searchText("91 Dalhousie Drive (hold to cancel this booking"));
        Assert.assertTrue(solo.searchText("29 St. Mary's Rd (hold to cancel this booking"));
        Assert.assertTrue(solo.searchText("Tue, 12 Jun 2018, 12:00"));
        Assert.assertTrue(solo.searchText("Tue, 12 Jun 2018, 12:30"));
        Assert.assertTrue(solo.searchText("Tue, 12 Jun 2018, 1:00"));
        Assert.assertTrue(solo.searchText("Tue, 12 Jun 2018, 1:30"));


        Assert.assertTrue(solo.searchText("29 St. Mary's Rd"));
        solo.clickLongInList(1);
        Assert.assertTrue(solo.searchText("Delete this reservation"));
        solo.clickOnText("Delete this reservation");
        Assert.assertFalse(solo.searchText("Mon, 12 Jun 2018, 12:00 PM"));

        solo.clickLongInList(1);
        Assert.assertTrue(solo.searchText("Delete this reservation"));
        solo.clickOnText("Delete this reservation");
        Assert.assertFalse(solo.searchText("Mon, 12 Jun 2018, 12:30 PM"));

        solo.goBack();

        solo.clickOnButton("View your booked spots");
        solo.waitForActivity("ParkerLogViewActivity");
        solo.assertCurrentActivity("Expected activity Parker Log View Activity", "ParkerLogViewActivity");

        solo.clickLongInList(1);
        Assert.assertTrue(solo.searchText("Delete this reservation"));
        solo.clickOnText("Delete this reservation");
        Assert.assertFalse(solo.searchText("Tue, 12 Jun 2018, 1:00 PM"));

        solo.clickLongInList(1);
        Assert.assertTrue(solo.searchText("Delete this reservation"));
        solo.clickOnText("Delete this reservation");
        Assert.assertFalse(solo.searchText("Tue, 12 Jun 2018, 1:30 PM"));

        Assert.assertFalse(solo.searchText("29 St. Mary's Rd"));
        Assert.assertTrue(solo.searchText("There are no available spots to display"));

        solo.goBackToActivity("HomeActivity");
        solo.assertCurrentActivity("Expected activity HomeActivity", "HomeActivity");
    }

    public void testEmptyUsername()
    {
        solo.waitForActivity("HomeActivity");
        solo.enterText((EditText) solo.getView(R.id.editTextName), "");
        solo.assertCurrentActivity("Expected activity Home Activity", "HomeActivity");

        solo.clickOnButton("I am looking for parking");
        Assert.assertTrue(solo.searchText("Enter a name"));
        solo.assertCurrentActivity("Expected activity Home Activity", "HomeActivity");
    }


}
