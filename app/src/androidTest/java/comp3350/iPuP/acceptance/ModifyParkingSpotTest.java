package comp3350.iPuP.acceptance;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import junit.framework.Assert;

import com.robotium.solo.Solo;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import comp3350.iPuP.R;
import comp3350.iPuP.application.Services;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.presentation.HomeActivity;

import static comp3350.iPuP.application.Main.dbName;

public class ModifyParkingSpotTest extends ActivityInstrumentationTestCase2<HomeActivity>
{
    private final String DB_PATH = "db";
    private final String DB_FILE_NAME = "SC.script";
    private AssetManager assetManager;
    private ContextWrapper c;
    private Solo solo;
    private String dbAsset = null;

    public ModifyParkingSpotTest()
    {
        super(HomeActivity.class);
    }

    @Override
    protected void setUp() throws Exception
    {
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

        System.out.println("\nStarting Acceptance test ModifyParkingSpot (using default DB)");
    }

    @Override
    protected void tearDown() throws Exception
    {
        solo.finishOpenedActivities();

        replaceDbWithDefault();

        System.out.println("\nFinished Acceptance test ModifyParkingSpot (using default DB)");

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
    }

    public void testExistingUserModifyParking()
    {
        try
        {
            replaceDbWithDefault();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
            System.exit(1);
        }

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

        solo.clickOnText("End: Mon, 11 Jun 2018, 12:30 PM");
        Assert.assertTrue(solo.searchText("Address: 1000 Plaza Drive"));
        Assert.assertTrue(solo.searchText(Pattern.quote("Rate: $4.50")));
        Assert.assertTrue(solo.searchText("Start: Mon, 11 Jun 2018, 10:30 AM"));
        Assert.assertTrue(solo.searchText("End: Mon, 11 Jun 2018, 11:00 AM"));
        Assert.assertTrue(solo.searchText("Start: Mon, 11 Jun 2018, 11:00 AM"));
        Assert.assertTrue(solo.searchText("Start: Mon, 11 Jun 2018, 12:00 PM"));
        Assert.assertTrue(solo.searchText("End: Mon, 11 Jun 2018, 11:30 AM"));
        Assert.assertTrue(solo.searchText("End: Mon, 11 Jun 2018, 12:00 PM"));

        solo.clickOnButton("Delete");
        Assert.assertFalse(solo.searchText("End: Mon, 11 Jun 2018, 11:00 AM"));

        solo.clickOnButton("Delete");
        Assert.assertFalse(solo.searchText("End: Mon, 11 Jun 2018, 11:30 AM"));
        solo.clickOnButton("Delete");
        Assert.assertFalse(solo.searchText("End: Mon, 11 Jun 2018, 12:00 PM"));
        solo.clickOnButton("Delete");
        Assert.assertFalse(solo.searchText("End: Mon, 11 Jun 2018, 12:30 PM"));
        solo.clickOnButton("Delete");
        Assert.assertTrue(solo.searchText("There are no available spots to display"));

        solo.sleep(3000);
        solo.goBackToActivity("HomeActivity");
        solo.assertCurrentActivity("Expected activity HomeActivity", "HomeActivity");

    }

    public void testNewUserModifyParking()
    {
        try
        {
            replaceDbWithDefault();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        solo.waitForActivity("HomeActivity");
        solo.enterText((EditText) solo.getView(R.id.editTextName), "new_host");
        solo.assertCurrentActivity("Expected activity Home Activity", "HomeActivity");

        solo.clickOnButton("I have available parking to advertise");
        solo.waitForActivity("HostMenuActivity");
        solo.assertCurrentActivity("Expected activity HostMenuActivity", "HostMenuActivity");

        solo.clickOnButton("View your parking spots");
        solo.waitForActivity("HostViewActivity");
        solo.assertCurrentActivity("Expected activity Host View Activity", "HostViewActivity");

        Assert.assertTrue(solo.searchText("There are no available spots to display"));

        solo.sleep(3000);
        solo.goBackToActivity("HomeActivity");
        solo.assertCurrentActivity("Expected activity HomeActivity", "HomeActivity");
    }

}
