package comp3350.iPuP.objects;

import java.util.Date;


public class ParkingSpot
{
    private ReservationTime reservation;
    private String address;
    private String name;
    private String phone;
    private String email;
    private String id;
    private double rate;
    private boolean isBooked;

    public ParkingSpot(ReservationTime reservation, String address, String name, String phone, String email, double rate)
    {
        this.reservation = reservation; //required
        this.address = address;// required
        this.name = name;// required

        // either phone or email required
        this.phone = phone;
        this.email = email;

        this.rate = rate;// required
        id = address+name;
        isBooked = false;
    }

    public ParkingSpot(String id, ReservationTime reservation, String address, String name, String phone, String email, double rate, boolean isBooked) throws Exception
    {
        this(reservation, address, name, phone, email, rate);
        this.isBooked = isBooked;

        if (!this.id.equals(id)) {
            throw new Exception("Passed in ID (" + id + ") does not match generated ID (" + this.id + ") !");
        }
    }

    public ReservationTime getReservationTime() { return reservation; }
//    public Date getStartTime()
//    {
//        return reservation.getStart();N
//    }
//
//    public Date getEndTime()
//    {
//        return reservation.getEnd();
//    }
//
//    public String getSqlStartDateTime() { return reservation.getSqlStartDateTime(); }
//
//    public String getSqlEndDateTime() { return reservation.getSqlEndDateTime(); }
//
//    public String getSqlStartTime() { return reservation.getSqlStartTime(); }
//
//    public String getSqlEndTime() { return reservation.getSqlEndTime(); }
//
//    public String getSqlDate() { return reservation.getSqlDate(); }

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

    public String getId()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return this.address + "\n" + this.reservation.toString();
    }

    @Override
    public boolean equals(Object other)
    {
        if (other != null && other.getClass() == ParkingSpot.class)
        {
            ParkingSpot otherSpot = (ParkingSpot) other;
            if (this.name.equals(otherSpot.name) && this.address.equals(otherSpot.address) &&
                    this.phone.equals(otherSpot.phone) && this.email.equals(otherSpot.email) &&
                    this.rate == otherSpot.rate && this.reservation.equals(otherSpot.reservation))
                return true;
        }
        return false;
    }
}
