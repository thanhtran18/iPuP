package comp3350.iPuP.objects;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DaySlot extends TimeSlot
{
    private ArrayList<TimeSlot> timeSlots;

    public DaySlot(Date newStart, Date newEnd)
    {
        super(newStart, newEnd);
        this.timeSlots = new ArrayList<TimeSlot>();


        Calendar start = new GregorianCalendar();
        Calendar end = new GregorianCalendar();
        start.setTime(newStart);
        end.setTime(newEnd);

        int numSlots = (int)(end.getTimeInMillis() - start.getTimeInMillis()) / 1000 / 60 / 30;

        for (int i = 0; i < numSlots; i++)
        {
            Date startTime = start.getTime();
            start.add(Calendar.MINUTE, 30);
            Date endTime = start.getTime();
            this.addTimeSlot(new TimeSlot(startTime, endTime));
        }
    }

    public DaySlot(Date newStart, Date newEnd, String id)
    {
        this(newStart, newEnd);
        this.slotID = id;
    }

    private void addTimeSlot(TimeSlot newSlot)
    {
        timeSlots.add(newSlot);
    }

    public ArrayList<TimeSlot> getTimeSlots()
    {
        return this.timeSlots;
    }
}
