package comp3350.iPuP.tests.objects;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ObjectTests
{
    private static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("Object Tests");
        suite.addTestSuite(ParkingSpotTest.class);
        suite.addTestSuite(TimeSlotTest.class);
        suite.addTestSuite(BookingTest.class);
        return suite;
    }

}
