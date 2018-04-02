package comp3350.iPuP.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.modules.IArchiveFile;
import org.osmdroid.tileprovider.modules.OfflineTileProvider;
import org.osmdroid.tileprovider.tilesource.BitmapTileSourceBase;
import org.osmdroid.tileprovider.tilesource.FileBasedTileSource;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.tileprovider.util.SimpleRegisterReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.File;
import java.util.Set;

import comp3350.iPuP.R;

public class HostMapActivity extends AppCompatActivity implements MapObserver
{
    MapView map = null;
    GeoPoint markerLoc;
    @Override public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        setContentView(R.layout.activity_host_map);

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

            String source = "";
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
            map.getOverlays().add(new TouchOverlay(this));
            map.invalidate();
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Unable to load map data",Toast.LENGTH_LONG).show();
        }
    }

    private void SetMarker(GeoPoint loc)
    {
        Marker marker = new Marker(map);
        marker.setPosition(loc);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setTitle("Start point");
        if (map.getOverlays().size() > 1)
            map.getOverlays().remove(1);
        map.getOverlays().add(marker);
        map.invalidate();
        markerLoc = loc;
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

    @Override
    public void update(GeoPoint loc)
    {
        SetMarker(loc);
    }

    public void buttonCancelOnClick(View view)
    {
        finish();
    }

    public void buttonConfirmOnClick(View view)
    {
        if (markerLoc != null)
        {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(getResources().getString(R.string.extra_lat), markerLoc.getLatitude());
            resultIntent.putExtra(getResources().getString(R.string.extra_long), markerLoc.getLongitude());
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    }
}
