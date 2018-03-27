package comp3350.iPuP.tests.objects;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;

import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.TimeSlot;

public class ParkingSpotTest extends TestCase
{

    public ParkingSpotTest(String arg0)
    {
        super(arg0);
    }

    public void testParkingSpots()
    {
        ParkingSpot spot;

        spot = new ParkingSpot("Camelot", "King Arthur", "888-9999", "king@roundtable.com", 10000, 5.3, 53);
        assertEquals("Camelot", spot.getAddress());
        assertEquals("King Arthur", spot.getName());
        assertEquals("888-9999", spot.getPhone());
        assertEquals("king@roundtable.com", spot.getEmail());
        assertEquals(10000.0, spot.getRate());
        assertEquals(5.3, spot.getLatitude());
        assertEquals(53.0, spot.getLongitude());

        spot.modifySpot("British Countryside", "222-5555", "king@roundtable.com", 10, 56.8, 5346.0);
        assertEquals("British Countryside", spot.getAddress());
        assertEquals("King Arthur", spot.getName());
        assertEquals("222-5555", spot.getPhone());
        assertEquals("king@roundtable.com", spot.getEmail());
        assertEquals(10.0, spot.getRate());
        assertEquals(56.8, spot.getLatitude());
        assertEquals(5346.0, spot.getLongitude());

        spot.modifySpot("British Countryside", "222-5555", "formerking@roundtable.com", 1,0,0);
        assertEquals("British Countryside", spot.getAddress());
        assertEquals("King Arthur", spot.getName());
        assertEquals("222-5555", spot.getPhone());
        assertEquals("formerking@roundtable.com", spot.getEmail());
        assertEquals(1.0, spot.getRate());
        assertEquals(0.0, spot.getLatitude());
        assertEquals(0.0, spot.getLongitude());

        spot.modifySpot(null, "222-5555", "king@roundtable.com", -9, Double.MAX_VALUE, Double.MIN_VALUE);
        assertNull(spot.getAddress());
        assertEquals("King Arthur", spot.getName());
        assertEquals("222-5555", spot.getPhone());
        assertEquals("king@roundtable.com", spot.getEmail());
        assertEquals(-9.0, spot.getRate());
        assertEquals(Double.MAX_VALUE, spot.getLatitude());
        assertEquals(Double.MIN_VALUE, spot.getLongitude());

        spot.modifySpot("Camelot", "222-5555", "", -9, 9.9, 9.99);
        assertEquals("Camelot", spot.getAddress());
        assertEquals("King Arthur", spot.getName());
        assertEquals("222-5555", spot.getPhone());
        assertEquals("", spot.getEmail());
        assertEquals(-9.0, spot.getRate());
        assertEquals(9.9, spot.getLatitude());
        assertEquals(9.99, spot.getLongitude());

        spot = new ParkingSpot(100,"Millenium Falcon", "Han Solo", "", "han@solo.com", 77,1138,2246);
        assertEquals("Millenium Falcon", spot.getAddress());
        assertEquals("Millenium Falcon", spot.toString());
        assertEquals("Han Solo", spot.getName());
        assertEquals("", spot.getPhone());
        assertEquals("han@solo.com", spot.getEmail());
        assertEquals(77.0, spot.getRate());
        assertEquals(1138.0, spot.getLatitude());
        assertEquals(2246.0, spot.getLongitude());
        assertFalse(spot.equals(new ParkingSpot(9001,"Tattoine", "Luke Skywalker", "111-5555", "luke@sky.com", 1138, 0, 0)));
        assertTrue(spot.equals(new ParkingSpot(100,"Tattoine", "Luke Skywalker", "111-5555", "luke@sky.com", 1138, 0 ,0)));
        assertFalse(spot.equals(null));
        assertFalse(spot.equals(this));

        spot = new ParkingSpot(9001,"Death Star", "Darth Vader", "", "vader@sith.com", 39, 1138,2246);
        assertEquals("Death Star", spot.getAddress());
        assertEquals("Death Star", spot.toString());
        assertEquals("Darth Vader", spot.getName());
        assertEquals("", spot.getPhone());
        assertEquals("vader@sith.com", spot.getEmail());
        assertEquals(39.0, spot.getRate());
        assertEquals(1138.0, spot.getLatitude());
        assertEquals(2246.0, spot.getLongitude());
        assertTrue(spot.equals(new ParkingSpot(9001,"Tattoine", "Luke Skywalker", "111-5555", "luke@sky.com", 1138, 0, 0)));
        assertFalse(spot.equals(new ParkingSpot(100,"Tattoine", "Luke Skywalker", "111-5555", "luke@sky.com", 1138, 0, 0)));
        assertFalse(spot.equals(null));
        assertFalse(spot.equals(this));

        spot.modifySpot("Death Star",  "555-5555", null, 1, 0,0);
        assertEquals("Death Star", spot.getAddress());
        assertEquals("Death Star", spot.toString());
        assertEquals("Darth Vader", spot.getName());
        assertEquals("555-5555", spot.getPhone());
        assertNull(spot.getEmail());
        assertEquals(1.0, spot.getRate());
        assertEquals(0.0, spot.getLatitude());
        assertEquals(0.0, spot.getLongitude());
        assertTrue(spot.equals(new ParkingSpot(9001,"Tattoine", "Luke Skywalker", "111-5555", "luke@sky.com", 1138, 0, 0)));
        assertFalse(spot.equals(new ParkingSpot(100,"Tattoine", "Luke Skywalker", "111-5555", "luke@sky.com", 1138, 0, 0)));
        assertFalse(spot.equals(null));
        assertFalse(spot.equals(this));
    }
}
