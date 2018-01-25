package comp3350.iPuP.business;

import comp3350.iPuP.application.Main;
import comp3350.iPuP.application.Services;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.persistence.DataAccessStub;

/**
 * Created by Amanjyot on 2018-01-24.
 */

public class AccessParkingSpots {
    private DataAccessStub dataAccess;

    public AccessParkingSpots()
    {
        dataAccess = Services.getDataAccess();
    }

    public String insertParkingSpot(ParkingSpot currentParkingSpot)
    {
        return dataAccess.insertParkingSpot(currentParkingSpot);
    }
}
