package comp3350.iPuP.objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ReservationTime
{
    private Date start;
    private Date end;
    private DateFormatter df = new DateFormatter();
    private boolean repeat;

    public ReservationTime(int startYear, int startMonth, int startDay, int startHour, int startMinute, int endYear, int endMonth, int endDay, int endHour, int endMinute, boolean repeat)
    {
        Calendar c = new GregorianCalendar(startYear, startMonth, startDay, startHour, startMinute);
        start = c.getTime();
        c.set(endYear, endMonth, endDay, endHour, endMinute);
        end = c.getTime();
        this.repeat = repeat;
    }

    public ReservationTime(Date newStart, Date newEnd)
    {
        start = newStart;
        end = newEnd;
        this.repeat = false;
    }

    public static ReservationTime parseString(String s, boolean repeat) throws ParseException
    {
        if (s.split("-").length == 2)
        {
            String start = s.split("-")[0].trim();
            String end = s.split("-")[1].trim();
            SimpleDateFormat datetime = new SimpleDateFormat("EEE, d MMM yyyy, h:mm a");
            return new ReservationTime(datetime.parse(start), datetime.parse(end));
        }
        else throw new ParseException("There is not two dates separated by \" -\"",0);
    }

    @Override
    public String toString()
    {
        if (start != null && end != null)
            return df.getDateFormat().format(start) + ", " +
                    df.getTimeFormat().format(start) + " - " +
                    df.getTimeFormat().format(end);
        else
            return "Invalid date";
    }

    public boolean isRepeat() { return repeat; }

    public Date getStartTime()
    {
        return start;
    }

    public Date getEndTime()
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
