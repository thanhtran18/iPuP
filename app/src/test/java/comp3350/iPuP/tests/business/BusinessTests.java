package comp3350.iPuP.tests.business;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Created by Amanjyot on 2018-03-22.
 */

public class BusinessTests
{
    public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("Object Tests");
        suite.addTestSuite(AccessParkingSpotsTest.class);
        suite.addTestSuite(AccessUsersTest.class);
        return suite;
    }
}
