package comp3350.iPuP.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import comp3350.iPuP.objects.DateFormater;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.ReservationTime;

public class DataAccessObject implements DataAccess
{
	private PreparedStatement pstmt;
//	private Statement stmt;
	private Connection con;
	private ResultSet rs;

	private String dbName;
	private String dbType;

    private DateFormater df = new DateFormater();

	private ArrayList<ParkingSpot> parkingSpots;

	private String cmdString;
	private int updateCount;
	private String result;
	private static String EOF = "  ";


	public DataAccessObject(String dbName)
	{
		this.dbName = dbName;
	}


	public void open(String dbPath)
	{
		String url;
		try
		{
			// Setup for HSQL
			dbType = "HSQL";
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			url = "jdbc:hsqldb:file:" + dbPath; // stored on disk mode
			con = DriverManager.getConnection(url, "iPuP", "iPuP");
//			stmt = con.createStatement();

			/*** Alternate setups for different DB engines, just given as examples. Don't use them. ***/
			
			/*
			 * // Setup for SQLite. Note that this is undocumented and is not guaranteed to work.
			 * // See also: https://github.com/SQLDroid/SQLDroid
			 * dbType = "SQLite";
			 * Class.forName("SQLite.JDBCDriver").newInstance();
			 * url = "jdbc:sqlite:" + dbPath;
			 * c1 = DriverManager.getConnection(url);     
			 * 
			 * ... create statements
			 */

			/*** The following two work on desktop builds: ***/

			/*
			 * // Setup for Access
			 * dbType = "Access";
			 * Class.forName("sun.jdbc.odbc.JdbcOdbcDriver").newInstance();
			 * url = "jdbc:odbc:SC";
			 * c1 = DriverManager.getConnection(url,"userid","userpassword");
			 * 
			 * ... create statements
			 */

			/*
			 * //Setup for MySQL
			 * dbType = "MySQL";
			 * Class.forName("com.mysql.jdbc.Driver");
			 * url = "jdbc:mysql://localhost/database01";
			 * c1 = DriverManager.getConnection(url, "root", "");
			 * 
			 * ... create statements
			 */
		}
		catch (Exception e)
		{
			processSQLError(e);
		}
		System.out.println("Opened " +dbType +" database " +dbPath);
	}


	public void close()
	{
		try
		{	// commit all changes to the database
			cmdString = "shutdown compact";
			pstmt = con.prepareStatement(cmdString);
			rs = pstmt.executeQuery();
//			rs = stmt.executeQuery(cmdString);
			con.close();
		}
		catch (Exception e)
		{
			processSQLError(e);
		}
		System.out.println("Closed " +dbType +" database " +dbName);
	}


	public String insertParkingSpot(ParkingSpot currentParkingSpot)
    {
//        String values;
        result = null;

        try
        {
            pstmt = con.prepareStatement("Insert into Parkingspots Values(?,?,?,?,?,?,?,?,?)");
            pstmt.setString(1,currentParkingSpot.getId());
            pstmt.setString(2,currentParkingSpot.getName());
            pstmt.setString(3,currentParkingSpot.getAddress());
            pstmt.setString(4,currentParkingSpot.getPhone());
            pstmt.setString(5,currentParkingSpot.getEmail());
            pstmt.setDouble(6,currentParkingSpot.getRate());
            pstmt.setBoolean(7,currentParkingSpot.isBooked());
            pstmt.setString(8,currentParkingSpot.getSqlStartDateTime());
            pstmt.setString(9,currentParkingSpot.getSqlEndDateTime());

//            values = "'" + currentParkingSpot.getId()
//                    + "', '" + currentParkingSpot.getName()
//                    + "', '" + currentParkingSpot.getAddress()
//                    + "', '" + currentParkingSpot.getPhone()
//                    + "', '" + currentParkingSpot.getEmail()
//                    + "', " + currentParkingSpot.getRate()
//                    + ", " + currentParkingSpot.isBooked()
//                    + ", '" + currentParkingSpot.getSqlStartDateTime()
//                    + "', '" + currentParkingSpot.getSqlEndDateTime()
//                    +"'";
//            cmdString = "Insert into Parkingspots Values(" + values +")";
            //System.out.println(cmdString);
//            updateCount = stmt.executeUpdate(cmdString);
            updateCount = pstmt.executeUpdate();
//            result = checkWarning(stmt, updateCount);
            result = checkWarning(pstmt, updateCount);
        }
        catch (Exception e)
        {
            result = processSQLError(e);
        }

        return null;
    }


