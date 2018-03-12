package comp3350.iPuP.tests.persistence;

import java.lang.reflect.Array;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import comp3350.iPuP.objects.Booking;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.objects.DateFormatter;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.TimeSlot;
import comp3350.iPuP.persistence.DataAccess;

public class DataAccessStub implements DataAccess
{
	private String dbName;
	private String dbType = "stub";
	private long dayslotCounter = 0;
	private long timeslotCounter = 0;

	private DateFormatter df = new DateFormatter();

	private ArrayList<String> users;
    private ArrayList<ParkingSpot> parkingSpots;
    private ArrayList<TimeSlot> daySlots;
    private ArrayList<String> daySlotsParkingSpotID;
    private ArrayList<TimeSlot> timeSlots;
    private ArrayList<String> timeSlotsParkingSpotID;
    private ArrayList<Booking> bookings;


	public DataAccessStub(String dbName)
	{
        this.dbName = dbName;
	}


	public void open(String dbPath) throws DAOException
	{
	    users = new ArrayList<String>();
		parkingSpots = new ArrayList<ParkingSpot>();
		daySlots = new ArrayList<TimeSlot>();
        daySlotsParkingSpotID = new ArrayList<String>();
		timeSlots = new ArrayList<TimeSlot>();
		timeSlotsParkingSpotID = new ArrayList<String>();
		bookings = new ArrayList<Booking>();

		String address;
		String name;
		String phone;
		String email;
		double rate;
		Calendar calStart = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();

		try {
            address = "88 Plaza Drive";
            name = "Rodney N-chris";
            phone = "204-855-2342";
            email = "poor&Homeless@gmail.com";
            rate = 2;
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 12:30:00"));
            addDefaultData(name,address,phone,email,rate,calStart,calEnd);

            address = "2 Chancellor Drive";
            name = "Scott Gordon";
            phone = "204-122-1234";
            email = "scottfils@hotmail.ca";
            rate = 4.50;
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 14:30:00"));
            addDefaultData(name,address,phone,email,rate,calStart,calEnd);

            address = "30 Chancellor Drive";
            name = "Roberto Nesta Marley";
            phone = "204-577-3422";
            email = "rastaLikebob@gmail.com";
            rate = 0.10;
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 11:30:00"));
            addDefaultData(name,address,phone,email,rate,calStart,calEnd);

            address = "60 Main Street";
            name = "Avocado Stevenson";
            phone = "601-122-1211";
            email = "avocadoisgood@gmail.com";
            rate = 5.25;
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 11:00:00"));
            addDefaultData(name,address,phone,email,rate,calStart,calEnd);

            address = "566 Pasedina avenue";
            name = "Brian Cambell";
            phone = "204-419-8819";
            email = "Brian1989@gmail.com";
            rate = 4;
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 11:00:00"));
            addDefaultData(name,address,phone,email,rate,calStart,calEnd);

            address = "1 Kings Drive";
            name = "Jenifer Aniston";
            phone = "604-253-1111";
            email = "JeniferAniston@hotmail.ca";
            rate = 7;
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 20:00:00"));
            addDefaultData(name,address,phone,email,rate,calStart,calEnd);

            address = "20 Silverston Avenue";
            name = "Christopher Turk";
            phone = "204-236-2322";
            email = "chrisTurk27@gmail.com";
            rate = 5;
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 13:30:00"));
            addDefaultData(name,address,phone,email,rate,calStart,calEnd);

            address = "20 Kings Drive";
            name = "Tom Brady";
            phone = "877-377-4234";
            email = "theGoat@gmail.com";
            rate = 10;
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 15:00:00"));
            addDefaultData(name,address,phone,email,rate,calStart,calEnd);

            address = "1 Pembina Hwy";
            name = "George H. Bush";
            phone = "204-927-9277";
            email = "myFamilyLikesWar@gmail.com";
            rate = 10;
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 14:30:00"));
            addDefaultData(name,address,phone,email,rate,calStart,calEnd);

            address = "100 St. Mary's Rd";
            name = "Watson k. Smith";
            phone = "204-245-3433";
            email = "watsonK@gmail.com";
            rate = 7;
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 16:00:00"));
            addDefaultData(name,address,phone,email,rate,calStart,calEnd);

            address = "1691 Pembina Hwy";
            name = "Victory Iyakoregha";
            phone = "204-888-9292";
            email = "Ivic565@gmail.com";
            rate = 5;
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 12:30:00"));
            addDefaultData(name,address,phone,email,rate,calStart,calEnd);

            address = "1338 Chancellor Drive";
            name = "Micheal Douglas";
            phone = "204-123-1234";
            email = "theblondegirl22@hotmail.ca";
            rate = 4.50;
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 14:30:00"));
            addDefaultData(name,address,phone,email,rate,calStart,calEnd);

            address = "1122 Chancellor Drive";
            name = "Kelly Cook";
            phone = "204-566-7122";
            email = "cookk@gmail.com";
            rate = 4;
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 11:30:00"));
            addDefaultData(name,address,phone,email,rate,calStart,calEnd);

            address = "91 Dalhousie Drive";
            name = "Madison Fishburne";
            phone = "204-345-4353";
            email = "madifish101@gmail.com";
            rate = 5.25;
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 11:00:00"));
            addDefaultData(name,address,phone,email,rate,calStart,calEnd);

            address = "565 Pasedina Avenue";
            name = "Ronald Regan";
            phone = "204-419-1419";
            email = "theDevil666@gmail.com";
            rate = 100;
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 16:30:00"));
            addDefaultData(name,address,phone,email,rate,calStart,calEnd);

            address = "1334 Pembina Hwy";
            name = "Marilyn Monroe";
            phone = "604-253-3424";
            email = "iammonroe@hotmail.ca";
            rate = 7;
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 20:00:00"));
            addDefaultData(name,address,phone,email,rate,calStart,calEnd);

            address = "Brady Road Landfill";
            name = "Donald Trump";
            phone = "877-311-4974";
            email = "lolattheUSA@gmail.com";
            rate = 100;
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 15:00:00"));
            addDefaultData(name,address,phone,email,rate,calStart,calEnd);

            address = "1 Pembina Hwy";
            name = "George W. Bush";
            phone = "204-927-9277";
            email = "myFamilyLikesWar@gmail.com";
            rate = 10;
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 14:30:00"));
            addDefaultData(name,address,phone,email,rate,calStart,calEnd);

            address = "29 St. Mary's Rd";
            name = "Mary Watson";
            phone = "204-242-2255";
            email = "sherlock101@gmail.com";
            rate = 4.50;
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 20:30:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 02:00:00"));
            addDefaultData(name,address,phone,email,rate,calStart,calEnd);

            address = "1000 St. Mary's Rd";
            name = "Philipe Coutinho";
            phone = "204-124-2222";
            email = "iAmAsnake10@hotmail.ca";
            rate = 0.10;
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 16:00:00"));
            addDefaultData(name,address,phone,email,rate,calStart,calEnd);

            address = "1000 St. Mary's Rd";
            name = "Anne Coutinho";
            phone = "204-124-2222";
            email = "iAmAlsoAsnake10@hotmail.ca";
            rate = 0.20;
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 17:30:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 19:00:00"));
            addDefaultData(name,address,phone,email,rate,calStart,calEnd);

        } catch (ParseException e)
        {
            throw new DAOException("Failed to open " + dbType +" database " + dbName + "!",e);
        }

        System.out.println("Opened " +dbType +" database " +dbName);
	}


