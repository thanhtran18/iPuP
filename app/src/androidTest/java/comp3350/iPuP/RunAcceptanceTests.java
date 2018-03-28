package comp3350.iPuP;

import android.app.Application;
import android.test.ApplicationTestCase;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.iPuP.acceptance.AcceptanceTests;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class RunAcceptanceTests
{
    public static TestSuite suite;

    public static Test suite()
    {
        suite = new TestSuite("Acceptance tests");
        suite.addTest(AcceptanceTests.suite());
        return suite;
    }
}