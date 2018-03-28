package comp3350.iPuP.tests.persistence;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import comp3350.iPuP.objects.Booking;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.objects.DateFormatter;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.TimeSlot;
import comp3350.iPuP.persistence.DataAccess;
import comp3350.iPuP.persistence.DataAccessObject;

public class DataAccessTest extends TestCase {

    private static String dbName;
    private static String dbPathName;
    private DataAccess dataAccess;
    private DateFormatter dateFormatter;

    public DataAccessTest(String arg0)
    {
        super(arg0);
    }

    public void setUp()
    {
        System.out.println("Starting Persistence test DataAccess (using stub)");

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
        if (this.getName().equals("IntegrationTest"))
        {
            try {
                replaceDbWithDefault();
            } catch (DAOException daoe) {
                System.err.println(daoe.getMessage());
                System.exit(1);
            }
        }
        System.out.println("Finished Persistence test DataAccess (using stub)");
    }

    public static void dataAccessTest(String dbname, String dbpathname)
    {
        DataAccessTest dataAccessTest = new DataAccessTest("IntegrationTest");

        dbName = dbname;
        dbPathName = dbpathname.replace('/', '\\');

        dataAccessTest.testDefaultData();
        dataAccessTest.testInsertAndGetUsers();
        dataAccessTest.testGetParkingSpotsWithoutNewInsertion();
        dataAccessTest.testInsertThenGetParkingSpots();
        dataAccessTest.testInsertAndDeleteDayslots();
        dataAccessTest.testInsertAndGetTimeslots();
        dataAccessTest.testInsertAndGetBookings();
        dataAccessTest.testGettingAUserBookingsEmptyList();
        dataAccessTest.testDeleteAValidBooking();
        dataAccessTest.testDeleteANonExistingBooking();
    }

    private void replaceDbWithDefault() throws DAOException
    {
        try
        {
            String dbFilePath = System.getProperty("user.dir") + "/" + dbPathName + ".script";
            String defaultDbFilePath = System.getProperty("user.dir") + "/app/src/main/assets/db/" + dbName + ".script";

            File dbFile = new File(dbFilePath);
            File defaultDbFile = new File(defaultDbFilePath);

            if (defaultDbFile.exists())
            {
                InputStream in = new FileInputStream(defaultDbFile);
                FileUtils.copyInputStreamToFile(in, dbFile);
                in.close();
            } else
            {
                throw new DAOException("Error in locating default database files!");
            }

        }
        catch (FileNotFoundException fnfe)
        {
            throw new DAOException("Unable to open default database file!", fnfe);
        }
        catch (IOException ioe)
        {
            throw new DAOException("Unable to access database: ", ioe);
        }
    }

    private void openDataAccess()
    {
        dateFormatter = new DateFormatter();
        try
        {
            if (this.getName().equals("IntegrationTest"))
            {
                replaceDbWithDefault();
                dataAccess = new DataAccessObject(dbName);
                dataAccess.open(dbPathName);
            } else
            {
                dataAccess = new DataAccessStub();
                dataAccess.open("Stub");
            }
        } catch (DAOException daoe)
        {
            System.err.println(daoe.getMessage());
            System.exit(1);
        }
    }

    private void closeDataAccess()
    {
        if (this.getName().equals("IntegrationTest"))
        {
            System.out.println("Closed HSQL database " + dbName);
        } else
        {
            System.out.println("Closed stub database " + dbName);
        }
    }

