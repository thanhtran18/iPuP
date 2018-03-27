package comp3350.iPuP.tests.business;

import junit.framework.TestCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.CharArrayReader;
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
    ArrayList<ParkingSpot> parkingSpot;
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

        assertTrue(spots.size()==0);
        System.out.println("Finished testAccessParkingSpots: No parking spots inserted.");
    }

    public void testCreateSpotOneDay()
    {
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        ParkingSpot spot = null;

        parkSpotAccess = new AccessParkingSpots();
        parkSpotAccess.clearSpots();

        Calendar c = Calendar.getInstance();
        c.set(2018, 3, 24, 10, 30);
        Date start, end;
        start = c.getTime();
        c.add(Calendar.HOUR_OF_DAY, 2);
        end = c.getTime();

        TimeSlot timeSlot = new TimeSlot(start, end);
        try
        {
            parkSpotAccess.insertParkingSpot("testuser", timeSlot, null, "356 testing drive, Winnipeg, MB", "456-6789", "", 42,0,0);
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
        c.add(Calendar.HOUR_OF_DAY, 2);
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
        c.add(Calendar.MINUTE, 30);
        end = c.getTime();
        timeSlot = timeSlots.get(0);

        assertEquals(timeSlot.getStart(), start);
        assertEquals(timeSlot.getEnd(), end);

        start = c.getTime();
        c.add(Calendar.MINUTE, 30);
        end = c.getTime();
        timeSlot = timeSlots.get(1);

        assertEquals(timeSlot.getStart(), start);
        assertEquals(timeSlot.getEnd(), end);
    }

    public void testCreateSpotRepeatDay()
    {
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        ParkingSpot spot = null;

        parkSpotAccess = new AccessParkingSpots();
        parkSpotAccess.clearSpots();

        Calendar c = Calendar.getInstance();
        Date start, end;
        long spotID, daySlotID;
        ArrayList<TimeSlot> daySlots;
        ArrayList<TimeSlot> timeSlots;
        TimeSlot daySlot;
        TimeSlot timeSlot;

        c.set(2018, 10, 12, 16, 30);
        start = c.getTime();
        c.add(Calendar.HOUR_OF_DAY,1);
        end = c.getTime();

        timeSlot = new TimeSlot(start,end);
        try
        {
            parkSpotAccess.insertParkingSpot("testuser2", timeSlot, "Days 3 4", "whodunnit St.", "555-5555", "hans@hans.hans", 10,0,0);
        }
        catch (Exception e)
        {
            fail();
        }

        try
        {
            assertEquals(parkSpotAccess.getAllParkingSpots().size(), 1);

            spot = parkSpotAccess.getAllParkingSpots().get(0);
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
    }

    public void testCreateSpotRepeatWeek()
    {
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        ParkingSpot spot = null;

        parkSpotAccess = new AccessParkingSpots();
        parkSpotAccess.clearSpots();

        Calendar c = Calendar.getInstance();
        Date start, end;
        long spotID, daySlotID;
        ArrayList<TimeSlot> daySlots = null;
        ArrayList<TimeSlot> timeSlots = null;
        TimeSlot daySlot;
        TimeSlot timeSlot;

        c.set(1455, 1, 1, 1, 0);
        start = c.getTime();
        c.add(Calendar.HOUR_OF_DAY,6);
        end = c.getTime();

        timeSlot = new TimeSlot(start,end);
        try
        {
            parkSpotAccess.insertParkingSpot("Sir Galavant", timeSlot, "Weeks 2 5 0100111", "5 Smithy Lane, Camelot", "0909090", "galavant@roundtable.brit", 0.02,0,0);
        }
        catch (Exception e)
        {
            fail();
        }

        try
        {
            assertEquals(parkSpotAccess.getAllParkingSpots().size(), 1);

            spot = parkSpotAccess.getAllParkingSpots().get(0);
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

    public void testDeleteTimeSlotsOnDeleteDaySlot()
    {
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        ParkingSpot spot = null;

        parkSpotAccess = new AccessParkingSpots();
        parkSpotAccess.clearSpots();

        Calendar c = Calendar.getInstance();
        Date start, end;
        long daySlotID;
        ArrayList<TimeSlot> daySlots = null;
        TimeSlot timeSlot;

        c.set(1455, 1, 1, 1, 0);
        start = c.getTime();
        c.add(Calendar.HOUR_OF_DAY,6);
        end = c.getTime();

        timeSlot = new TimeSlot(start,end);
        try
        {
            parkSpotAccess.insertParkingSpot("Sir Galavant", timeSlot, "Weeks 2 1 0100111", "5 Smithy Lane, Camelot", "0909090", "galavant@roundtable.brit", 0.02,0,0);
            spot = parkSpotAccess.getAllParkingSpots().get(0);
            daySlots = parkSpotAccess.getDaySlots(spot.getSpotID());
            daySlotID = daySlots.get(0).getSlotID();
            boolean ret = parkSpotAccess.deleteDaySlot(daySlotID);
            daySlots = parkSpotAccess.getDaySlots(spot.getSpotID());

            assertEquals(3, daySlots.size());
            assertEquals(0, parkSpotAccess.getTimeSlots(daySlotID).size());
            assertFalse(ret);
        }
        catch (DAOException daoe) { fail(); }
    }

    public void testDeleteDaySlotLast()
    {
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        ParkingSpot spot = null;

        parkSpotAccess = new AccessParkingSpots();
        parkSpotAccess.clearSpots();

        Calendar c = Calendar.getInstance();
        Date start, end;
        long daySlotID;
        ArrayList<TimeSlot> daySlots = null;
        TimeSlot timeSlot;

        c.set(1455, 1, 1, 1, 0);
        start = c.getTime();
        c.add(Calendar.HOUR_OF_DAY,6);
        end = c.getTime();

        timeSlot = new TimeSlot(start,end);
        try
        {
            parkSpotAccess.insertParkingSpot("Sir Galavant", timeSlot, "", "5 Smithy Lane, Camelot", "0909090", "galavant@roundtable.brit", 0.02,0,0);
            spot = parkSpotAccess.getAllParkingSpots().get(0);
            daySlots = parkSpotAccess.getDaySlots(spot.getSpotID());
            daySlotID = daySlots.get(0).getSlotID();
            boolean ret = parkSpotAccess.deleteDaySlot(daySlotID);
            daySlots = parkSpotAccess.getDaySlots(spot.getSpotID());

            assertEquals(0, daySlots.size());
            assertTrue(ret);
            assertEquals(0, parkSpotAccess.getAllParkingSpots().size());
        }
        catch (DAOException daoe) { fail(); }
    }

    public void testDeleteTimeSlotsLastTimeSlot()
    {
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        ParkingSpot spot = null;

        parkSpotAccess = new AccessParkingSpots();
        parkSpotAccess.clearSpots();

        Calendar c = Calendar.getInstance();
        Date start, end;
        long daySlotID, timeSlotID;
        ArrayList<TimeSlot> daySlots = null;
        ArrayList<TimeSlot> timeSlots = null;
        TimeSlot timeSlot;

        c.set(1455, 1, 1, 1, 0);
        start = c.getTime();
        c.add(Calendar.MINUTE, 30);
        end = c.getTime();

        timeSlot = new TimeSlot(start,end);
        try
        {
            parkSpotAccess.insertParkingSpot("Sir Galavant", timeSlot, "Weeks 2 1 0100001", "5 Smithy Lane, Camelot", "0909090", "galavant@roundtable.brit", 0.02,0,0);
            spot = parkSpotAccess.getAllParkingSpots().get(0);
            daySlots = parkSpotAccess.getDaySlots(spot.getSpotID());
            daySlotID = daySlots.get(0).getSlotID();
            timeSlots = parkSpotAccess.getTimeSlots(daySlotID);
            timeSlotID = timeSlots.get(0).getSlotID();
            boolean ret = parkSpotAccess.deleteTimeSlot(timeSlotID);
            daySlots = parkSpotAccess.getDaySlots(spot.getSpotID());

            assertEquals(1, daySlots.size());
            assertEquals(0, parkSpotAccess.getTimeSlots(daySlotID).size());
            assertTrue(ret);
        }
        catch (DAOException daoe) { fail(); }
    }

    public void testDeleteTimeSlotLastDaySlot()
    {
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        ParkingSpot spot = null;

        parkSpotAccess = new AccessParkingSpots();
        parkSpotAccess.clearSpots();

        Calendar c = Calendar.getInstance();
        Date start, end;
        long daySlotID, timeSlotID;
        ArrayList<TimeSlot> daySlots = null;
        ArrayList<TimeSlot> timeSlots = null;
        TimeSlot timeSlot;

        c.set(1455, 1, 1, 1, 0);
        start = c.getTime();
        c.add(Calendar.MINUTE, 30);
        end = c.getTime();

        timeSlot = new TimeSlot(start, end);
        try
        {
            parkSpotAccess.insertParkingSpot("Sir Galavant", timeSlot, "Weeks 2 1 0100001", "5 Smithy Lane, Camelot", "0909090", "galavant@roundtable.brit", 0.02,0,0);
            spot = parkSpotAccess.getAllParkingSpots().get(0);
            daySlots = parkSpotAccess.getDaySlots(spot.getSpotID());
            daySlotID = daySlots.get(0).getSlotID();
            timeSlots = parkSpotAccess.getTimeSlots(daySlotID);
            timeSlotID = timeSlots.get(0).getSlotID();
            boolean ret = parkSpotAccess.deleteTimeSlot(timeSlotID);
            daySlots = parkSpotAccess.getDaySlots(spot.getSpotID());

            assertEquals(1, daySlots.size());
            assertEquals(0, parkSpotAccess.getTimeSlots(daySlotID).size());
            assertTrue(ret);

            daySlots = parkSpotAccess.getDaySlots(spot.getSpotID());
            daySlotID = daySlots.get(0).getSlotID();
            timeSlots = parkSpotAccess.getTimeSlots(daySlotID);
            timeSlotID = timeSlots.get(0).getSlotID();
            ret = parkSpotAccess.deleteTimeSlot(timeSlotID);
            daySlots = parkSpotAccess.getDaySlots(spot.getSpotID());

            assertEquals(0, daySlots.size());
            assertEquals(0, parkSpotAccess.getTimeSlots(daySlotID).size());
            assertEquals(0, parkSpotAccess.getAllParkingSpots().size());
            assertTrue(ret);
        }
        catch (DAOException daoe) { fail(); }
    }


    public void testMyDailyParkingSpotsEmptyList()
    {
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        parkSpotAccess = new AccessParkingSpots();
        //parkSpotAccess.clearSpots();

        try
        {
            DateFormatter dateFormatter = new DateFormatter();
            Date date = dateFormatter.getSqlDateFormat().parse("2018-06-10");
            ParkingSpot newSpots;
            parkingSpot = parkSpotAccess.getDailySpots(null,date );
            assertEquals(0, parkingSpot.size());
            parkingSpot = parkSpotAccess.getDailySpots("",date );
            assertEquals(0, parkingSpot.size());
            parkingSpot = parkSpotAccess.getDailySpots("         ",date );
            assertEquals(0, parkingSpot.size());
            parkingSpot = parkSpotAccess.getDailySpots("pembina Hwy",date );
            assertEquals(0, parkingSpot.size());
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

    public void testFullListOfDailyParkingSpots()
    {
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        parkSpotAccess = new AccessParkingSpots();
//        parkSpotAccess.clearSpots();

        try
        {
            ParkingSpot newSpots;
            parkingSpot = parkSpotAccess.getDailySpots("",null );
            assertEquals(22, parkingSpot.size());

            parkingSpot = parkSpotAccess.getDailySpots("          ",null );
            assertEquals(22, parkingSpot.size());

            parkingSpot = parkSpotAccess.getDailySpots(null,null );
            assertEquals(22, parkingSpot.size());



            newSpots = parkingSpot.get(0);
            assertEquals("Jenifer Aniston",newSpots.getName());
            assertEquals("604-253-1111",newSpots.getPhone());
            assertEquals("JeniferAniston@hotmail.ca",newSpots.getEmail());
            assertEquals("1 Kings Drive",newSpots.getAddress());
            assertEquals(7.0,newSpots.getRate());

            newSpots = parkingSpot.get(11);
            assertEquals("Tom Brady",newSpots.getName());
            assertEquals("877-377-4234",newSpots.getPhone());
            assertEquals("theGoat@gmail.com",newSpots.getEmail());
            assertEquals("20 Kings Drive",newSpots.getAddress());
            assertEquals(10.0,newSpots.getRate());

            newSpots = parkingSpot.get(21);
            assertEquals("Donald Trump",newSpots.getName());
            assertEquals("877-311-4974",newSpots.getPhone());
            assertEquals("lolattheUSA@gmail.com",newSpots.getEmail());
            assertEquals("Brady Road Landfill",newSpots.getAddress());
            assertEquals(100.0,newSpots.getRate());
        }
        catch (DAOException de)
        {
            System.out.print(de.getMessage());
            fail();
        }
    }


    public void testGettingMyDailyParkingSpots()
    {
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(dbName));
        parkSpotAccess = new AccessParkingSpots();
       //parkSpotAccess.clearSpots();

        try
        {
            DateFormatter dateFormatter = new DateFormatter();
            Date date = dateFormatter.getSqlDateFormat().parse("2018-06-12");
            ParkingSpot newSpots;
            parkingSpot = parkSpotAccess.getDailySpots("",date );
            assertEquals(2, parkingSpot.size());
            parkingSpot = parkSpotAccess.getDailySpots(null,date);
            assertEquals(2, parkingSpot.size());
            parkingSpot = parkSpotAccess.getDailySpots("      ",date );
            assertEquals(2, parkingSpot.size());
            newSpots = parkingSpot.get(0);
            assertEquals("Mary Watson", newSpots.getName());
            assertEquals("29 St. Mary's Rd", newSpots.getAddress());
            assertEquals("204-242-2255",newSpots.getPhone());
            assertEquals("sherlock101@gmail.com",newSpots.getEmail());
            assertEquals(4.5,newSpots.getRate());

            newSpots = parkingSpot.get(1);
            assertEquals("marker", newSpots.getName());
            assertEquals("88 Plaza Drive", newSpots.getAddress());
            assertEquals("204-855-2342",newSpots.getPhone());
            assertEquals("theBestMarker@gmail.com",newSpots.getEmail());
            assertEquals(2.0,newSpots.getRate());

            parkingSpot = parkSpotAccess.getDailySpots("S",date );
            assertEquals(1, parkingSpot.size());
            newSpots = parkingSpot.get(0);
            assertEquals("Mary Watson", newSpots.getName());
            assertEquals("29 St. Mary's Rd", newSpots.getAddress());
            assertEquals("204-242-2255",newSpots.getPhone());
            assertEquals("sherlock101@gmail.com",newSpots.getEmail());
            assertEquals(4.5,newSpots.getRate());

            parkingSpot = parkSpotAccess.getDailySpots("Pembina Hwy",date );
            assertEquals(0, parkingSpot.size());


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




    public void testGettingMyBookingsValid()
    {
        Services.closeDataAccess();

        Services.createDataAccess(new DataAccessStub(dbName));

        parkSpotAccess = new AccessParkingSpots();
        //parkSpotAccess.clearSpots();

        String username = "marker";
        try
        {
            Booking abooking;
            DateFormatter dateFormatter = new DateFormatter();
            bookings = parkSpotAccess.getMyBookedSpots(username);
            assertEquals(4, bookings.size());
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
        //parkSpotAccess.clearSpots();

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
            assertEquals(0, bookings.size());
        }
        catch (DAOException de)
        {
            System.out.print(de.getMessage());
            fail();
        }
    }

    public void testCancelABookingOfEmptyList()
    {
        Services.closeDataAccess();

        Services.createDataAccess(new DataAccessStub(dbName));
        parkSpotAccess = new AccessParkingSpots();
        parkSpotAccess.clearSpots();

        String username = "tester";
        long timeSlotId = 91;
        try
        {
            parkSpotAccess.cancelThisSpot(username, timeSlotId);
            bookings = parkSpotAccess.getMyBookedSpots(username);
            assertEquals(0, bookings.size());
        }
        catch (DAOException de)
        {
            System.out.print(de.getMessage());
            fail();
        }
    }


    public void testFreeNoTimeSLots() throws Exception{
        Services.closeDataAccess();

        Services.createDataAccess(new DataAccessStub(dbName));
        parkSpotAccess = new AccessParkingSpots();
        parkSpotAccess.clearSpots();

        assertEquals(0, parkSpotAccess.getFreeTimeSlotsByID(885000).size());
        assertEquals(null,parkSpotAccess.getSpotByID(88500));

    }

    public void testInvalidSlotIDs() throws Exception{
        Services.closeDataAccess();

        Services.createDataAccess(new DataAccessStub(dbName));
        parkSpotAccess = new AccessParkingSpots();

        //negative slotID
        assertEquals(0, parkSpotAccess.getFreeTimeSlotsByID(-1500).size());
        assertEquals(null, parkSpotAccess.getSpotByID(-1500));
        assertEquals(0, parkSpotAccess.getFreeTimeSlotsByID(-1).size());
        assertEquals(null, parkSpotAccess.getSpotByID(-1));
        //non-existing slotID
        assertEquals(0, parkSpotAccess.getFreeTimeSlotsByID(100000).size());
        assertEquals(null,parkSpotAccess.getSpotByID(100000));

        ArrayList<TimeSlot> testTimeSlots=parkSpotAccess.getFreeTimeSlotsByID(0);
        //passing different slot id from the one used to get time slots
        assertFalse(parkSpotAccess.bookTimeSlots(testTimeSlots,"Jenifer Aniston",
                -1));
        testTimeSlots=parkSpotAccess.getFreeTimeSlotsByID(0);
        assertFalse(parkSpotAccess.bookTimeSlots(testTimeSlots,"Jenifer Aniston",
                1000000));

    }

    public void testRegularSlotIDs() throws Exception{
        Services.closeDataAccess();

        Services.createDataAccess(new DataAccessStub(dbName));
        parkSpotAccess = new AccessParkingSpots();

        assertEquals(8, parkSpotAccess.getFreeTimeSlotsByID(0).size());
        assertEquals("marker", parkSpotAccess.getSpotByID(0).getName());
        assertEquals(0, parkSpotAccess.getSpotByID(0).getSpotID());
        assertEquals("88 Plaza Drive", parkSpotAccess.getSpotByID(0).getAddress());
        assertEquals("204-855-2342", parkSpotAccess.getSpotByID(0).getPhone());
        assertEquals("theBestMarker@gmail.com", parkSpotAccess.getSpotByID(0).
                getEmail());
        assertEquals(2.0, parkSpotAccess.getSpotByID(0).getRate());

        assertEquals(19, parkSpotAccess.getFreeTimeSlotsByID(5).size());

        assertEquals("Jenifer Aniston", parkSpotAccess.getSpotByID(5).getName());
        assertEquals(5, parkSpotAccess.getSpotByID(5).getSpotID());
        assertEquals("1 Kings Drive", parkSpotAccess.getSpotByID(5).getAddress());
        assertEquals("604-253-1111", parkSpotAccess.getSpotByID(5).getPhone());
        assertEquals("JeniferAniston@hotmail.ca", parkSpotAccess.getSpotByID(5).
                getEmail());
        assertEquals(7.0, parkSpotAccess.getSpotByID(5).getRate());

        ArrayList<TimeSlot> testTimeSlots=parkSpotAccess.getFreeTimeSlotsByID(0);
        assertTrue(parkSpotAccess.bookTimeSlots(testTimeSlots,"Jenifer Aniston",
                0));
        testTimeSlots=parkSpotAccess.getFreeTimeSlotsByID(5);
        assertTrue(parkSpotAccess.bookTimeSlots(testTimeSlots,"Jenifer Aniston",
                5));

    }

    public void testInvalidTimeSlotList() throws Exception{
        Services.closeDataAccess();

        Services.createDataAccess(new DataAccessStub(dbName));
        parkSpotAccess = new AccessParkingSpots();

        ArrayList<TimeSlot> testSlots=null;
        assertFalse(parkSpotAccess.bookTimeSlots(testSlots, "Jenifer Aniston",
                0));
        testSlots=new ArrayList<TimeSlot>();
        assertFalse(parkSpotAccess.bookTimeSlots(testSlots,"Jenifer Aniston",
                0));
    }

    public void testRegularTimeSlotList() throws Exception{
        Services.closeDataAccess();

        Services.createDataAccess(new DataAccessStub(dbName));
        parkSpotAccess = new AccessParkingSpots();

        ArrayList<TimeSlot> testTimeSlots=parkSpotAccess.getFreeTimeSlotsByID(0);
        assertTrue(parkSpotAccess.bookTimeSlots(testTimeSlots,"Jenifer Aniston",
                0));
    }

    public void testInvalidUsernames() throws Exception{
        Services.closeDataAccess();

        Services.createDataAccess(new DataAccessStub(dbName));
        parkSpotAccess = new AccessParkingSpots();

        ArrayList<TimeSlot> testTimeSlots=parkSpotAccess.getFreeTimeSlotsByID(0);
        assertFalse(parkSpotAccess.bookTimeSlots(testTimeSlots,null,
                0));
        testTimeSlots=parkSpotAccess.getFreeTimeSlotsByID(0);
        assertFalse(parkSpotAccess.bookTimeSlots(testTimeSlots,"",
                0));
    }



}