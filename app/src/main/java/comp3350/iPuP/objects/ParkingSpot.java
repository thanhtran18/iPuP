package comp3350.iPuP.objects;

import java.util.Date;


public class ParkingSpot
{
    private TimeSlot timeSlot;
    private String address;
    private String name;
    private String phone;
    private String email;
    private String spotID;
    private double rate;
    private boolean isBooked;

    public ParkingSpot(TimeSlot timeSlot, String address, String name, String phone, String email, double rate)
    {
        this.timeSlot = timeSlot; //required
        this.address = address;// required
        this.name = name;// required

        // either phone or email required
        this.phone = phone;
        this.email = email;

        this.rate = rate;// required

        spotID = address+name;
        isBooked = false;
    }

    public ParkingSpot(String id, TimeSlot timeSlot, String address, String name, String phone, String email, double rate, boolean isBooked) throws Exception
    {
        this(timeSlot, address, name, phone, email, rate);
        spotID = id;
        this.isBooked = isBooked;
    }

    public boolean isSpot(String spotID, int slotID)
    {
        return this.spotID.equals(spotID) && timeSlot.getSlotID() == slotID;
    }

    public Date getStartTime()
    {
        return timeSlot.getStart();
    }

    public Date getEndTime()
    {
        return timeSlot.getEnd();
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

    public boolean isBooked()
    {
        return isBooked;
    }

    public void setBooked(boolean booked)
    {
        isBooked = booked;
    }

    public String getSpotID()
    {
        return spotID;
    }
    public int getSlotID()
    {
        return timeSlot.getSlotID();
    }

    @Override
    public String toString()
    {
        return this.address + "\n" + this.timeSlot.toString();
    }

    @Override
    public boolean equals(Object other)
    {
        if (other != null && other.getClass() == ParkingSpot.class)
        {
            ParkingSpot otherSpot = (ParkingSpot) other;
            if (this.name.equals(otherSpot.name) && this.address.equals(otherSpot.address) &&
                    this.phone.equals(otherSpot.phone) && this.email.equals(otherSpot.email) &&
                    this.rate == otherSpot.rate && this.timeSlot.equals(otherSpot.timeSlot))
                return true;
        }
        return false;
    }
}
