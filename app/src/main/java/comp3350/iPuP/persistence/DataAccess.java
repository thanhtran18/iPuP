package comp3350.iPuP.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import comp3350.iPuP.objects.DaySlot;
import comp3350.iPuP.objects.ParkingSpot;

public interface DataAccess
{
	void open(String string) throws Exception;

	void close();

	String insertDaySlot(String psID, DaySlot daySlot);

	String insertDaySlots(String psID, ArrayList<DaySlot> daySlots);

	String insertParkingSpot(String user, ParkingSpot currentParkingSpot);

	String insertTimeSlot(String psID, Long tsID, Date start, Date end);

	boolean insertUser(String username);

//	ArrayList<ParkingSpot> getParkingSpotsByDate(Date date);

	ArrayList<ParkingSpot> getParkingSpotsByDateTime(Date start, Date end);
//
//	ArrayList<ParkingSpot> getParkingSpotsByDateRate(Date date, Double rate);
//
//	ArrayList<ParkingSpot> getParkingSpotsByDateStreet(Date date, String street);

	void clearSpotList();

	public ArrayList<ParkingSpot> getSpotsOfGivenUser(String username);
}
