package comp3350.iPuP.objects;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;


public class ParkingSpot
{
    private String address;
    private String name;
    private String phone;
    private String email;
    private String spotID;
    private double rate;
    private ArrayList<DaySlot> daySlots;
    //private boolean cancelled;

    public ParkingSpot(String address, String name, String phone, String email, double rate)
    {
        this.address = address;// required
        this.name = name;// required

        // either phone or email required
        this.phone = phone;
        this.email = email;

        this.rate = rate;// required

        this.spotID = address+name;


        this.daySlots = new ArrayList<DaySlot>();

//        this.cancelled = false;
    }

    public ParkingSpot(String id, String address, String name, String phone, String email, double rate) throws Exception {
        this(address, name, phone, email, rate);
//        this.cancelled = false;

        if (!this.spotID.equals(id)) {
            throw new Exception("Passed in ID (" + id + ") does not match generated ID (" + this.spotID + ") !");
        }
    }

    public boolean isSpot(String spotID)
    {
        return this.spotID.equals(spotID);
    }

    public void addDaySlot(DaySlot newSlot)
    {
        daySlots.add(newSlot);
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

    public ArrayList<DaySlot> getDaySlots()
    {
        return this.daySlots;
    }

    public String getSpotID()
    {
        return spotID;
    }

//    public boolean isCancelled() {
//        return cancelled;
//    }
//
//    public void setCancelled(boolean cancelled) {
//        this.cancelled = cancelled;
//    }

    @Override
    public String toString()
    {
        return this.address + " (hold to cancel this booking)";
    }

    @Override
    public boolean equals(Object other)
    {
        if (other != null && other.getClass() == ParkingSpot.class)
        {
            ParkingSpot otherSpot = (ParkingSpot) other;
            if (this.name.equals(otherSpot.name) && this.address.equals(otherSpot.address) &&
                    this.phone.equals(otherSpot.phone) && this.email.equals(otherSpot.email) &&
                    this.rate == otherSpot.rate)
                return true;
        }
        return false;
    }
}
