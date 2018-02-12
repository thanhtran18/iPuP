package comp3350.iPuP.tests.objects;

import junit.framework.TestCase;

import comp3350.iPuP.objects.ReservationTime;
import comp3350.iPuP.objects.ParkingSpot;
/**
 * Created by Amanjyot on 2018-02-05.
 */

public class ParkingSpotTest extends TestCase {

    public ParkingSpotTest(String arg0)
    {
        super(arg0);
    }

    public void testParkingSpots()
    {
        ReservationTime time = new ReservationTime(2017,3, 4, 8,30,9,30);
        assertEquals((new ParkingSpot(time, "34 software drive", "Braico", "5555555", "m@m.m", 34)).getTime(), time);
        assertEquals((new ParkingSpot(time, "34 software drive", "Braico", "5555555", "m@m.m", 34)).getAddress(), "34 software drive");
        assertEquals((new ParkingSpot(time, "34 software drive", "Braico", "5555555", "m@m.m", 34)).getName(), "Braico");
        assertEquals((new ParkingSpot(time, "34 software drive", "Braico", "5555555", "m@m.m", 34)).getPhone(), "5555555");
        assertEquals((new ParkingSpot(time, "34 software drive", "Braico", "5555555", "m@m.m", 34)).getEmail(), "m@m.m");
        assertEquals((new ParkingSpot(time, "34 software drive", "Braico", "5555555", "m@m.m", 34)).getRate(), 34.0);
    }
}
