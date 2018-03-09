package comp3350.iPuP.persistence;

import org.hsqldb.Types;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import comp3350.iPuP.objects.Booking;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.objects.DateFormatter;
import comp3350.iPuP.objects.DaySlot;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.TimeSlot;

public class DataAccessObject implements DataAccess
{
	private PreparedStatement pstmt, pstmt2, pstmt3;
	private Statement stmt;
	private Connection con;
	private ResultSet rss, rsp, rsp2, rsp3, genkey;

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


	public void open(String dbPath) throws DAOException
	{
		String url;
		try
		{
			// Setup for HSQL
			dbType = "HSQL";
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			url = "jdbc:hsqldb:file:" + dbPath; // stored on disk mode
			con = DriverManager.getConnection(url, "iPuP", "iPuP");
			stmt = con.createStatement();
		}
		catch (Exception e)
		{
			processSQLError(e);
			throw new DAOException("Error in opening "+dbType+" database "+dbPath+"!",e);
		}
		System.out.println("Opened " +dbType +" database " +dbPath);
	}


	public void close() throws DAOException
	{
		try
		{	// commit all changes to the database
			cmdString = "shutdown compact";
			rss = stmt.executeQuery(cmdString);
			con.close();
		}
		catch (Exception e)
		{
			processSQLError(e);
			throw new DAOException("Error in closing "+dbType+" database "+dbName+"!",e);
		}
		System.out.println("Closed " +dbType +" database " +dbName);
	}


	private boolean doesParkingSpotExists(String psID) throws DAOException
    {
        boolean result = false;

        try {
            cmdString = "SELECT * FROM PARKINGSPOTS WHERE PS_ID = ?";
            pstmt = con.prepareStatement(cmdString);
            pstmt.setString(1, psID);

            rsp = pstmt.executeQuery();

            if (rsp.next())
            {
                result = true;
            }

            rsp.close();
        } catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in checking if ParkingSpot object with PS_ID = "+psID+" exists!",sqle);
        }

