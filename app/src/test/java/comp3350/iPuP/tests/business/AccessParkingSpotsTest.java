package comp3350.iPuP.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.ReservationTime;

/**
 * Created by Rodney on 2018-02-12.
 */
public class AccessParkingSpotsTest extends TestCase {
    //DataAccessStub dataStub;
    AccessParkingSpots parkSpotAccess;
    ParkingSpot ps;
    ArrayList<ParkingSpot> spots;
    ArrayList<ParkingSpot> allSpots;

    public void testGetAvailableSpots() throws Exception {
    }

    public void testBookSpot() throws Exception {
    }

    public void testNullList(){
        System.out.println("Starting testAccessParkingSpots: null access.");
        parkSpotAccess.bookSpot("noID");
        spots=parkSpotAccess.getAvailableSpots();
        System.out.println("Finishing testAccessParkingSpots: null access.");

    }

    public void testEmptyList(){
        System.out.println("Starting testAccessParkingSpots: Empty list.");
        parkSpotAccess=new AccessParkingSpots();
        spots=parkSpotAccess.getAvailableSpots();
        assertTrue(spots.size()==0);

        parkSpotAccess.bookSpot("fakeId");
        System.out.println("Finished testAccessParkingSpots: Empty list");
    }

    public void testOneParkingSpotInList(){
        System.out.println("Starting testAccessParkingSpots: 1 item in list.");
        //dataStub=new DataAccessStub();
        parkSpotAccess=new AccessParkingSpots();
        ReservationTime time = new ReservationTime(2018, 6, 11, 10, 30,
                12, 30);
        ps=new ParkingSpot(time, "70 Plaza Place", "ThePerson", "201789465",
                "theperson@domainname.com", 0.25);
        parkSpotAccess.insertParkingSpot(ps);
        spots=parkSpotAccess.getAvailableSpots();
        assertTrue(spots.size()==0);


        parkSpotAccess.bookSpot(ps.getId());
        spots=parkSpotAccess.getAvailableSpots();
        assertTrue(spots.size()==1);

        System.out.println("Finished testAccessParkingSpots: 1 item in list");
    }

    public void testListWithRegularData(){
        System.out.println("Starting testAccessParkingSpots: regular data in list.");
        /*String idToBook1="";
        String idToBook2="";*/
        parkSpotAccess=new AccessParkingSpots();
        ReservationTime time = new ReservationTime(2018, 6, 11, 10, 30,
                12, 30);
        ps=new ParkingSpot(time, "70 Plaza Place", "ThePerson", "201789465",
                "theperson@domainname.com", 0.25);
        parkSpotAccess.insertParkingSpot(ps); //pos 0
        //parkSpotAccess.bookSpot("70 Plaza PlaceThePerson201789465theperson@domainname.com");


        time = new ReservationTime(2017, 5, 17, 10, 30,
                12, 30);
        ps=new ParkingSpot(time, "788 Plaza Place", "TheGuy", "20178978",
                "theGuy@domainname.com", 0.75);
        parkSpotAccess.insertParkingSpot(ps); //pos 1
        //parkSpotAccess.bookSpot("788 Plaza PlaceTheGuy20178978theGuy@domainname.com");

        time = new ReservationTime(2017, 5, 20, 6, 15,
                10, 40);
        ps=new ParkingSpot(time, "707 Ave Place", "TheGirl", "204899465",
                "theGirl@domainname.com", 0.95);
        parkSpotAccess.insertParkingSpot(ps); //pos 2
        //parkSpotAccess.bookSpot("707 Ave PlaceTheGirl204899465theGirl@domainname.com");

        time = new ReservationTime(2016, 7, 20, 10, 30,
                8, 30);
        ps=new ParkingSpot(time, "588 Markham Place", "TheLady", "2047589465",
                "theLady@domainname.com", 1.25);
        parkSpotAccess.insertParkingSpot(ps); //pos 3
        //parkSpotAccess.bookSpot("588 Markham PlaceTheLady2047589465theLady@domainname.com");


        parkSpotAccess.bookSpot("788 Plaza PlaceTheGuy20178978theGuy@domainname.com");
        parkSpotAccess.bookSpot("588 Markham PlaceTheLady2047589465theLady@domainname.com");

        //spots=parkSpotAccess.getAvailableSpots();
        //assertTrue(spots.size()==0);

        //parkSpotAccess.bookSpot(ps.getId());
        spots=parkSpotAccess.getAvailableSpots();
        assertTrue(spots.size()==2);

        allSpots=parkSpotAccess.getAllSpots();
        assertTrue(allSpots.get(1).isBooked());
        assertTrue(allSpots.get(3).isBooked());
        System.out.println("Finished testAccessParkingSpots: regular data in list");
    }

