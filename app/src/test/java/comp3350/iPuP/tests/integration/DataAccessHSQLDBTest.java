package comp3350.iPuP.tests.integration;

import android.text.StaticLayout;

import junit.framework.TestCase;

import comp3350.iPuP.application.Main;
import comp3350.iPuP.application.Services;
import comp3350.iPuP.persistence.DataAccess;
import comp3350.iPuP.tests.persistence.DataAccessTest;

public class DataAccessHSQLDBTest extends TestCase
{
    private static String dbName = Main.dbName;
    private static String dbPathName = Main.getDBPathName();

    public DataAccessHSQLDBTest(String arg0) { super(arg0); }

    public void testDataAccess()
    {
//        DataAccess dataAccess;

        Services.closeDataAccess();

        System.out.println("\nStarting Integration test DataAccess (using default DB)");

        // Use the following two statements to run with the real database
//        Services.createDataAccess(dbName);
//        dataAccess = Services.getDataAccess();

        DataAccessTest.dataAccessTest(dbName, dbPathName);

        Services.closeDataAccess();

        System.out.println("\nFinished Integration test DataAccess (using default DB)");
    }
}
