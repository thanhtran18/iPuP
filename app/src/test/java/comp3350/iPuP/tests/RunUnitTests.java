package comp3350.iPuP.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.iPuP.tests.business.AccessParkingSpotsTest;
import comp3350.iPuP.tests.business.AccessUsersTest;
import comp3350.iPuP.tests.business.BusinessTests;
import comp3350.iPuP.tests.objects.BookingTest;
import comp3350.iPuP.tests.objects.ObjectTests;
import comp3350.iPuP.tests.objects.ParkingSpotTest;
import comp3350.iPuP.tests.objects.TimeSlotTest;
import comp3350.iPuP.tests.persistence.DataAccessTest;
import comp3350.iPuP.tests.persistence.PersistenceTests;

public class RunUnitTests
{
	public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("All tests");
        suite.addTest(ObjectTests.suite());
        suite.addTest(BusinessTests.suite());
        suite.addTest(PersistenceTests.suite());
        return suite;
    }
}
