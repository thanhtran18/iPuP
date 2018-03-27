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

        spot = new ParkingSpot("Camelot", "King Arthur", "888-9999", "king@roundtable.com", 10000,0,0);
        assertEquals("Camelot", spot.getAddress());
        assertEquals("King Arthur", spot.getName());
        assertEquals("888-9999", spot.getPhone());
        assertEquals("king@roundtable.com", spot.getEmail());
        assertEquals(10000.0, spot.getRate());

        spot.modifySpot("British Countryside", "222-5555", "king@roundtable.com", 10);
        assertEquals("British Countryside", spot.getAddress());
        assertEquals("King Arthur", spot.getName());
        assertEquals("222-5555", spot.getPhone());
        assertEquals("king@roundtable.com", spot.getEmail());
        assertEquals(10.0, spot.getRate());

        spot.modifySpot("British Countryside", "222-5555", "formerking@roundtable.com", 1);
        assertEquals("British Countryside", spot.getAddress());
        assertEquals("King Arthur", spot.getName());
        assertEquals("222-5555", spot.getPhone());
        assertEquals("formerking@roundtable.com", spot.getEmail());
        assertEquals(1.0, spot.getRate());

        spot.modifySpot(null, "222-5555", "king@roundtable.com", -9);
        assertNull(spot.getAddress());
        assertEquals("King Arthur", spot.getName());
        assertEquals("222-5555", spot.getPhone());
        assertEquals("king@roundtable.com", spot.getEmail());
        assertEquals(-9.0, spot.getRate());

        spot.modifySpot("Camelot", "222-5555", "", -9);
        assertEquals("Camelot", spot.getAddress());
        assertEquals("King Arthur", spot.getName());
        assertEquals("222-5555", spot.getPhone());
        assertEquals("", spot.getEmail());
        assertEquals(-9.0, spot.getRate());

        spot = new ParkingSpot(100,"Millenium Falcon", "Han Solo", "", "han@solo.com", 77,0,0);
        assertEquals("Millenium Falcon", spot.getAddress());
        assertEquals("Millenium Falcon", spot.toString());
        assertEquals("Han Solo", spot.getName());
        assertEquals("", spot.getPhone());
        assertEquals("han@solo.com", spot.getEmail());
        assertEquals(77.0, spot.getRate());
        assertFalse(spot.equals(new ParkingSpot(9001,"Tattoine", "Luke Skywalker", "111-5555", "luke@sky.com", 1138,0,0)));
        assertTrue(spot.equals(new ParkingSpot(100,"Tattoine", "Luke Skywalker", "111-5555", "luke@sky.com", 1138,0,0)));
        assertFalse(spot.equals(null));
        assertFalse(spot.equals(this));

        spot = new ParkingSpot(9001,"Death Star", "Darth Vader", "", "vader@sith.com", 39,0,0);
        assertEquals("Death Star", spot.getAddress());
        assertEquals("Death Star", spot.toString());
        assertEquals("Darth Vader", spot.getName());
        assertEquals("", spot.getPhone());
        assertEquals("vader@sith.com", spot.getEmail());
        assertEquals(39.0, spot.getRate());
        assertTrue(spot.equals(new ParkingSpot(9001,"Tattoine", "Luke Skywalker", "111-5555", "luke@sky.com", 1138,0,0)));
        assertFalse(spot.equals(new ParkingSpot(100,"Tattoine", "Luke Skywalker", "111-5555", "luke@sky.com", 1138,0,0)));
        assertFalse(spot.equals(null));
        assertFalse(spot.equals(this));

        spot.modifySpot("Death Star",  "555-5555", null, 1);
        assertEquals("Death Star", spot.getAddress());
        assertEquals("Death Star", spot.toString());
        assertEquals("Darth Vader", spot.getName());
        assertEquals("555-5555", spot.getPhone());
        assertNull(spot.getEmail());
        assertEquals(1.0, spot.getRate());
        assertTrue(spot.equals(new ParkingSpot(9001,"Tattoine", "Luke Skywalker", "111-5555", "luke@sky.com", 1138,0,0)));
        assertFalse(spot.equals(new ParkingSpot(100,"Tattoine", "Luke Skywalker", "111-5555", "luke@sky.com", 1138,0,0)));
        assertFalse(spot.equals(null));
        assertFalse(spot.equals(this));
    }
}