    public void testDefaultData()
    {
        openDataAccess();
        System.out.println("Starting testDataAccess: Default Data");

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
            assertEquals(4,timeSlotsOfParkingSpot.size());

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
            assertTrue(adayslot.getIsBooked());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-02-11 17:30:00"),adayslot.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-02-11 19:00:00"),adayslot.getEnd());
            timeSlotsOfParkingSpot = dataAccess.getTimeSlots(parkingSpot.getSpotID());
            assertEquals(3,timeSlotsOfParkingSpot.size());
            atimeslot = timeSlotsOfParkingSpot.get(0);
            assertEquals((long)171,atimeslot.getSlotID());
            assertFalse(atimeslot.getIsBooked());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-02-11 17:30:00"),atimeslot.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-02-11 18:00:00"),atimeslot.getEnd());
            atimeslot = timeSlotsOfParkingSpot.get(1);
            assertEquals((long)172,atimeslot.getSlotID());
            assertFalse(atimeslot.getIsBooked());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-02-11 18:00:00"),atimeslot.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-02-11 18:30:00"),atimeslot.getEnd());
            atimeslot = timeSlotsOfParkingSpot.get(2);
            assertEquals((long)173,atimeslot.getSlotID());
            assertTrue(atimeslot.getIsBooked());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-02-11 18:30:00"),atimeslot.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-02-11 19:00:00"),atimeslot.getEnd());

            bookings = dataAccess.getBookedSpotsOfGivenUser("marker");
            assertEquals(4,bookings.size());
            abooking = bookings.get(0);
            assertEquals("marker",abooking.getName());
            assertEquals((long)173,abooking.getTimeSlotId());
            assertEquals("1000 St. Mary's Rd",abooking.getAddress());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-02-11 18:30:00"),abooking.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-02-11 19:00:00"),abooking.getEnd());
            abooking = bookings.get(1);
            assertEquals("marker",abooking.getName());
            assertEquals((long)94,abooking.getTimeSlotId());
            assertEquals("91 Dalhousie Drive",abooking.getAddress());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"),abooking.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 11:00:00"),abooking.getEnd());
            abooking = bookings.get(2);
            assertEquals("marker",abooking.getName());
            assertEquals((long)145,abooking.getTimeSlotId());
            assertEquals("1 Pembina Hwy",abooking.getAddress());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 12:30:00"),abooking.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 13:00:00"),abooking.getEnd());
            abooking = bookings.get(3);
            assertEquals("marker",abooking.getName());
            assertEquals((long)91,abooking.getTimeSlotId());
            assertEquals("1338 Chancellor Drive",abooking.getAddress());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 14:00:00"),abooking.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 14:30:00"),abooking.getEnd());
        } catch (DAOException daoe)
        {
            fail("DAOException Caught with message: "+daoe.getMessage());
        } catch (ParseException pe)
        {
            fail("ParseException Caught with message: "+pe.getMessage());
        }

        System.out.println("Finished testDataAccess: Default Data");
        closeDataAccess();
    }

    public void testInsertAndGetUsers()
    {
        openDataAccess();
        System.out.println("Starting testDataAccess: Insert and Get Users");

        try
        {
            assertFalse(dataAccess.insertUser("marker"));
            assertFalse(dataAccess.insertUser("tester"));
            assertFalse(dataAccess.insertUser("tester2"));
            assertTrue(dataAccess.insertUser("tester3"));
            assertTrue(dataAccess.insertUser("tester4"));
        } catch (DAOException daoe)
        {
            fail("DAOException Caught with message: "+daoe.getMessage());
        }

        System.out.println("Finished testDataAccess: Insert and Get Users");
        closeDataAccess();
    }

    public void testGetParkingSpotsWithoutNewInsertion()
    {
        openDataAccess();
        System.out.println("Starting testDataAccess: Get ParkingSpots Without New Insertion");

        try
        {
            ArrayList<ParkingSpot> parkingSpots = dataAccess.getParkingSpotsByAddressDate("", dateFormatter.getSqlDateFormat().parse("2018-06-11"));
            assertEquals(20,parkingSpots.size());
            assertTrue(parkingSpots.get(0).getAddress().toLowerCase().compareTo(parkingSpots.get(1).getAddress().toLowerCase()) < 0);
            assertTrue(parkingSpots.get(16).getAddress().toLowerCase().compareTo(parkingSpots.get(17).getAddress().toLowerCase()) < 0);
            assertFalse(parkingSpots.get(19).getAddress().toLowerCase().compareTo(parkingSpots.get(12).getAddress().toLowerCase()) < 0);
            assertTrue(parkingSpots.get(18).getAddress().toLowerCase().compareTo(parkingSpots.get(19).getAddress().toLowerCase()) < 0);
            assertTrue(parkingSpots.get(0).getAddress().toLowerCase().compareTo(parkingSpots.get(19).getAddress().toLowerCase()) < 0);
        }
        catch (ParseException pe)
        {
            fail("ParseException Caught with message: "+pe.getMessage());
        }
        catch (DAOException daoe)
        {
            fail("DAOException Caught with message: "+daoe.getMessage());
        }

        System.out.println("Finished testDataAccess: Get ParkingSpots Without New Insertion");
        closeDataAccess();
    }

    public void testInsertThenGetParkingSpots()
    {
        openDataAccess();
        System.out.println("Starting testDataAccess: Insert Then Get ParkingSpots");

        ParkingSpot parkingSpot = new ParkingSpot(-1, "1 Tester Street", "tester", "2042222222", "testing@tester.ca", 101, 0, 0);

        try
        {
            assertEquals((long)22, dataAccess.insertParkingSpot("tester", parkingSpot));
            parkingSpot = dataAccess.getParkingSpotByID((long)22);
            assertEquals((long)22,parkingSpot.getSpotID());
            assertEquals("1 Tester Street",parkingSpot.getAddress());
            assertEquals("tester",parkingSpot.getName());
            assertEquals("testing@tester.ca",parkingSpot.getEmail());
            assertEquals("2042222222",parkingSpot.getPhone());
            assertEquals(101.0,parkingSpot.getRate());
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
            assertEquals("ParkingSpot object already exists with HostName = tester and Address = 1 Tester Street!",daoe.getMessage());
        }

        parkingSpot = new ParkingSpot(-1, "2 Tester Street", "tester", "2042222222", "testing@tester.ca", 101, 0, 0);

        try
        {
            assertEquals((long)23, dataAccess.insertParkingSpot("tester", parkingSpot));
            parkingSpot = dataAccess.getParkingSpotByID((long)23);
            assertEquals((long)23,parkingSpot.getSpotID());
            assertEquals("2 Tester Street",parkingSpot.getAddress());
            assertEquals("tester",parkingSpot.getName());
            assertEquals("testing@tester.ca",parkingSpot.getEmail());
            assertEquals("2042222222",parkingSpot.getPhone());
            assertEquals(101.0,parkingSpot.getRate());
        } catch (DAOException daoe)
        {
            fail("DAOException Caught with message: "+daoe.getMessage());
        }

        try
        {
            ArrayList<ParkingSpot> parkingSpots = dataAccess.getParkingSpotsByAddressDate("", null);
            assertEquals(22,parkingSpots.size());
            assertTrue(parkingSpots.get(0).getAddress().toLowerCase().compareTo(parkingSpots.get(1).getAddress().toLowerCase()) < 0);
            assertTrue(parkingSpots.get(16).getAddress().toLowerCase().compareTo(parkingSpots.get(17).getAddress().toLowerCase()) < 0);
            assertFalse(parkingSpots.get(21).getAddress().toLowerCase().compareTo(parkingSpots.get(13).getAddress().toLowerCase()) < 0);
            assertTrue(parkingSpots.get(20).getAddress().toLowerCase().compareTo(parkingSpots.get(21).getAddress().toLowerCase()) < 0);
            assertTrue(parkingSpots.get(0).getAddress().toLowerCase().compareTo(parkingSpots.get(21).getAddress().toLowerCase()) < 0);
        }
        catch (DAOException daoe)
        {
            fail("DAOException Caught with message: "+daoe.getMessage());
        }

        System.out.println("Finished testDataAccess: Insert Then Get ParkingSpots");
        closeDataAccess();
    }

    public void testInsertAndDeleteDayslots()
    {
        openDataAccess();
        System.out.println("Starting testDataAccess: Insert and Delete Dayslots");

        ArrayList<TimeSlot> daySlots;

        try
        {
            daySlots = dataAccess.getDaySlots(0);
            assertEquals(2,daySlots.size());

            daySlots = dataAccess.getDaySlots(15);
            assertEquals(1, daySlots.size());

            daySlots = dataAccess.getDaySlots(21);
            assertEquals(1, daySlots.size());
        }
        catch (DAOException daoe)
        {
            fail("DAOException Caught with message: "+daoe.getMessage());
        }

        System.out.println("Finished testDataAccess: Insert and Delete Dayslots");
        closeDataAccess();
    }

    public void testInsertAndGetTimeslots()
    {
        openDataAccess();
        System.out.println("Starting testDataAccess: Insert and Get Timeslots");

        ArrayList<TimeSlot> timeSlots;

        try
        {
            timeSlots = dataAccess.getTimeSlots(0);
            assertEquals(4,timeSlots.size());

            timeSlots = dataAccess.getTimeSlots(15);
            assertEquals(19, timeSlots.size());

            timeSlots = dataAccess.getTimeSlots(21);
            assertEquals(3, timeSlots.size());
        }
        catch (DAOException daoe)
        {
            fail("DAOException Caught with message: "+daoe.getMessage());
        }

        System.out.println("Finished testDataAccess: Insert and Get Timeslots");
        closeDataAccess();
    }

    public void testInsertAndGetBookings()
    {
        openDataAccess();
        System.out.println("Starting testDataAccess: Insert and Get Bookings");

        ArrayList<Booking> bookings;
        Booking abooking;
        try {
            bookings = dataAccess.getBookedSpotsOfGivenUser("marker");
            assertEquals(4, bookings.size());
            abooking = bookings.get(0);
            assertEquals("marker", abooking.getName());
            assertEquals((long) 173, abooking.getTimeSlotId());
            assertEquals("1000 St. Mary's Rd", abooking.getAddress());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-02-11 18:30:00"), abooking.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-02-11 19:00:00"), abooking.getEnd());
            abooking = bookings.get(1);
            assertEquals("marker", abooking.getName());
            assertEquals((long) 94, abooking.getTimeSlotId());
            assertEquals("91 Dalhousie Drive", abooking.getAddress());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"), abooking.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 11:00:00"), abooking.getEnd());
            abooking = bookings.get(2);
            assertEquals("marker", abooking.getName());
            assertEquals((long) 145, abooking.getTimeSlotId());
            assertEquals("1 Pembina Hwy", abooking.getAddress());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 12:30:00"), abooking.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 13:00:00"), abooking.getEnd());
            abooking = bookings.get(3);
            assertEquals("marker", abooking.getName());
            assertEquals((long) 91, abooking.getTimeSlotId());
            assertEquals("1338 Chancellor Drive", abooking.getAddress());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 14:00:00"), abooking.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 14:30:00"), abooking.getEnd());
        }
        catch (ParseException pe)
        {
            fail("ParseException Caught with message: "+pe.getMessage());
        }
        catch (DAOException daoe)
        {
            fail("DAOException Caught with message: "+daoe.getMessage());
        }

        System.out.println("Finished testDataAccess: Insert and Get Bookings");
        closeDataAccess();
    }

    public void testGettingAUserBookingsEmptyList()
    {
        openDataAccess();
        System.out.println("Starting testDataAccess: Getting a User Bookings Empty List");

        dataAccess.clearSpotList();

        String username = "tester";
        ArrayList<Booking> bookings;
        try
        {
            bookings = dataAccess.getBookedSpotsOfGivenUser(username);
            assertEquals(0, bookings.size());
        }
        catch (DAOException daoe)
        {
            fail("DAOException Caught with message: "+daoe.getMessage());
        }

        System.out.println("Finished testDataAccess: Getting a User Bookings Empty List");
        closeDataAccess();
    }

    public void testDeleteAValidBooking()
    {
        openDataAccess();
        System.out.println("Starting testDataAccess: Delete a Valid Booking");

        String username = "marker";
        ArrayList<Booking> bookings;
        long timeSlotId = 91;
        try
        {
            bookings = dataAccess.getBookedSpotsOfGivenUser(username);
            Booking removed = bookings.get(1);
            dataAccess.deleteBooking(username, timeSlotId);
            bookings = dataAccess.getBookedSpotsOfGivenUser(username);
            assertEquals(3, bookings.size());
            assertEquals(false, bookings.contains(removed));
        }
        catch (DAOException daoe)
        {
            fail("DAOException Caught with message: "+daoe.getMessage());
        }

        System.out.println("Finished testDataAccess: Delete a Valid Booking");
        closeDataAccess();
    }

    public void testDeleteANonExistingBooking()
    {
        openDataAccess();
        System.out.println("Starting testDataAccess: Delete a Non-Existing Booking");

        String username = "Donald Trump";
        ArrayList<Booking> bookings;
        long timeSlotId = 91;

        try
        {
            bookings = dataAccess.getBookedSpotsOfGivenUser(username);
            assertEquals(4, bookings.size());
        } catch (DAOException daoe)
        {
            fail("DAOException Caught with message: "+daoe.getMessage());
        }

        try
        {
            dataAccess.deleteBooking(username, timeSlotId);
            fail("Error: Deleted a booking that does not exist!");
        }
        catch (DAOException daoe)
        {
            assertEquals("Tuple not inserted correctly.",daoe.getMessage());
        }

        try
        {
            bookings = dataAccess.getBookedSpotsOfGivenUser(username);
            assertEquals(4, bookings.size());
        } catch (DAOException daoe)
        {
            fail("DAOException Caught with message: "+daoe.getMessage());
        }

        System.out.println("Finished testDataAccess: Delete a Non-Existing Booking");
        closeDataAccess();
    }

    public void testParkingSpotsInTimeSlot()
    {
        try
        {
            dataAccess = new DataAccessStub();
            dataAccess.open("Stub");
        }
        catch (DAOException daoe)
        {
            System.err.println(daoe.getMessage());
        }

        Calendar c = Calendar.getInstance();
        try
        {
            c.set(2018, 5, 11, 11,31);
            assertEquals(16,dataAccess.getParkingSpotsByTime(c.getTime()).size());
            c.set(2018, 5, 11, 12,31);
            assertEquals(14,dataAccess.getParkingSpotsByTime(c.getTime()).size());
            c.set(2018, 5, 11, 18,31);
            assertEquals(2,dataAccess.getParkingSpotsByTime(c.getTime()).size());
            c.set(2018, 5, 11, 0,0);
            assertEquals(0,dataAccess.getParkingSpotsByTime(c.getTime()).size());
            c.set(0, 0, 0, 0,0);
            assertEquals(0,dataAccess.getParkingSpotsByTime(c.getTime()).size());
            c.set(1, 2, 3, 4,5);
            assertEquals(0,dataAccess.getParkingSpotsByTime(c.getTime()).size());
        }
        catch (Exception e)
        {
            fail();
        }
    }
}
