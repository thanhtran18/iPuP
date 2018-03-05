package comp3350.iPuP.objects;

import java.util.ArrayList;

/**
 * Created by ThanhTran on 2018-03-05.
 */

public class Booking
{
    private String username; //the unique username of each user
    private ArrayList<ParkingSpot> spotList;

    public Booking(String username)
    {
        this.username = username;
        this.spotList = null;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public ArrayList<ParkingSpot> getSpotList()
    {
        return spotList;
    }

    public void setSpotList(ArrayList<ParkingSpot> spotList)
    {
        this.spotList = spotList;
    }

}