    public ArrayList<ParkingSpot> getParkingSpots()
    {
        Boolean isBooked;
        Calendar calStart = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        Date start, end;
        Double rate;
        ParkingSpot ps;
        ReservationTime rt;
        String id, name, addr, phone, email;

        parkingSpots = new ArrayList<ParkingSpot>();
        result = null;

        try
        {
            cmdString = "Select * from ParkingSpots";
            pstmt = con.prepareStatement(cmdString);
            rs = pstmt.executeQuery();
//            rs = stmt.executeQuery(cmdString);
            //ResultSetMetaData md = rs.getMetaData();

            while (rs.next())
            {
                id = rs.getString("PS_ID");
                name = rs.getString("Name");
                addr = rs.getString("Address");
                phone = rs.getString("Phone");
                email = rs.getString("Email");
                rate = rs.getDouble("Rate");
                isBooked = rs.getBoolean("Is_Booked");
                start = rs.getTimestamp("Startdatetime");
                end = rs.getTimestamp("Enddatetime");

                calStart.setTime(start);
                calEnd.setTime(end);

                rt = new ReservationTime(calStart.get(Calendar.YEAR), calStart.get(Calendar.MONTH),
                        calStart.get(Calendar.DAY_OF_MONTH), calStart.get(Calendar.HOUR_OF_DAY),
                        calStart.get(Calendar.MINUTE), calEnd.get(Calendar.HOUR_OF_DAY),
                        calEnd.get(Calendar.MINUTE));

                ps = new ParkingSpot(id, rt, addr, name, phone, email, rate, isBooked);
                parkingSpots.add(ps);
            }

            rs.close();
        }
        catch (Exception e)
        {
            processSQLError(e);
        }

        return parkingSpots;
    }


    public String setSpotToBooked(String id)
    {
        boolean isBooked;
        String bookMessage = "Not Booked";
        String values, where;

        result = null;

        try
        {
            cmdString = "Select * from ParkingSpots Where PS_ID=?";
//            cmdString = "Select * from ParkingSpots Where PS_ID='" + id + "'";
            pstmt = con.prepareStatement(cmdString);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
//            rs = stmt.executeQuery(cmdString);

            if (rs.next() == true)
            {
                isBooked = rs.getBoolean("Is_Booked");

                if (isBooked)
                {
                    bookMessage = "Already Booked";
                } else
                {
//                    values = "Is_Booked=TRUE";
//                    where = "where PS_ID='" + id + "'";

//                    cmdString = "Update ParkingSpots Set " + values + " " + where;
                    cmdString = "Update ParkingSpots Set Is_Booked=? where PS_ID=?";
                    pstmt = con.prepareStatement(cmdString);
                    pstmt.setBoolean(1, true);
                    pstmt.setString(2,id);
                    //System.out.println(cmdString);
                    updateCount = pstmt.executeUpdate();
//                    updateCount = stmt.executeUpdate(cmdString);
                    result = checkWarning(pstmt, updateCount);
//                    result = checkWarning(stmt, updateCount);

                    bookMessage = "Booked";
                }
            }

            rs.close();
        }
        catch (Exception e)
        {
            result = processSQLError(e);
        }

        return bookMessage;
    }


    public void clearSpotList()
    {
        this.parkingSpots.clear();
    }


	public String checkWarning(Statement st, int updateCount)
	{
		String result;

		result = null;
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
			result = processSQLError(e);
		}
		if (updateCount != 1)
		{
			result = "Tuple not inserted correctly.";
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
}
