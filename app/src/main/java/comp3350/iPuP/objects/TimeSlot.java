package comp3350.iPuP.objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeSlot
{
    private Date start;
    private Date end;

    private boolean isBooked;
    protected long slotID;

    public static DateFormatter df = new DateFormatter();

    public TimeSlot(Date newStart, Date newEnd)
    {
        start = newStart;
        end = newEnd;
        slotID = -1;
        isBooked=false;
    }

    public TimeSlot(Date newStart, Date newEnd, long slotID)
    {
        this(newStart, newEnd);
        this.slotID = slotID;
    }

    public TimeSlot(Date newStart, Date newEnd, long slotID, boolean bookedOrNot)
    {
        this(newStart, newEnd);
        this.slotID=slotID;
        this.isBooked=bookedOrNot;
    }

    public static TimeSlot parseString(String s) throws ParseException
    {
        if (s == null)
            throw new ParseException("Cannot parse a null string",0);
        if (s.split("-").length == 2)
        {
            String start = s.split("-")[0].trim();
            String end = s.split("-")[1].trim();

            return new TimeSlot(df.getDateTimeFormat().parse(start), df.getDateTimeFormat().parse(end));
        }
        else throw new ParseException("There is not two dates separated by \" -\"",0);
    }

    public static boolean [] weekCodeToBoolArray(String code) throws ParseException
    {
        if (code == null || code.length() != 7)
            throw new ParseException("Null or invalid week code", 0);
        boolean [] days = new boolean[7];
        for (int i = 0; i < 7; i++)
        {
            if (code.charAt(i) == '1')
                days[i] = true;
            else if (code.charAt(i) == '0')
                days[i] = false;
            else
                throw new ParseException("Invalid week code", i);
        }
        return days;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other != null && other.getClass() == this.getClass())
        {
            return this.slotID == ((TimeSlot)other).slotID;
        }
        return false;
    }

    public Date getStart()
    {
        return start;
    }

    public Date getEnd()
    {
        return end;
    }

    @Override
    public String toString()
    {
        if (start != null && end != null)
            return df.getDateTimeFormat().format(start) + " - " + df.getDateTimeFormat().format(end);
        else
            return "Invalid date";
    }

    public long getSlotID()
    {
        return slotID;
    }

    public void setSlotID(long slotID)
    {
        this.slotID = slotID;
    }

    public boolean getIsBooked()
    {
        return isBooked;
    }

    public void setIsBooked(boolean booked)
    {
        isBooked = booked;
    }
}
