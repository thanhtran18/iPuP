package comp3350.iPuP.tests.integration;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import comp3350.iPuP.application.Main;
import comp3350.iPuP.application.Services;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.business.AccessUsers;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.objects.DateFormatter;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.TimeSlot;
import comp3350.iPuP.persistence.DataAccess;
import comp3350.iPuP.persistence.DataAccessObject;
import comp3350.iPuP.tests.persistence.DataAccessStub;

public class BusinessPersistenceSeamTest extends TestCase
{
    private static String dbName = Main.dbName;
    private static String dbPathName = Main.getDBPathName();
    private DataAccess dataAccess;
    private DateFormatter dateFormatter = new DateFormatter();

    public BusinessPersistenceSeamTest(String arg0) { super(arg0); }

    public void testAccessUsers()
    {
        openDataAccess();
        System.out.println("Starting testBusinessPersistenceSeam: Access Users");

        AccessUsers accessUsers;

        try
        {
            accessUsers = new AccessUsers();
            assertFalse(accessUsers.createUser("marker"));
            assertTrue(accessUsers.createUser("tester3"));
            assertFalse(accessUsers.createUser("tester"));
            assertTrue(accessUsers.createUser("tester1"));
            assertTrue(accessUsers.createUser("tester4"));
            assertTrue(accessUsers.createUser("marker1"));
            assertTrue(accessUsers.createUser("marker2"));
            assertTrue(accessUsers.createUser("marker3"));
            assertTrue(accessUsers.createUser("marker4"));
        }
        catch (DAOException daoe)
        {
            fail("DAOException Caught with message: "+daoe.getMessage());
        }

        System.out.println("Finished testBusinessPersistenceSeam: Access Users");
    }

    public void testAccessParkingSpotsToView()
    {
        openDataAccess();
        System.out.println("Starting testBusinessPersistenceSeam: Access ParkingSpots To View");

        AccessParkingSpots accessParkingSpots;
        AccessUsers accessUsers;
        ArrayList<ParkingSpot> parkingSpots;

        try
        {
            accessParkingSpots = new AccessParkingSpots();
            accessUsers = new AccessUsers();

            assertFalse(accessUsers.createUser("marker"));

            parkingSpots = accessParkingSpots.getDailySpots("",dateFormatter.getSqlDateFormat().parse("2018-03-28"));
            assertEquals(0,parkingSpots.size());

            parkingSpots = accessParkingSpots.getDailySpots("",dateFormatter.getSqlDateFormat().parse("2018-06-11"));
            assertEquals(20,parkingSpots.size());

            parkingSpots = accessParkingSpots.getDailySpots("1",dateFormatter.getSqlDateFormat().parse("2018-06-11"));
            assertEquals(10,parkingSpots.size());

            parkingSpots = accessParkingSpots.getDailySpots("Pembina",dateFormatter.getSqlDateFormat().parse("2018-06-11"));
            assertEquals(4,parkingSpots.size());

            parkingSpots = accessParkingSpots.getDailySpots("pembina",dateFormatter.getSqlDateFormat().parse("2018-06-11"));
            assertEquals(4,parkingSpots.size());

            parkingSpots = accessParkingSpots.getDailySpots("pEmBiNa",dateFormatter.getSqlDateFormat().parse("2018-06-11"));
            assertEquals(4,parkingSpots.size());

            parkingSpots = accessParkingSpots.getDailySpots("dafoe",dateFormatter.getSqlDateFormat().parse("2018-06-11"));
            assertEquals(0,parkingSpots.size());

            parkingSpots = accessParkingSpots.getDailySpots("main",dateFormatter.getSqlDateFormat().parse("2018-06-11"));
            assertEquals(1,parkingSpots.size());

            parkingSpots = accessParkingSpots.getDailySpots("",dateFormatter.getSqlDateFormat().parse("2018-06-12"));
            assertEquals(2,parkingSpots.size());

            parkingSpots = accessParkingSpots.getDailySpots("plaza",dateFormatter.getSqlDateFormat().parse("2018-06-12"));
            assertEquals(1,parkingSpots.size());

            parkingSpots = accessParkingSpots.getDailySpots("Plaza",dateFormatter.getSqlDateFormat().parse("2018-06-12"));
            assertEquals(1,parkingSpots.size());

            parkingSpots = accessParkingSpots.getDailySpots("dafoe",dateFormatter.getSqlDateFormat().parse("2018-06-12"));
            assertEquals(0,parkingSpots.size());
        }
        catch (ParseException pe)
        {
            fail("ParseException Caught with message: "+pe.getMessage());
        }
        catch (DAOException daoe)
        {
            fail("DAOException Caught with message: "+daoe.getMessage());
        }

        System.out.println("Finished testBusinessPersistenceSeam: Access ParkingSpots To View");
    }
    public void testCreateParkingSpot()
    {
        openDataAccess();
        System.out.println("Starting testBusinessPersistenceSeam: Create A ParkingSpot");

        AccessParkingSpots accessParkingSpots;
        AccessUsers accessUsers;
        ArrayList<ParkingSpot> parkingSpots;
        ParkingSpot aparkingspot;
        TimeSlot timeSlot;
        //ArrayList<TimeSlot> daysSlots;
        try
        {
            accessParkingSpots = new AccessParkingSpots();
            accessUsers = new AccessUsers();

            Date start = new Date();
            Date end = new Date();
            start.setMonth(6);
            start.setYear(118);
            start.setDate(20);

            end.setMonth(7);
            end.setYear(118);
            end.setDate(20);
            timeSlot = new TimeSlot(start,end);

            assertFalse(accessUsers.createUser("marker"));

            accessParkingSpots.insertParkingSpot("marker", timeSlot, "", "120 Dafoe Road", "204-123-1231","iAmMarker@gmail.com" ,5.0, 77.4, 88.8);
            parkingSpots = accessParkingSpots.getDailySpots("120 Dafoe Road", start);

            assertEquals(1,parkingSpots.size());

            aparkingspot = parkingSpots.get(0);
            assertEquals("120 Dafoe Road",aparkingspot.getAddress());
            assertEquals("marker",aparkingspot.getName());
            assertEquals("204-123-1231",aparkingspot.getPhone());
            assertEquals("iAmMarker@gmail.com",aparkingspot.getEmail());
            assertEquals(5.0,aparkingspot.getRate());
            assertEquals(77.4,aparkingspot.getLatitude());
            assertEquals(88.8,aparkingspot.getLongitude());


            accessUsers.createUser("Metta World Peace");
            start.setMonth(7);
            start.setYear(118);
            start.setDate(15);

            end.setMonth(7);
            end.setYear(118);
            end.setDate(20);
            timeSlot = new TimeSlot(start, end);

            accessParkingSpots = new AccessParkingSpots();

            accessParkingSpots.insertParkingSpot("Metta World Peace", timeSlot, "", "1999 St. Mary's Road", "204-902-1223","fight@gmail.com" ,10.0, 555.5, 66.66);
            parkingSpots = accessParkingSpots.getDailySpots("1999 St. Mary's Road", start);

            assertEquals(1,parkingSpots.size());

            aparkingspot = parkingSpots.get(0);
            assertEquals("1999 St. Mary's Road",aparkingspot.getAddress());
            assertEquals("Metta World Peace",aparkingspot.getName());

            assertEquals("204-902-1223",aparkingspot.getPhone());
            assertEquals("fight@gmail.com" ,aparkingspot.getEmail());
            assertEquals(10.0,aparkingspot.getRate());
            assertEquals(555.5,aparkingspot.getLatitude());
            assertEquals(66.66,aparkingspot.getLongitude());
        } catch (DAOException daoe)
        {
            fail("DAOException Caught with message: "+daoe.getMessage());
        }
        System.out.println("Finished testBusinessPersistenceSeam: Create A ParkingSpot");

    }


