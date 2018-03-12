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

    long insertDaySlot(TimeSlot daySlot, String spotID) throws DAOException;

    long insertTimeSlot(TimeSlot timeSlot, long daySlotID, String spotID) throws DAOException;

	void insertParkingSpot(String user, ParkingSpot currentParkingSpot) throws DAOException;

	boolean insertUser(String username) throws DAOException;

	ArrayList<ParkingSpot> getParkingSpotsByAddressDate(String address, Date date) throws DAOException;

	ParkingSpot getParkingSpot(String spotID) throws DAOException;

	void clearSpotList();

	ArrayList<Booking> getBookedSpotsOfGivenUser(String username) throws DAOException;

	ArrayList<ParkingSpot> getHostedSpotsOfGivenUser(String username) throws DAOException;

	void setBookedSpotToDeleted(String username, long timeSlotId) throws  DAOException;

    void modifyParkingSpot(String spotID, String address, String phone, String email, Double rate) throws DAOException;

	ArrayList<TimeSlot> getTimeSlotsForParkingSpot(String spotID) throws DAOException;

	//TODO: COnfirm if returning ParkingSpot is ideal here.
	ParkingSpot getParkingSpotByID(String spotID) throws DAOException;

	boolean bookTimeSlot(String theUser, long timeSlot_ID, String spot_ID) throws DAOException;
}
