package comp3350.iPuP.tests.business;

import junit.framework.TestCase;

import comp3350.iPuP.application.Main;
import comp3350.iPuP.application.Services;
import comp3350.iPuP.business.AccessParkingSpots;
import comp3350.iPuP.business.AccessUsers;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.tests.persistence.DataAccessStub;

public class AccessUsersTest extends TestCase {

    private static String dbName = Main.dbName;

    public AccessUsersTest(String arg0)
    {
        super(arg0);
    }

    public void testCreateUsers()
    {
        Services.closeDataAccess();

        Services.createDataAccess(new DataAccessStub(dbName));

        AccessUsers accessUsers = new AccessUsers();

        try
        {
            assertFalse(accessUsers.createUser("marker"));
            assertFalse(accessUsers.createUser("tester"));
            assertFalse(accessUsers.createUser("tester2"));
            assertTrue(accessUsers.createUser("tester3"));
            assertTrue(accessUsers.createUser("tester4"));
        } catch (DAOException daoe)
        {
            fail("DAOException Caught with message: "+daoe.getMessage());
        }
    }
}
