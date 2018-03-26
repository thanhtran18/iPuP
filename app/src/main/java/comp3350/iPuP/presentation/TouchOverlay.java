package comp3350.iPuP.presentation;

import android.graphics.Canvas;
import android.view.MotionEvent;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.Overlay;

public class TouchOverlay extends Overlay
{
    private MapObserver observer;
    public TouchOverlay(MapObserver observer)
    {
        this.observer = observer;
    }
    @Override
    public void draw(Canvas c, MapView osmv, boolean shadow)
    {

    }

    @Override
    public boolean onSingleTapConfirmed(final MotionEvent e, final MapView mapView)
    {

        Projection proj = mapView.getProjection();
        GeoPoint loc = (GeoPoint) proj.fromPixels((int)e.getX(), (int)e.getY());
        observer.update(loc);
        return true;
    }
}
