package comp3350.iPuP.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import comp3350.iPuP.application.Main;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.TimeSlot;

public class AccessParkingSpotsTest extends TestCase
{
    AccessParkingSpots parkSpotAccess;
    ParkingSpot ps;
    ArrayList<ParkingSpot> spots;
    ArrayList<ParkingSpot> allSpots;

    public AccessParkingSpotsTest(String arg0)
    {
        super(arg0);
    }

    public void testEmptyList()
    {
        Main.startUp();
        System.out.println("Starting testAccessParkingSpots: No parking spots inserted.");
        parkSpotAccess=new AccessParkingSpots();
        parkSpotAccess.clearSpots();
        spots=parkSpotAccess.getAvailableSpots();
        assertTrue(spots.size()==0);

        parkSpotAccess.bookSpot("fakeId", 0);
        assertTrue(parkSpotAccess.bookSpot("fakeId", 0).equals("Not Booked"));
        assertTrue(spots.size()==0);
        System.out.println("Finished testAccessParkingSpots: No parking spots inserted.");
    }

    public void testInsertParkingSpot()
    {
        Main.startUp();

        //First test================================================
        parkSpotAccess=new AccessParkingSpots();
        parkSpotAccess.clearSpots();

        Calendar c = Calendar.getInstance();
        c.set(2018, 3, 24, 10, 30);
        Date start, end;
        start = c.getTime();
        c.add(Calendar.HOUR_OF_DAY,2);
        end = c.getTime();

        TimeSlot timeSlot = new TimeSlot(start,end);
        try
        {
            parkSpotAccess.insertParkingSpot("testuser", timeSlot, null, "356 testing drive, Winnipeg, MB", "456-6789", "", 42);
        }
        catch (Exception e)
        {
            fail();
        }

        assertEquals(parkSpotAccess.getAllSpots().size(), 1);

        ParkingSpot spot = parkSpotAccess.getAllSpots().get(0);

        assertEquals(spot.getName(), "testuser");
        assertEquals(spot.getPhone(), "456-6789");
        assertEquals(spot.getEmail(), "");
        assertEquals(spot.getAddress(), "356 testing drive, Winnipeg, MB");
        assertEquals(spot.getRate(), 42);

        long spotID = spot.getSpotID();
        ArrayList<TimeSlot> daySlots = null;
        try
        {
            daySlots = parkSpotAccess.getDaySlots(spotID);
        }
        catch (Exception e)
        {
            fail();
        }

        c.set(2018, 3, 24, 10, 30);
        start = c.getTime();
        c.add(Calendar.HOUR_OF_DAY,2);
        end = c.getTime();

        assertEquals(daySlots.size(), 1);
        TimeSlot daySlot = daySlots.get(0);
        assertEquals(daySlot.getStart(), start);
        assertEquals(daySlot.getEnd(), end);

        long daySlotID = daySlot.getSlotID();
        ArrayList<TimeSlot> timeSlots = null;
        try
        {
            timeSlots = parkSpotAccess.getTimeSlots(daySlotID);
        }
        catch (Exception e)
        {
            fail();
        }

        assertEquals(timeSlots.size(), 4);

        c.set(2018, 3, 24, 10, 30);
        start = c.getTime();
        c.add(Calendar.MINUTE,30);
        end = c.getTime();
        timeSlot = daySlots.get(0);

        assertEquals(timeSlot.getStart(), start);
        assertEquals(timeSlot.getEnd(), end);

        start = c.getTime();
        c.add(Calendar.MINUTE,30);
        end = c.getTime();
        timeSlot = daySlots.get(1);

        assertEquals(timeSlot.getStart(), start);
        assertEquals(timeSlot.getEnd(), end);

        //Second test================================================
        c.set(2018, 10, 12, 16, 30);
        start = c.getTime();
        c.add(Calendar.HOUR_OF_DAY,1);
        end = c.getTime();

        timeSlot = new TimeSlot(start,end);
        try
        {
            parkSpotAccess.insertParkingSpot("testuser2", timeSlot, "Days 3 4", "whodunnit St.", "555-5555", "hans@hans.hans", 10);
        }
        catch (Exception e)
        {
            fail();
        }

        assertEquals(parkSpotAccess.getAllSpots().size(), 2);

        spot = parkSpotAccess.getAllSpots().get(1);

        assertEquals(spot.getName(), "testuser2");
        assertEquals(spot.getPhone(), "555-5555");
        assertEquals(spot.getEmail(), "hans@hans.hans");
        assertEquals(spot.getAddress(), "whodunnit St.");
        assertEquals(spot.getRate(), 10);

        spotID = spot.getSpotID();
        daySlots = null;
        try
        {
            daySlots = parkSpotAccess.getDaySlots(spotID);
        }
        catch (Exception e)
        {
            fail();
        }

        assertEquals(daySlots.size(), 4);

        c.set(2018, 3, 27, 10, 30);
        start = c.getTime();
        c.add(Calendar.HOUR_OF_DAY,1);
        end = c.getTime();

        daySlot = daySlots.get(1);
        assertEquals(daySlot.getStart(), start);
        assertEquals(daySlot.getEnd(), end);

        c.set(2018, 3, 30, 10, 30);
        start = c.getTime();
        c.add(Calendar.HOUR_OF_DAY,1);
        end = c.getTime();

        daySlot = daySlots.get(2);
        assertEquals(daySlot.getStart(), start);
        assertEquals(daySlot.getEnd(), end);


        c.set(2018, 3, 24, 10, 30);
        start = c.getTime();
        c.add(Calendar.HOUR_OF_DAY,2);
        end = c.getTime();

        assertEquals(daySlots.size(), 4);
        daySlot = daySlots.get(0);
        assertEquals(daySlot.getStart(), start);
        assertEquals(daySlot.getEnd(), end);

        daySlotID = daySlot.getSlotID();
        ArrayList<TimeSlot> timeSlots = null;
        try
        {
            timeSlots = parkSpotAccess.getTimeSlots(daySlotID);
        }
        catch (Exception e)
        {
            fail();
        }

        assertEquals(timeSlots.size(), 4);

        c.set(2018, 3, 24, 10, 30);
        start = c.getTime();
        c.add(Calendar.MINUTE,30);
        end = c.getTime();
        timeSlot = daySlots.get(0);

        assertEquals(timeSlot.getStart(), start);
        assertEquals(timeSlot.getEnd(), end);

        start = c.getTime();
        c.add(Calendar.MINUTE,30);
        end = c.getTime();
        timeSlot = daySlots.get(1);

        assertEquals(timeSlot.getStart(), start);
        assertEquals(timeSlot.getEnd(), end);
    }

