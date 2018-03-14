package comp3350.iPuP.tests.persistence;

import junit.framework.TestCase;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import comp3350.iPuP.objects.Booking;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.objects.DateFormatter;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.TimeSlot;
import comp3350.iPuP.persistence.DataAccess;

public class DataAccessTest extends TestCase {

    private DataAccess dataAccess;
    private DateFormatter dateFormatter;

    public DataAccessTest(String arg0)
    {
        super(arg0);
    }

    public void setUp()
    {
        System.out.println("\nStarting Persistence test DataAccess (using stub)");

        try
        {
            dateFormatter = new DateFormatter();
            dataAccess = new DataAccessStub();
            dataAccess.open("Stub");
        } catch (DAOException daoe)
        {
            System.err.println(daoe.getMessage());
        }
    }

    public void tearDown()
    {
        System.out.println("Finished Persistence test DataAccess (using stub)");
    }

    public void testDefaultData()
    {
        Booking abooking;
        ArrayList<Booking> bookings;
        TimeSlot adayslot, atimeslot;
        ParkingSpot parkingSpot;
        ArrayList<TimeSlot> daySlotsOfParkingSpot;
        ArrayList<TimeSlot> timeSlotsOfParkingSpot;

        try
        {
            parkingSpot = dataAccess.getParkingSpot(0);
            assertEquals((long)0,parkingSpot.getSpotID());
            daySlotsOfParkingSpot = dataAccess.getDaySlots(parkingSpot.getSpotID());
            assertEquals(2,daySlotsOfParkingSpot.size());
            timeSlotsOfParkingSpot = dataAccess.getTimeSlots(parkingSpot.getSpotID());
            assertEquals(8,timeSlotsOfParkingSpot.size());

            parkingSpot = dataAccess.getParkingSpot(2);
            assertEquals((long)2,parkingSpot.getSpotID());
            assertEquals("30 Chancellor Drive",parkingSpot.getAddress());
            assertEquals("Roberto Nesta Marley",parkingSpot.getName());
            assertEquals("204-577-3422",parkingSpot.getPhone());
            assertEquals("rastaLikebob@gmail.com",parkingSpot.getEmail());
            assertEquals(0.10,parkingSpot.getRate());
            daySlotsOfParkingSpot = dataAccess.getDaySlots(parkingSpot.getSpotID());
            assertEquals(1,daySlotsOfParkingSpot.size());
            adayslot = daySlotsOfParkingSpot.get(0);
            assertEquals((long)2,adayslot.getSlotID());
            assertFalse(adayslot.getIsBooked());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"),adayslot.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 11:30:00"),adayslot.getEnd());
            timeSlotsOfParkingSpot = dataAccess.getTimeSlots(parkingSpot.getSpotID());
            assertEquals(2,timeSlotsOfParkingSpot.size());
            atimeslot = timeSlotsOfParkingSpot.get(0);
            assertEquals((long)12,atimeslot.getSlotID());
            assertFalse(atimeslot.getIsBooked());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"),atimeslot.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 11:00:00"),atimeslot.getEnd());
            atimeslot = timeSlotsOfParkingSpot.get(1);
            assertEquals((long)13,atimeslot.getSlotID());
            assertFalse(atimeslot.getIsBooked());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 11:00:00"),atimeslot.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 11:30:00"),atimeslot.getEnd());

            parkingSpot = dataAccess.getParkingSpot(21);
            assertEquals((long)21,parkingSpot.getSpotID());
            assertEquals("1000 St. Mary's Rd",parkingSpot.getAddress());
            assertEquals("Anne Coutinho",parkingSpot.getName());
            assertEquals("204-124-2222",parkingSpot.getPhone());
            assertEquals("iAmAlsoAsnake10@hotmail.ca",parkingSpot.getEmail());
            assertEquals(0.20,parkingSpot.getRate());
            daySlotsOfParkingSpot = dataAccess.getDaySlots(parkingSpot.getSpotID());
            assertEquals(1,daySlotsOfParkingSpot.size());
            adayslot = daySlotsOfParkingSpot.get(0);
            assertEquals((long)21,adayslot.getSlotID());
            assertFalse(adayslot.getIsBooked());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 17:30:00"),adayslot.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 19:00:00"),adayslot.getEnd());
            timeSlotsOfParkingSpot = dataAccess.getTimeSlots(parkingSpot.getSpotID());
            assertEquals(3,timeSlotsOfParkingSpot.size());
            atimeslot = timeSlotsOfParkingSpot.get(0);
            assertEquals((long)171,atimeslot.getSlotID());
            assertFalse(atimeslot.getIsBooked());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 17:30:00"),atimeslot.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 18:00:00"),atimeslot.getEnd());
            atimeslot = timeSlotsOfParkingSpot.get(1);
            assertEquals((long)172,atimeslot.getSlotID());
            assertFalse(atimeslot.getIsBooked());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 18:00:00"),atimeslot.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 18:30:00"),atimeslot.getEnd());
            atimeslot = timeSlotsOfParkingSpot.get(2);
            assertEquals((long)173,atimeslot.getSlotID());
            assertTrue(atimeslot.getIsBooked());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 18:30:00"),atimeslot.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 19:00:00"),atimeslot.getEnd());

            bookings = dataAccess.getBookedSpotsOfGivenUser("marker");
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

        } catch (DAOException daoe)
        {
            fail("DAOException Caught with message: "+daoe.getMessage());
        } catch (ParseException pe)
        {
            fail("ParseException Caught with message: "+pe.getMessage());
        }

    }

    public void testInsertAndGetUsers()
    {
        try
        {
            dataAccess.insertUser("tester");
        } catch (DAOException daoe)
        {

        }
    }

    public void testInsertAndGetParkingSpots()
    {
        ParkingSpot parkingSpot = new ParkingSpot(-1, "1 Tester Street", "tester", "testing@tester.ca", "2042222222", 101);

        try
        {
            assertEquals((long)22, dataAccess.insertParkingSpot("tester", parkingSpot));
        } catch (DAOException daoe)
        {
            fail("DAOException Caught with message: "+daoe.getMessage());
        }

        try
        {
            Long spotID = dataAccess.insertParkingSpot("tester", parkingSpot);
            fail("Error: Duplicate ParkingSpot created with spotID = "+spotID);
        } catch (DAOException daoe)
        {
            assertEquals("Error in creating ParkingSpot object with SPOT_ID = -1 for Username: tester!",daoe.getMessage());
        }

        parkingSpot = new ParkingSpot(-1, "2 Tester Street", "tester", "testing@tester.ca", "2042222222", 101);

        try
        {
            assertEquals((long)23, dataAccess.insertParkingSpot("tester", parkingSpot));
        } catch (DAOException daoe)
        {
            fail("DAOException Caught with message: "+daoe.getMessage());
        }

        try
        {
            ArrayList<ParkingSpot> parkingSpots = dataAccess.getParkingSpotsByAddressDate("", dateFormatter.getSqlDateFormat().parse("2018-06-11"));
            assertEquals(24,parkingSpots.size());
            assertTrue(parkingSpots.get(0).getAddress().toLowerCase().compareTo(parkingSpots.get(1).getAddress().toLowerCase()) < 0);
        } catch (ParseException pe)
        {

        } catch (DAOException daoe)
        {

        }
    }

    public void testInsertAndGetDayslots()
    {

    }

    public void testInsertAndGetTimeslots()
    {

    }
}