	public void close()
    {
        System.out.println("Closed " +dbType +" database " +dbName);
    }


    private boolean doesParkingSpotExists(String spotID)
    {
        return daySlotsParkingSpotID.contains(spotID);
    }


    private boolean doesUserExists(String username)
    {
        return users.contains(username);
    }


	public long insertDaySlot(TimeSlot daySlot, String spotID) throws DAOException
	{
        int i;
        long rtn = dayslotCounter;

        for (i = 0; i < daySlots.size(); i++) {
            if ((daySlot.getSlotID()).equals(daySlots.get(i).getSlotID()))
            {
                break;
            }
        }

        if (!(i >= 0))
        {
            daySlot.setSlotID(dayslotCounter++);
            daySlots.add(daySlot);
            daySlotsParkingSpotID.add(spotID);
        } else
        {
            throw new DAOException("Error in inserting DaySlot object with spotID = "+spotID+"!");
        }

		return rtn;
	}


	public long insertTimeSlot(TimeSlot timeSlot, long daySlotID, String spotID) throws DAOException
	{
        int i;
        long rtn = timeslotCounter;

        for (i = 0; i < timeSlots.size(); i++) {
            TimeSlot atimespot = timeSlots.get(i);
            if ((timeSlot.getSlotID()).equals(atimespot.getSlotID()) ||
                    ((timeSlot.getStart()).equals(atimespot.getStart()) &&
                            (timeSlot.getEnd()).equals(atimespot.getEnd())))
            {
                break;
            }
        }

        if (!(i >= 0))
        {
            timeSlot.setSlotID(timeslotCounter++);
            timeSlots.add(timeSlot);
            timeSlotsParkingSpotID.add(spotID);
        } else
        {
            throw new DAOException("Error in inserting TimeSlot object with dayslotID = "+daySlotID+" and slotID = "+spotID+"!");
        }

        return rtn;
	}