    public void testOneParkingSpotInList()
    {
        Main.startUp();
        System.out.println("Starting testAccessParkingSpots: 1 parking spot in list.");
/*
        parkSpotAccess=new AccessParkingSpots();
        parkSpotAccess.clearSpots();
        ReservationTime time = new ReservationTime(2018, 6, 11, 10, 30,
                12, 30);
        ps=new ParkingSpot(time, "70 Plaza Place", "ThePerson", "201789465",
                "theperson@domainname.com", 0.25);
        parkSpotAccess.insertParkingSpot(ps);
        spots=parkSpotAccess.getAvailableSpots();
        assertTrue(spots.size()==1);

        assertTrue(parkSpotAccess.
                bookSpot("70 Plaza PlaceThePerson201789465theperson@domainname.com")
                .equals("Booked"));
        spots=parkSpotAccess.getAvailableSpots();
        assertTrue(spots.size()==0);

        allSpots=parkSpotAccess.getAllSpots();
        assertTrue(allSpots.get(0).isBooked());
*/
        System.out.println("Finished testAccessParkingSpots: 1 parking spot in list");
    }

    public void testRegularParkingSpotData()
    {
        Main.startUp();
        System.out.println("Starting testAccessParkingSpots: regular data in list.");

        parkSpotAccess = new AccessParkingSpots();
        parkSpotAccess.clearSpots();
/*
        ReservationTime time = new ReservationTime(2018, 6, 11, 10, 30,
                12, 30);
        ps = new ParkingSpot(time, "70 Plaza Place", "ThePerson", "201789465",
                "theperson@domainname.com", 0.25);
        parkSpotAccess.insertParkingSpot(ps); //pos 0

        time = new ReservationTime(2017, 5, 17, 10, 30,
                12, 30);
        ps = new ParkingSpot(time, "788 Plaza Place", "TheGuy", "20178978",
                "theGuy@domainname.com", 0.75);
        parkSpotAccess.insertParkingSpot(ps); //pos 1

        time = new ReservationTime(2017, 5, 20, 6, 15,
                10, 40);
        ps = new ParkingSpot(time, "707 Ave Place", "TheGirl", "204899465",
                "theGirl@domainname.com", 0.95);
        parkSpotAccess.insertParkingSpot(ps); //pos 2

        time = new ReservationTime(2016, 7, 20, 10, 30,
                8, 30);
        ps = new ParkingSpot(time, "588 Markham Place", "TheLady", "2047589465",
                "theLady@domainname.com", 1.25);
        parkSpotAccess.insertParkingSpot(ps); //pos 3

        assertTrue(parkSpotAccess.bookSpot("788 Plaza PlaceTheGuy20178978theGuy@domainname.com")
                .equals("Booked"));
        assertTrue(parkSpotAccess.
                bookSpot("588 Markham PlaceTheLady2047589465theLady@domainname.com")
                .equals("Booked"));
*/
        spots = parkSpotAccess.getAvailableSpots();
        assertTrue(spots.size()==2);

        allSpots = parkSpotAccess.getAllSpots();
        assertFalse(allSpots.get(0).isBooked());
        assertTrue(allSpots.get(1).isBooked());
        assertFalse(allSpots.get(2).isBooked());
        assertTrue(allSpots.get(3).isBooked());

        System.out.println("Finished testAccessParkingSpots: regular data in list");
    }

