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

    long insertDaySlot(TimeSlot daySlot, long spotID) throws DAOException;

    long insertTimeSlot(TimeSlot timeSlot, long daySlotID, long spotID) throws DAOException;

	long insertParkingSpot(String user, ParkingSpot currentParkingSpot) throws DAOException;

	boolean insertUser(String username) throws DAOException;

	TimeSlot getAvailableTimeForAParkingSpot(long spotID) throws DAOException;

	ArrayList<ParkingSpot> getParkingSpotsByAddressDate(String address, Date date) throws DAOException;

	ParkingSpot getParkingSpot(long spotID) throws DAOException;

	void clearSpotList();

	ArrayList<Booking> getBookedSpotsOfGivenUser(String username) throws DAOException;

	ArrayList<ParkingSpot> getHostedSpotsOfGivenUser(String username) throws DAOException;

	void deleteBooking(String username, long timeSlotID) throws  DAOException;

    void modifyParkingSpot(long spotID, String address, String phone, String email, Double rate) throws DAOException;

    ArrayList<TimeSlot> getUnbookedTimeSlotsForParkingSpot(long spotID) throws DAOException;

    //TODO: COnfirm if returning ParkingSpot is ideal here.
    ParkingSpot getParkingSpotByID(long spotID) throws DAOException;

    boolean bookTimeSlot(String username, long timeSlotID, long spotID) throws DAOException;

	ArrayList<TimeSlot> getDaySlots(long spotID) throws DAOException;

	ArrayList<TimeSlot> getTimeSlots(long spotID) throws DAOException;

	boolean deleteDaySlot(long daySlotID) throws DAOException;

	boolean deleteTimeSlot(long timeSlotID) throws DAOException;

	ArrayList<ParkingSpot> getAllParkingSpots() throws DAOException;
}