    public void testBookAParkingSpot()
    {
        openDataAccess();
        System.out.println("Starting testBusinessPersistenceSeam: Book A ParkingSpot");

        AccessParkingSpots accessParkingSpots;
        AccessUsers accessUsers;
        ArrayList<ParkingSpot> parkingSpots;
        ParkingSpot aparkingspot;
        ArrayList<TimeSlot> daysSlots;

        try
        {
            accessParkingSpots = new AccessParkingSpots();
            accessUsers = new AccessUsers();

            assertFalse(accessUsers.createUser("marker"));

            parkingSpots = accessParkingSpots.getDailySpots("",dateFormatter.getSqlDateFormat().parse("2018-03-28"));
            assertEquals(0,parkingSpots.size());

            parkingSpots = accessParkingSpots.getDailySpots("main",dateFormatter.getSqlDateFormat().parse("2018-06-11"));
            assertEquals(1,parkingSpots.size());

            aparkingspot = parkingSpots.get(0);
            assertEquals("60 Main Street",aparkingspot.getAddress());
            assertEquals("Avocado Stevenson",aparkingspot.getName());
            assertEquals("601-122-1211",aparkingspot.getPhone());
            assertEquals("avocadoisgood@gmail.com",aparkingspot.getEmail());
            assertEquals(5.25,aparkingspot.getRate());

        }
        catch (ParseException pe)
        {
            fail("ParseException Caught with message: "+pe.getMessage());
        }
        catch (DAOException daoe)
        {
            fail("DAOException Caught with message: "+daoe.getMessage());
        }

        System.out.println("Finished testBusinessPersistenceSeam: Book A ParkingSpot");
    }

    private void replaceDbWithDefault() throws DAOException
    {
        Services.closeDataAccess();

        try
        {
            String dbFilePath = System.getProperty("user.dir") + "/" + dbPathName + ".script";
            String defaultDbFilePath = System.getProperty("user.dir") + "/app/src/main/assets/db/" + dbName + ".script";

            File dbFile = new File(dbFilePath);
            File defaultDbFile = new File(defaultDbFilePath);

            if (defaultDbFile.exists())
            {
                InputStream in = new FileInputStream(defaultDbFile);
                FileUtils.copyInputStreamToFile(in, dbFile);
                in.close();
            } else
            {
                throw new DAOException("Error in locating default database files!");
            }

        }
        catch (FileNotFoundException fnfe)
        {
            throw new DAOException("Unable to open default database file!", fnfe);
        }
        catch (IOException ioe)
        {
            throw new DAOException("Unable to access database: ", ioe);
        }

        Services.createDataAccess(dbName);
    }

    private void openDataAccess()
    {
        try
        {
            replaceDbWithDefault();
            dataAccess = new DataAccessObject(dbName);
            dataAccess.open(dbPathName);
        } catch (DAOException daoe)
        {
            System.err.println(daoe.getMessage());
            System.exit(1);
        }
    }

    private void closeDataAccess()
    {
        try
        {
            replaceDbWithDefault();
            System.out.println("Closed HSQL database " + dbName);
        } catch (DAOException daoe)
        {
            System.err.println(daoe.getMessage());
            System.exit(1);
        }
    }
}
