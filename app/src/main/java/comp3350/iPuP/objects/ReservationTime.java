package comp3350.iPuP.objects;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ReservationTime
{
    private Date start;
    private Date end;
    private DateFormater df = new DateFormater();

    public ReservationTime(int year, int month, int day, int startHour, int startMinute, int endHour, int endMinute)
    {
        if (year > 2000)
        {
            Calendar c = new GregorianCalendar(year, month, day, startHour, startMinute);
            start = c.getTime();
            c.set(year, month, day, endHour, endMinute);
            end = c.getTime();
        }
    }

    @Override
    public String toString()
    {
        if (start != null && end != null)
            return df.getDateFormat().format(start) + ", " +
                    df.getSqlTimeFormat().format(start) + " - " +
                    df.getSqlTimeFormat().format(end);
        else
            return "Invalid date";
    }

    public Date getStart()
    {
        return start;
    }

    public Date getEnd()
    {
        return end;
    }

    public String getSqlStartDateTime() { return df.getSqlDateTimeFormat().format(start); }

    public String getSqlEndDateTime()
    {
        return df.getSqlDateTimeFormat().format(end);
    }

    public String getSqlStartTime() { return df.getSqlTimeFormat().format(start); }

    public String getSqlEndTime()
    {
        return df.getSqlTimeFormat().format(end);
    }

    public String getSqlDate()
    {
        return df.getSqlDateFormat().format(start);
    }

    @Override
    public boolean equals(Object other)
    {
        if (other != null && other.getClass() == ReservationTime.class)
        {
            ReservationTime otherTime = (ReservationTime) other;
            if (this.start.equals(otherTime.start) && this.end.equals(otherTime.end))
                return true;
        }
        return false;
    }
}