    public void testBookNonExistingParkingSpot()
    {
        Main.startUp();
        System.out.println("Starting testAccessParkingSpots: Booking a spot that does not exist.");

        parkSpotAccess=new AccessParkingSpots();
        parkSpotAccess.clearSpots();
/*
        ReservationTime time = new ReservationTime(2018, 6, 11, 10, 30,
                12, 30);
        ps = new ParkingSpot(time, "70 Plaza Place", "ThePerson", "201789465",
                "theperson@domainname.com", 0.25);
        parkSpotAccess.insertParkingSpot(ps); //pos 0

        time = new ReservationTime(2017, 5, 17, 10, 30,
                12, 30);
        ps = new ParkingSpot(time, "788 Plaza Place", "TheGuy", "20178978",
                "theGuy@domainname.com", 0.75);
        parkSpotAccess.insertParkingSpot(ps); //pos 1

        time = new ReservationTime(2017, 5, 20, 6, 15,
                10, 40);
        ps = new ParkingSpot(time, "707 Ave Place", "TheGirl", "204899465",
                "theGirl@domainname.com", 0.95);
        parkSpotAccess.insertParkingSpot(ps); //pos 2

        time = new ReservationTime(2016, 7, 20, 10, 30,
                8, 30);
        ps = new ParkingSpot(time, "588 Markham Place", "TheLady", "2047589465",
                "theLady@domainname.com", 1.25);
        parkSpotAccess.insertParkingSpot(ps); //pos 3

        //book using incorrect id's
        assertTrue(parkSpotAccess.bookSpot("789 Plaza PlaceTheGuy20178978theGuy@domainname.com")
                .equals("Not Booked"));
        assertTrue(parkSpotAccess.
                bookSpot("588 Markham PlaceTheLady2047589465theLady@domainname.comkj")
                .equals("Not Booked"));
*/
        spots = parkSpotAccess.getAvailableSpots();
        assertTrue(spots.size() == 4);
        System.out.println("Finished testAccessParkingSpots: Booking a spot that does not exist");
    }

    public void testBookingAllSpots()
    {
        Main.startUp();
        System.out.println("Starting testAccessParkingSpots: Booking all spots.");

        parkSpotAccess = new AccessParkingSpots();
        parkSpotAccess.clearSpots();
/*
        ReservationTime time = new ReservationTime(2018, 6, 11, 10, 30,
                12, 30);
        ps = new ParkingSpot(time, "70 Plaza Place", "ThePerson", "201789465",
                "theperson@domainname.com", 0.25);
        parkSpotAccess.insertParkingSpot(ps); //pos 0

        time = new ReservationTime(2017, 5, 17, 10, 30,
                12, 30);
        ps = new ParkingSpot(time, "788 Plaza Place", "TheGuy", "20178978",
                "theGuy@domainname.com", 0.75);
        parkSpotAccess.insertParkingSpot(ps); //pos 1

        time = new ReservationTime(2017, 5, 20, 6, 15,
                10, 40);
        ps = new ParkingSpot(time, "707 Ave Place", "TheGirl", "204899465",
                "theGirl@domainname.com", 0.95);
        parkSpotAccess.insertParkingSpot(ps); //pos 2

        time = new ReservationTime(2016, 7, 20, 10, 30,
                8, 30);
        ps = new ParkingSpot(time, "588 Markham Place", "TheLady", "2047589465",
                "theLady@domainname.com", 1.25);
        parkSpotAccess.insertParkingSpot(ps); //pos 3

        assertTrue(parkSpotAccess.
                bookSpot("70 Plaza PlaceThePerson201789465theperson@domainname.com")
                .equals("Booked"));
        assertTrue(parkSpotAccess.
                bookSpot("788 Plaza PlaceTheGuy20178978theGuy@domainname.com")
                .equals("Booked"));
        assertTrue(parkSpotAccess.
                bookSpot("707 Ave PlaceTheGirl204899465theGirl@domainname.com")
                .equals("Booked"));
        assertTrue(parkSpotAccess.
                bookSpot("588 Markham PlaceTheLady2047589465theLady@domainname.com")
                .equals("Booked"));
        spots=parkSpotAccess.getAvailableSpots();
        assertTrue(spots.size() == 0);
*/
        allSpots = parkSpotAccess.getAllSpots();
        assertTrue(allSpots.get(0).isBooked());
        assertTrue(allSpots.get(1).isBooked());
        assertTrue(allSpots.get(2).isBooked());
        assertTrue(allSpots.get(3).isBooked());
        System.out.println("Finished testAccessParkingSpots: Booking all spots");
    }

