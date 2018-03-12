package comp3350.iPuP.objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeSlot
{
    private Date start;
    private Date end;

    //TODO: CONFIRM THIS
    private boolean isBooked;
    protected long slotID;

    public static DateFormatter df = new DateFormatter();

    public static TimeSlot parseString(String s) throws ParseException
    {
        if (s.split("-").length == 2)
        {
            String start = s.split("-")[0].trim();
            String end = s.split("-")[1].trim();

            return new TimeSlot(df.getDateTimeFormat().parse(start), df.getDateTimeFormat().parse(end));
        }
        else throw new ParseException("There is not two dates separated by \" -\"",0);
    }

    public static boolean [] weekCodeToBoolArray(String code)
    {
        boolean [] days = new boolean[7];
        for (int i = 0; i < 7; i++)
        {
            days[i] = code.charAt(i) == '1';
        }
        return days;
    }

    public TimeSlot(Date newStart, Date newEnd)
    {
        start = newStart;
        end = newEnd;
        slotID = 0;

        isBooked=false;
    }

    public TimeSlot(Date newStart, Date newEnd, long slotID)
    {
        this(newStart, newEnd);
        this.slotID = slotID;

        isBooked=false;
    }

    public TimeSlot(Date newStart, Date newEnd, long slotID, boolean bookedOrNot){
        start=newStart;
        end=newEnd;
        this.slotID=slotID;
        this.isBooked=bookedOrNot;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other != null && other.getClass() == TimeSlot.class)
        {
            TimeSlot otherTime = (TimeSlot) other;
            if (this.start.equals(otherTime.start) && this.end.equals(otherTime.end))
                return true;
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

    public Long getSlotID()
    {
        return slotID;
    }

    public void setSlotID(long slotID)
    {
        this.slotID = slotID;
    }

    public boolean isBooked() {
        return isBooked;
    }
}
