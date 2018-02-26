package comp3350.iPuP.tests.objects;

import junit.framework.TestCase;

import comp3350.iPuP.objects.ReservationTime;
import comp3350.iPuP.objects.ParkingSpot;

public class ParkingSpotTest extends TestCase
{

    public ParkingSpotTest(String arg0)
    {
        super(arg0);
    }

    public void testParkingSpots()
    {
        ReservationTime time = new ReservationTime(2017,3, 4, 8,30,2017,3, 4,9,30, false);
        assertEquals((new ParkingSpot(time, "34 software drive", "Braico", "5555555", "m@m.m", 34)).getStartTime(), time.getStart());
        assertEquals((new ParkingSpot(time, "34 software drive", "Braico", "5555555", "m@m.m", 34)).getEndTime(), time.getEnd());
        assertEquals((new ParkingSpot(time, "34 software drive", "Braico", "5555555", "m@m.m", 34)).getAddress(), "34 software drive");
        assertEquals((new ParkingSpot(time, "34 software drive", "Braico", "5555555", "m@m.m", 34)).getName(), "Braico");
        assertEquals((new ParkingSpot(time, "34 software drive", "Braico", "5555555", "m@m.m", 34)).getPhone(), "5555555");
        assertEquals((new ParkingSpot(time, "34 software drive", "Braico", "5555555", "m@m.m", 34)).getEmail(), "m@m.m");
        assertEquals((new ParkingSpot(time, "34 software drive", "Braico", "5555555", "m@m.m", 34)).getRate(), 34.0);

        ParkingSpot spot = new ParkingSpot(time, "34 software drive", "Braico", "5555555", "m@m.m", 34);
        assertEquals(spot, new ParkingSpot(time, "34 software drive", "Braico", "5555555", "m@m.m", 34));
        assertFalse(spot.equals(new ParkingSpot(time, "35 software drive", "Braico", "5555555", "m@m.m", 34)));
        assertFalse(spot.equals(new ParkingSpot(time, "34 software drive", "Braico", "5555556", "m@m.m", 34)));
        assertFalse(spot.equals(new ParkingSpot(time, "34 software drive", "Braico", "5555555", "m@m.m", 1000)));
        assertFalse(spot.equals(new ParkingSpot(time, "", "", "", "", 0)));
        assertFalse(spot.equals(null));
        assertFalse(spot.equals("some string"));
    }
}
