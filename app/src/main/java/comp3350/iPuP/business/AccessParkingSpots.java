package comp3350.iPuP.business;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import comp3350.iPuP.application.Main;
import comp3350.iPuP.application.Services;
import comp3350.iPuP.objects.Booking;
import comp3350.iPuP.objects.DaySlot;
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
        availableSpots = new ArrayList<ParkingSpot>();
    }

    public String insertParkingSpots(String user, TimeSlot timeSlot, String repetitionInfo, String address, String name, String phone, String email, double rate)
    {
        Calendar start = new GregorianCalendar();
        Calendar end = new GregorianCalendar();
        start.setTime(timeSlot.getStart());
        end.setTime(timeSlot.getEnd());

        ParkingSpot spot = new ParkingSpot(address, name, phone, email, rate);

        if (repetitionInfo != null && !repetitionInfo.equals(""))
        {
            String[] splits = repetitionInfo.split(" ");
            if (splits[0].equals("Days"))
            {
                for (int j = 0; j < Integer.parseInt(splits[2]); j++)
                {
                    spot.addDaySlot(new DaySlot(start.getTime(), end.getTime()));

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
                            spot.addDaySlot(new DaySlot(start.getTime(), end.getTime()));
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
            spot.addDaySlot(new DaySlot(start.getTime(), end.getTime()));
        }

        insertParkingSpot(user, spot);
        return null;
    }
    public String insertParkingSpot(String user, ParkingSpot newParkingSpot)
    {
        return dataAccess.insertParkingSpot(user, newParkingSpot);
    }
    public ArrayList<ParkingSpot> getAllSpots()
    {
        ArrayList<ParkingSpot> returnList=new ArrayList<ParkingSpot>();
//        returnList.addAll(dataAccess.getParkingSpots());
        return returnList;
    }

    public ArrayList<ParkingSpot> getAvailableSpots()
    {
//        List<ParkingSpot> temp = dataAccess.getParkingSpots();
        availableSpots = new ArrayList<ParkingSpot>();
//        for (int i = 0; i < temp.size(); i++)
//        {
//            if (!(temp.get(i).isBooked()))
//            {
//                availableSpots.add(temp.get(i));
//            }
//        }
        return availableSpots;
    }

    public String bookSpot(String spotID, String slotID)
    {
//        return dataAccess.setSpotToBooked(spotID, slotID);
        return null;
    }

    public void clearSpots()
    {
        dataAccess.clearSpotList();
    }

    public ArrayList<Booking> getMySpots(String name) throws ParseException
    {
        //test
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
//        String dateInString = "31-08-1982 10:00:00";
//        Date date1 = sdf.parse(dateInString);
//        dateInString = "31-08-1982 10:30:00";
//        Date date2 = sdf.parse(dateInString);
//        TimeSlot timeSlot = new TimeSlot(date1, date2);
//        DaySlot daySlot = new DaySlot(date1, date2, "abc");
//        ArrayList<ParkingSpot> list = new ArrayList<ParkingSpot>();
//        list.add(new ParkingSpot("1 sd", "sdfd", "12321", "srewr", 10.0, timeSlot));
        //return list;
        //end test
//        ArrayList<Booking> result = new ArrayList<>();
//        result.addAll(dataAccess.getSpotsOfGivenUser(name)
        return dataAccess.getSpotsOfGivenUser(name);
        //return new ArrayList<ParkingSpot>();
    }

    public boolean cancelThisSpot(String username, Long timeSlotId)
    {
        return dataAccess.setSpotToCancelled(username, timeSlotId);
    }
}
