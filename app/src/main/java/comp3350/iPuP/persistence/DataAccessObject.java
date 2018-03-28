package comp3350.iPuP.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import comp3350.iPuP.objects.Booking;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.objects.DateFormatter;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.TimeSlot;

public class DataAccessObject implements DataAccess
{
	private PreparedStatement pstmt, pstmt2, pstmt3;
	private Statement stmt;
	private Connection con;
	private ResultSet rss, rsp;

	private String dbName;
	private String dbType;

    private DateFormatter df = new DateFormatter();

    private ArrayList<Booking> bookingSpotsOfAUser;

	private ArrayList<ParkingSpot> parkingSpots;
    private ArrayList<ParkingSpot> parkingSpotsOfAUser;

	private String cmdString;
	private int updateCount;
	private static String EOF = "  ";


	public DataAccessObject(String dbName)
	{
		this.dbName = dbName;
	}

    @Override
	public void open(String dbPath) throws DAOException
	{
		String url;
		try
		{
			dbType = "HSQL";
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			url = "jdbc:hsqldb:file:" + dbPath;
			con = DriverManager.getConnection(url, "iPuP", "iPuP");
			stmt = con.createStatement();
		}
		catch (Exception e)
		{
			processSQLError(e);
			throw new DAOException("Error in opening " + dbType + " database " + dbPath + "!", e);
		}
		System.out.println("Opened " + dbType + " database " + dbPath);
	}

    @Override
	public void close() throws DAOException
	{
		try
		{
			cmdString = "shutdown compact";
			rss = stmt.executeQuery(cmdString);
			con.close();
		}
		catch (Exception e)
		{
			processSQLError(e);
			throw new DAOException("Error in closing " + dbType + " database " + dbName + "!", e);
		}
		System.out.println("Closed " + dbType + " database " + dbName);
	}


	private boolean doesParkingSpotExists(String address, String name) throws DAOException
    {
        boolean result = false;

        try
        {
            cmdString = "SELECT * FROM PARKINGSPOTS WHERE ADDRESS = ? AND NAME = ?";
            pstmt = con.prepareStatement(cmdString);
            pstmt.setString(1, address);
            pstmt.setString(2, name);

            rsp = pstmt.executeQuery();

            if (rsp.next())
            {
                result = true;
            }

            rsp.close();
        }
        catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in checking if ParkingSpot object with address = " + address + " and name = " + name + " exists!", sqle);
        }

