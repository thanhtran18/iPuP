package comp3350.iPuP.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import comp3350.iPuP.objects.DaySlot;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.User;

public interface DataAccess
{
	void open(String string);

	void close();

	String insertDaySlot(String psID, DaySlot daySlot);

	String insertDaySlots(String psID, ArrayList<DaySlot> daySlots);

	String insertParkingSpot(User user, ParkingSpot currentParkingSpot);

	String insertTimeSlot(String psID, Long tsID, Date start, Date end);

	String insertUser(String username);



	ArrayList<ParkingSpot> getParkingSpots();

	User getUser(String username);

	String setSpotToBooked(String spotID, String slotID);

	void clearSpotList();
}
