package comp3350.iPuP.objects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Mark Van Egmond on 2/5/2018.
 */

public class ReservationTime
{
    private Date start;
    private Date end;
    private SimpleDateFormat date;
    private SimpleDateFormat time;

    public ReservationTime(int year, int month, int day, int startHour, int startMinute, int endHour, int endMinute)
    {
        Calendar c = new GregorianCalendar(year, month, day, startHour, startMinute);
        date = new SimpleDateFormat("EEE, d MMM yyyy");
        time = new SimpleDateFormat("h:mm");
        start =  c.getTime();
        c.set(year, month, day, endHour, endMinute);
        end = c.getTime();
    }

    @Override
    public String toString()
    {
        return date.format(start) + ", " + time.format(start) + " - " + time.format(end);
    }

    public Date getStart()
    {
        return start;
    }
    public Date getEnd()
    {
        return end;
    }
}
