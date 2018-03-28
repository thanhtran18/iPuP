package comp3350.iPuP.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.iPuP.tests.integration.IntegrationTests;

public class RunIntegrationTests
{
    private static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("Integration Tests");
        suite.addTest(IntegrationTests.suite());
        return suite;
    }
}
