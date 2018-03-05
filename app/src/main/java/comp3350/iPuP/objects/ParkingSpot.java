package comp3350.iPuP.objects;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
    private ArrayList<DaySlot> daySlots;

    public ParkingSpot(String address, String name, String phone, String email, double rate, TimeSlot timeSlot)
    {
        this.address = address;// required
        this.name = name;// required

        // either phone or email required
        this.phone = phone;
        this.email = email;

        this.rate = rate;// required

        this.spotID = address+name;
        this.isBooked = false;

        this.timeSlot = timeSlot;

        this.daySlots = new ArrayList<DaySlot>();
    }

    public ParkingSpot(String id, String address, String name, String phone, String email, double rate, TimeSlot timeSlot, boolean isBooked) throws Exception {
        this(address, name, phone, email, rate, timeSlot);
//        this.spotID = id;
        this.isBooked = isBooked;

        if (!this.spotID.equals(id)) {
            throw new Exception("Passed in ID (" + id + ") does not match generated ID (" + this.spotID + ") !");
        }
    }

    public boolean isSpot(String spotID, String slotID)
    {
        return this.spotID.equals(spotID) && timeSlot.getSlotID().equals(slotID);
    }

    public void addDaySlot(DaySlot newSlot)
    {
        daySlots.add(newSlot);
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

    public ArrayList<DaySlot> getDaySlots()
    {
        return this.daySlots;
    }

    public void setBooked(boolean booked)
    {
        isBooked = booked;
    }

    public String getSpotID()
    {
        return spotID;
    }
    public String getSlotID()
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
