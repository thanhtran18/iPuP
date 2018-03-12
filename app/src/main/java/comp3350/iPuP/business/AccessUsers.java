package comp3350.iPuP.business;

import comp3350.iPuP.application.Services;
import comp3350.iPuP.objects.DAOException;
import comp3350.iPuP.persistence.DataAccess;

/**
 * Created by Amanjyot on 2018-03-04.
 */

public class AccessUsers {

    private DataAccess dataAccess;

    public AccessUsers()
    {
        dataAccess = Services.getDataAccess();
    }

    public boolean createUser(String username) throws DAOException
    {
        return dataAccess.insertUser(username);
    }
}
