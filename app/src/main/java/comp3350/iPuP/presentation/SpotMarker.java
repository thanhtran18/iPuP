package comp3350.iPuP.presentation;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class SpotMarker extends Marker
{
    long spotID;
    public SpotMarker(MapView mapView, long spotID)
    {
        super(mapView);
        this.spotID = spotID;
    }

    public long getSpotID()
    {
        return this.spotID;
    }
}
