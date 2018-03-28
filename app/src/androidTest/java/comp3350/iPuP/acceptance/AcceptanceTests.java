package comp3350.iPuP.acceptance;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Created by ThanhTran on 2018-03-26.
 */

public class AcceptanceTests
{
    public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("Acceptance tests");
        suite.addTestSuite(BookParkingSpotTest.class);
        suite.addTestSuite(OfferParkingSpotTest.class);
        suite.addTestSuite(ModifyParkingSpotTest.class);
        return suite;
    }
}
