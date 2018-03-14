package comp3350.iPuP.tests.business;

import junit.framework.TestCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import comp3350.iPuP.application.Main;
import comp3350.iPuP.application.Services;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.Booking;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.objects.DateFormatter;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.TimeSlot;
import comp3350.iPuP.tests.persistence.DataAccessStub;

public class AccessParkingSpotsTest extends TestCase
{
    private static String dbName = Main.dbName;
    AccessParkingSpots parkSpotAccess;
    ParkingSpot ps;
    ArrayList<ParkingSpot> spots;
    ArrayList<ParkingSpot> allSpots;
    ArrayList<Booking> bookings;

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

//        parkSpotAccess.bookSpot("fakeId", 0);
//        assertTrue(parkSpotAccess.bookSpot("fakeId", 0).equals("Not Booked"));
        assertTrue(spots.size()==0);
        System.out.println("Finished testAccessParkingSpots: No parking spots inserted.");
    }

    public void testInsertParkingSpot()
    {
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        ParkingSpot spot = null;

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
        catch (DAOException daoe)
        {
            System.out.println(daoe.getMessage());
            fail();
        }

        try
        {
            assertEquals(parkSpotAccess.getAllParkingSpots().size(), 1);

            spot = parkSpotAccess.getAllParkingSpots().get(0);
        }
        catch (DAOException daoe)
        {
            fail();
        }

        assertEquals(spot.getName(), "testuser");
        assertEquals(spot.getPhone(), "456-6789");
        assertEquals(spot.getEmail(), "");
        assertEquals(spot.getAddress(), "356 testing drive, Winnipeg, MB");
        assertEquals(spot.getRate(), 42.0);

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
        timeSlot = timeSlots.get(0);

        assertEquals(timeSlot.getStart(), start);
        assertEquals(timeSlot.getEnd(), end);

        start = c.getTime();
        c.add(Calendar.MINUTE,30);
        end = c.getTime();
        timeSlot = timeSlots.get(1);

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

        try
        {
            assertEquals(parkSpotAccess.getAllParkingSpots().size(), 2);

            spot = parkSpotAccess.getAllParkingSpots().get(1);
        }
        catch (DAOException daoe) {fail();}

        assertEquals(spot.getName(), "testuser2");
        assertEquals(spot.getPhone(), "555-5555");
        assertEquals(spot.getEmail(), "hans@hans.hans");
        assertEquals(spot.getAddress(), "whodunnit St.");
        assertEquals(spot.getRate(), 10.0);

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

        c.set(2018, 10, 15, 16, 30);
        start = c.getTime();
        c.add(Calendar.HOUR_OF_DAY,1);
        end = c.getTime();

        daySlot = daySlots.get(1);
        assertEquals(daySlot.getStart(), start);
        assertEquals(daySlot.getEnd(), end);

        c.set(2018, 10, 18, 16, 30);
        start = c.getTime();
        c.add(Calendar.HOUR_OF_DAY,1);
        end = c.getTime();

        daySlot = daySlots.get(2);
        assertEquals(daySlot.getStart(), start);
        assertEquals(daySlot.getEnd(), end);


        c.set(2018, 10, 21, 16, 30);
        start = c.getTime();
        c.add(Calendar.HOUR_OF_DAY,1);
        end = c.getTime();

        assertEquals(daySlots.size(), 4);
        daySlot = daySlots.get(3);
        assertEquals(daySlot.getStart(), start);
        assertEquals(daySlot.getEnd(), end);

        daySlotID = daySlot.getSlotID();
        timeSlots = null;
        try
        {
            timeSlots = parkSpotAccess.getTimeSlots(daySlotID);
        }
        catch (Exception e)
        {
            fail();
        }

        assertEquals(2, timeSlots.size());

        c.set(2018, 10, 21, 16, 30);
        start = c.getTime();
        c.add(Calendar.MINUTE,30);
        end = c.getTime();
        timeSlot = timeSlots.get(0);

        assertEquals(timeSlot.getStart(), start);
        assertEquals(timeSlot.getEnd(), end);

        start = c.getTime();
        c.add(Calendar.MINUTE,30);
        end = c.getTime();
        timeSlot = timeSlots.get(1);

        assertEquals(start, timeSlot.getStart());
        assertEquals(end, timeSlot.getEnd());

        //Third test================================================
        c.set(1455, 1, 1, 1, 0);
        start = c.getTime();
        c.add(Calendar.HOUR_OF_DAY,6);
        end = c.getTime();

        timeSlot = new TimeSlot(start,end);
        try
        {
            parkSpotAccess.insertParkingSpot("Sir Galavant", timeSlot, "Weeks 2 5 0100111", "5 Smithy Lane, Camelot", "0909090", "galavant@roundtable.brit", 0.02);
        }
        catch (Exception e)
        {
            fail();
        }

        try
        {
            assertEquals(parkSpotAccess.getAllParkingSpots().size(), 3);

            spot = parkSpotAccess.getAllParkingSpots().get(2);
        }
        catch (DAOException daoe) {fail();}

        assertEquals(spot.getName(), "Sir Galavant");
        assertEquals(spot.getPhone(), "0909090");
        assertEquals(spot.getEmail(), "galavant@roundtable.brit");
        assertEquals(spot.getAddress(), "5 Smithy Lane, Camelot");
        assertEquals(spot.getRate(), 0.02);

        spotID = spot.getSpotID();
        try
        {
            daySlots = parkSpotAccess.getDaySlots(spotID);
        }
        catch (Exception e)
        {
            fail();
        }

        assertEquals(20, daySlots.size());

        c.set(1455, 1, 1, 1, 0);
        start = c.getTime();
        c.add(Calendar.HOUR_OF_DAY,6);
        end = c.getTime();

        daySlot = daySlots.get(0);
        assertEquals(start, daySlot.getStart());
        assertEquals(end, daySlot.getEnd());

        c.set(1455, 1, 1, 1, 0);
        start = c.getTime();
        c.add(Calendar.HOUR_OF_DAY,6);
        end = c.getTime();

        daySlot = daySlots.get(0);
        assertEquals(start, daySlot.getStart());
        assertEquals(end, daySlot.getEnd());

        c.set(1455, 1, 3, 1, 0);
        start = c.getTime();
        c.add(Calendar.HOUR_OF_DAY,6);
        end = c.getTime();

        daySlot = daySlots.get(1);
        assertEquals(daySlot.getStart(), start);
        assertEquals(daySlot.getEnd(), end);

        c.set(1455, 1, 6, 1, 0);
        start = c.getTime();
        c.add(Calendar.HOUR_OF_DAY,6);
        end = c.getTime();

        daySlot = daySlots.get(2);
        assertEquals(start, daySlot.getStart());
        assertEquals(end, daySlot.getEnd());

        c.set(1455, 1, 7, 1, 0);
        start = c.getTime();
        c.add(Calendar.HOUR_OF_DAY,6);
        end = c.getTime();

        daySlot = daySlots.get(3);
        assertEquals(start, daySlot.getStart());
        assertEquals(end, daySlot.getEnd());

        daySlotID = daySlot.getSlotID();
        timeSlots = null;
        try
        {
            timeSlots = parkSpotAccess.getTimeSlots(daySlotID);
        }
        catch (Exception e)
        {
            fail();
        }

        assertEquals(timeSlots.size(), 12);

        c.set(1455, 1, 7, 1, 0);
        start = c.getTime();
        c.add(Calendar.MINUTE,30);
        end = c.getTime();
        timeSlot = timeSlots.get(0);

        assertEquals(timeSlot.getStart(), start);
        assertEquals(timeSlot.getEnd(), end);

        start = c.getTime();
        c.add(Calendar.MINUTE,30);
        end = c.getTime();
        timeSlot = timeSlots.get(1);

        assertEquals(timeSlot.getStart(), start);
        assertEquals(timeSlot.getEnd(), end);

        start = c.getTime();
        c.add(Calendar.MINUTE,30);
        end = c.getTime();
        timeSlot = timeSlots.get(2);

        assertEquals(timeSlot.getStart(), start);
        assertEquals(timeSlot.getEnd(), end);

        start = c.getTime();
        c.add(Calendar.MINUTE,30);
        end = c.getTime();
        timeSlot = timeSlots.get(3);

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

        allSpots=parkSpotAccess.getAllParkingSpots();
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

        //allSpots = parkSpotAccess.getAllParkingSpots();
//        assertFalse(allSpots.get(0).isBooked());
//        assertTrue(allSpots.get(1).isBooked());
//        assertFalse(allSpots.get(2).isBooked());
//        assertTrue(allSpots.get(3).isBooked());

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
 //       allSpots = parkSpotAccess.getAllParkingSpots();
//        assertTrue(allSpots.get(0).isBooked());
//        assertTrue(allSpots.get(1).isBooked());
//        assertTrue(allSpots.get(2).isBooked());
//        assertTrue(allSpots.get(3).isBooked());
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
//        allSpots = parkSpotAccess.getAllParkingSpots();
//        assertFalse(allSpots.get(0).isBooked());
//        assertFalse(allSpots.get(1).isBooked());
//        assertFalse(allSpots.get(2).isBooked());
//        assertFalse(allSpots.get(3).isBooked());
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
//        assertTrue(parkSpotAccess.
//                bookSpot("788 Plaza PlaceTheGuy", 0)
//                .equals("Already Booked"));
//
//        assertTrue(parkSpotAccess.
//                bookSpot("588 Markham PlaceTheLady", 0)
//                .equals("Already Booked"));

        spots = parkSpotAccess.getAvailableSpots();
        assertTrue(spots.size()==1);

//        allSpots = parkSpotAccess.getAllParkingSpots();
//        assertFalse(allSpots.get(0).getisBooked());
//        assertTrue(allSpots.get(1).isBooked());
//        assertTrue(allSpots.get(2).isBooked());
//        assertTrue(allSpots.get(3).isBooked());
        System.out.println("Finished testAccessParkingSpots: Booking a spot twice.");
    }


    public void testGettingMyBookingsValid()
    {
//        Main.startUp();
//        System.out.println("Starting testAccessParkingSpots: No parking spots inserted.");
//        parkSpotAccess=new AccessParkingSpots();
//        parkSpotAccess.clearSpots();
        Services.closeDataAccess();

        Services.createDataAccess(new DataAccessStub(dbName));

        parkSpotAccess = new AccessParkingSpots();
        parkSpotAccess.clearSpots();

        String username = "marker";
        try
        {
            Booking abooking;
            DateFormatter dateFormatter = new DateFormatter();
            bookings = parkSpotAccess.getMyBookedSpots("marker");
            assertEquals(4,bookings.size());
            abooking = bookings.get(0);
            assertEquals("marker",abooking.getName());
            assertEquals((long)173,abooking.getTimeSlotId());
            assertEquals("1000 St. Mary's Rd",abooking.getAddress());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 18:30:00"),abooking.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 19:00:00"),abooking.getEnd());
            abooking = bookings.get(1);
            assertEquals("marker",abooking.getName());
            assertEquals((long)91,abooking.getTimeSlotId());
            assertEquals("1338 Chancellor Drive",abooking.getAddress());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 14:00:00"),abooking.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 14:30:00"),abooking.getEnd());
            abooking = bookings.get(2);
            assertEquals("marker",abooking.getName());
            assertEquals((long)94,abooking.getTimeSlotId());
            assertEquals("91 Dalhousie Drive",abooking.getAddress());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"),abooking.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 11:00:00"),abooking.getEnd());
            abooking = bookings.get(3);
            assertEquals("marker",abooking.getName());
            assertEquals((long)145,abooking.getTimeSlotId());
            assertEquals("1 Pembina Hwy",abooking.getAddress());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 12:30:00"),abooking.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 13:00:00"),abooking.getEnd());
        }
        catch (DAOException de)
        {
            System.out.print(de.getMessage());
            fail();
        }
        catch (ParseException pe)
        {
            System.out.print(pe.getMessage());
            fail();
        }
    }

    public void testGettingMyBookingsEmptyList()
    {
        Services.closeDataAccess();

        Services.createDataAccess(new DataAccessStub(dbName));
        parkSpotAccess = new AccessParkingSpots();
        parkSpotAccess.clearSpots();

        String username = "tester";
        try
        {
            bookings = parkSpotAccess.getMyBookedSpots(username);
            assertEquals(0, bookings.size());
        }
        catch (DAOException de)
        {
            System.out.print(de.getMessage());
            fail();
        }
    }

    public void testCancelABookingValid()
    {
        Services.closeDataAccess();

        Services.createDataAccess(new DataAccessStub(dbName));
        parkSpotAccess = new AccessParkingSpots();
        parkSpotAccess.clearSpots();

        String username = "marker";
        long timeSlotId = 91;
        try
        {
            bookings = parkSpotAccess.getMyBookedSpots(username);
            Booking removed = bookings.get(1);
            parkSpotAccess.cancelThisSpot(username, timeSlotId);
            bookings = parkSpotAccess.getMyBookedSpots(username);
            assertEquals(3, bookings.size());
            assertEquals(false, bookings.contains(removed));
        }
        catch (DAOException de)
        {
            System.out.print(de.getMessage());
            fail();
        }
    }

    public void testCancelABookingOfAnotherUser()
    {
        Services.closeDataAccess();

        Services.createDataAccess(new DataAccessStub(dbName));
        parkSpotAccess = new AccessParkingSpots();
        parkSpotAccess.clearSpots();
        String username = "Donald Trump";
        long timeSlotId = 91;
        try
        {
            parkSpotAccess.cancelThisSpot(username, timeSlotId);
            bookings = parkSpotAccess.getMyBookedSpots(username);
            assertEquals(4, bookings.size());
        }
        catch (DAOException de)
        {
            System.out.print(de.getMessage());
            fail();
        }
    }

}