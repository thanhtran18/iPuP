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

    public void testModiifyBooking(){
        openDataAccess();
        System.out.println("Starting testBusinessPersistenceSeam: Book A ParkingSpot");

        AccessParkingSpots accessParkingSpots;
        AccessUsers accessUsers;
        ArrayList<ParkingSpot> parkingSpotsToModify;
        ParkingSpot currParkingSpot;
        ParkingSpot modifiedSpot;
        try {
            accessParkingSpots = new AccessParkingSpots();
            accessUsers = new AccessUsers();

            assertFalse(accessUsers.createUser("marker"));
            parkingSpotsToModify=accessParkingSpots.getMyHostedSpots("marker");
            assertEquals(1,parkingSpotsToModify.size());
            currParkingSpot=accessParkingSpots.getParkingSpot(parkingSpotsToModify.get(0).
                    getSpotID());
            assertEquals("88 Plaza Drive", currParkingSpot.getAddress());
            assertEquals("204-855-2342", currParkingSpot.getPhone());
            assertEquals("theBestMarker@gmail.com", currParkingSpot.getEmail());
            assertEquals(2.0,currParkingSpot.getRate());

            accessParkingSpots.modifyParkingSpot(currParkingSpot.getSpotID(),
                    currParkingSpot.getAddress(),
                    currParkingSpot.getPhone(),
                    "changedEmail@fmail.com",
                    10.5,
                    currParkingSpot.getLatitude(),
                    currParkingSpot.getLongitude());
            parkingSpotsToModify=accessParkingSpots.getMyHostedSpots("marker");
            assertEquals(1, parkingSpotsToModify.size());
            modifiedSpot=accessParkingSpots.getParkingSpot(currParkingSpot.getSpotID());
            assertEquals("changedEmail@fmail.com",
                    modifiedSpot.getEmail());
            assertEquals(10.5, modifiedSpot.getRate());
            accessParkingSpots.modifyParkingSpot(currParkingSpot.getSpotID(),
                    "15 thatPlace Avenue",
                    "254-441-8879",
                    currParkingSpot.getEmail(),
                    currParkingSpot.getRate(),
                    currParkingSpot.getLatitude(),
                    currParkingSpot.getLongitude());
            parkingSpotsToModify=accessParkingSpots.getMyHostedSpots("marker");
            assertEquals(1, parkingSpotsToModify.size());
            modifiedSpot=accessParkingSpots.getParkingSpot(currParkingSpot.getSpotID());
            assertEquals("15 thatPlace Avenue",
                    modifiedSpot.getAddress());
            assertEquals("254-441-8879", modifiedSpot.getPhone());
        }catch (DAOException daoe){
            fail("ParseException Caught with message: "+daoe.getMessage());
        }
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
