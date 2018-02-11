package comp3350.iPuP.tests;

import junit.framework.Test;
import junit.framework.TestSuite;
//import comp3350.iPuP.tests.objects.CourseTest;
import comp3350.iPuP.objects.ReservationTime;
import comp3350.iPuP.tests.business.AccessParkingSpotsTest;
import comp3350.iPuP.tests.objects.ParkingSpotTest;
import comp3350.iPuP.tests.objects.ReservationTimeTest;
//import comp3350.iPuP.tests.objects.SCTest;
//import comp3350.iPuP.tests.objects.StudentTest;
//import comp3350.iPuP.tests.business.CalculateGPATest;

public class AllTests
{
	public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("All tests");
        testObjects();
        testBusiness();
        return suite;
    }

    private static void testObjects()
    {
        suite.addTestSuite(ParkingSpotTest.class);
        suite.addTestSuite(ReservationTimeTest.class);
//        suite.addTestSuite(CourseTest.class);
//        suite.addTestSuite(SCTest.class);
    }

    private static void testBusiness()
    {
        suite.addTestSuite(AccessParkingSpotsTest.class);
//        suite.addTestSuite(CalculateGPATest.class);
    }
}