        return result;
    }


	private boolean doesUserExists(String user) throws DAOException
    {
        boolean result = false;

        try
        {
            cmdString = "SELECT * FROM USERS WHERE USER_ID = ?";
            pstmt = con.prepareStatement(cmdString);
            pstmt.setString(1,user);

            rsp = pstmt.executeQuery();

            if (rsp.next())
            {
                result = true;
            }
        } catch (Exception e)
        {
            processSQLError(e);
            throw new DAOException("Error in checking if User ("+user+") exists!",e);
        }

        return result;
    }


	public boolean insertDaySlot(String psID, DaySlot daySlot) throws DAOException
    {
        boolean result = false;
        long dsID;
        String theResult = null;

        try
        {
            cmdString = "INSERT INTO DAYSLOTS (PS_ID,STARTDAYTIME,ENDDAYTIME) " +
                        "VALUES (?,?,?,?)";
            pstmt2 = con.prepareStatement(cmdString);
            pstmt2.setString(1, psID);
            pstmt2.setString(2, df.getSqlDateTimeFormat().format(daySlot.getStart()));
            pstmt2.setString(3, df.getSqlDateTimeFormat().format(daySlot.getEnd()));
            pstmt2.setBoolean(4,false);
            updateCount = pstmt2.executeUpdate();
            checkWarning(pstmt, updateCount);
        } catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in inserting DaySlot object with PS_ID = "+psID+"!",sqle);
        }

        try
        {
            cmdString = "CALL IDENTITY()";
            rss = stmt.executeQuery(cmdString);

            if (rss.next())
            {
                dsID = rss.getLong(1);

                // insert all timeSlots related to this daySlot
                result = insertTimeSlots(psID, dsID, daySlot.getTimeSlots());
            } else
            {
                theResult += "\nCould not retrieve DaySlot ID from Database!";
            }
        } catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Could not retrieve last auto generated DaySlot ID!",sqle);
        }

        return result;
    }


	public boolean insertDaySlots(String psID, ArrayList<DaySlot> daySlots) throws DAOException
    {
        boolean result = false;

        for (int i = 0; i < daySlots.size(); i++)
        {
            result = insertDaySlot(psID, daySlots.get(i));
        }

        return result;
    }

    public boolean insertTimeSlot(String psID, Long dsID, Date start, Date end) throws DAOException
    {
        boolean result = false;

        try
        {
            cmdString = "INSERT INTO TIMESLOTS (PS_ID,DS_ID,STARTDATETIME,ENDDATETIME) " +
                    "VALUES (?,?,?,?,?)";
            pstmt3 = con.prepareStatement(cmdString);
            pstmt3.setString(1, psID);
            if (dsID == null)
            {
                pstmt3.setNull(2, Types.NULL);
            } else
            {
                pstmt3.setLong(2, dsID);
            }
            pstmt3.setString(3, df.getSqlDateTimeFormat().format(start));
            pstmt3.setString(4, df.getSqlDateTimeFormat().format(end));
            pstmt3.setBoolean(5,false);

            updateCount = pstmt3.executeUpdate();
            checkWarning(pstmt, updateCount);

            result = true;

        } catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in inserting TimeSlot object with DS_ID = "+dsID+" and PS_ID = "+psID+"!",sqle);
        }

        return result;
    }


    public boolean insertTimeSlot(String psID, Long dsID, TimeSlot timeSlot) throws DAOException
    {
        return insertTimeSlot(psID, dsID, timeSlot.getStart(), timeSlot.getEnd());
    }


    public boolean insertTimeSlots(String psID, Long dsID, ArrayList<TimeSlot> timeSlots) throws DAOException
    {
        boolean result = false;

        for (int i = 0; i < timeSlots.size(); i++)
        {
            result = insertTimeSlot(psID, dsID, timeSlots.get(i));
        }

        return result;
    }


	public boolean insertParkingSpot(String user, ParkingSpot currentParkingSpot) throws DAOException
    {
        boolean result = false;
        try
        {
            if (!doesParkingSpotExists(currentParkingSpot.getSpotID())) {
                cmdString = "INSERT INTO PARKINGSPOTS VALUES(?,?,?,?,?,?,?,?)";
                pstmt = con.prepareStatement(cmdString);
                pstmt.setString(1, currentParkingSpot.getSpotID());
                pstmt.setString(2, user);
                pstmt.setString(3, currentParkingSpot.getName());
                pstmt.setString(4, currentParkingSpot.getAddress());
                pstmt.setString(5, currentParkingSpot.getPhone());
                pstmt.setString(6, currentParkingSpot.getEmail());
                pstmt.setDouble(7, currentParkingSpot.getRate());
                pstmt.setBoolean(8,false);

                //System.out.println(cmdString);
                updateCount = pstmt.executeUpdate();
                checkWarning(pstmt, updateCount);

                result = true;
            }

            result = insertDaySlots(currentParkingSpot.getSpotID(), currentParkingSpot.getDaySlots());
        }
        catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in creating ParkingSpot object with PS_ID = "+currentParkingSpot.getSpotID()+" for User: "+user+"!",sqle);
        }

        return result;
    }

    public boolean insertUser(String userID) throws DAOException
    {
        boolean result = false;

        if (!doesUserExists(userID)) { // if user does not exist
            try {
                cmdString = "INSERT INTO USERS VALUES(?)";
                pstmt = con.prepareStatement(cmdString);
                pstmt.setString(1, userID);

                updateCount = pstmt.executeUpdate();
                checkWarning(pstmt, updateCount);

                result = true;

            } catch (SQLException sqle) {
                processSQLError(sqle);
                throw new DAOException("Error in creating new user with USER_ID = "+userID+"!",sqle);
            }
        }

        return result;
    }


    private void setTimeSlotsForDaySlot(DaySlot daySlot) throws DAOException
    {
        Calendar calStart = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        Date start, end;
        long tsID;
        TimeSlot timeSlot;

        try {
            cmdString = "SELECT * FROM TIMESLOTS WHERE DS_ID = ?";
            pstmt = con.prepareStatement(cmdString);
            pstmt.setLong(1, daySlot.getSlotID());
            rsp = pstmt.executeQuery();

            while (rsp.next()) {
                tsID = rsp.getLong("TS_ID");
                start = rsp.getTimestamp("STARTDATETIME");
                end = rsp.getTimestamp("ENDDATETIME");

                calStart.setTime(start);
                calEnd.setTime(end);

                timeSlot = new TimeSlot(calStart.getTime(), calEnd.getTime(), tsID);
                daySlot.addTimeSlot(timeSlot);
            }
        } catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in getting list of TimeSlots for TS_ID = "+daySlot.getSlotID()+"!",sqle);
        }
    }


    private void setDaySlotsForParkingSpot(ParkingSpot parkingSpot) throws DAOException
    {
        Calendar calStart = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        Date start, end;
        DaySlot daySlot;
        long dsID;

        try
        {
            cmdString = "SELECT * FROM DAYSLOTS WHERE PS_ID = ?";
            pstmt = con.prepareStatement(cmdString);
            pstmt.setString(1,parkingSpot.getSpotID());
            rsp = pstmt.executeQuery();

            while (rsp.next())
            {
                dsID = rsp.getLong("DS_ID");
                start = rsp.getTimestamp("STARTDAYTIME");
                end = rsp.getTimestamp("ENDDAYTIME");

                calStart.setTime(start);
                calEnd.setTime(end);

                daySlot = new DaySlot(calStart.getTime(), calEnd.getTime(), dsID);
                setTimeSlotsForDaySlot(daySlot);
                parkingSpot.addDaySlot(daySlot);
            }
        } catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in getting list of DaySlots for PS_ID = "+parkingSpot.getSpotID()+"!",sqle);
        }
    }


    public ArrayList<ParkingSpot> getParkingSpotsByAddressDate(String address, Date date) throws DAOException
    {
        parkingSpots = new ArrayList<ParkingSpot>();

        try {
            cmdString = "SELECT * FROM PARKINGSPOTS P WHERE P.ADDRESS LIKE ? " +
                        "AND EXISTS (SELECT * FROM DAYSLOTS D WHERE D.PS_ID = P.PS_ID " +
                        "AND ? BETWEEN CAST(D.STARTDAYTIME AS DATE) AND CAST(D.ENDDAYTIME AS DATE))";
            pstmt = con.prepareStatement(cmdString);

            if (address == null)
            {
                pstmt.setString(1,"%");
            } else
            {
                pstmt.setString(1,"%"+address+"%");
            }
            pstmt.setString(2, df.getSqlDateFormat().format(date));

            rss = pstmt.executeQuery();

            getParkingSpots(rss, parkingSpots);

            rss.close();
        } catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in getting ParkingSpots ordered by Date: "+df.getSqlDateFormat().format(date)+"!",sqle);
        }

        return parkingSpots;
    }

    private void getParkingSpots(ResultSet rs, ArrayList<ParkingSpot> parkingSpots) throws DAOException
    {
        Double rate;
        ParkingSpot ps;
        String id, name, addr, phone, email;

        try {
            while (rs.next()) {
                id = rs.getString("PS_ID");
                name = rs.getString("NAME");
                addr = rss.getString("ADDRESS");
                phone = rss.getString("PHONE");
                email = rss.getString("EMAIL");
                rate = rss.getDouble("RATE");

                ps = new ParkingSpot(id, addr, name, phone, email, rate);
                parkingSpots.add(ps);
            }
        } catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in getting list of ParkingSpot objects!",sqle);
        } catch (Exception e)
        {
            processSQLError(e);
            throw new DAOException("Error in creating a new ParkingSpot object!",e);
        }
    }


