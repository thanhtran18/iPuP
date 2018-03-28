package comp3350.iPuP.business;

import org.osmdroid.util.GeoPoint;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import comp3350.iPuP.application.Services;
import comp3350.iPuP.objects.Booking;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.TimeSlot;
import comp3350.iPuP.persistence.DataAccess;


public class AccessParkingSpots
{
    private DataAccess dataAccess;
    private ArrayList<ParkingSpot> availableSpots;

    private void getDataAccess()
    {
        dataAccess = Services.getDataAccess();
    }

    public AccessParkingSpots()
    {
        getDataAccess();
        availableSpots = new ArrayList<>();
    }

    public void insertParkingSpot(String name, TimeSlot timeSlot, String repetitionInfo, String address, String phone, String email, double rate, double latitude, double longitude) throws DAOException
    {
        getDataAccess();

        Calendar start = new GregorianCalendar();
        Calendar end = new GregorianCalendar();
        start.setTime(timeSlot.getStart());
        end.setTime(timeSlot.getEnd());

        ParkingSpot spot = new ParkingSpot(address, name, phone, email, rate, latitude, longitude);

        spot.setSpotID(insertParkingSpot(name, spot));

        if (repetitionInfo != null && !repetitionInfo.equals(""))
        {
            String[] splits = repetitionInfo.split(" ");
            switch (splits[0])
            {
                case "Days":
                    for (int j = 0; j < Integer.parseInt(splits[2]); j++)
                    {
                        insertDaySlot(start.getTime(), end.getTime(), spot.getSpotID(), dataAccess);
                        start.add(Calendar.DAY_OF_YEAR, Integer.parseInt(splits[1]));
                        end.add(Calendar.DAY_OF_YEAR, Integer.parseInt(splits[1]));
                    }
                    break;
                case "Weeks":
                    try
                    {
                        boolean[] days = TimeSlot.weekCodeToBoolArray(splits[3]);
                        for (int j = 0; j < Integer.parseInt(splits[2]); j++)
                        {
                            for (int i = 0; i < 7; i++)
                            {
                                if (days[(start.get(Calendar.DAY_OF_WEEK) - 1) % 7])
                                {
                                    insertDaySlot(start.getTime(), end.getTime(), spot.getSpotID(), dataAccess);
                                }

                                start.add(Calendar.DAY_OF_YEAR, 1);
                                end.add(Calendar.DAY_OF_YEAR, 1);
                            }
                            start.add(Calendar.WEEK_OF_YEAR, Integer.parseInt(splits[1]) - 1);
                            end.add(Calendar.WEEK_OF_YEAR, Integer.parseInt(splits[1]) - 1);
                        }
                    }
                    catch (ParseException pe)
                    {
                        throw new DAOException("Repeat acivity had some strange error and returned: " + splits[3]);
                    }
                    break;
                default:

                    break;
            }
        }
        else
        {
            insertDaySlot(start.getTime(), end.getTime(), spot.getSpotID(), dataAccess);
        }
    }

    private void insertDaySlot(Date start, Date end, long spotID, DataAccess dataAccess) throws DAOException
    {
        getDataAccess();

        TimeSlot daySlot = new TimeSlot(start, end);
        daySlot.setSlotID( dataAccess.insertDaySlot(daySlot, spotID));
        insertTimeSlots(daySlot, spotID, dataAccess);
    }

    private void insertTimeSlots(TimeSlot daySlot, long spotID, DataAccess dataAccess) throws DAOException
    {
        getDataAccess();

        Calendar start = new GregorianCalendar();
        Calendar end = new GregorianCalendar();
        start.setTime(daySlot.getStart());
        end.setTime(daySlot.getEnd());

        int numSlots = (int)(end.getTimeInMillis() - start.getTimeInMillis()) / 1000 / 60 / 30;

        for (int i = 0; i < numSlots; i++)
        {
            Date startTime = start.getTime();
            start.add(Calendar.MINUTE, 30);
            Date endTime = start.getTime();
            dataAccess.insertTimeSlot(new TimeSlot(startTime, endTime), daySlot.getSlotID(), spotID);
        }
    }

