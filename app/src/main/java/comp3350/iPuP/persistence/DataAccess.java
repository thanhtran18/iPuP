package comp3350.iPuP.persistence;

import java.util.ArrayList;
import java.util.Date;

import comp3350.iPuP.objects.Booking;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.TimeSlot;

public interface DataAccess
{
	void open(String string) throws DAOException;

	void close() throws DAOException;

	boolean insertParkingSpot(String user, ParkingSpot currentParkingSpot) throws DAOException;

	long insertDaySlot(TimeSlot daySlot, String spotID) throws DAOException;

	boolean insertTimeSlot(TimeSlot timeSlot, long daySlotID, String spotID) throws DAOException;

	boolean insertUser(String username) throws DAOException;

    ArrayList<TimeSlot> getDaySlotsForAParkingSpot(String spotID) throws DAOException;

	ArrayList<ParkingSpot> getParkingSpotsByAddressDate(String address, Date date) throws DAOException;

	void clearSpotList();

	ArrayList<Booking> getBookedSpotsOfGivenUser(String username) throws DAOException;

    void getDaySlotsForAParkingSpot(ParkingSpot parkingSpot) throws DAOException;

	ArrayList<ParkingSpot> getHostedSpotsOfGivenUser(String username) throws DAOException;

	boolean setBookedSpotToDeleted(String username, long timeSlotId) throws  DAOException;
}
