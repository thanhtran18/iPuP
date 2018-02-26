package comp3350.iPuP.application;

import comp3350.iPuP.persistence.DataAccess;
import comp3350.iPuP.persistence.DataAccessObject;

public class Services
{
	private static DataAccess dataAccessService = null;

	public static DataAccess createDataAccess(String dbName)
	{
		if (dataAccessService == null)
		{
			dataAccessService = new DataAccessObject(dbName);
			dataAccessService.open(Main.getDBPathName());
		}
		return dataAccessService;
	}


    public static DataAccess getDataAccess()
    {
        if (dataAccessService == null)
        {
            System.out.println("Connection to data access has not been established.");
            System.exit(1);
        }
        return dataAccessService;
    }


	public static void closeDataAccess()
	{
		if (dataAccessService != null)
		{
			dataAccessService.close();
		}
		dataAccessService = null;
	}
}
