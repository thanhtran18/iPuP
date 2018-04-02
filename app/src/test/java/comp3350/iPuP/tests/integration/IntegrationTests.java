package comp3350.iPuP.tests.integration;

import junit.framework.Test;
import junit.framework.TestSuite;

public class IntegrationTests
{
    public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("Integration Tests");
        suite.addTestSuite(BusinessPersistenceSeamTest.class);
//        suite.addTestSuite(DataAccessHSQLDBTest.class);
        return suite;
    }
}
