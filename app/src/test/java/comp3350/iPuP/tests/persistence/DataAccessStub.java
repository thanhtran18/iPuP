package comp3350.iPuP.tests.persistence;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import comp3350.iPuP.application.Main;
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
    private ArrayList<Long> timeSlotsDaySlotID;
    private ArrayList<Long> timeSlotsParkingSpotID;
    private ArrayList<Booking> bookings;
    private ArrayList<Long> bookingsParkingSpotID;

    public DataAccessStub()
    {
        this(Main.dbName);
    }

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
		timeSlotsDaySlotID = new ArrayList<>();
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

		try
        {
            address = "88 Plaza Drive";
            name = "marker";
            phone = "204-855-2342";
            email = "theBestMarker@gmail.com";
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
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 16:30:00"));
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

            address = "200 Pasedina Avenue";
            name = "Nelson Mandela";
            phone = "204-234-2555";
            email = "Nelson27@gmail.com";
            rate = 5;
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 13:30:00"));
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
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-12 02:00:00"));
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

            bookTimeSlot("marker",173, 21);
            bookTimeSlot("marker",91, 11);
            bookTimeSlot("marker",94, 13);
            bookTimeSlot("marker",145, 18);

            bookTimeSlot("Donald Trump",141, 18);
            bookTimeSlot("Donald Trump",142, 18);
            bookTimeSlot("Donald Trump",143, 18);
            bookTimeSlot("Donald Trump",144, 18);

            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-12 14:30:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-12 16:30:00"));
            daySlots.add(new TimeSlot(calStart.getTime(),calEnd.getTime(),dayslotCounter));
            daySlotsParkingSpotID.add((long)0);
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-12 14:30:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-12 15:00:00"));
            timeSlots.add(new TimeSlot(calStart.getTime(),calEnd.getTime(),timeslotCounter++));
            timeSlotsParkingSpotID.add((long)0);
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-12 15:00:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-12 15:30:00"));
            timeSlots.add(new TimeSlot(calStart.getTime(),calEnd.getTime(),timeslotCounter++));
            timeSlotsParkingSpotID.add((long)0);
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-12 15:30:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-12 16:00:00"));
            timeSlots.add(new TimeSlot(calStart.getTime(),calEnd.getTime(),timeslotCounter++));
            timeSlotsParkingSpotID.add((long)0);
            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-12 16:00:00"));
            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-12 16:30:00"));
            timeSlots.add(new TimeSlot(calStart.getTime(),calEnd.getTime(),timeslotCounter++));
            timeSlotsParkingSpotID.add((long)0);

            users.add("tester");
            users.add("tester2");

        }
        catch (ParseException e)
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
        boolean found = false;

        for (int i = 0; i < parkingSpots.size(); i++)
        {
            ParkingSpot aparkingspot = parkingSpots.get(i);
            if (address.equals(aparkingspot.getAddress()) && name.equals(aparkingspot.getName()))
            {
                found = true;
                break;
            }
        }

        return found;
    }


    private boolean doesUserExists(String username)
    {
        return users.contains(username);
    }

    @Override
	public long insertDaySlot(TimeSlot daySlot, long spotID) throws DAOException
	{
        long rtn = dayslotCounter;

        daySlot.setSlotID(dayslotCounter++);
        daySlots.add(daySlot);
        daySlotsParkingSpotID.add(spotID);

		return rtn;
	}

    @Override
	public long insertTimeSlot(TimeSlot timeSlot, long daySlotID, long spotID) throws DAOException
	{
        long rtn = timeslotCounter;

        timeSlot.setSlotID(timeslotCounter++);
        timeSlots.add(timeSlot);
        timeSlotsDaySlotID.add(daySlotID);
        timeSlotsParkingSpotID.add(spotID);

        return rtn;
	}

    @Override
    public long insertParkingSpot(String username, ParkingSpot currentParkingSpot) throws DAOException
    {
        long spotID;

        if (!doesParkingSpotExists(currentParkingSpot.getAddress(), currentParkingSpot.getName()))
        {
            spotID = parkingspotCounter++;
            parkingSpots.add(new ParkingSpot(spotID,currentParkingSpot.getAddress(),
                    currentParkingSpot.getName(),currentParkingSpot.getPhone(),
                    currentParkingSpot.getEmail(),currentParkingSpot.getRate()));
        }
        else
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
        Map<Long,TimeSlot> map = new HashMap<>();
        for (int i=0; i<daySlots.size(); i++)
        {
            map.put(daySlotsParkingSpotID.get(i), daySlots.get(i));
        }

        for (int i = 0; i < parkingSpots.size(); i++)
        {
            ParkingSpot parkingSpot = parkingSpots.get(i);
            boolean check = false;
            if (address != null && !address.equals(""))
            {
                if (parkingSpot.getAddress().contains(address))
                    check = true;
            }
            else
            {
                check = true;
            }

            if (check)
            {
                boolean found = false;

                if (date != null)
                {
                    if (map.containsKey(parkingSpot.getSpotID()))
                    {
                        TimeSlot daySlot = map.get(parkingSpot.getSpotID());
                        try
                        {
                            if (df.getSqlDateFormat().parse(df.getSqlDateFormat().format(daySlot.getStart())).compareTo(date) <= 0 &&
                                    date.compareTo(df.getSqlDateFormat().parse(df.getSqlDateFormat().format(daySlot.getEnd()))) <= 0) {
                                found = true;
                            }
                        }
                        catch (ParseException pe)
                        {
                            throw new DAOException("Error in comparing given date between start and end dates of a TimeSlot!");
                        }
                    }
                }
                else
                {
                    found = true;
                }

                try
                {
                    if (found)
                    {
                        parkingSpotsByAddrDate.add(new ParkingSpot(parkingSpot.getSpotID(),
                                parkingSpot.getAddress(), parkingSpot.getName(), parkingSpot.getPhone(),
                                parkingSpot.getEmail(), parkingSpot.getRate()));
                    }
                }
                catch (Exception e)
                {
                    throw new DAOException("Error in getting ParkingSpots ordered by Date: "+df.getSqlDateFormat().format(date)+"!",e);
                }
            }
        }

        Collections.sort( parkingSpotsByAddrDate, new Comparator<ParkingSpot>()
        {
            @Override
            public int compare( ParkingSpot p1, ParkingSpot p2 )
            {
                return (p1.getAddress().compareTo(p2.getAddress()));
            }
        } );

        return parkingSpotsByAddrDate;
    }

    @Override
    public ParkingSpot getParkingSpot(long spotID) throws DAOException
    {
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
                }
                catch (Exception e)
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
        users.clear();
        parkingSpots.clear();
        daySlots.clear();
        daySlotsParkingSpotID.clear();
        timeSlots.clear();
        timeSlotsDaySlotID.clear();
        timeSlotsParkingSpotID.clear();
        bookings.clear();
        bookingsParkingSpotID.clear();
        parkingspotCounter = 0;
        dayslotCounter = 0;
        timeslotCounter = 0;
    }

    @Override
	public ArrayList<Booking> getBookedSpotsOfGivenUser(String username) throws DAOException
    {
        ArrayList<Booking> bookedSpotsOfGivenUser = new ArrayList<>();

        for(int i = 0; i < bookings.size(); i++)
        {
            Booking booking = bookings.get(i);
            if ((booking.getName()).equals(username))
            {
                try
                {
                    bookedSpotsOfGivenUser.add(new Booking(booking.getName(), booking.getTimeSlotId(),
                            booking.getAddress(), booking.getStart(), booking.getEnd()));
                }
                catch (Exception e)
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
            if ((booking.getName()).equals(username) && (booking.getTimeSlotId()) == timeSlotId)
            {
                bookings.remove(i);
            }
        }
    }

    @Override
    public void modifyParkingSpot(long spotID, String address, String phone, String email, Double rate, double latitude, double longitude) throws DAOException
    {
       (parkingSpots.get((int)spotID)).modifySpot(address, phone, email, rate);
    }

    @Override
    public ArrayList<TimeSlot> getUnbookedTimeSlotsForParkingSpot(long spotID) throws DAOException
    {
        ArrayList<TimeSlot> unbookedTimeSlotsForParkingSpot = new ArrayList<>();

	    for (int i = 0; i < timeSlotsParkingSpotID.size(); i++)
        {
            if (timeSlotsParkingSpotID.get(i) == spotID)
            {
                TimeSlot atimeslot = timeSlots.get(i);
                if (!atimeslot.getIsBooked())
                {
                    unbookedTimeSlotsForParkingSpot.add(new TimeSlot(atimeslot.getStart(),
                            atimeslot.getEnd(), atimeslot.getSlotID(), atimeslot.getIsBooked()));
                }
            }
        }

        return unbookedTimeSlotsForParkingSpot;
    }

    @Override
    public ParkingSpot getParkingSpotByID(long spotID) throws DAOException {
        if((int)spotID>parkingSpots.size() || (int)spotID<0){
            return null;
        }
        ParkingSpot aparkingspot = parkingSpots.get((int)spotID);
	    return new ParkingSpot(aparkingspot.getSpotID(), aparkingspot.getAddress(),
                aparkingspot.getName(), aparkingspot.getPhone(), aparkingspot.getEmail(),
                aparkingspot.getRate());
    }

    @Override
    public boolean bookTimeSlot(String username, long timeSlotID, long spotID) throws DAOException {

        boolean found = false;
        if(spotID<0 || spotID >= parkingSpots.size()){
            return found;
        }

        for (int i = 0; i < bookings.size(); i++)
        {
            Booking abooking = bookings.get(i);
            if (timeSlotID == abooking.getTimeSlotId())
            {
                found = true;
                break;
            }
        }

        if (!found)
        {
            bookingsParkingSpotID.add(spotID);
            ParkingSpot parkingSpot = parkingSpots.get((int)spotID);
            TimeSlot timeSlot = timeSlots.get((int)timeSlotID);
            timeSlot.setIsBooked(true);
            try
            {
                bookings.add(new Booking(username, timeSlotID, parkingSpot.getAddress(),
                        timeSlot.getStart(), timeSlot.getEnd()));
            }
            catch (Exception e)
            {
                System.out.print(e.getMessage());
            }

            return true;
        }
        else
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
    public ArrayList<TimeSlot> getTimeSlots(long daySlotID) throws DAOException
    {
        ArrayList<TimeSlot> timeSlotsList = new ArrayList<>();

        for (int i = 0; i < timeSlotsDaySlotID.size(); i++)
        {
            if (timeSlotsDaySlotID.get(i) == daySlotID)
            {
                TimeSlot atimeslot = timeSlots.get(i);
                timeSlotsList.add(new TimeSlot(atimeslot.getStart(), atimeslot.getEnd(),
                        atimeslot.getSlotID(), atimeslot.getIsBooked()));
            }
        }

        return timeSlotsList;
    }

    @Override
    public boolean deleteDaySlot(long daySlotID) throws DAOException
    {
        long spotID = -1;
        boolean exitOnReturn = false;

        for (int i = timeSlots.size() - 1; i >=0; i--)
        {
            if (timeSlotsDaySlotID.get(i) == daySlotID)
            {
                timeSlots.remove(i);
                timeSlotsDaySlotID.remove(i);
                timeSlotsParkingSpotID.remove(i);
            }
        }

        boolean removed = false;
        for (int i = 0; i < daySlots.size() && !removed; i++)
        {
            if (daySlots.get(i).getSlotID() == daySlotID)
            {
                spotID = daySlotsParkingSpotID.get(i);
                daySlots.remove(i);
                daySlotsParkingSpotID.remove(i);
                removed = true;
            }
        }

        boolean empty = true;
        for (int i = 0; i < daySlots.size(); i++)
        {
            if (daySlotsParkingSpotID.get(i) == spotID)
                empty = false;
        }

        if (empty)
        {
            removed = false;
            for (int i = 0; i < parkingSpots.size() && !removed; i++)
            {
                if (parkingSpots.get(i).getSpotID() == spotID)
                {
                    parkingSpots.remove(i);
                    removed = true;
                }
            }
            exitOnReturn = true;
        }

        return exitOnReturn;
    }

    @Override
    public boolean deleteTimeSlot(long timeSlotID) throws DAOException
    {
        boolean exitOnReturn = false;
        long daySlotID = -1;

        boolean removed = false;
        for (int i = 0; i < timeSlots.size() && !removed; i++)
        {
            if (timeSlotID == timeSlots.get(i).getSlotID())
            {
                daySlotID = timeSlotsDaySlotID.get(i);
                timeSlots.remove(i);
                timeSlotsDaySlotID.remove(i);
                timeSlotsParkingSpotID.remove(i);
                removed = true;
            }
        }

        boolean empty = true;
        for (int i = 0; i < timeSlots.size(); i++)
        {
            if (daySlotID == timeSlotsDaySlotID.get(i))
            {
                empty = false;
            }
        }

        if (empty)
        {
            deleteDaySlot(daySlotID);
            exitOnReturn = true;
        }

        return exitOnReturn;
    }

    private void addDefaultData(String name, String address, String phone, String email,
                                double rate, Calendar calStart, Calendar calEnd)
    {
        users.add(name);

        parkingSpots.add(new ParkingSpot(parkingspotCounter, address, name, phone, email, rate));

        daySlots.add(new TimeSlot(calStart.getTime(), calEnd.getTime(), dayslotCounter));
        daySlotsParkingSpotID.add(parkingspotCounter);

        int numSlots = (int)(calEnd.getTimeInMillis() - calStart.getTimeInMillis()) / 1000 / 60 / 30;

        for (int i = 0; i < numSlots; i++)
        {
            Date startTime = calStart.getTime();
            calStart.add(Calendar.MINUTE, 30);
            Date endTime = calStart.getTime();
            timeSlots.add(new TimeSlot(startTime, endTime, timeslotCounter++));
            timeSlotsParkingSpotID.add(parkingspotCounter);
            timeSlotsDaySlotID.add(dayslotCounter);
        }
        dayslotCounter++;
        parkingspotCounter++;
    }

    public ArrayList<ParkingSpot> getAllParkingSpots()
    {
        return parkingSpots;
    }
}