    public void testListWithMissingParkingSpot(){
        System.out.println("Starting testAccessParkingSpots: Booking a spot that does not exist.");
        parkSpotAccess=new AccessParkingSpots();
        ReservationTime time = new ReservationTime(2018, 6, 11, 10, 30,
                12, 30);
        ps=new ParkingSpot(time, "70 Plaza Place", "ThePerson", "201789465",
                "theperson@domainname.com", 0.25);
        parkSpotAccess.insertParkingSpot(ps); //pos 0

        time = new ReservationTime(2017, 5, 17, 10, 30,
                12, 30);
        ps=new ParkingSpot(time, "788 Plaza Place", "TheGuy", "20178978",
                "theGuy@domainname.com", 0.75);
        parkSpotAccess.insertParkingSpot(ps); //pos 1

        time = new ReservationTime(2017, 5, 20, 6, 15,
                10, 40);
        ps=new ParkingSpot(time, "707 Ave Place", "TheGirl", "204899465",
                "theGirl@domainname.com", 0.95);
        parkSpotAccess.insertParkingSpot(ps); //pos 2

        time = new ReservationTime(2016, 7, 20, 10, 30,
                8, 30);
        ps=new ParkingSpot(time, "588 Markham Place", "TheLady", "2047589465",
                "theLady@domainname.com", 1.25);
        parkSpotAccess.insertParkingSpot(ps); //pos 3

        parkSpotAccess.bookSpot("789 Plaza PlaceTheGuy20178978theGuy@domainname.com");
        parkSpotAccess.bookSpot("588 Markham PlaceTheLady2047589465theLady@domainname.comkj");

        spots=parkSpotAccess.getAvailableSpots();
        assertTrue(spots.size()==0);

        /*allSpots=parkSpotAccess.getAllSpots();
        assertTrue(allSpots.get(1).isBooked());
        assertTrue(allSpots.get(3).isBooked());*/
        System.out.println("Finished testAccessParkingSpots: Booking a spot that does not exist");
    }

