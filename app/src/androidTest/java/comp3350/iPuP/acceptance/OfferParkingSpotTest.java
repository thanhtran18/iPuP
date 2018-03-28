package comp3350.iPuP.acceptance;

/**
 * Created by Victory on 2018-03-26.
 */
import android.graphics.Point;
import android.graphics.Rect;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextSwitcher;
import android.widget.TextView;

import junit.framework.Assert;

import com.robotium.solo.Solo;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;

import java.util.Date;
import java.util.List;
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

        //todo tings dem here:
        solo.clickOnButton("Map");
        solo.waitForActivity("HostMapActivity");
        solo.clickOnButton("Cancel");
        solo.waitForActivity("HostActivity");
        solo.clickOnButton("Map");
        solo.waitForActivity("HostMapActivity");
        float clickPointX=600;
        float clickPointY=990;
        solo.clickOnScreen(clickPointX,clickPointY);
        solo.sleep(2000);
        solo.clickOnButton("Confirm");
        solo.waitForActivity("HostActivity");
        //check the geopoint

        solo.enterText((EditText) solo.getView(R.id.editTextPhone), "204-234-5678");
        solo.waitForText("204-234-5678");
        solo.enterText((EditText) solo.getView(R.id.editTextEmail), "manlikerodney@gmail.com");
        solo.waitForText("manlikerodney@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.editTextRate), "2");
        solo.waitForText("2");


        solo.clickOnView((TextView) solo.getView(R.id.textViewToDate));
        solo.setDatePicker(0, 2018, 2, 27);
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
        MapView thisMap=(MapView)solo.getView(R.id.map);

        float pisteX;
        float pisteY;
        Projection projection = thisMap.getProjection();

        //TODO: Remove this later on:
        boolean foundClickPos=false;
        List<Overlay> OverlayList=thisMap.getOverlays();
        for(Overlay current: OverlayList){
            if(current instanceof Marker){
                Marker theMarker= (Marker) current;
                Point pt = new Point();
                GeoPoint position=theMarker.getPosition();
                projection.toPixels(position, pt);
                pisteX = clickPointX-2;//pt.x-rec.left; // car X screen coord
                pisteY = clickPointY-218;//pt.y-rec.top; // car Y screen coord
                if(pisteX==pt.x && pisteY==pt.y){
                    foundClickPos=true;
                }
            }
        }
        assertTrue(foundClickPos);
    }

    public void testInvalidCreateParkingSpot()
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

