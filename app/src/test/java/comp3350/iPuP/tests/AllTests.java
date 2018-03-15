package comp3350.iPuP.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.iPuP.tests.business.AccessParkingSpotsTest;
import comp3350.iPuP.tests.business.AccessUsersTest;
import comp3350.iPuP.tests.objects.BookingTest;
import comp3350.iPuP.tests.objects.ParkingSpotTest;
import comp3350.iPuP.tests.objects.TimeSlotTest;
import comp3350.iPuP.tests.persistence.DataAccessTest;

public class AllTests
{
	public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("All tests");
        testObjects();
        testBusiness();
        testPersistence();
        return suite;
    }

    private static void testObjects()
    {
        suite.addTestSuite(ParkingSpotTest.class);
        suite.addTestSuite(TimeSlotTest.class);
        suite.addTestSuite(BookingTest.class);
    }

    private static void testBusiness()
    {
        suite.addTestSuite(AccessParkingSpotsTest.class);
        suite.addTestSuite(AccessUsersTest.class);
    }

    private static void testPersistence()
    {
        suite.addTestSuite(DataAccessTest.class);
    }
}
