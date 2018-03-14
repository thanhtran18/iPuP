package comp3350.iPuP.business;

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

    public AccessParkingSpots()
    {
        dataAccess = Services.getDataAccess();
        availableSpots = new ArrayList<>();
    }

    public void insertParkingSpot(String name, TimeSlot timeSlot, String repetitionInfo, String address, String phone, String email, double rate) throws DAOException
    {
        Calendar start = new GregorianCalendar();
        Calendar end = new GregorianCalendar();
        start.setTime(timeSlot.getStart());
        end.setTime(timeSlot.getEnd());

        ParkingSpot spot = new ParkingSpot(address, name, phone, email, rate);

        spot.setSpotID(insertParkingSpot(name, spot));

        if (repetitionInfo != null && !repetitionInfo.equals(""))
        {
            String[] splits = repetitionInfo.split(" ");
            if (splits[0].equals("Days"))
            {
                for (int j = 0; j < Integer.parseInt(splits[2]); j++)
                {
                    insertDaySlot(start.getTime(), end.getTime(), spot.getSpotID(), dataAccess);
                    start.add(Calendar.DAY_OF_YEAR, Integer.parseInt(splits[1]));
                    end.add(Calendar.DAY_OF_YEAR, Integer.parseInt(splits[1]));
                }
            }
            else if (splits[0].equals("Weeks"))
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
            else
            {

            }
        }
        else
        {
            insertDaySlot(start.getTime(), end.getTime(), spot.getSpotID(), dataAccess);
        }
    }

    private void insertDaySlot(Date start, Date end, long spotID, DataAccess dataAccess) throws DAOException
    {
        TimeSlot daySlot = new TimeSlot(start, end);
        daySlot.setSlotID( dataAccess.insertDaySlot(daySlot, spotID));
        insertTimeSlots(daySlot, spotID, dataAccess);
    }

    private void insertTimeSlots(TimeSlot daySlot, long spotID, DataAccess dataAccess) throws DAOException
    {
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
        return dataAccess.insertParkingSpot(user, newParkingSpot);
    }

    public ArrayList<ParkingSpot> getAllSpots()
    {
        //        returnList.addAll(dataAccess.getParkingSpots());
        return new ArrayList<>();
    }

    public ArrayList<ParkingSpot> getAvailableSpots()
    {
//        List<ParkingSpot> temp = dataAccess.getParkingSpots();
        availableSpots = new ArrayList<>();
//        for (int i = 0; i < temp.size(); i++)
//        {
//            if (!(temp.get(i).isBooked()))
//            {
//                availableSpots.add(temp.get(i));
//            }
//        }
        return availableSpots;
    }

    public void bookSpot(long spotID, String slotID)
    {
//        return dataAccess.setSpotToBooked(spotID, slotID);
    }

    public void clearSpots()
    {
        dataAccess.clearSpotList();
    }

    public ArrayList<ParkingSpot> getMyHostedSpots(String name) throws DAOException
    {
        return dataAccess.getHostedSpotsOfGivenUser(name);
    }

    public ArrayList<Booking> getMyBookedSpots(String name) throws DAOException
    {
        return dataAccess.getBookedSpotsOfGivenUser(name);
    }

    public ArrayList<ParkingSpot> getDailySpots(String address, Date today) throws DAOException
    {
        return dataAccess.getParkingSpotsByAddressDate(address, today);
    }

    public void cancelThisSpot(String username, Long timeSlotId) throws DAOException
    {
        dataAccess.deleteBooking(username, timeSlotId);
    }
    public ParkingSpot getParkingSpot(long spotID) throws DAOException
    {
        return dataAccess.getParkingSpot(spotID);
    }

    public void modifyParkingSpot(long spotID, String address, String phone, String email, Double rate) throws DAOException
    {
        dataAccess.modifyParkingSpot(spotID,address,phone,email,rate);
    }

    //TODO: Methods added by me for timeslot functionality begin here may be edited.
    public ArrayList<TimeSlot> getFreeTimeSlotsByID(long spotID) throws DAOException{
        return dataAccess.getUnbookedTimeSlotsForParkingSpot(spotID);
    }

    public ParkingSpot getSpotBYID(long spotID) throws DAOException{
        return dataAccess.getParkingSpotByID(spotID);
    }

    public boolean bookTimeSlots(ArrayList<TimeSlot> timeSlots, String userBooking, long pSpotID) throws DAOException{
        boolean returnVal = false;
        int checkLoop=0;

        for(TimeSlot currSlot:timeSlots){
            long timeSLotID = currSlot.getSlotID();
            boolean bookingWorked = dataAccess.bookTimeSlot(userBooking, timeSLotID, pSpotID);

            if(bookingWorked) {
                checkLoop++;
            }
        }
        if(checkLoop == timeSlots.size()){
            returnVal = false;
        }

        return returnVal;
    }

    public ArrayList<TimeSlot> getDaySlots(long spotID) throws DAOException
    {
        return dataAccess.getDaySlots(spotID);
    }

    public ArrayList<TimeSlot> getTimeSlots(long daySlotID) throws DAOException
    {
        return dataAccess.getTimeSlots(daySlotID);
    }

    public boolean deleteTimeSlot(Long slotID) throws DAOException
    {
        return dataAccess.deleteTimeSlot(slotID);
    }

    public boolean deleteDaySlot(long slotID) throws  DAOException
    {
        return dataAccess.deleteDaySlot(slotID);
    }
}
