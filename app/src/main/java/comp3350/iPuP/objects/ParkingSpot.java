package comp3350.iPuP.objects;

import java.time.LocalDateTime;

/**
 * Created by Mark Van Egmond on 1/23/2018.
 */

public class ParkingSpot
{
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public double getRate()
    {
        return rate;
    }

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String address;
    private String name;
    private String phone;
    private String email;
    private double rate;

    public ParkingSpot(LocalDateTime startTime, LocalDateTime endTime, String address, String name, String phone, String email, double rate)
    {
        this.startTime = startTime;
        this.endTime = endTime;
        this.address = address;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.rate = rate;
    }
}
