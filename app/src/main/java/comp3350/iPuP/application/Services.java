package comp3350.iPuP.application;

import comp3350.iPuP.persistence.DataAccessStub;

public class Services
{
	private static DataAccessStub dataAccessService = null;

	public static DataAccessStub createDataAccess()
	{
		if (dataAccessService == null)
		{
			dataAccessService = new DataAccessStub();
			dataAccessService.open();
		}
		return dataAccessService;
	}


    public static DataAccessStub getDataAccess()
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
