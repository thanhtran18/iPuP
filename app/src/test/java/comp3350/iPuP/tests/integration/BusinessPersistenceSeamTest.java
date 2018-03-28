package comp3350.iPuP.tests.integration;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import comp3350.iPuP.application.Main;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.objects.DateFormatter;
import comp3350.iPuP.persistence.DataAccess;
import comp3350.iPuP.persistence.DataAccessObject;
import comp3350.iPuP.tests.persistence.DataAccessStub;

public class BusinessPersistenceSeamTest extends TestCase
{
    private static String dbName = Main.dbName;
    private static String dbPathName = Main.getDBPathName();
    private DataAccess dataAccess;

    public BusinessPersistenceSeamTest(String arg0) { super(arg0); }

    public void testAccessUsers()
    {
        openDataAccess();
        System.out.println("Starting testAccessUsers: Default Data");



    }

    private void replaceDbWithDefault() throws DAOException
    {
        try
        {
            String dbFilePath = System.getProperty("user.dir") + "/" + dbPathName + ".script";
            String defaultDbFilePath = System.getProperty("user.dir") + "/app/src/main/assets/db/" + dbName + ".script";

            File dbFile = new File(dbFilePath);
            File defaultDbFile = new File(defaultDbFilePath);

            if (defaultDbFile.exists())
            {
                InputStream in = new FileInputStream(defaultDbFile);
                FileUtils.copyInputStreamToFile(in, dbFile);
                in.close();
            } else
            {
                throw new DAOException("Error in locating default database files!");
            }

        }
        catch (FileNotFoundException fnfe)
        {
            throw new DAOException("Unable to open default database file!", fnfe);
        }
        catch (IOException ioe)
        {
            throw new DAOException("Unable to access database: ", ioe);
        }
    }

    private void openDataAccess()
    {
        try
        {
            replaceDbWithDefault();
            dataAccess = new DataAccessObject(dbName);
            dataAccess.open(dbPathName);
        } catch (DAOException daoe)
        {
            System.err.println(daoe.getMessage());
            System.exit(1);
        }
    }

    private void closeDataAccess()
    {
        try
        {
            replaceDbWithDefault();
            System.out.println("Closed HSQL database " + dbName);
        } catch (DAOException daoe)
        {
            System.err.println(daoe.getMessage());
            System.exit(1);
        }
    }
}