    public void insertParkingSpot(String username, ParkingSpot currentParkingSpot) throws DAOException
    {
        int i;

        for (i = 0; i < parkingSpots.size(); i++) {
            ParkingSpot aparkingspot = parkingSpots.get(i);
            if ((currentParkingSpot.getSpotID()).equals(aparkingspot.getSpotID()) ||
                    (currentParkingSpot.getName()).equals(aparkingspot.getName()))
            {
                break;
            }
        }

        if (!(i >= 0))
        {
            parkingSpots.add(currentParkingSpot);
        } else
        {
            throw new DAOException("Error in creating ParkingSpot object with SPOT_ID = "+currentParkingSpot.getSpotID()+" for Username: "+username+"!");
        }
    }


	public boolean insertUser(String username)
	{
		boolean result = false;

		if (!doesUserExists(username))
        {
            users.add(username);
            result = true;
        }

		return result;
	}


	//TODO: need this?
//	public ArrayList<TimeSlot> getDaySlotsForAParkingSpot(String slotID)
//    {
//        return null;
//    }


    public ArrayList<ParkingSpot> getParkingSpotsByAddressDate(String address, Date date) throws DAOException
    {
        ArrayList<ParkingSpot> parkingSpotsByAddrDate = new ArrayList<ParkingSpot>();

        for (int i = 0; i < parkingSpots.size(); i++)
        {
            ParkingSpot parkingSpot = parkingSpots.get(i);
            boolean check = false;
            if (address != null && address.equals(""))
            {
                if (parkingSpot.getAddress().contains(address))
                    check = true;
            } else
            {
                check = true;
            }

            if (check)
            {
                boolean found = false;
                for (int j = 0; j < daySlotsParkingSpotID.size(); j++)
                {
                    if ((parkingSpot.getSpotID()).equals(daySlotsParkingSpotID.get(j)))
                    {
                        TimeSlot daySlot = daySlots.get(j);

                        if (date.after(daySlot.getStart()) && date.before(daySlot.getEnd()))
                        {
                            found = true;
                        }
                    }

                    if (found)
                    {
                        try
                        {
                            parkingSpotsByAddrDate.add(new ParkingSpot(parkingSpot.getSpotID(),
                                    parkingSpot.getAddress(), parkingSpot.getName(), parkingSpot.getPhone(),
                                    parkingSpot.getEmail(), parkingSpot.getRate()));
                        } catch (Exception e)
                        {
                            throw new DAOException("Error in getting ParkingSpots ordered by Date: "+df.getSqlDateFormat().format(date)+"!",e);
                        }
                        break;
                    }
                }
            }
        }

        return parkingSpotsByAddrDate;
    }


