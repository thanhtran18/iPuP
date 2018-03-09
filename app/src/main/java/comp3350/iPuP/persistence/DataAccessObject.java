package comp3350.iPuP.persistence;

import org.hsqldb.Types;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
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


	private boolean doesParkingSpotExists(String spotID) throws DAOException
    {
        boolean result = false;

        try {
            cmdString = "SELECT * FROM PARKINGSPOTS WHERE SPOT_ID = ?";
            pstmt = con.prepareStatement(cmdString);
            pstmt.setString(1, spotID);

            rsp = pstmt.executeQuery();

            if (rsp.next())
            {
                result = true;
            }

            rsp.close();
        } catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in checking if ParkingSpot object with spotID = "+spotID+" exists!",sqle);
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
            pstmt.setString(1,username);

            rsp = pstmt.executeQuery();

            if (rsp.next())
            {
                result = true;
            }
        } catch (Exception e)
        {
            processSQLError(e);
            throw new DAOException("Error in checking if User ("+username+") exists!",e);
        }

        return result;
    }


	public long insertDaySlot(TimeSlot daySlot, String spotID) throws DAOException
    {
        long dayslotID;

        try
        {
            cmdString = "INSERT INTO DAYSLOTS (SPOT_ID,STARTDAYTIME,ENDDAYTIME) " +
                        "VALUES (?,?,?,?)";
            pstmt2 = con.prepareStatement(cmdString);
            pstmt2.setString(1, spotID);
            pstmt2.setString(2, df.getSqlDateTimeFormat().format(daySlot.getStart()));
            pstmt2.setString(3, df.getSqlDateTimeFormat().format(daySlot.getEnd()));
            pstmt2.setBoolean(4,false);
            updateCount = pstmt2.executeUpdate();
            checkWarning(pstmt, updateCount);
        }
        catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in inserting DaySlot object with spotID = "+spotID+"!",sqle);
        }

        try
        {
            cmdString = "CALL IDENTITY()";
            rss = stmt.executeQuery(cmdString);

            if (rss.next())
            {
                dayslotID = rss.getLong(1);
            } else
            {
                throw new DAOException("Could not retrieve last auto generated DaySlot ID!");
            }
        } catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Could not retrieve last auto generated DaySlot ID!",sqle);
        }

        return dayslotID;
    }

    public long insertTimeSlot(TimeSlot timeSlot, long dayslotID, String spotID) throws DAOException
    {
        long timeslotID;

        try
        {
            cmdString = "INSERT INTO TIMESLOTS (SPOT_ID,DAYSLOT_ID,STARTDATETIME,ENDDATETIME) " +
                    "VALUES (?,?,?,?,?)";
            pstmt3 = con.prepareStatement(cmdString);
            pstmt3.setString(1, spotID);
            pstmt3.setLong(2, dayslotID);
            pstmt3.setString(3, df.getSqlDateTimeFormat().format(timeSlot.getStart()));
            pstmt3.setString(4, df.getSqlDateTimeFormat().format(timeSlot.getEnd()));
            pstmt3.setBoolean(5,false);

            updateCount = pstmt3.executeUpdate();
            checkWarning(pstmt, updateCount);
        } catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in inserting TimeSlot object with dayslotID = "+dayslotID+" and slotID = "+spotID+"!",sqle);
        }

        try
        {
            cmdString = "CALL IDENTITY()";
            rss = stmt.executeQuery(cmdString);

            if (rss.next())
            {
                timeslotID = rss.getLong(1);
            } else
            {
                throw new DAOException("Could not retrieve last auto generated TimeSlot ID!");
            }
        } catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Could not retrieve last auto generated TimeSlot ID!",sqle);
        }

        return timeslotID;
    }

	public boolean insertParkingSpot(String username, ParkingSpot currentParkingSpot) throws DAOException
    {
        boolean result = false;
        try
        {
            if (!doesParkingSpotExists(currentParkingSpot.getSpotID())) {
                cmdString = "INSERT INTO PARKINGSPOTS VALUES(?,?,?,?,?,?,?,?)";
                pstmt = con.prepareStatement(cmdString);
                pstmt.setString(1, currentParkingSpot.getSpotID());
                pstmt.setString(2, username);
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
        }
        catch (SQLException sqle)
        {
            processSQLError(sqle);
            throw new DAOException("Error in creating ParkingSpot object with SPOT_ID = "+currentParkingSpot.getSpotID()+" for Username: "+username+"!",sqle);
        }

        return result;
    }

    public boolean insertUser(String username) throws DAOException
    {
        boolean result = false;

        if (!doesUserExists(username)) { // if user does not exist
            try {
                cmdString = "INSERT INTO USERS VALUES(?)";
                pstmt = con.prepareStatement(cmdString);
                pstmt.setString(1, username);

                updateCount = pstmt.executeUpdate();
                checkWarning(pstmt, updateCount);

                result = true;

            } catch (SQLException sqle) {
                processSQLError(sqle);
                throw new DAOException("Error in creating new user with USERNAME = "+username+"!",sqle);
            }
        }

        return result;
    }

    public ArrayList<TimeSlot> getDaySlotsForAParkingSpot(String slotID) throws DAOException
    {
        //TODO: implement this
        ArrayList<TimeSlot> daySlots = new ArrayList<TimeSlot>();

        return daySlots;
    }

    public ArrayList<ParkingSpot> getParkingSpotsByAddressDate(String address, Date date) throws DAOException
    {
        parkingSpots = new ArrayList<ParkingSpot>();

        try {
            cmdString = "SELECT * FROM PARKINGSPOTS P WHERE P.ADDRESS LIKE ? " +
                        "AND EXISTS (SELECT * FROM DAYSLOTS D WHERE D.SPOT_ID = P.SPOT_ID " +
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
                id = rs.getString("SPOT_ID");
                name = rs.getString("NAME");
                addr = rs.getString("ADDRESS");
                phone = rs.getString("PHONE");
                email = rs.getString("EMAIL");
                rate = rs.getDouble("RATE");

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
////            cmdString = "SELECT * FROM ParkingSpots WHERE SPOT_ID = ?";
//            cmdString = "UPDATE PARKINGSPOTS SET IS_BOOKED = ? WHERE SPOT_ID = ? AND IS_BOOKED = FALSE";
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
////                    cmdString = "Update ParkingSpots Set Is_Booked=? where SPOT_ID=?";
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
            cmdString = "SELECT * FROM PARKINGSPOTS WHERE  USERNAME = ? AND DELETED = FALSE";
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
        long timeslotID;
        String addr;

        bookingSpotsOfAUser = new ArrayList<Booking>();

        try
        {
            cmdString = "SELECT B.USERNAME, B.TIMESLOT_ID, P.SPOT_ID, P.ADDRESS, T.STARTDATETIME, T.ENDDATETIME " +
                        "FROM BOOKINGS B LEFT JOIN PARKINGSPOTS P ON B.SPOT_ID = P.SPOT_ID " +
                        "LEFT JOIN TIMESLOTS T ON B.TIMESLOT_ID = T.TIMESLOT_ID " +
                        "WHERE B.USERNAME = ? AND B.DELETED = FALSE " +
                            "AND NOT T.DAYSLOT_ID IS NULL ORDER BY T.STARTDATETIME";
            //TODO: remove null check on dayslotID after change in database
            pstmt = con.prepareStatement(cmdString);
            pstmt.setString(1, username);
            rss = pstmt.executeQuery();
            //ResultSetMetaData md = rs.getMetaData();

            while (rss.next())
            {
                timeslotID = rss.getLong("TIMESLOT_ID");
                addr = rss.getString("Address");
                start = rss.getTimestamp("Startdatetime");
                end = rss.getTimestamp("Enddatetime");

                calStart.setTime(start);
                calEnd.setTime(end);

                booking = new Booking(username, timeslotID, addr, calStart.getTime(), calEnd.getTime());
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

    public boolean setBookedSpotToDeleted(String username, long timeSlotId) throws DAOException
    {
        boolean result = false;
        try
        {
            cmdString = "UPDATE BOOKINGS SET DELETED = TRUE WHERE USERNAME = ? AND TIMESLOT_ID = ?";
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
            throw new DAOException("Error in cancelling booking slot with TIMESLOT_ID = "+timeSlotId+"!",sqle);
        }
        return result;
    }


}
