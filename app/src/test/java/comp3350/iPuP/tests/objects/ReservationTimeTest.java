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
        assertTrue("Tue, 4 Apr 2017, 8:30 - 9:30".equals((new ReservationTime(2017,3, 4, 8,30,9,30)).toString()));
        assertFalse((new ReservationTime(2017,3, 4, 8,30,9,30)).equals(new ReservationTime(2018,3, 4, 8,30,9,30)));
        assertFalse((new ReservationTime(2017,3, 4, 8,30,9,30)).equals(null));
        assertFalse((new ReservationTime(2017,3, 4, 8,30,9,30)).equals(this));

        assertTrue((new ReservationTime(2017,3, 4, 8,30,8,30)).getStart().equals((new GregorianCalendar(2017,3, 4, 8,30).getTime())));

        assertTrue((new ReservationTime(2017,3, 4, 8,30,9,30)).getEnd().equals((new GregorianCalendar(2017,3, 4, 9,30).getTime())));

        assertTrue("Invalid date".equals((new ReservationTime(0,0, 0, 0,0,0,0)).toString()));
    }
}