//    public String setSpotToBooked(String spotID, String slotID)
//    {
//        boolean isBooked;
//        String bookMessage = "Not Booked";
//
//        result = null;
//
//        try
//        {
////            cmdString = "SELECT * FROM ParkingSpots WHERE PS_ID = ?";
//            cmdString = "UPDATE PARKINGSPOTS SET IS_BOOKED = ? WHERE PS_ID = ? AND IS_BOOKED = FALSE";
//            pstmt = con.prepareStatement(cmdString);
//            pstmt.setBoolean(1, true);
//            pstmt.setString(2, spotID);
////            pstmt.setString(2, spotID + "_" + slotID);
//            updateCount = pstmt.executeUpdate();
//
//            if (updateCount == 0)
//            {
//                bookMessage = "Already Booked";
//            } else
//            {
//                bookMessage = "Booked";
//            }
//
////            if (rsp.next())
////            {
////                isBooked = rsp.getBoolean("Is_Booked");
////
////                if (isBooked)
////                {
////                    bookMessage = "Already Booked";
////                }
////                else
////                {
////                    cmdString = "Update ParkingSpots Set Is_Booked=? where PS_ID=?";
////                    pstmt = con.prepareStatement(cmdString);
////                    pstmt.setBoolean(1, true);
////                    pstmt.setString(2,spotID + "_" + slotID);
////                    //System.out.println(cmdString);
////                    updateCount = pstmt.executeUpdate();
////                    result = checkWarning(pstmt, updateCount);
////
////                    bookMessage = "Booked";
////                }
////            }
////
////            rsp.close();
//        }
//        catch (Exception e)
//        {
//            result = processSQLError(e);
//        }
//
//        return bookMessage;
//    }


    public ArrayList<ParkingSpot> getHostedSpotsOfGivenUser(String username) throws DAOException
    {
        parkingSpotsOfAUser = new ArrayList<ParkingSpot>();

        try
        {
            cmdString = "SELECT * FROM PARKINGSPOTS WHERE  USER_ID = ? AND DELETED = FALSE";
            pstmt = con.prepareStatement(cmdString);
            pstmt.setString(1, username);
            rss = pstmt.executeQuery();
            //ResultSetMetaData md = rs.getMetaData();

            getParkingSpots(rss, parkingSpotsOfAUser);

            rss.close();
        } catch (SQLException e)
        {
            processSQLError(e);
            throw new DAOException("Error in getting hosted Parking Spots by "+username+"!",e);
        }

        return parkingSpotsOfAUser;
    }

    public void clearSpotList()
    {
        this.parkingSpots.clear();
    }


	public String checkWarning(Statement st, int updateCount) throws DAOException
	{
		String result = null;
		try
		{
			SQLWarning warning = st.getWarnings();
			if (warning != null)
			{
				result = warning.getMessage();
			}
		}
		catch (Exception e)
		{
			processSQLError(e);
			throw new DAOException("Error in getting warnings!",e);
		}
		if (updateCount != 1)
		{
			result = "Tuple not inserted correctly.";
            throw new DAOException(result);
		}
		return result;
	}


	public String processSQLError(Exception e)
	{
		String result = "*** SQL Error: " + e.getMessage();

		// Remember, this will NOT be seen by the user!
		e.printStackTrace();
		
		return result;
	}


	//added by Kevin
    public ArrayList<Booking> getBookedSpotsOfGivenUser(String username) throws DAOException
    {
        Calendar calStart = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        Date start, end;
        Booking booking;
        long tsID;
        String userID, addr;

        bookingSpotsOfAUser = new ArrayList<Booking>();

        try
        {
            cmdString = "SELECT B.USER_ID, B.TS_ID, P.PS_ID, P.ADDRESS, T.STARTDATETIME, T.ENDDATETIME " +
                        "FROM BOOKINGS B LEFT JOIN PARKINGSPOTS P ON B.PS_ID = P.PS_ID " +
                        "LEFT JOIN TIMESLOTS T ON B.TS_ID = T.TS_ID " +
                        "WHERE B.USER_ID = ? AND B.DELETED = FALSE " +
                            "AND NOT T.DS_ID IS NULL ORDER BY T.STARTDATETIME";
            //TODO: remove null check on dsID after change in database
            pstmt = con.prepareStatement(cmdString);
            pstmt.setString(1, username);
            rss = pstmt.executeQuery();
            //ResultSetMetaData md = rs.getMetaData();

            while (rss.next())
            {
                userID = rss.getString("USER_ID");
                tsID = rss.getLong("TS_ID");
                addr = rss.getString("Address");
                start = rss.getTimestamp("Startdatetime");
                end = rss.getTimestamp("Enddatetime");

                calStart.setTime(start);
                calEnd.setTime(end);

                booking = new Booking(userID, tsID, addr, calStart.getTime(), calEnd.getTime());
                bookingSpotsOfAUser.add(booking);
            }

            rss.close();
        }
        catch (Exception e)
        {
            processSQLError(e);
            throw new DAOException("Error in getting bookings list for User: "+username+"!",e);
        }

        return bookingSpotsOfAUser;
    }

    public boolean setBookedSpotToDeleted(String username, Long timeSlotId) throws DAOException
    {
        boolean result = false;
        try
        {
            cmdString = "UPDATE BOOKINGS SET DELETED = TRUE WHERE USER_ID = ? AND TS_ID = ?";
            pstmt = con.prepareStatement(cmdString);
            pstmt.setString(1, username);
            pstmt.setLong(2, timeSlotId);
            updateCount = pstmt.executeUpdate();
            checkWarning(pstmt, updateCount);

            result = true;
        }
        catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in cancelling booking slot with TS_ID = "+timeSlotId+"!",sqle);
        }
        return result;
    }


}
