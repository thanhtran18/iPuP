package comp3350.iPuP.business;

import comp3350.iPuP.application.Services;
import comp3350.iPuP.objects.User;
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

    public User getUser(String username)
    {
        User user = dataAccess.getUser(username);

        // create new user if cannot find user
        if (user == null)
        {
            dataAccess.insertUser(username);
            user = dataAccess.getUser(username);
        }

        return user;
    }

    public String createUser(String username)
    {
        return dataAccess.insertUser(username);
    }

    public User createAndGetUser (String username)
    {
        User user = null;
        String result = createUser(username);

        if (result == null)
        {
            user = getUser(username);
        }

        return user;
    }
}
