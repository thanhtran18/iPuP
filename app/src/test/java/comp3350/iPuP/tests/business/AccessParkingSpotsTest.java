package comp3350.iPuP.tests.business;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.ReservationTime;
import comp3350.iPuP.persistence.DataAccessStub;

/**
 * Created by Rodney on 2018-02-12.
 */
public class AccessParkingSpotsTest extends TestCase {
    //DataAccessStub dataStub;
    AccessParkingSpots parkSpotAccess;
    ParkingSpot ps;
    ArrayList<ParkingSpot> spots;

    public void testGetAvailableSpots() throws Exception {
    }

    public void testBookSpot() throws Exception {
    }

    public void testNullList(){
        System.out.println("Starting testAccessParkingSpots: null list.");



    }

    public void testEmptyList(){
        System.out.println("Starting testAccessParkingSpots: Empty list.");
        //dataStub=new DataAccessStub();
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

    }

    public void testListWithMissingParkingSpot(){

    }

    public void testAllSpotsBooked(){

    }

    public void testAllSpotsAvailable(){

    }

    public void testSpotNotPresentInList(){

    }

    public void testSpotAlreadyBooked(){

    }

    public void testSpotSpotsWithNoName(){

    }

    public void testSpotWithNoAddress(){

    }

}