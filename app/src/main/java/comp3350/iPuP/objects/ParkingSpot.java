package comp3350.iPuP.objects;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


public class ParkingSpot
{
    private String name;
    private String address;
    private String phone;
    private String email;
    private long spotID;
    private double rate;

    public ParkingSpot(String address, String name, String phone, String email, double rate)
    {
        this.address = address;
        this.name = name;

        this.phone = phone;
        this.email = email;

        this.rate = rate;
    }

    public ParkingSpot(long id, String address, String name, String phone, String email, double rate)
    {
        this(address, name, phone, email, rate);
        this.spotID = id;
    }

    public String getName()
    {
        return name;
    }

    public String getPhone()
    {
        return phone;
    }

    public String getAddress()
    {
        return address;
    }

    public String getEmail()
    {
        return email;
    }

    public double getRate()
    {
        return rate;
    }

    public long getSpotID()
    {
        return spotID;
    }

    public void setSpotID(long spotID)
    {
        this.spotID = spotID;
    }

    public void modifySpot(String address, String phone, String email, double rate)
    {
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.rate = rate;
    }

    @Override
    public String toString()
    {
        return this.address;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other != null && other.getClass() == ParkingSpot.class)
        {
            ParkingSpot otherSpot = (ParkingSpot) other;
            if (this.spotID == otherSpot.spotID)
                return true;
        }
        return false;
    }
}
