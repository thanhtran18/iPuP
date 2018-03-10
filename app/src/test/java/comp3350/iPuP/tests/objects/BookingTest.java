package comp3350.iPuP.tests.objects;

import junit.framework.TestCase;

import java.text.SimpleDateFormat;
import java.util.Date;

import comp3350.iPuP.objects.Booking;

/**
 * Created by ThanhTran on 2018-03-10.
 */
public class BookingTest extends TestCase
{
    public void testToStringPastTime() throws Exception
    {
        SimpleDateFormat timeFormat = new SimpleDateFormat("EEE, d MMM yyyy, h:mm a");
        Date start = timeFormat.parse("Sat, 10 Mar 2018, 2:18 AM");
        Date end = timeFormat.parse("Sat, 10 Mar 2018, 6:19 AM");
        Booking booking = new Booking("goat", Long.parseLong("02"), "1 address avenue", start, end);
        assertEquals("\n1 address avenue\nSat, 10 Mar 2018, 2:18 AM - Sat, 10 Mar 2018, 6:19 AM\n", booking.toString());
    }

    public void testToStringFutureTime() throws Exception
    {
        SimpleDateFormat timeFormat = new SimpleDateFormat("EEE, d MMM yyyy, h:mm a");
        Date start = timeFormat.parse("Tue, 19 Jun 2018, 2:18 AM");
        Date end = timeFormat.parse("Tue, 19 Jun 2018, 6:19 AM");
        Booking booking = new Booking("goat", Long.parseLong("02"), "1 address avenue", start, end);
        assertEquals("\n1 address avenue (hold to cancel this booking) \nTue, 19 Jun 2018, 2:18 AM - Tue, 19 Jun 2018, 6:19 AM\n", booking.toString());
    }

}