        return result;
    }


	private boolean doesUserExists(String username) throws DAOException
    {
        boolean result = false;

        try
        {
            cmdString = "SELECT * FROM USERS WHERE USERNAME = ?";
            pstmt = con.prepareStatement(cmdString);
            pstmt.setString(1, username);

            rsp = pstmt.executeQuery();

            if (rsp.next())
            {
                result = true;
            }
        } catch (Exception e)
        {
            processSQLError(e);
            throw new DAOException("Error in checking if User (" + username + ") exists!", e);
        }

        return result;
    }

    @Override
	public long insertDaySlot(TimeSlot daySlot, long spotID) throws DAOException
    {
        long daySlotID;

        try
        {
            cmdString = "INSERT INTO DAYSLOTS (SPOT_ID,STARTDAYTIME,ENDDAYTIME) " +
                        "VALUES (?,?,?)";
            pstmt2 = con.prepareStatement(cmdString);
            pstmt2.setLong(1, spotID);
            pstmt2.setString(2, df.getSqlDateTimeFormat().format(daySlot.getStart()));
            pstmt2.setString(3, df.getSqlDateTimeFormat().format(daySlot.getEnd()));
            updateCount = pstmt2.executeUpdate();
            checkWarning(pstmt, updateCount);
        }
        catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in inserting DaySlot object with spotID = " + spotID + "!", sqle);
        }

        try
        {
            cmdString = "CALL IDENTITY()";
            rss = stmt.executeQuery(cmdString);

            if (rss.next())
            {
                daySlotID = rss.getLong(1);
            }
            else
            {
                throw new DAOException("Could not retrieve last auto generated DaySlot ID!");
            }
        }
        catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Could not retrieve last auto generated DaySlot ID!", sqle);
        }

        return daySlotID;
    }

    @Override
    public long insertTimeSlot(TimeSlot timeSlot, long daySlotID, long spotID) throws DAOException
    {
        long timeSlotID;

        try
        {
            cmdString = "INSERT INTO TIMESLOTS (SPOT_ID,DAYSLOT_ID,STARTDATETIME,ENDDATETIME) " +
                        "VALUES (?,?,?,?)";
            pstmt3 = con.prepareStatement(cmdString);
            pstmt3.setLong(1, spotID);
            pstmt3.setLong(2, daySlotID);
            pstmt3.setString(3, df.getSqlDateTimeFormat().format(timeSlot.getStart()));
            pstmt3.setString(4, df.getSqlDateTimeFormat().format(timeSlot.getEnd()));

            updateCount = pstmt3.executeUpdate();
            checkWarning(pstmt, updateCount);
        }
        catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in inserting TimeSlot object with dayslotID = " + daySlotID + " and spotID = " + spotID + "!", sqle);
        }

        try
        {
            cmdString = "CALL IDENTITY()";
            rss = stmt.executeQuery(cmdString);

            if (rss.next())
            {
                timeSlotID = rss.getLong(1);
            }
            else
            {
                throw new DAOException("Could not retrieve last auto generated TimeSlot ID!");
            }
        }
        catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Could not retrieve last auto generated TimeSlot ID!", sqle);
        }

        return timeSlotID;
    }

    @Override
	public long insertParkingSpot(String username, ParkingSpot currentParkingSpot) throws DAOException
    {
        long spotID;

        try
        {
            if (!doesParkingSpotExists(currentParkingSpot.getAddress(),currentParkingSpot.getName()))
            {
                cmdString = "INSERT INTO PARKINGSPOTS (USERNAME,NAME,ADDRESS,PHONE,EMAIL,RATE,LATITUDE,LONGITUDE) VALUES(?,?,?,?,?,?,?,?)";
                pstmt = con.prepareStatement(cmdString);
                pstmt.setString(1, username);
                pstmt.setString(2, currentParkingSpot.getName());
                pstmt.setString(3, currentParkingSpot.getAddress());
                pstmt.setString(4, currentParkingSpot.getPhone());
                pstmt.setString(5, currentParkingSpot.getEmail());
                pstmt.setDouble(6, currentParkingSpot.getRate());
                pstmt.setDouble(7, currentParkingSpot.getLatitude());
                pstmt.setDouble(8, currentParkingSpot.getLongitude());

                updateCount = pstmt.executeUpdate();
                checkWarning(pstmt, updateCount);
            }
        }
        catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in creating ParkingSpot object with SPOT_ID = " + currentParkingSpot.getSpotID() + " for Username: " + username + "!", sqle);
        }

        try
        {
            cmdString = "CALL IDENTITY()";
            rss = stmt.executeQuery(cmdString);

            if (rss.next())
            {
                spotID = rss.getLong(1);
            }
            else
            {
                throw new DAOException("Could not retrieve last auto generated Spot ID!");
            }
        }
        catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Could not retrieve last auto generated Spot ID!", sqle);
        }

        return spotID;
    }

    @Override
    public boolean insertUser(String username) throws DAOException
    {
        boolean result = false;

        if (!doesUserExists(username))
        {
            try
            {
                cmdString = "INSERT INTO USERS VALUES(?)";
                pstmt = con.prepareStatement(cmdString);
                pstmt.setString(1, username);

                updateCount = pstmt.executeUpdate();
                checkWarning(pstmt, updateCount);

                result = true;

            }
            catch (SQLException sqle)
            {
                processSQLError(sqle);
                throw new DAOException("Error in creating new user with USERNAME = " + username + "!", sqle);
            }
        }

        return result;
    }

    @Override
    public TimeSlot getAvailableTimeForAParkingSpot(long spotID) throws DAOException
    {
        Calendar calStart = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        Date start, end;
        TimeSlot daySlot = null;

        try
        {
            cmdString = "SELECT MIN(STARTDAYTIME) STARTTIME, MAX(ENDDAYTIME) ENDTIME FROM DAYSLOTS " +
                        "WHERE SPOT_ID = ? AND DELETED = FALSE";
            pstmt = con.prepareStatement(cmdString);
            pstmt.setLong(1, spotID);
            rsp = pstmt.executeQuery();

            if (rsp.next())
            {
                start = rsp.getTimestamp("STARTTIME");
                end = rsp.getTimestamp("ENDTIME");

                calStart.setTime(start);
                calEnd.setTime(end);

                daySlot = new TimeSlot(calStart.getTime(), calEnd.getTime());
            }

        }
        catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in retrieving the available time for spotID = " + spotID + "!", sqle);
        }

        return daySlot;
    }

    @Override
    public ArrayList<ParkingSpot>
    getParkingSpotsByAddressDate(String address, Date date) throws DAOException
    {
        parkingSpots = new ArrayList<>();

        try
        {
            cmdString = "SELECT * FROM PARKINGSPOTS P WHERE LCASE(P.ADDRESS) LIKE ? " +
                        "AND EXISTS (SELECT * FROM DAYSLOTS D WHERE D.SPOT_ID = P.SPOT_ID " +
                        "AND ? BETWEEN CAST(D.STARTDAYTIME AS DATE) AND CAST(D.ENDDAYTIME AS DATE)) " +
                        "ORDER BY LCASE(P.ADDRESS)";
            pstmt = con.prepareStatement(cmdString);

            if (address == null || address.trim().length() == 0 )
            {
                pstmt.setString(1,"%");
            }
            else
            {
                pstmt.setString(1,"%" + address.toLowerCase() + "%");
            }
            pstmt.setString(2, df.getSqlDateFormat().format(date));

            rss = pstmt.executeQuery();

            getParkingSpots(rss, parkingSpots);

            rss.close();
        }
        catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in getting ParkingSpots ordered by Date: " + df.getSqlDateFormat().format(date) + "!", sqle);
        }

        return parkingSpots;
    }

    @Override
    public ArrayList<ParkingSpot> getParkingSpotsByTime(Date time) throws DAOException
    {
        parkingSpots = new ArrayList<>();

        try
        {
            Calendar c = Calendar.getInstance();
            c.setTime(time);
            c.add(Calendar.MINUTE, 15);
            cmdString = "SELECT * FROM PARKINGSPOTS P WHERE " +
                    "EXISTS (SELECT * FROM TIMESLOTS T WHERE T.SPOT_ID = P.SPOT_ID " +
                    "AND ? BETWEEN T.STARTDATETIME AND T.ENDDATETIME )" /*+
                    "AND EXISTS (SELECT * FROM BOOKINGS B WHERE B.TIMESLOT_ID=T.TIMESLOT_ID)) " +
                    "AND P.LATITUDE!=0E0 AND P.LONGITUDE!=0E0"*/;
            pstmt = con.prepareStatement(cmdString);

            pstmt.setString(1, df.getSqlDateTimeFormat().format(c.getTime()));

            rss = pstmt.executeQuery();

            getParkingSpots(rss, parkingSpots);

            rss.close();
        }
        catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in getting ParkingSpots for timeslots: " + df.getSqlDateFormat().format(time) + "!", sqle);
        }

        return parkingSpots;
    }

    @Override
    public ParkingSpot getParkingSpot(long spotID) throws DAOException
    {
        try
        {
            cmdString = "SELECT * FROM PARKINGSPOTS P WHERE P.SPOT_ID = ? ";
            pstmt = con.prepareStatement(cmdString);

            pstmt.setLong(1, spotID);

            rss = pstmt.executeQuery();
        }
        catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in getting ParkingSpot!", sqle);
        }

        Double rate, latitude, longitude;
        ParkingSpot ps;
        String name, addr, phone, email;

        try
        {
            rss.next();
            name = rss.getString("NAME");
            addr = rss.getString("ADDRESS");
            phone = rss.getString("PHONE");
            email = rss.getString("EMAIL");
            rate = rss.getDouble("RATE");
            latitude = rss.getDouble("LATITUDE");
            longitude = rss.getDouble("LONGITUDE");

            ps = new ParkingSpot(spotID, addr, name, phone, email, rate, latitude, longitude);
            rss.close();
        }
        catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in ParkingSpot object!",sqle);
        }
        catch (Exception e)
        {
            processSQLError(e);
            throw new DAOException("Error in creating a new ParkingSpot object!",e);
        }
        return ps;
    }

    private void getParkingSpots(ResultSet rs, ArrayList<ParkingSpot> parkingSpots) throws DAOException
    {
        Double rate, latitude, longitude;
        long spotID;
        ParkingSpot ps;
        String name, addr, phone, email;

        try
        {
            while (rs.next())
            {
                spotID = rs.getLong("SPOT_ID");
                name = rs.getString("NAME");
                addr = rs.getString("ADDRESS");
                phone = rs.getString("PHONE");
                email = rs.getString("EMAIL");
                rate = rs.getDouble("RATE");
                latitude = rs.getDouble("LATITUDE");
                longitude = rs.getDouble("LONGITUDE");

                ps = new ParkingSpot(spotID, addr, name, phone, email, rate, latitude, longitude);
                parkingSpots.add(ps);
            }
        }
        catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in getting list of ParkingSpot objects!",sqle);
        }
        catch (Exception e)
        {
            processSQLError(e);
            throw new DAOException("Error in creating a new ParkingSpot object!",e);
        }
    }

    @Override
    public ArrayList<ParkingSpot> getHostedSpotsOfGivenUser(String username) throws DAOException
    {
        parkingSpotsOfAUser = new ArrayList<>();

        try
        {
            cmdString = "SELECT * FROM PARKINGSPOTS WHERE  USERNAME = ?";
            pstmt = con.prepareStatement(cmdString);
            pstmt.setString(1, username);
            rss = pstmt.executeQuery();

            getParkingSpots(rss, parkingSpotsOfAUser);

            rss.close();
        }
        catch (SQLException e)
        {
            processSQLError(e);
            throw new DAOException("Error in getting hosted Parking Spots by "+username+"!",e);
        }

        return parkingSpotsOfAUser;
    }

    @Override
    public void clearSpotList()
    {
        this.parkingSpots.clear();
    }

	private void checkWarning(Statement st, int updateCount) throws DAOException
	{
		if (updateCount != 1)
		{
            throw new DAOException("Tuple not inserted correctly.");
		}
	}

	private void processSQLError(Exception e)
	{
		String result = "*** SQL Error: " + e.getMessage();

		e.printStackTrace();
    }

    @Override
    public ArrayList<Booking> getBookedSpotsOfGivenUser(String username) throws DAOException
    {
        Calendar calStart = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        Date start, end;
        Booking booking;
        long timeSlotID;
        String addr;

        bookingSpotsOfAUser = new ArrayList<>();

        try
        {
            cmdString = "SELECT B.USERNAME, B.TIMESLOT_ID, P.SPOT_ID, P.ADDRESS, T.STARTDATETIME, T.ENDDATETIME " +
                        "FROM BOOKINGS B LEFT JOIN PARKINGSPOTS P ON B.SPOT_ID = P.SPOT_ID " +
                        "LEFT JOIN TIMESLOTS T ON B.TIMESLOT_ID = T.TIMESLOT_ID " +
                        "WHERE B.USERNAME = ? AND NOT T.DAYSLOT_ID IS NULL " +
                        "ORDER BY T.STARTDATETIME";

            pstmt = con.prepareStatement(cmdString);
            pstmt.setString(1, username);
            rss = pstmt.executeQuery();

            while (rss.next())
            {
                timeSlotID = rss.getLong("TIMESLOT_ID");
                addr = rss.getString("Address");
                start = rss.getTimestamp("Startdatetime");
                end = rss.getTimestamp("Enddatetime");

                calStart.setTime(start);
                calEnd.setTime(end);

                booking = new Booking(username, timeSlotID, addr, calStart.getTime(), calEnd.getTime());
                bookingSpotsOfAUser.add(booking);
            }

            rss.close();
        }
        catch (Exception e)
        {
            processSQLError(e);
            throw new DAOException("Error in getting bookings list for User: " + username + "!", e);
        }

        return bookingSpotsOfAUser;
    }

    @Override
    public void deleteBooking(String username, long timeSlotID) throws DAOException
    {
        try
        {
            cmdString = "DELETE FROM BOOKINGS WHERE USERNAME = ? AND TIMESLOT_ID = ?";
            pstmt = con.prepareStatement(cmdString);
            pstmt.setString(1, username);
            pstmt.setLong(2, timeSlotID);
            updateCount = pstmt.executeUpdate();
            checkWarning(pstmt, updateCount);
        }
        catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in cancelling booking slot with TIMESLOT_ID = " + timeSlotID + "!", sqle);
        }
    }

    @Override
    public void modifyParkingSpot(long spotID, String address, String phone, String email, Double rate, double latitude, double longitude) throws DAOException
    {
        try
        {
            cmdString = "UPDATE PARKINGSPOTS SET ADDRESS=?, PHONE=?, EMAIL=?, RATE=?, LATITUDE=?, LONGITUDE=? WHERE SPOT_ID=?";
            pstmt = con.prepareStatement(cmdString);
            pstmt.setString(1, address);
            pstmt.setString(2, phone);
            pstmt.setString(3, email);
            pstmt.setDouble(4, rate);
            pstmt.setLong(5, spotID);
            pstmt.setDouble(6, latitude);
            pstmt.setDouble(7, longitude);
            updateCount = pstmt.executeUpdate();
            checkWarning(pstmt, updateCount);
        }
        catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in updating ParkingSpot with id = " + spotID + "!", sqle);
        }
    }

    @Override
    public ArrayList<TimeSlot> getUnbookedTimeSlotsForParkingSpot(long spotID) throws DAOException
    {
        ArrayList<TimeSlot> returnVal=new ArrayList<TimeSlot>();
        TimeSlot currSlot;
        Calendar calStart = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        Date start, end;
        long timeSlotID;
        boolean bookedVar=false;

        try
        {
            cmdString = "SELECT T.TIMESLOT_ID, T.SPOT_ID, T.STARTDATETIME, T.ENDDATETIME, B.USERNAME" +
                    " FROM TIMESLOTS T LEFT JOIN BOOKINGS B ON T.TIMESLOT_ID=B.TIMESLOT_ID " +
                    "WHERE T.SPOT_ID=? ORDER BY T.STARTDATETIME";
            pstmt = con.prepareStatement(cmdString);
            pstmt.setLong(1, spotID);
            rss = pstmt.executeQuery();
            while (rss.next())
            {
                timeSlotID = rss.getLong("TIMESLOT_ID");
                start = rss.getTimestamp("STARTDATETIME");
                end = rss.getTimestamp("ENDDATETIME");

                calStart.setTime(start);
                calEnd.setTime(end);

                if(rss.getString("USERNAME")!=null)
                {
                    bookedVar = true;
                }
                else
                {
                    currSlot=new TimeSlot(calStart.getTime(),calEnd.getTime(),timeSlotID, bookedVar);
                    returnVal.add(currSlot);
                }
            }
            rss.close();

        }
        catch (SQLException SqlEx)
        {
            processSQLError(SqlEx);
            throw new DAOException("Error in getting timeslots from parking spot with SPOT_ID" +
                    " = "+spotID+"!",SqlEx);
        }

        return returnVal;
    }

    @Override
    public ParkingSpot getParkingSpotByID(long spotID) throws DAOException
    {
        ParkingSpot returnVal = null;
        String name, address, phone, email ;
        Double rate, latitude, longitude;

        try
        {
            cmdString = "SELECT * FROM PARKINGSPOTS WHERE SPOT_ID = ?";
            pstmt = con.prepareStatement(cmdString);
            pstmt.setLong(1, spotID);
            rss = pstmt.executeQuery();
            while (rss.next())
            {
                name=rss.getString("NAME");
                address=rss.getString("ADDRESS");
                phone=rss.getString("PHONE");
                email=rss.getString("EMAIL");
                rate=rss.getDouble("RATE");
                latitude = rss.getDouble("LATITUDE");
                longitude = rss.getDouble("LONGITUDE");
                returnVal=new ParkingSpot(spotID,address,name, phone, email, rate, latitude, longitude);
            }
            rss.close();

        }
        catch (SQLException SqlEx)
        {
            processSQLError(SqlEx);
            throw new DAOException("Error in getting timeslots from parking spot with SPOT_ID" +
                    " = " + spotID + "!", SqlEx);
        }
        return returnVal;
    }

    @Override
    public boolean bookTimeSlot(String theUser, long timeSlotID, long spotID) throws DAOException
    {
        boolean returnVal = false;

        try
        {
            cmdString = "INSERT INTO BOOKINGS VALUES(?,?,?)";
            pstmt = con.prepareStatement(cmdString);
            pstmt.setString(1, theUser);
            pstmt.setLong(2, timeSlotID);
            pstmt.setLong(3, spotID);
            updateCount = pstmt.executeUpdate();
            checkWarning(pstmt,updateCount);
            returnVal=true;

        }
        catch (SQLException SqlEx)
        {
            processSQLError(SqlEx);
            throw new DAOException("Error in booking timeslots for parking spot with SPOT_ID" +
                    " = "+spotID+"!",SqlEx);
        }
	    return returnVal;
    }

    @Override
    public ArrayList<TimeSlot> getDaySlots(long spotID) throws DAOException
    {
        ArrayList<TimeSlot> slots = new ArrayList<>();

        try
        {
            cmdString = "SELECT * FROM DAYSLOTS D WHERE D.SPOT_ID=?";
            pstmt = con.prepareStatement(cmdString);

            pstmt.setLong(1, spotID);

            rss = pstmt.executeQuery();
            long daySlotID;
            Date start, end;
            boolean isBooked;

            while (rss.next())
            {
                daySlotID = rss.getLong("DAYSLOT_ID");
                start = rss.getTimestamp("STARTDAYTIME");
                end = rss.getTimestamp("ENDDAYTIME");

                slots.add(new TimeSlot(start, end, daySlotID));
            }

            rss.close();

            for (TimeSlot daySlot : slots)
            {
                cmdString = "SELECT T.TIMESLOT_ID FROM TIMESLOTS T INNER JOIN BOOKINGS B " +
                        "ON T.TIMESLOT_ID=B.TIMESLOT_ID " +
                        "WHERE T.DAYSLOT_ID=?";
                pstmt = con.prepareStatement(cmdString);

                pstmt.setLong(1, daySlot.getSlotID());

                rss = pstmt.executeQuery();

                if (rss.next())
                {
                    daySlot.setIsBooked(true);
                }
            }

            rss.close();

        }
        catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in getting day slot for ParkingSpot with spotID: "+spotID+"!",sqle);
        }

        return slots;
    }

    @Override
    public ArrayList<TimeSlot> getTimeSlots(long daySlotID) throws DAOException
    {
        ArrayList<TimeSlot> slots = new ArrayList<>();

        try
        {
            cmdString = "SELECT * FROM TIMESLOTS T WHERE T.DAYSLOT_ID=? ";
            pstmt = con.prepareStatement(cmdString);

            pstmt.setLong(1, daySlotID);

            rss = pstmt.executeQuery();
            long timeSlotID;
            Date start, end;

            while (rss.next())
            {
                timeSlotID = rss.getLong("TIMESLOT_ID");
                start = rss.getTimestamp("Startdatetime");
                end = rss.getTimestamp("Enddatetime");

                slots.add(new TimeSlot(start, end, timeSlotID));
            }

            rss.close();

            for (TimeSlot timeSlot : slots)
            {
                cmdString = "SELECT B.TIMESLOT_ID FROM BOOKINGS B " +
                        "WHERE B.TIMESLOT_ID=?";
                pstmt = con.prepareStatement(cmdString);

                pstmt.setLong(1, timeSlot.getSlotID());

                rss = pstmt.executeQuery();

                if (rss.next())
                {
                    timeSlot.setIsBooked(true);
                }
            }

            rss.close();
        }
        catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in getting time slot for day slot with slotID: "+daySlotID+"!",sqle);
        }

        return slots;
    }

    public boolean deleteDaySlot(long daySlotID) throws DAOException
    {
        long spotID = -1;
        boolean exitOnReturn = false;

        try
        {
            cmdString = "DELETE FROM TIMESLOTS T " +
                "WHERE T.DAYSLOT_ID=?";
            pstmt = con.prepareStatement(cmdString);
            pstmt.setLong(1, daySlotID);
            pstmt.execute();
        }
        catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error deleting time slot for day slot with slotID: "+daySlotID+"!",sqle);
        }

        try
        {
            cmdString = "SELECT D.SPOT_ID FROM DAYSLOTS D " +
                    "WHERE D.DAYSLOT_ID=?";
            pstmt = con.prepareStatement(cmdString);

            pstmt.setLong(1, daySlotID);

            rss = pstmt.executeQuery();

            if (rss.next())
            {
                spotID = rss.getLong("SPOT_ID");
            }

            cmdString = "DELETE FROM DAYSLOTS D " +
                    "WHERE D.DAYSLOT_ID=?";
            pstmt = con.prepareStatement(cmdString);
            pstmt.setLong(1, daySlotID);
            pstmt.execute();

            cmdString = "SELECT D.SPOT_ID FROM DAYSLOTS D " +
                    "WHERE D.SPOT_ID=?";
            pstmt = con.prepareStatement(cmdString);

            pstmt.setLong(1, spotID);

            rss = pstmt.executeQuery();

            if (!rss.next())
            {
                cmdString = "DELETE FROM PARKINGSPOTS P " +
                        "WHERE P.SPOT_ID=?";
                pstmt = con.prepareStatement(cmdString);
                pstmt.setLong(1, spotID);
                pstmt.execute();
                exitOnReturn = true;
            }
        }
        catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error deleting day slot with slotID: "+daySlotID+"!",sqle);
        }
        return exitOnReturn;
    }

    public boolean deleteTimeSlot(long timeSlotID) throws DAOException
    {
        boolean exitOnReturn = false;
        long daySlotID = -1;

        try
        {
            cmdString = "SELECT T.DAYSLOT_ID FROM TIMESLOTS T " +
                    "WHERE T.TIMESLOT_ID=?";
            pstmt = con.prepareStatement(cmdString);
            pstmt.setLong(1, timeSlotID);
            rss = pstmt.executeQuery();

            if (rss.next())
                daySlotID = rss.getLong("DAYSLOT_ID");

            cmdString = "DELETE FROM TIMESLOTS T " +
                    "WHERE T.TIMESLOT_ID=?";
            pstmt = con.prepareStatement(cmdString);
            pstmt.setLong(1, timeSlotID);
            pstmt.execute();

            cmdString = "SELECT T.DAYSLOT_ID FROM TIMESLOTS T " +
                    "WHERE T.DAYSLOT_ID=?";
            pstmt = con.prepareStatement(cmdString);
            pstmt.setLong(1, daySlotID);
            rss = pstmt.executeQuery();

            if (!rss.next())
            {
                deleteDaySlot(daySlotID);
                exitOnReturn = true;
            }
        }
        catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error deleting time slot with slotID: "+timeSlotID+"!",sqle);
        }
        return exitOnReturn;
    }

    @Override
    public ArrayList<ParkingSpot> getAllParkingSpots() throws DAOException
    {
        return null;
    }
}
