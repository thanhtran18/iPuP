package comp3350.iPuP.presentation;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.UpdateAppearance;
import android.view.View;
import android.widget.TextView;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import comp3350.iPuP.R;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.DateFormatter;

public class ParkerMapActivity extends AppCompatActivity implements DateFragmentObserver
{
    MapView map = null;
    protected AccessParkingSpots accessParkingSpots;
    ArrayList<GeoPoint> points;

    private DateFormatter df;
    Calendar current;

    @Override public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        accessParkingSpots = new AccessParkingSpots();
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        setContentView(R.layout.activity_parker_map);

        current = Calendar.getInstance();
        df = new DateFormatter();

        TextView tv = findViewById(R.id.textViewDate);
        tv.setText(df.getDateFormat().format(current.getTime()));

        points = new ArrayList<>();
        points.add(new GeoPoint(49.841347, -97.244210));
        points.add(new GeoPoint(49.924305, -97.079415));

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        IMapController mapController = map.getController();
        mapController.setZoom(12.0);
        GeoPoint startPoint = new GeoPoint(49.899444, -97.139167);
        mapController.setCenter(startPoint);

        updateMap();
    }

    private void updateMap()
    {
        map.getOverlays().clear();
        for (int i = 0; i < points.size(); i++)
        {
            SpotMarker marker = new SpotMarker(map, 1);
            marker.setPosition(points.get(i));
            marker.setTitle("Address");
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener()
            {
                @Override
                public boolean onMarkerClick(Marker marker, MapView mapView)
                {
                    return false;
                }
            });
            map.getOverlays().add(marker);
        }
        //map.invalidate();
    }

    public void onPrevClick(View v)
    {

        current.add(Calendar.DATE, -1);
        TextView tv = findViewById(R.id.textViewDate);
        tv.setText(df.getDateFormat().format(current.getTime()));
        updateMap();
    }

    public void onNextClick(View v)
    {
        current.add(Calendar.DATE, +1);
        TextView tv = findViewById(R.id.textViewDate);
        tv.setText(df.getDateFormat().format(current.getTime()));
        updateMap();
    }

    public void onDateClick(View v)
    {
        DialogFragment dateFragment = DatePickerFragment.newInstance();
        dateFragment.show(getFragmentManager(), "DatePicker");
    }

    public void onResume()
    {
        super.onResume();
        map.onResume();
    }

    public void onPause()
    {
        super.onPause();
        map.onPause();
    }

    public void buttonCancelOnClick(View view)
    {
        finish();
    }

    public void buttonConfirmOnClick(View view)
    {
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void update(Date date)
    {
        TextView tv = findViewById(R.id.textViewDate);
        tv.setText(df.getDateFormat().format(date));
        current.setTime(date);
        updateMap();
    }
}
