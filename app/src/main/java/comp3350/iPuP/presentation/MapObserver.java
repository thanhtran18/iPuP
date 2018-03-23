package comp3350.iPuP.presentation;

import org.osmdroid.util.GeoPoint;

public interface MapObserver
{
    void update(GeoPoint loc);
}
