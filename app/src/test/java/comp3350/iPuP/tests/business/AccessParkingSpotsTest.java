package comp3350.iPuP.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.iPuP.application.Main;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.ReservationTime;

public class AccessParkingSpotsTest extends TestCase
{
    AccessParkingSpots parkSpotAccess;
    ParkingSpot ps;
    ArrayList<ParkingSpot> spots;
    ArrayList<ParkingSpot> allSpots;

    public void testEmptyList()
    {
        Main.startUp();
        System.out.println("Starting testAccessParkingSpots: No parking spots inserted.");
        parkSpotAccess=new AccessParkingSpots();
        parkSpotAccess.clearSpots();
        spots=parkSpotAccess.getAvailableSpots();
        assertTrue(spots.size()==0);

        parkSpotAccess.bookSpot("fakeId");
        assertTrue(parkSpotAccess.bookSpot("fakeId").equals("Not Booked"));
        assertTrue(spots.size()==0);
        System.out.println("Finished testAccessParkingSpots: No parking spots inserted.");
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
                bookSpot("788 Plaza PlaceTheGuy20178978theGuy@domainname.com")
                .equals("Already Booked"));

        assertTrue(parkSpotAccess.
                bookSpot("588 Markham PlaceTheLady2047589465theLady@domainname.com")
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