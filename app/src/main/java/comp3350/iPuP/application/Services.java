package comp3350.iPuP.application;

import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.persistence.DataAccess;
import comp3350.iPuP.persistence.DataAccessObject;

public class Services
{
	private static DataAccess dataAccessService = null;

	public static DataAccess createDataAccess(String dbName)
	{
		try {
			if (dataAccessService == null) {
				dataAccessService = new DataAccessObject(dbName);
				dataAccessService.open(Main.getDBPathName());
			}
		} catch (DAOException daoe)
		{
            System.out.println(daoe.getMessage());
            System.exit(1);
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
		    try
            {
                dataAccessService.close();
            } catch (DAOException daoe)
            {
                System.err.println(daoe.getMessage());
                System.exit(1);
            }
		}
		dataAccessService = null;
	}
}
