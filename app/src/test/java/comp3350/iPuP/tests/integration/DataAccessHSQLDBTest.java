package comp3350.iPuP.tests.integration;

import junit.framework.TestCase;

import comp3350.iPuP.application.Main;
import comp3350.iPuP.application.Services;
import comp3350.iPuP.tests.persistence.DataAccessTest;

public class DataAccessHSQLDBTest extends TestCase
{
    private static String dbName = Main.dbName;
    private static String dbPathName = Main.getDBPathName();

    public DataAccessHSQLDBTest(String arg0) { super(arg0); }

    public void testDataAccess()
    {
        Services.closeDataAccess();

        System.out.println("\nStarting Integration test DataAccess (using default DB)");

        DataAccessTest.dataAccessTest(dbName, dbPathName);

        Services.closeDataAccess();

        System.out.println("\nFinished Integration test DataAccess (using default DB)");
    }
}
