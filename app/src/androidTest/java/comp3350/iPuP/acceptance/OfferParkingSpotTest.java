package comp3350.iPuP.acceptance;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;

import android.graphics.Point;
import android.graphics.Rect;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.robotium.solo.Solo;
import junit.framework.Assert;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import comp3350.iPuP.R;
import comp3350.iPuP.application.Services;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.presentation.HomeActivity;

import static comp3350.iPuP.application.Main.dbName;

public class OfferParkingSpotTest extends ActivityInstrumentationTestCase2<HomeActivity>
{

    private final String DB_PATH = "db";
    private final String DB_FILE_NAME = "SC.script";
    private AssetManager assetManager;
    private ContextWrapper c;
    private Solo solo;
    private String dbAsset = null;

    public OfferParkingSpotTest()
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

        System.out.println("\nStarting Acceptance test BookParkingSpot (using default DB)");
    }

    @Override
    protected void tearDown() throws Exception
    {
        solo.finishOpenedActivities();

        replaceDbWithDefault();

        System.out.println("\nFinished Acceptance test OfferParkingSpot (using default DB)");

    }

    private void replaceDbWithDefault() throws DAOException
    {
        Services.closeDataAccess();

        try
        {
            File dataDirectory = c.getDir(DB_PATH, Context.MODE_PRIVATE);
            String[] components = dbAsset.split("/");
            String copyPath = dataDirectory + "/" + components[components.length - 1];

            File outFile = new File(copyPath);

            if (outFile.exists())
            {
                InputStream in = assetManager.open(dbAsset);
                FileUtils.copyInputStreamToFile(in, outFile);
                in.close();
            }
            else
            {
                throw new DAOException("Error in locating database file in assets!");
            }

        }
        catch (IOException ioe)
        {
            throw new DAOException("Unable to access database: ", ioe);
        }

        Services.createDataAccess(dbName);
    }

    public void testValidCreateParkingSpot()
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
        solo.setDatePicker(0, 2018, 4, 27);
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

        solo.clickOnButton("Map");
        solo.waitForActivity("HostMapActivity");
        solo.clickOnButton("Cancel");
        solo.waitForActivity("HostActivity");
        solo.clickOnButton("Map");
        solo.waitForActivity("HostMapActivity");
        float clickPointX = 600;
        float clickPointY = 990;
        solo.clickOnScreen(clickPointX, clickPointY);
        solo.sleep(2000);
        solo.clickOnButton("Confirm");
        solo.waitForActivity("HostActivity");


        solo.enterText((EditText) solo.getView(R.id.editTextPhone), "204-234-5678");
        solo.waitForText("204-234-5678");
        solo.enterText((EditText) solo.getView(R.id.editTextEmail), "manlikerodney@gmail.com");
        solo.waitForText("manlikerodney@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.editTextRate), "2");
        solo.waitForText("2");


        solo.clickOnView((TextView) solo.getView(R.id.textViewToDate));
        solo.setDatePicker(0, 2018, 4, 28);
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

        solo.goBackToActivity("HomeActivity");
        solo.waitForActivity("HomeActivity");
        solo.clickOnButton("I am looking for parking");
        solo.waitForActivity("ParkerMenuActivity");
        solo.clickOnButton("Search for available parking spots");
        solo.waitForActivity("ParkerSearchActivity");
        solo.clickOnButton("Map");
        MapView thisMap = (MapView) solo.getView(R.id.map);

        float pisteX;
        float pisteY;
        Projection projection = thisMap.getProjection();

        boolean foundClickPos=false;
        List<Overlay> OverlayList = thisMap.getOverlays();
        for(Overlay current: OverlayList)
        {
            if(current instanceof Marker)
            {
                Marker theMarker= (Marker) current;
                Point pt = new Point();
                GeoPoint position=theMarker.getPosition();
                projection.toPixels(position, pt);
                pisteX = clickPointX-2;
                pisteY = clickPointY-266;
                if(pisteX == pt.x && pisteY == pt.y){
                    foundClickPos=true;
                }
            }
        }
        assertTrue(foundClickPos);
        solo.goBackToActivity("HomeActivity");
        solo.assertCurrentActivity("Expected activity HomeActivity", "HomeActivity");

    }

    public void testInvalidCreateParkingSpot()
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


        solo.goBackToActivity("HomeActivity");
        solo.assertCurrentActivity("Expected activity HomeActivity", "HomeActivity");

    }


    public void testNewUserCreateParkingSpot()
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
        solo.enterText((EditText) solo.getView(R.id.editTextName), "new_host_creates_spots");
        solo.assertCurrentActivity("Expected activity Home Activity", "HomeActivity");

        solo.clickOnButton("I have available parking to advertise");
        solo.waitForActivity("HostMenuActivity");
        solo.assertCurrentActivity("Expected activity HostMenuActivity", "HostMenuActivity");
        solo.sleep(3000);

        solo.clickOnButton("Create a new parking spot");
        solo.waitForActivity("HostActivity");
        solo.assertCurrentActivity("Expected activity Host  Activity", "HostActivity");

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
        solo.setDatePicker(0, 2018, 4, 27);
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
        solo.setDatePicker(0, 2018, 4, 28);
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
        solo.sleep(3000);

        solo.goBackToActivity("HomeActivity");
        solo.assertCurrentActivity("Expected activity HomeActivity", "HomeActivity");
    }
}