    public ArrayList<ParkingSpot> getHostedSpotsOfGivenUser(String username) throws DAOException
    {
        ArrayList<ParkingSpot> hostedParkingSpotsOfGivenUser = new ArrayList<ParkingSpot>();

        for(int i = 0; i < parkingSpots.size(); i++)
        {
            ParkingSpot parkingSpot = parkingSpots.get(i);
            if ((parkingSpot.getName()).equals(username))
            {
                try
                {
                    hostedParkingSpotsOfGivenUser.add(new ParkingSpot(parkingSpot.getSpotID(),
                            parkingSpot.getAddress(), parkingSpot.getName(), parkingSpot.getPhone(),
                            parkingSpot.getEmail(), parkingSpot.getRate()));
                } catch (Exception e)
                {
                    throw new DAOException("Error in getting hosted Parking Spots by "+username+"!",e);
                }
            }
        }

        return hostedParkingSpotsOfGivenUser;
    }


    public void clearSpotList()
    {
        parkingSpots.clear();
    }


	public ArrayList<Booking> getBookedSpotsOfGivenUser(String username) throws DAOException
    {
        ArrayList<Booking> bookedSpotsOfGivenUser = new ArrayList<Booking>();
        ParkingSpot parkingSpot;

        for(int i = 0; i < bookings.size(); i++)
        {
            Booking booking = bookings.get(i);
            if ((booking.getUsername()).equals(username))
            {
                try
                {
                    bookedSpotsOfGivenUser.add(new Booking(booking.getUsername(), booking.getTimeSlotId(),
                            booking.getAddress(), booking.getStart(), booking.getEnd()));
                } catch (Exception e)
                {
                    throw new DAOException("Error in getting bookings list for User: "+username+"!",e);
                }
            }
        }

        return bookedSpotsOfGivenUser;
    }


    public void setBookedSpotToDeleted(String username, long timeSlotId)
    {
        for(int i = 0; i < bookings.size(); i++)
        {
            Booking booking = bookings.get(i);
            if ((booking.getUsername()).equals(username) && (booking.getTimeSlotId()) == timeSlotId)
            {
                bookings.remove(i);
            }
        }
    }


//    private boolean addABooking (Booking booking)
//    {
//        int i;
//
//        for (i = 0; i < bookings.size(); i++) {
//            Booking abooking = bookings.get(i);
//            if ((booking.getSlotID()).equals(abooking.getSlotID()) ||
//                    ((booking.gets()).equals(abooking.getName()) &&
//                            ()))
//            {
//                break;
//            }
//        }
//
//        if (!(i >= 0))
//        {
//            bookings.add(booking);
//            return true;
//        } else
//        {
//            return false;
//        }
//    }


    private void addDefaultData(String name, String address, String phone, String email,
                                double rate, Calendar calStart, Calendar calEnd)
    {
        users.add(name);

        parkingSpots.add(new ParkingSpot(address, name, phone, email, rate));

        daySlots.add(new TimeSlot(calStart.getTime(), calEnd.getTime(), dayslotCounter++));
        daySlotsParkingSpotID.add(address+name);

        int numSlots = (int)(calEnd.getTimeInMillis() - calStart.getTimeInMillis()) / 1000 / 60 / 30;

        for (int i = 0; i < numSlots; i++)
        {
            Date startTime = calStart.getTime();
            calStart.add(Calendar.MINUTE, 30);
            Date endTime = calStart.getTime();
            timeSlots.add(new TimeSlot(startTime, endTime, timeslotCounter++));
            timeSlotsParkingSpotID.add(address+name);
        }
    }
}
