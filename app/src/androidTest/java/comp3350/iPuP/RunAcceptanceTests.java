package comp3350.iPuP;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.iPuP.acceptance.AcceptanceTests;

public class RunAcceptanceTests
{
    private static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("Acceptance tests");
        suite.addTest(AcceptanceTests.suite());
        return suite;
    }
}