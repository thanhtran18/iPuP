package comp3350.iPuP.tests.objects;

import junit.framework.TestCase;

import java.sql.Time;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import comp3350.iPuP.objects.TimeSlot;


public class TimeSlotTest extends TestCase
{
    public TimeSlotTest(String arg0)
    {
        super(arg0);
    }

    public void testTimeSlot()
    {
        Date start, end;
        TimeSlot time;
        Calendar c = Calendar.getInstance();
        c.set(2017,3, 4, 8,30);
        start = c.getTime();
        c.add(Calendar.HOUR, 2);
        end = c.getTime();
        time = new TimeSlot(start, end);
        assertEquals("Tue, 4 Apr 2017, 8:30 AM - Tue, 4 Apr 2017, 10:30 AM", time.toString());
        assertFalse(time.getIsBooked());


        c.set(2017,8, 20, 12,0);
        start = c.getTime();
        c.add(Calendar.HOUR, 200);
        end = c.getTime();
        time = new TimeSlot(start, end);
        assertEquals("Wed, 20 Sep 2017, 12:00 PM - Thu, 28 Sep 2017, 8:00 PM", time.toString());
        assertFalse(time.getIsBooked());


        c.set(4,11, 30, 12,30);
        start = c.getTime();
        c.set(5000,3, 4, 18,00);
        end = c.getTime();
        time = new TimeSlot(start, end, 54);
        assertEquals("Tue, 30 Dec 0004, 12:30 PM - Fri, 4 Apr 5000, 6:00 PM", time.toString());
        assertEquals(54, time.getSlotID());
        assertFalse(time.getIsBooked());
        time.setIsBooked(true);
        assertTrue(time.getIsBooked());
        time.setIsBooked(false);
        assertFalse(time.getIsBooked());

        try
        {
            time = TimeSlot.parseString("Tue, 30 Dec 0004, 12:30 PM - Fri, 4 Apr 5000, 6:00 PM");
            assertEquals("Tue, 30 Dec 0004, 12:30 PM - Fri, 4 Apr 5000, 6:00 PM", time.toString());
            time = TimeSlot.parseString("Wed, 20 Sep 2017, 12:00 PM - Thu, 28 Sep 2017, 8:00 PM");
            assertEquals("Wed, 20 Sep 2017, 12:00 PM - Thu, 28 Sep 2017, 8:00 PM", time.toString());
            time = TimeSlot.parseString("Tue, 4 Apr 2017, 8:30 AM - Tue, 4 Apr 2017, 10:30 AM");
            assertEquals("Tue, 4 Apr 2017, 8:30 AM - Tue, 4 Apr 2017, 10:30 AM", time.toString());
            time = TimeSlot.parseString("Wed, 14 Mar 2018, 00:30 AM - Wed, 14 Mar 2018, 10:30 AM");
            assertEquals("Wed, 14 Mar 2018, 12:30 AM - Wed, 14 Mar 2018, 10:30 AM", time.toString());
        }
        catch (Exception e)
        {
            fail();
        }

        try
        {

            time = TimeSlot.parseString("");
            fail();
        }
        catch(ParseException pe){}

        try
        {

            time = TimeSlot.parseString(null);
            fail();
        }
        catch(ParseException pe){}

        try
        {

            time = TimeSlot.parseString("hello world");
            fail();
        }
        catch(ParseException pe){}

        try
        {

            time = TimeSlot.parseString("efaupfhaewifhld");
            fail();
        }
        catch(ParseException pe){}

        time = new TimeSlot(null,c.getTime(), -1);
        assertNull(time.getStart());
        assertEquals(time.getEnd(), c.getTime());
        time = new TimeSlot(c.getTime(),null, -1, true);
        assertNull(time.getEnd());
        assertEquals(time.getStart(), c.getTime());
        time = new TimeSlot(null,null, -1);
        assertNull(time.getStart());
        assertNull(time.getEnd());


        boolean[] weekCode;
        try
        {
            weekCode = TimeSlot.weekCodeToBoolArray("1101001");
            assertTrue(weekCode[0]);
            assertTrue(weekCode[1]);
            assertFalse(weekCode[2]);
            assertTrue(weekCode[3]);
            assertFalse(weekCode[4]);
            assertFalse(weekCode[5]);
            assertTrue(weekCode[6]);

            weekCode = TimeSlot.weekCodeToBoolArray("0000000");
            assertFalse(weekCode[0]);
            assertFalse(weekCode[1]);
            assertFalse(weekCode[2]);
            assertFalse(weekCode[3]);
            assertFalse(weekCode[4]);
            assertFalse(weekCode[5]);
            assertFalse(weekCode[6]);

            weekCode = TimeSlot.weekCodeToBoolArray("1111111");
            assertTrue(weekCode[0]);
            assertTrue(weekCode[1]);
            assertTrue(weekCode[2]);
            assertTrue(weekCode[3]);
            assertTrue(weekCode[4]);
            assertTrue(weekCode[5]);
            assertTrue(weekCode[6]);
        }
        catch (Exception e)
        {
            fail();
        }

        try
        {
            weekCode = TimeSlot.weekCodeToBoolArray("");
            fail();
        }
        catch(ParseException pe) { }
        try
        {
            weekCode = TimeSlot.weekCodeToBoolArray(null);
            fail();
        }
        catch(ParseException pe) { }
        try
        {
            weekCode = TimeSlot.weekCodeToBoolArray("111111111110000001111111111");
            fail();
        }
        catch(ParseException pe) { }
        try
        {
            weekCode = TimeSlot.weekCodeToBoolArray("abc1111");
            fail();
        }
        catch(ParseException pe) { }
        try
        {
            weekCode = TimeSlot.weekCodeToBoolArray("100001h");
            fail();
        }
        catch(ParseException pe) { }
    }
}