    public void testBookingNoSpots()
    {
        Main.startUp();
        System.out.println("Starting testAccessParkingSpots: regular data in list.");

        parkSpotAccess = new AccessParkingSpots();
        parkSpotAccess.clearSpots();
/*
        ReservationTime time = new ReservationTime(2018, 6, 11, 10, 30,
                12, 30);
        ps = new ParkingSpot(time, "70 Plaza Place", "ThePerson", "201789465",
                "theperson@domainname.com", 0.25);
        parkSpotAccess.insertParkingSpot(ps); //pos 0

        time = new ReservationTime(2017, 5, 17, 10, 30,
                12, 30);
        ps = new ParkingSpot(time, "788 Plaza Place", "TheGuy", "20178978",
                "theGuy@domainname.com", 0.75);
        parkSpotAccess.insertParkingSpot(ps); //pos 1

        time = new ReservationTime(2017, 5, 20, 6, 15,
                10, 40);
        ps = new ParkingSpot(time, "707 Ave Place", "TheGirl", "204899465",
                "theGirl@domainname.com", 0.95);
        parkSpotAccess.insertParkingSpot(ps); //pos 2

        time = new ReservationTime(2016, 7, 20, 10, 30,
                8, 30);
        ps = new ParkingSpot(time, "588 Markham Place", "TheLady", "2047589465",
                "theLady@domainname.com", 1.25);
        parkSpotAccess.insertParkingSpot(ps); //pos 3

        spots = parkSpotAccess.getAvailableSpots();
        assertTrue(spots.size()==4);
*/
        allSpots = parkSpotAccess.getAllSpots();
        assertFalse(allSpots.get(0).isBooked());
        assertFalse(allSpots.get(1).isBooked());
        assertFalse(allSpots.get(2).isBooked());
        assertFalse(allSpots.get(3).isBooked());
        System.out.println("Finished testAccessParkingSpots: regular data in list");
    }

    public void testBookingTwice()
    {
        Main.startUp();
        System.out.println("Starting testAccessParkingSpots: Booking a spot twice.");

        parkSpotAccess = new AccessParkingSpots();
        parkSpotAccess.clearSpots();
/*
        ReservationTime time = new ReservationTime(2018, 6, 11, 10, 30,
                12, 30);
        ps = new ParkingSpot(time, "70 Plaza Place", "ThePerson", "201789465",
                "theperson@domainname.com", 0.25);
        parkSpotAccess.insertParkingSpot(ps); //pos 0

        time = new ReservationTime(2017, 5, 17, 10, 30,
                12, 30);
        ps = new ParkingSpot(time, "788 Plaza Place", "TheGuy", "20178978",
                "theGuy@domainname.com", 0.75);
        parkSpotAccess.insertParkingSpot(ps); //pos 1
        ps.setBooked(true);

        time = new ReservationTime(2017, 5, 20, 6, 15,
                10, 40);
        ps = new ParkingSpot(time, "707 Ave Place", "TheGirl", "204899465",
                "theGirl@domainname.com", 0.95);
        parkSpotAccess.insertParkingSpot(ps); //pos 2
        ps.setBooked(true);

        time = new ReservationTime(2016, 7, 20, 10, 30,
                8, 30);
        ps = new ParkingSpot(time, "588 Markham Place", "TheLady", "2047589465",
                "theLady@domainname.com", 1.25);
        parkSpotAccess.insertParkingSpot(ps); //pos 3
        ps.setBooked(true);
*/
        assertTrue(parkSpotAccess.
                bookSpot("788 Plaza PlaceTheGuy", 0)
                .equals("Already Booked"));

        assertTrue(parkSpotAccess.
                bookSpot("588 Markham PlaceTheLady", 0)
                .equals("Already Booked"));

        spots = parkSpotAccess.getAvailableSpots();
        assertTrue(spots.size()==1);

        allSpots = parkSpotAccess.getAllSpots();
        assertFalse(allSpots.get(0).isBooked());
        assertTrue(allSpots.get(1).isBooked());
        assertTrue(allSpots.get(2).isBooked());
        assertTrue(allSpots.get(3).isBooked());
        System.out.println("Finished testAccessParkingSpots: Booking a spot twice.");
    }
}