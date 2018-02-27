package comp3350.iPuP.business;

import java.util.ArrayList;
import java.util.List;

import comp3350.iPuP.application.Main;
import comp3350.iPuP.application.Services;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.persistence.DataAccess;


public class AccessParkingSpots
{
    private DataAccess dataAccess;
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

    public ArrayList<ParkingSpot> getAllSpots()
    {
        ArrayList<ParkingSpot> returnList=new ArrayList<ParkingSpot>();
        returnList.addAll(dataAccess.getParkingSpots());
        return returnList;
    }


    public ArrayList<ParkingSpot> getAvailableSpots()
    {
        List<ParkingSpot> temp = dataAccess.getParkingSpots();
        availableSpots = new ArrayList<ParkingSpot>();
        for (int i = 0; i < temp.size(); i++)
        {
            if (!(temp.get(i).isBooked()))
            {
                availableSpots.add(temp.get(i));
            }
        }
        return availableSpots;
    }

    public String bookSpot(String id)
    {
        return dataAccess.setSpotToBooked(id);
    }

    public void clearSpots()
    {
        dataAccess.clearSpotList();
    }
}
