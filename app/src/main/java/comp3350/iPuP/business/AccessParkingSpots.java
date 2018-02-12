package comp3350.iPuP.business;

import java.util.ArrayList;
import java.util.List;

import comp3350.iPuP.application.Main;
import comp3350.iPuP.application.Services;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.persistence.DataAccessStub;

/**
 * Created by Amanjyot on 2018-01-24.
 */

public class AccessParkingSpots {
    private DataAccessStub dataAccess;
    private ArrayList<ParkingSpot> availableSpots;

    public AccessParkingSpots()
    {
        dataAccess = Services.getDataAccess();
        availableSpots=new ArrayList<ParkingSpot>();
    }

    public String insertParkingSpot(ParkingSpot currentParkingSpot)
    {
        return dataAccess.insertParkingSpot(currentParkingSpot);
    }

    public ArrayList<ParkingSpot> getAvailableSpots(){
        List<ParkingSpot> temp=dataAccess.getParkingSpots();
        for (int i=0; i<temp.size(); i++){
            if(!(temp.get(i).isBooked())){
                availableSpots.add(temp.get(i));
            }
        }
        return availableSpots;
    }

    public void bookSpot(String id){
        dataAccess.setSpotToBooked(id);
    }
}
