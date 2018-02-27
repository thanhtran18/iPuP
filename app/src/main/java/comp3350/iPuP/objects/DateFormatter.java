package comp3350.iPuP.objects;

import java.text.SimpleDateFormat;

/**
 * Created by Amanjyot on 2018-02-25.
 */

public class DateFormater {

    private SimpleDateFormat date;
    private SimpleDateFormat sqlDate;
    private SimpleDateFormat sqlTime;
    private SimpleDateFormat sqlDateTime;

    public DateFormater()
    {
        this.date = new SimpleDateFormat("EEE, d MMM yyyy");
        this.sqlDate = new SimpleDateFormat("yyyy-MM-dd");
        this.sqlTime = new SimpleDateFormat("hh:mm:ss");
        this.sqlDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    }

    public SimpleDateFormat getDateFormat()
    {
        return this.date;
    }

    public SimpleDateFormat getSqlDateFormat()
    {
        return this.sqlDate;
    }

    public SimpleDateFormat getSqlTimeFormat()
    {
        return this.sqlTime;
    }

    public SimpleDateFormat getSqlDateTimeFormat()
    {
        return this.sqlDateTime;
    }

}