    public void testAllSpotsBooked(){
        System.out.println("Starting testAccessParkingSpots: Booking all spots.");
        parkSpotAccess=new AccessParkingSpots();
        ReservationTime time = new ReservationTime(2018, 6, 11, 10, 30,
                12, 30);
        ps=new ParkingSpot(time, "70 Plaza Place", "ThePerson", "201789465",
                "theperson@domainname.com", 0.25);
        parkSpotAccess.insertParkingSpot(ps); //pos 0
        parkSpotAccess.bookSpot("70 Plaza PlaceThePerson201789465theperson@domainname.com");

        time = new ReservationTime(2017, 5, 17, 10, 30,
                12, 30);
        ps=new ParkingSpot(time, "788 Plaza Place", "TheGuy", "20178978",
                "theGuy@domainname.com", 0.75);
        parkSpotAccess.insertParkingSpot(ps); //pos 1
        parkSpotAccess.bookSpot("788 Plaza PlaceTheGuy20178978theGuy@domainname.com");

        time = new ReservationTime(2017, 5, 20, 6, 15,
                10, 40);
        ps=new ParkingSpot(time, "707 Ave Place", "TheGirl", "204899465",
                "theGirl@domainname.com", 0.95);
        parkSpotAccess.insertParkingSpot(ps); //pos 2
        parkSpotAccess.bookSpot("707 Ave PlaceTheGirl204899465theGirl@domainname.com");

        time = new ReservationTime(2016, 7, 20, 10, 30,
                8, 30);
        ps=new ParkingSpot(time, "588 Markham Place", "TheLady", "2047589465",
                "theLady@domainname.com", 1.25);
        parkSpotAccess.insertParkingSpot(ps); //pos 3
        parkSpotAccess.bookSpot("588 Markham PlaceTheLady2047589465theLady@domainname.com");

        spots=parkSpotAccess.getAvailableSpots();
        assertTrue(spots.size()==0);

        allSpots=parkSpotAccess.getAllSpots();
        assertTrue(allSpots.get(0).isBooked());
        assertTrue(allSpots.get(1).isBooked());
        assertTrue(allSpots.get(2).isBooked());
        assertTrue(allSpots.get(3).isBooked());
        System.out.println("Finished testAccessParkingSpots: Booking all spots");
    }

    public void testAllSpotsAvailable(){
        System.out.println("Starting testAccessParkingSpots: regular data in list.");

        parkSpotAccess=new AccessParkingSpots();
        ReservationTime time = new ReservationTime(2018, 6, 11, 10, 30,
                12, 30);
        ps=new ParkingSpot(time, "70 Plaza Place", "ThePerson", "201789465",
                "theperson@domainname.com", 0.25);
        parkSpotAccess.insertParkingSpot(ps); //pos 0

        time = new ReservationTime(2017, 5, 17, 10, 30,
                12, 30);
        ps=new ParkingSpot(time, "788 Plaza Place", "TheGuy", "20178978",
                "theGuy@domainname.com", 0.75);
        parkSpotAccess.insertParkingSpot(ps); //pos 1

        time = new ReservationTime(2017, 5, 20, 6, 15,
                10, 40);
        ps=new ParkingSpot(time, "707 Ave Place", "TheGirl", "204899465",
                "theGirl@domainname.com", 0.95);
        parkSpotAccess.insertParkingSpot(ps); //pos 2

        time = new ReservationTime(2016, 7, 20, 10, 30,
                8, 30);
        ps=new ParkingSpot(time, "588 Markham Place", "TheLady", "2047589465",
                "theLady@domainname.com", 1.25);
        parkSpotAccess.insertParkingSpot(ps); //pos 3

        spots=parkSpotAccess.getAvailableSpots();
        assertTrue(spots.size()==4);

        allSpots=parkSpotAccess.getAllSpots();
        assertFalse(allSpots.get(0).isBooked());
        assertFalse(allSpots.get(1).isBooked());
        assertFalse(allSpots.get(2).isBooked());
        assertFalse(allSpots.get(3).isBooked());
        System.out.println("Finished testAccessParkingSpots: regular data in list");
    }

    /*public void testSpotNotPresentInList(){

    }*/

