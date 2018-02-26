package comp3350.iPuP.tests.objects;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import comp3350.iPuP.objects.ReservationTime;


public class ReservationTimeTest extends TestCase
{
    public ReservationTimeTest(String arg0)
    {
        super(arg0);
    }

    public void testReservationTimes()
    {
        ReservationTime time = new ReservationTime(2017,3, 4, 8,30,2017,3, 4,9,30, false);
        assertEquals("Tue, 4 Apr 2017, 8:30 AM - Tue, 4 Apr 2017, 9:30 AM",time.toString());

        time = new ReservationTime(2017,3, 4, 8,30,2017,3, 4,9,30, false);
        try { assertEquals(time, ReservationTime.parseString("Tue, 4 Apr 2017, 8:30 AM - Tue, 4 Apr 2017, 9:30 AM", false));} catch(Exception e){}
    }
}
