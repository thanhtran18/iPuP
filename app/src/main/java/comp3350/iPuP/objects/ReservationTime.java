package comp3350.iPuP.objects;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ReservationTime
{
    private Date start;
    private Date end;
    private DateFormater df = new DateFormater();
    private SimpleDateFormat date;
    private SimpleDateFormat time;
    private boolean repeat;

    public ReservationTime(int startYear, int startMonth, int startDay, int startHour, int startMinute, int endYear, int endMonth, int endDay, int endHour, int endMinute, boolean repeat)
    {
        date = new SimpleDateFormat("EEE, d MMM yyyy");
        time = new SimpleDateFormat("h:mm a");

        Calendar c = new GregorianCalendar(startYear, startMonth, startDay, startHour, startMinute);
        start = c.getTime();
        c.set(endYear, endMonth, endDay, endHour, endMinute);
        end = c.getTime();
        this.repeat = repeat;
    }

    public ReservationTime(Date newStart, Date newEnd, boolean repeat)
    {
        date = new SimpleDateFormat("EEE, d MMM yyyy");
        time = new SimpleDateFormat("h:mm a");

        start = newStart;
        end = newEnd;
        this.repeat = repeat;
    }

    public static ReservationTime parseString(String s, boolean repeat) throws ParseException
    {
        if (s.split("-").length == 2)
        {
            String start = s.split("-")[0].trim();
            String end = s.split("-")[1].trim();
            SimpleDateFormat datetime = new SimpleDateFormat("EEE, d MMM yyyy, h:mm a");
            return new ReservationTime(datetime.parse(start), datetime.parse(end), repeat);
        }
        else throw new ParseException("There is not two dates separated by \" -\"",0);
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
