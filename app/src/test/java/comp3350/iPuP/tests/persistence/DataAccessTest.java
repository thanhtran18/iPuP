package comp3350.iPuP.tests.persistence;

import junit.framework.TestCase;

import java.text.ParseException;
import java.util.ArrayList;

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
        try
        {
            dataAccess = new DataAccessStub();
            dataAccess.open("Stub");
        } catch (DAOException daoe)
        {
            System.err.println(daoe.getMessage());
        }

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
            dataAccess = new DataAccessStub();
            dataAccess.open("Stub");
        } catch (DAOException daoe)
        {
            System.err.println(daoe.getMessage());
        }

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
    }

    public void testGetParkingSpotsWithoutNewInsertion()
    {
        try
        {
            dataAccess = new DataAccessStub();
            dataAccess.open("Stub");
        } catch (DAOException daoe)
        {
            System.err.println(daoe.getMessage());
        }

        try
        {
            ArrayList<ParkingSpot> parkingSpots = dataAccess.getParkingSpotsByAddressDate("", dateFormatter.getSqlDateFormat().parse("2018-06-11"));
            assertEquals(21,parkingSpots.size());
            assertTrue(parkingSpots.get(0).getAddress().toLowerCase().compareTo(parkingSpots.get(1).getAddress().toLowerCase()) < 0);
            assertTrue(parkingSpots.get(16).getAddress().toLowerCase().compareTo(parkingSpots.get(17).getAddress().toLowerCase()) < 0);
            assertFalse(parkingSpots.get(20).getAddress().toLowerCase().compareTo(parkingSpots.get(12).getAddress().toLowerCase()) < 0);
            assertTrue(parkingSpots.get(19).getAddress().toLowerCase().compareTo(parkingSpots.get(20).getAddress().toLowerCase()) < 0);
            assertTrue(parkingSpots.get(0).getAddress().toLowerCase().compareTo(parkingSpots.get(20).getAddress().toLowerCase()) < 0);
        }
        catch (ParseException pe)
        {
            fail("ParseException Caught with message: "+pe.getMessage());
        }
        catch (DAOException daoe)
        {
            fail("DAOException Caught with message: "+daoe.getMessage());
        }
    }

    public void testInsertThenGetParkingSpots()
    {
        try
        {
            dataAccess = new DataAccessStub();
            dataAccess.open("Stub");
        } catch (DAOException daoe)
        {
            System.err.println(daoe.getMessage());
        }

        ParkingSpot parkingSpot = new ParkingSpot(-1, "1 Tester Street", "tester", "2042222222", "testing@tester.ca", 101);

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
            assertEquals("Error in creating ParkingSpot object with SPOT_ID = 22 for Username: tester!",daoe.getMessage());
        }

        parkingSpot = new ParkingSpot(-1, "2 Tester Street", "tester", "2042222222", "testing@tester.ca", 101);

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
            assertEquals(24,parkingSpots.size());
            assertTrue(parkingSpots.get(0).getAddress().toLowerCase().compareTo(parkingSpots.get(1).getAddress().toLowerCase()) < 0);
            assertTrue(parkingSpots.get(16).getAddress().toLowerCase().compareTo(parkingSpots.get(17).getAddress().toLowerCase()) < 0);
            assertFalse(parkingSpots.get(23).getAddress().toLowerCase().compareTo(parkingSpots.get(13).getAddress().toLowerCase()) < 0);
            assertTrue(parkingSpots.get(22).getAddress().toLowerCase().compareTo(parkingSpots.get(23).getAddress().toLowerCase()) < 0);
            assertTrue(parkingSpots.get(0).getAddress().toLowerCase().compareTo(parkingSpots.get(23).getAddress().toLowerCase()) < 0);
        }
        catch (DAOException daoe)
        {
            fail("DAOException Caught with message: "+daoe.getMessage());
        }

    }

    public void testInsertAndDeleteDayslots()
    {
        try
        {
            dataAccess = new DataAccessStub();
            dataAccess.open("Stub");
        } catch (DAOException daoe)
        {
            System.err.println(daoe.getMessage());
        }

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
    }

    public void testInsertAndGetTimeslots()
    {
        try
        {
            dataAccess = new DataAccessStub();
            dataAccess.open("Stub");
        } catch (DAOException daoe)
        {
            System.err.println(daoe.getMessage());
        }

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
    }

    public void testInsertAndGetBookings()
    {
        try
        {
            dataAccess = new DataAccessStub();
            dataAccess.open("Stub");
        } catch (DAOException daoe)
        {
            System.err.println(daoe.getMessage());
        }
        ArrayList<Booking> bookings;
        Booking abooking;
        try {
            bookings = dataAccess.getBookedSpotsOfGivenUser("marker");
            assertEquals(4, bookings.size());
            abooking = bookings.get(0);
            assertEquals("marker", abooking.getName());
            assertEquals((long) 173, abooking.getTimeSlotId());
            assertEquals("1000 St. Mary's Rd", abooking.getAddress());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 18:30:00"), abooking.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 19:00:00"), abooking.getEnd());
            abooking = bookings.get(1);
            assertEquals("marker", abooking.getName());
            assertEquals((long) 91, abooking.getTimeSlotId());
            assertEquals("1338 Chancellor Drive", abooking.getAddress());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 14:00:00"), abooking.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 14:30:00"), abooking.getEnd());
            abooking = bookings.get(2);
            assertEquals("marker", abooking.getName());
            assertEquals((long) 94, abooking.getTimeSlotId());
            assertEquals("91 Dalhousie Drive", abooking.getAddress());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"), abooking.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 11:00:00"), abooking.getEnd());
            abooking = bookings.get(3);
            assertEquals("marker", abooking.getName());
            assertEquals((long) 145, abooking.getTimeSlotId());
            assertEquals("1 Pembina Hwy", abooking.getAddress());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 12:30:00"), abooking.getStart());
            assertEquals(dateFormatter.getSqlDateTimeFormat().parse("2018-06-11 13:00:00"), abooking.getEnd());
        }
        catch (ParseException pe)
        {
            fail("ParseException Caught with message: "+pe.getMessage());
        }
        catch (DAOException daoe)
        {
            fail("DAOException Caught with message: "+daoe.getMessage());
        }
    }

    public void testGettingAUserBookingsEmptyList()
    {
        try
        {
            dataAccess = new DataAccessStub();
            dataAccess.open("Stub");
        } catch (DAOException daoe)
        {
            System.err.println(daoe.getMessage());
        }

        dataAccess.clearSpotList();

        String username = "tester";
        ArrayList<Booking> bookings;
        try
        {
            bookings = dataAccess.getBookedSpotsOfGivenUser(username);
            assertEquals(0, bookings.size());
        }
        catch (DAOException de)
        {
            System.out.print(de.getMessage());
            fail();
        }
    }

    public void testDeleteAValidBooking()
    {
        try
        {
            dataAccess = new DataAccessStub();
            dataAccess.open("Stub");
        } catch (DAOException daoe)
        {
            System.err.println(daoe.getMessage());
        }

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
        catch (DAOException de)
        {
            System.out.print(de.getMessage());
            fail();
        }
    }

    public void testDeleteABookingOfAnotherUser()
    {
        try
        {
            dataAccess = new DataAccessStub();
            dataAccess.open("Stub");
        } catch (DAOException daoe)
        {
            System.err.println(daoe.getMessage());
        }

        dataAccess.clearSpotList();
        String username = "Donald Trump";
        ArrayList<Booking> bookings;
        long timeSlotId = 91;
        try
        {
            dataAccess.deleteBooking(username, timeSlotId);
            bookings = dataAccess.getBookedSpotsOfGivenUser(username);
            assertEquals(0, bookings.size());
        }
        catch (DAOException de)
        {
            System.out.print(de.getMessage());
            fail();
        }
    }

    public void testDeleteABookingOfEmptyList()
    {
        try
        {
            dataAccess = new DataAccessStub();
            dataAccess.open("Stub");
        } catch (DAOException daoe)
        {
            System.err.println(daoe.getMessage());
        }

        dataAccess.clearSpotList();

        String username = "tester";
        ArrayList<Booking> bookings;
        long timeSlotId = 91;
        try
        {
            dataAccess.deleteBooking(username, timeSlotId);
            bookings = dataAccess.getBookedSpotsOfGivenUser(username);
            assertEquals(0, bookings.size());
        }
        catch (DAOException de)
        {
            System.out.print(de.getMessage());
            fail();
        }
    }
}
