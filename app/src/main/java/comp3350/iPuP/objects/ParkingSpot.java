package comp3350.iPuP.objects;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ParkingSpot
{
    private String name;
    private String address;
    private String phone;
    private String email;
    private long spotID;
    private double rate;
    private double longitude, latitude;

    public ParkingSpot(String address, String name, String phone, String email, double rate, double latitude, double longitude)
    {
        this.address = address;
        this.name = name;

        this.phone = phone;
        this.email = email;

        this.rate = rate;

        this.latitude = latitude;
        this.longitude = longitude;
    }

    public ParkingSpot(long id, String address, String name, String phone, String email, double rate, double latitude, double longitude)
    {
        this(address, name, phone, email, rate, latitude, longitude);
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

    public void modifySpot(String address, String phone, String email, double rate, double latitude, double longitude)
    {
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.rate = rate;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public double getLatitude()
    {
        return latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public static boolean validateEmail(String email)
    {
        Pattern p = Pattern.compile("^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$");//pattern from https://stackoverflow.com/questions/42266148/email-validation-regex-java
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean validatePhone(String phone)
    {
        Pattern p = Pattern.compile("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$");//pattern from https://stackoverflow.com/questions/16699007/regular-expression-to-match-standard-10-digit-phone-number
        Matcher m = p.matcher(phone);
        return m.matches();
    }
}
