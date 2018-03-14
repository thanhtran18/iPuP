package comp3350.iPuP.tests.persistence;

import java.lang.reflect.Array;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
	private long parkingspotCounter = 0;
	private long dayslotCounter = 0;
	private long timeslotCounter = 0;

	private DateFormatter df = new DateFormatter();

	private ArrayList<String> users;
    private ArrayList<ParkingSpot> parkingSpots;
    private ArrayList<TimeSlot> daySlots;
    private ArrayList<Long> daySlotsParkingSpotID;
    private ArrayList<TimeSlot> timeSlots;
    private ArrayList<Long> timeSlotsParkingSpotID;
    private ArrayList<Booking> bookings;
    private ArrayList<Long> bookingsParkingSpotID;


	public DataAccessStub(String dbName)
	{
        this.dbName = dbName;
	}

    @Override
	public void open(String dbPath) throws DAOException
	{
	    users = new ArrayList<>();
		parkingSpots = new ArrayList<>();
		daySlots = new ArrayList<>();
        daySlotsParkingSpotID = new ArrayList<>();
		timeSlots = new ArrayList<>();
		timeSlotsParkingSpotID = new ArrayList<>();
		bookings = new ArrayList<>();
		bookingsParkingSpotID = new ArrayList<>();

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

    @Override
	public void close()
    {
        System.out.println("Closed " +dbType +" database " +dbName);
    }


    private boolean doesParkingSpotExists(String address, String name)
    {
        int i;

        for (i = 0; i < parkingSpots.size(); i++) {
            ParkingSpot aparkingspot = parkingSpots.get(i);
            if (address.equals(aparkingspot.getAddress()) && name.equals(aparkingspot.getName()))
            {
                break;
            }
        }

        return (i >= 0);
    }


    private boolean doesUserExists(String username)
    {
        return users.contains(username);
    }

    @Override
	public long insertDaySlot(TimeSlot daySlot, long spotID) throws DAOException
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

    @Override
	public long insertTimeSlot(TimeSlot timeSlot, long daySlotID, long spotID) throws DAOException
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

    @Override
    public long insertParkingSpot(String username, ParkingSpot currentParkingSpot) throws DAOException
    {
        long spotID;

        if (!doesParkingSpotExists(currentParkingSpot.getAddress(), currentParkingSpot.getName()))
        {
            spotID = parkingspotCounter;
            currentParkingSpot.setSpotID(parkingspotCounter++);
            parkingSpots.add(currentParkingSpot);
        } else
        {
            throw new DAOException("Error in creating ParkingSpot object with SPOT_ID = "+currentParkingSpot.getSpotID()+" for Username: "+username+"!");
        }
        return spotID;
    }

    @Override
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

    @Override
    public TimeSlot getAvailableTimeForAParkingSpot(long spotID) throws DAOException {
        Date minStart = null;
        Date maxEnd = null;

        for (int i = 0; i < daySlots.size(); i++)
        {
            Long aspotid = daySlotsParkingSpotID.get(i);

            if (aspotid == spotID)
            {
                TimeSlot adayslot = daySlots.get(i);

                if (minStart == null || (adayslot.getStart()).before(minStart))
                {
                    minStart = new Date(adayslot.getStart().getTime());
                }

                if (maxEnd == null || (adayslot.getEnd()).after(maxEnd))
                {
                    maxEnd = new Date(adayslot.getEnd().getTime());
                }
            }
        }

        return new TimeSlot(minStart, maxEnd);
    }

    @Override
    public ArrayList<ParkingSpot> getParkingSpotsByAddressDate(String address, Date date) throws DAOException
    {
        ArrayList<ParkingSpot> parkingSpotsByAddrDate = new ArrayList<>();

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
                    if (parkingSpot.getSpotID() == daySlotsParkingSpotID.get(j))
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

    @Override
    public ParkingSpot getParkingSpot(long spotID) throws DAOException {
        ParkingSpot aparkingspot = parkingSpots.get((int)spotID);

        return new ParkingSpot(aparkingspot.getSpotID(), aparkingspot.getAddress(),
                aparkingspot.getName(), aparkingspot.getPhone(), aparkingspot.getEmail(),
                aparkingspot.getRate());
    }

    @Override
    public ArrayList<ParkingSpot> getHostedSpotsOfGivenUser(String username) throws DAOException
    {
        ArrayList<ParkingSpot> hostedParkingSpotsOfGivenUser = new ArrayList<>();

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

    @Override
    public void clearSpotList()
    {
        parkingSpots.clear();
    }

    @Override
	public ArrayList<Booking> getBookedSpotsOfGivenUser(String username) throws DAOException
    {
        ArrayList<Booking> bookedSpotsOfGivenUser = new ArrayList<>();

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

    @Override
    public void deleteBooking(String username, long timeSlotId)
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

    @Override
    public void modifyParkingSpot(long spotID, String address, String phone, String email, Double rate) throws DAOException {
       (parkingSpots.get((int)spotID)).modifySpot(address, phone, email, rate);
    }

    @Override
    public ArrayList<TimeSlot> getTimeSlotsForParkingSpot(long spotID) throws DAOException {
        ArrayList<TimeSlot> timeSlotsForParkingSpot = new ArrayList<>();

        for (int i = 0; i < timeSlotsParkingSpotID.size(); i++)
        {
            if (timeSlotsParkingSpotID.get(i) == spotID)
            {
                TimeSlot atimeslot = timeSlots.get(i);
                timeSlotsForParkingSpot.add(new TimeSlot(atimeslot.getStart(), atimeslot.getEnd(),
                        atimeslot.getSlotID(), atimeslot.isBooked()));
            }
        }

        Collections.sort(timeSlotsForParkingSpot, new Comparator<TimeSlot>() {
            @Override
            public int compare(TimeSlot timeSlot1, TimeSlot timeSlot2) {
                return timeSlot1.getStart().compareTo(timeSlot2.getStart());
            }
        });

        return timeSlotsForParkingSpot;
    }

    @Override
    public ArrayList<TimeSlot> getUnbookedTimeSlotsForParkingSpot(long spotID) throws DAOException {
        ArrayList<TimeSlot> unbookedTimeSlotsForParkingSpot = new ArrayList<>();

	    for (int i = 0; i < timeSlotsParkingSpotID.size(); i++)
        {
            if (timeSlotsParkingSpotID.get(i) == spotID)
            {
                TimeSlot atimeslot = timeSlots.get(i);
                if (!atimeslot.isBooked()) {
                    unbookedTimeSlotsForParkingSpot.add(new TimeSlot(atimeslot.getStart(),
                            atimeslot.getEnd(), atimeslot.getSlotID(), atimeslot.isBooked()));
                }
            }
        }

        return unbookedTimeSlotsForParkingSpot;
    }

    @Override
    public ParkingSpot getParkingSpotByID(long spotID) throws DAOException {
        ParkingSpot aparkingspot = parkingSpots.get((int)spotID);
	    return new ParkingSpot(aparkingspot.getSpotID(), aparkingspot.getAddress(),
                aparkingspot.getName(), aparkingspot.getPhone(), aparkingspot.getEmail(),
                aparkingspot.getRate());
    }

    @Override
    public boolean bookTimeSlot(String username, long timeSlotID, long spotID) throws DAOException {
        int i;

        for (i = 0; i < bookings.size(); i++) {
            Booking abooking = bookings.get(i);
            if (timeSlotID == abooking.getTimeSlotId() ||
                    username.equals(abooking.getUsername()))
            {
                break;
            }
        }

        if (!(i >= 0))
        {
            bookingsParkingSpotID.add(spotID);
            ParkingSpot parkingSpot = parkingSpots.get((int)spotID);
            TimeSlot timeSlot = timeSlots.get((int)timeSlotID);
            timeSlot.setBooked();
            bookings.add(new Booking(username, timeSlotID, parkingSpot.getAddress(),
                    timeSlot.getStart(), timeSlot.getEnd()));
            return true;
        } else
        {
            return false;
        }
    }

    @Override
    public ArrayList<TimeSlot> getDaySlots(long spotID) throws DAOException
    {
        ArrayList<TimeSlot> daySlotsList = new ArrayList<>();

        for (int i = 0; i < daySlotsParkingSpotID.size(); i++)
        {
            if (daySlotsParkingSpotID.get(i) == spotID)
            {
                TimeSlot adayslot = daySlots.get(i);
                daySlotsList.add(new TimeSlot(adayslot.getStart(), adayslot.getEnd(),
                        adayslot.getSlotID()));
            }
        }

        return daySlotsList;
    }

    @Override
    public ArrayList<TimeSlot> getTimeSlots(long spotID) throws DAOException
    {
        ArrayList<TimeSlot> timeSlotsList = new ArrayList<>();

        for (int i = 0; i < timeSlotsParkingSpotID.size(); i++)
        {
            if (timeSlotsParkingSpotID.get(i) == spotID)
            {
                TimeSlot adayslot = timeSlots.get(i);
                timeSlotsList.add(new TimeSlot(adayslot.getStart(), adayslot.getEnd(),
                        adayslot.getSlotID(), adayslot.isBooked()));
            }
        }

        return timeSlotsList;
    }

    @Override
    public boolean deleteDaySlot(long slotID) throws DAOException
    {
        return false;
    }

    @Override
    public boolean deleteTimeSlot(long slotID) throws DAOException
    {
        return false;
    }

    private void addDefaultData(String name, String address, String phone, String email,
                                double rate, Calendar calStart, Calendar calEnd)
    {
        users.add(name);

        parkingSpots.add(new ParkingSpot(parkingspotCounter, address, name, phone, email, rate));

        daySlots.add(new TimeSlot(calStart.getTime(), calEnd.getTime(), dayslotCounter++));
        daySlotsParkingSpotID.add(parkingspotCounter);

        int numSlots = (int)(calEnd.getTimeInMillis() - calStart.getTimeInMillis()) / 1000 / 60 / 30;

        for (int i = 0; i < numSlots; i++)
        {
            Date startTime = calStart.getTime();
            calStart.add(Calendar.MINUTE, 30);
            Date endTime = calStart.getTime();
            timeSlots.add(new TimeSlot(startTime, endTime, timeslotCounter++));
            timeSlotsParkingSpotID.add(parkingspotCounter);
        }

        parkingspotCounter++;
    }
}
