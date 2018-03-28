package comp3350.iPuP.presentation;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.modules.IArchiveFile;
import org.osmdroid.tileprovider.modules.OfflineTileProvider;
import org.osmdroid.tileprovider.tilesource.FileBasedTileSource;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.util.SimpleRegisterReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import comp3350.iPuP.R;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.DateFormatter;
import comp3350.iPuP.objects.ParkingSpot;

public class ParkerMapActivity extends AppCompatActivity implements DateFragmentObserver
{
    MapView map = null;
    protected AccessParkingSpots accessParkingSpots;

    private DateFormatter df;
    Calendar current;

    long currentSpotID = -1;

    @Override public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        accessParkingSpots = new AccessParkingSpots();
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        setContentView(R.layout.activity_parker_map);

        current = Calendar.getInstance();
        df = new DateFormatter();
        if (current.get(Calendar.MINUTE) > 30)
            current.set(Calendar.MINUTE, 30);
        else current.set(Calendar.MINUTE, 0);

        TextView tv = findViewById(R.id.textViewDate);
        tv.setText(df.getDateFormat().format(current.getTime()));
        tv = findViewById(R.id.textViewTime);
        tv.setText(df.getTimeFormat().format(current.getTime()));

        map = findViewById(R.id.map);
        map.setUseDataConnection(false);
        try
        {

            final String MAP_PATH = "tiles";

            Context context = getApplicationContext();
            File dataDirectory = context.getDir(MAP_PATH, Context.MODE_PRIVATE);
            File[] files = dataDirectory.listFiles();
            OfflineTileProvider tileProvider = new OfflineTileProvider(new SimpleRegisterReceiver(this),files);
            map.setTileProvider(tileProvider);

            String source;
            IArchiveFile[] archives = tileProvider.getArchives();
            if (archives.length > 0)
            {
                Set<String> tileSources = archives[0].getTileSources();
                if (!tileSources.isEmpty())
                {
                    source = tileSources.iterator().next();
                    this.map.setTileSource(FileBasedTileSource.getSource(source));
                }
                else
                {
                    this.map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
                }

            }
            else
            {
                this.map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
            }

            map.setBuiltInZoomControls(true);
            map.setMultiTouchControls(true);
            IMapController mapController = map.getController();
            mapController.setZoom(12.0);
            GeoPoint startPoint = new GeoPoint(49.899444, -97.139167);
            mapController.setCenter(startPoint);
            map.invalidate();
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Unable to load map data",Toast.LENGTH_LONG).show();
        }

        updateMap();
    }

    private void updateMap()
    {
        try
        {
            ArrayList<ParkingSpot> parkingSpots = accessParkingSpots.getParkingSpotsByTime(current.getTime());
            map.getOverlays().clear();
            for (final ParkingSpot spot : parkingSpots)
            {
                SpotMarker marker = new SpotMarker(map, spot.getSpotID());
                GeoPoint point = new GeoPoint(spot.getLatitude(), spot.getLongitude());
                marker.setPosition(point);
                marker.setTitle(spot.getAddress());
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener()
                {
                    @Override
                    public boolean onMarkerClick(Marker marker, MapView mapView)
                    {
                        marker.showInfoWindow();
                        currentSpotID = spot.getSpotID();
                        return true;
                    }
                });
                map.getOverlays().add(marker);
            }
            map.invalidate();
        }
        catch (Exception daoe)
        {
            Toast.makeText(this,"Unable to load parking spot data",Toast.LENGTH_LONG).show();
        }
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
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        current.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH),c.get(Calendar.DATE),
                current.get(Calendar.HOUR), current.get(Calendar.MINUTE));
        updateMap();
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

    public void onPrevTimeClick(View view)
    {
        current.add(Calendar.MINUTE, -30);
        TextView tv = findViewById(R.id.textViewDate);
        tv.setText(df.getDateFormat().format(current.getTime()));
        tv = findViewById(R.id.textViewTime);
        tv.setText(df.getTimeFormat().format(current.getTime()));
        updateMap();
    }

    public void onNextTimeClick(View view)
    {
        current.add(Calendar.MINUTE, 30);
        TextView tv = findViewById(R.id.textViewDate);
        tv.setText(df.getDateFormat().format(current.getTime()));
        tv = findViewById(R.id.textViewTime);
        tv.setText(df.getTimeFormat().format(current.getTime()));
        updateMap();
    }

    public void onBookClick(View view)
    {
        if (currentSpotID != -1)
        {
            String name;
            Bundle extras = getIntent().getExtras();
            if(extras == null)
            {
                name = null;
            }
            else
            {
                name = extras.getString(getResources().getString(R.string.extra_name));
            }
            Intent intent = new Intent(getApplicationContext(), BookTimeSlotsActivity.class);
            intent.putExtra(getResources().getString(R.string.extra_spotID), currentSpotID);
            intent.putExtra(getResources().getString(R.string.extra_name), name);
            startActivity(intent);
        }
    }
}