    /*public void testSpotAlreadyBooked(){
        System.out.println("Starting testAccessParkingSpots: regular data in list.");
        parkSpotAccess=new AccessParkingSpots();
        ReservationTime time = new ReservationTime(2018, 6, 11, 10, 30,
                12, 30);
        ps=new ParkingSpot(time, "70 Plaza Place", "ThePerson", "201789465",
                "theperson@domainname.com", 0.25);
        parkSpotAccess.insertParkingSpot(ps); //pos 0
        parkSpotAccess.bookSpot("70 Plaza PlaceThePerson201789465theperson@domainname.com");

        time = new ReservationTime(2017, 5, 17, 10, 30,
                12, 30);
        ps=new ParkingSpot(time, "788 Plaza Place", "TheGuy", "20178978",
                "theGuy@domainname.com", 0.75);
        parkSpotAccess.insertParkingSpot(ps); //pos 1
        parkSpotAccess.bookSpot("788 Plaza PlaceTheGuy20178978theGuy@domainname.com");

        time = new ReservationTime(2017, 5, 20, 6, 15,
                10, 40);
        ps=new ParkingSpot(time, "707 Ave Place", "TheGirl", "204899465",
                "theGirl@domainname.com", 0.95);
        parkSpotAccess.insertParkingSpot(ps); //pos 2
        parkSpotAccess.bookSpot("707 Ave PlaceTheGirl204899465theGirl@domainname.com");

        time = new ReservationTime(2016, 7, 20, 10, 30,
                8, 30);
        ps=new ParkingSpot(time, "588 Markham Place", "TheLady", "2047589465",
                "theLady@domainname.com", 1.25);
        parkSpotAccess.insertParkingSpot(ps); //pos 3
        parkSpotAccess.bookSpot("588 Markham PlaceTheLady2047589465theLady@domainname.com");

        spots=parkSpotAccess.getAvailableSpots();
        assertTrue(spots.size()==0);

        allSpots=parkSpotAccess.getAllSpots();
        assertTrue(allSpots.get(0).isBooked());
        assertTrue(allSpots.get(1).isBooked());
        assertTrue(allSpots.get(2).isBooked());
        assertTrue(allSpots.get(3).isBooked());
        System.out.println("Finished testAccessParkingSpots: regular data in list");
    }*/

    public void testSpotSpotsWithNoName(){

    }

    public void testSpotWithNoAddress(){

    }

    public void testDoubleBooking(){
        System.out.println("Starting testAccessParkingSpots: Booking a spot twice.");

        parkSpotAccess=new AccessParkingSpots();
        ReservationTime time = new ReservationTime(2018, 6, 11, 10, 30,
                12, 30);
        ps=new ParkingSpot(time, "70 Plaza Place", "ThePerson", "201789465",
                "theperson@domainname.com", 0.25);
        parkSpotAccess.insertParkingSpot(ps); //pos 0
        ps.setBooked(true);
        parkSpotAccess.bookSpot("70 Plaza PlaceThePerson201789465theperson@domainname.com");

        time = new ReservationTime(2017, 5, 17, 10, 30,
                12, 30);
        ps=new ParkingSpot(time, "788 Plaza Place", "TheGuy", "20178978",
                "theGuy@domainname.com", 0.75);
        parkSpotAccess.insertParkingSpot(ps); //pos 1
        ps.setBooked(true);
        parkSpotAccess.bookSpot("788 Plaza PlaceTheGuy20178978theGuy@domainname.com");

        time = new ReservationTime(2017, 5, 20, 6, 15,
                10, 40);
        ps=new ParkingSpot(time, "707 Ave Place", "TheGirl", "204899465",
                "theGirl@domainname.com", 0.95);
        parkSpotAccess.insertParkingSpot(ps); //pos 2
        ps.setBooked(true);
        parkSpotAccess.bookSpot("707 Ave PlaceTheGirl204899465theGirl@domainname.com");

        time = new ReservationTime(2016, 7, 20, 10, 30,
                8, 30);
        ps=new ParkingSpot(time, "588 Markham Place", "TheLady", "2047589465",
                "theLady@domainname.com", 1.25);
        parkSpotAccess.insertParkingSpot(ps); //pos 3
        ps.setBooked(true);
        parkSpotAccess.bookSpot("588 Markham PlaceTheLady2047589465theLady@domainname.com");

        spots=parkSpotAccess.getAvailableSpots();
        assertTrue(spots.size()==4);

        allSpots=parkSpotAccess.getAllSpots();
        assertTrue(allSpots.get(0).isBooked());
        assertTrue(allSpots.get(1).isBooked());
        assertTrue(allSpots.get(2).isBooked());
        assertTrue(allSpots.get(3).isBooked());
        System.out.println("Finished testAccessParkingSpots: Booking a spot twice.");
    }

}