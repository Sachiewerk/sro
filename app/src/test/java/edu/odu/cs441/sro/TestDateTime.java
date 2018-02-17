package edu.odu.cs441.sro;

import org.junit.Test;
import edu.odu.cs441.sro.metadata.DateTime;
import static org.junit.Assert.*;

/**
 * Test edu.odu.cs441.sro.metadata.DateTime
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestDateTime {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    public TestDateTime() {
        initializeDateTime();
    }

    private void initializeDateTime() {
        year = 2018;
        month = 1;
        day = 1;
        hour = 0;
        minute = 0;
        second= 0;
    }

    @Test
    public void TestDateTime() throws Exception {
        DateTime dateTime = new DateTime(year, month, day, hour, minute, second);

        assertEquals("The year as string was not correct", dateTime.getYear(), "2018");

        assertEquals("The long month as string was not correct",
                dateTime.getLongMonth(),
                "January");

        assertEquals("The short month as string was not correct",
                dateTime.getShortMonth(),
                "Jan");

        assertEquals("The day as string was not correct",
                dateTime.getDay(),
                "1");

        assertEquals("The hour as string was not correct",
                dateTime.getHour(),
                "0");

        assertEquals("The minute as string was not correct",
                dateTime.getMinute(),
                "0");

        assertEquals("The second as string was not correct",
                dateTime.getSecond(),
                "0");

        assertEquals("The year as integer was not correct",
                dateTime.getIntYear(),
                2018);

        assertEquals("The month as integer was not correct",
                dateTime.getIntMonth(),
                1);


        assertEquals("The day as integer was not correct",
                dateTime.getIntDay(),
                1);

        assertEquals("The hour as integer was not correct",
                dateTime.getIntHour(),
                0);

        assertEquals("The minute as integer was not correct",
                dateTime.getIntMinute(),
                0);

        assertEquals("The second as integer was not correct",
                dateTime.getIntSecond(),
                0);

        assertEquals("The string representation was not correct",
                dateTime.toString(),
                "01-01-2018 at 12:00:00 AM");
    }
}