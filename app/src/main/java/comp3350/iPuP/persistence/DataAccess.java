package comp3350.iPuP.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import comp3350.iPuP.objects.Booking;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.objects.DaySlot;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.TimeSlot;

public interface DataAccess
{
	void open(String string) throws DAOException;

	void close() throws DAOException;

	boolean insertDaySlot(String psID, DaySlot daySlot) throws DAOException;

	boolean insertDaySlots(String psID, ArrayList<DaySlot> daySlots) throws DAOException;

	boolean insertParkingSpot(String user, ParkingSpot currentParkingSpot) throws DAOException;

	boolean insertTimeSlot(String psID, Long tsID, Date start, Date end) throws DAOException;

	boolean insertUser(String username) throws DAOException;

	ArrayList<ParkingSpot> getParkingSpotsByAddressDate(String address, Date date) throws DAOException;

	void clearSpotList();

	ArrayList<Booking> getBookedSpotsOfGivenUser(String username) throws DAOException;

	ArrayList<ParkingSpot> getHostedSpotsOfGivenUser(String username) throws DAOException;

	boolean setBookedSpotToDeleted(String username, Long timeSlotId) throws  DAOException;
}
