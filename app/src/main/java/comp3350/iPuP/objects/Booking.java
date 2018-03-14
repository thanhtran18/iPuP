package comp3350.iPuP.objects;

import java.util.Date;

public class Booking
{
    private String name; //the unique username of each user
    private long timeSlotId;
    private String address;
    private Date start;
    private Date end;
    private DateFormatter df = new DateFormatter();

    public Booking(String name, Long timeSlotId, String address, Date start, Date end) throws Exception
    {
        if (name.equals(""))
            throw new Exception("User must have a username!");
        if (start.after(end))
            throw new Exception("Starting time must be after ending time!");
        this.name = name;
        this.timeSlotId = timeSlotId;
        this.address = address;
        this.start = start;
        this.end = end;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public long getTimeSlotId()
    {
        return timeSlotId;
    }

    public void setTimeSlotId(long timeSlotId)
    {
        this.timeSlotId = timeSlotId;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public Date getStart()
    {
        return start;
    }

    public void setStart(Date start)
    {
        this.start = start;
    }

    public Date getEnd()
    {
        return end;
    }

    public void setEnd(Date end)
    {
        this.end = end;
    }

    public String toString()
    {
        if (start.after(new Date()))
            return "\n" + this.address + " (hold to cancel this booking) \n" + df.getDateTimeFormat().format(start) + " - " + df.getDateTimeFormat().format(end) + "\n";
        else
            return "\n" + this.address + "\n" + df.getDateTimeFormat().format(start) + " - " + df.getDateTimeFormat().format(end) + "\n";
    }
}

