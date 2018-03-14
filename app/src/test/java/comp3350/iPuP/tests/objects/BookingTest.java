package comp3350.iPuP.tests.objects;

import android.support.v4.app.NavUtils;
import junit.framework.TestCase;
import org.junit.rules.ExpectedException;
import java.text.SimpleDateFormat;
import java.util.Date;
import comp3350.iPuP.objects.Booking;


public class BookingTest extends TestCase
{
    public ExpectedException thrown = ExpectedException.none();
    private SimpleDateFormat timeFormat = new SimpleDateFormat("EEE, d MMM yyyy, h:mm a");

    public BookingTest(String arg0)
    {
        super(arg0);
    }

    public void testLegalBooking() throws Exception
    {
        Date start = timeFormat.parse("Tue, 19 Jun 2018, 2:18 AM");
        Date end = timeFormat.parse("Tue, 19 Jun 2018, 6:19 AM");
        Booking booking = new Booking("goat", Long.parseLong("02"), "1 address avenue", start, end);
        assertTrue("goat".equals(booking.getName()));
        assertTrue("1 address avenue".equals(booking.getAddress()));
        assertTrue(02 == booking.getTimeSlotId());
        assertTrue(start.equals(booking.getStart()));
        assertTrue(end.equals(booking.getEnd()));
    }

    public void testEmptyNameBooking() throws Exception
    {
        Booking booking;
        Date start = timeFormat.parse("Tue, 19 Jun 2018, 2:18 AM");
        Date end = timeFormat.parse("Tue, 19 Jun 2018, 6:19 AM");
        try
        {
            booking = new Booking("", Long.parseLong("02"), "1 address avenue", start, end);
            fail();
        }
        catch (Exception e)
        {
            assertEquals("User must have a username!", e.getMessage());
        }
    }



    public void testToStringPastTime() throws Exception
    {
        Date start = timeFormat.parse("Sat, 10 Mar 2018, 2:18 AM");
        Date end = timeFormat.parse("Sat, 10 Mar 2018, 6:19 AM");
        Booking booking = new Booking("goat", Long.parseLong("02"), "1 address avenue", start, end);
        assertEquals("\n1 address avenue\nSat, 10 Mar 2018, 2:18 AM - Sat, 10 Mar 2018, 6:19 AM\n", booking.toString());
    }

    public void testToStringFutureTime() throws Exception
    {
        Date start = timeFormat.parse("Tue, 19 Jun 2018, 2:18 AM");
        Date end = timeFormat.parse("Tue, 19 Jun 2018, 6:19 AM");
        Booking booking = new Booking("goat", Long.parseLong("02"), "1 address avenue", start, end);
        assertEquals("\n1 address avenue (hold to cancel this booking) \nTue, 19 Jun 2018, 2:18 AM - Tue, 19 Jun 2018, 6:19 AM\n", booking.toString());
    }

    public void testStartAfterEndTime() throws Exception
    {
        Date start = timeFormat.parse("Tue, 20 Jun 2018, 2:18 AM");
        Date end = timeFormat.parse("Tue, 19 Jun 2018, 6:19 AM");
        try
        {
            Booking booking = new Booking("goat", Long.parseLong("02"), "1 address avenue", start, end);
            fail();
        }
        catch (Exception e)
        {
            assertEquals("Starting time must be after ending time!", e.getMessage());
        }
    }

}