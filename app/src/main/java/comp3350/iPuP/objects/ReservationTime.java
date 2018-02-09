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
    private String startTime;
    private String endTime;

    public ReservationTime(int year, int month, int day, int startHour, int startMinute, int endHour, int endMinute)
    {
        Calendar c = new GregorianCalendar(year, month, day, startHour, startMinute);
        Date start = new Date(year, month, day, startHour, startMinute);
        //date = new SimpleDateFormat("EEE, d MMM yyyy");
        date = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        //time = new SimpleDateFormat("h:mm");
        //start =  c.getTime();
        startTime = date.format(start);
        //c.set(year, month, day, endHour, endMinute);
        //end = c.getTime();
        Date end = new Date(year, month, day, startHour, startMinute);
        endTime = date.format(end);

    }

    @Override
    public String toString()
    {
        return date.format(start) + ", " + time.format(start) + " - " + time.format(end);
    }

    /*public Date getStart()
    {
        return start;
    }
    public Date getEnd()
    {
        return end;
    }*/

    public String getStart()
    {
        return startTime;
    }
    public String  getEnd()
    {
        return endTime;
    }
}
