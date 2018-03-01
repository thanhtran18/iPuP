package comp3350.iPuP.persistence;

import java.util.ArrayList;
import java.util.List;

import comp3350.iPuP.objects.ParkingSpot;

public interface DataAccess
{
	void open(String string);

	void close();

	String insertParkingSpot(ParkingSpot parkingSpot);

	ArrayList<ParkingSpot> getParkingSpots();

	String setSpotToBooked(String spotID, int slotID);

	void clearSpotList();
}