    private long insertParkingSpot(String user, ParkingSpot newParkingSpot) throws DAOException
    {
        getDataAccess();

        return dataAccess.insertParkingSpot(user, newParkingSpot);
    }

    public ArrayList<ParkingSpot> getAllParkingSpots() throws DAOException
    {
        getDataAccess();

        return dataAccess.getAllParkingSpots();
    }

    public ArrayList<ParkingSpot> getAvailableSpots()
    {
        getDataAccess();

        availableSpots = new ArrayList<>();
        return availableSpots;
    }

    public void clearSpots()
    {
        getDataAccess();

        dataAccess.clearSpotList();
    }

    public ArrayList<ParkingSpot> getMyHostedSpots(String name) throws DAOException
    {
        getDataAccess();

        return dataAccess.getHostedSpotsOfGivenUser(name);
    }

    public ArrayList<Booking> getMyBookedSpots(String name) throws DAOException
    {
        getDataAccess();

        return dataAccess.getBookedSpotsOfGivenUser(name);
    }


    public ArrayList<ParkingSpot> getDailySpots(String address, Date today) throws DAOException
    {
        getDataAccess();

        return dataAccess.getParkingSpotsByAddressDate(address, today);
    }

    public ArrayList<ParkingSpot> getParkingSpotsByTime(Date time) throws  DAOException
    {
        getDataAccess();

        return dataAccess.getParkingSpotsByTime(time);
    }

    public void cancelThisSpot(String username, long timeSlotId) throws DAOException
    {
        getDataAccess();

        dataAccess.deleteBooking(username, timeSlotId);
    }

    public ParkingSpot getParkingSpot(long spotID) throws DAOException
    {
        getDataAccess();

        return dataAccess.getParkingSpot(spotID);
    }

    public void modifyParkingSpot(long spotID, String address, String phone, String email, Double rate, double latitude, double longitude) throws DAOException
    {
        getDataAccess();

        dataAccess.modifyParkingSpot(spotID,address,phone,email,rate, latitude, longitude);
    }

    public ArrayList<TimeSlot> getFreeTimeSlotsByID(long spotID) throws DAOException
    {
        getDataAccess();

        return dataAccess.getUnbookedTimeSlotsForParkingSpot(spotID);
    }

    public ParkingSpot getSpotByID(long spotID) throws DAOException
    {
        getDataAccess();

        return dataAccess.getParkingSpotByID(spotID);
    }

    public boolean bookTimeSlots(ArrayList<TimeSlot> timeSlots, String userBooking, long pSpotID) throws DAOException
    {
        getDataAccess();

        boolean returnVal = false;
        int checkLoop=0;

        if(pSpotID >= 0 && timeSlots!=null && userBooking!=null && userBooking!="") {
            for (TimeSlot currSlot : timeSlots) {
                long timeSLotID = currSlot.getSlotID();
                boolean bookingWorked = dataAccess.bookTimeSlot(userBooking, timeSLotID, pSpotID);

                if (bookingWorked) {
                    checkLoop++;
                }
            }
            if (timeSlots.size() > 0 && checkLoop == timeSlots.size()) {
                returnVal = true;
            }
        }
        return returnVal;
    }

    public ArrayList<TimeSlot> getDaySlots(long spotID) throws DAOException
    {
        getDataAccess();

        return dataAccess.getDaySlots(spotID);
    }

    public ArrayList<TimeSlot> getTimeSlots(long daySlotID) throws DAOException
    {
        getDataAccess();

        return dataAccess.getTimeSlots(daySlotID);
    }

    public boolean deleteTimeSlot(long timeSlotID) throws DAOException
    {
        getDataAccess();

        return dataAccess.deleteTimeSlot(timeSlotID);
    }

    public boolean deleteDaySlot(long daySlotID) throws  DAOException
    {
        getDataAccess();

        return dataAccess.deleteDaySlot(daySlotID);
    }
}
