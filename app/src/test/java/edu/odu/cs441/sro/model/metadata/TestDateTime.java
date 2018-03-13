package edu.odu.cs441.sro.model.metadata;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.UUID;

/**
 * Created by michael on 2/16/18.
 * Test edu.odu.cs441.sro.Model.metadata.DateTime
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestDateTime {
    private final UUID uuid = UUID.randomUUID();
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    public TestDateTime() {
        initializeDateTime();
    }

    /**
     * The Test Date and Time is
     *
     * February 17, 2018
     * 6:56:19 PM
     */
    private void initializeDateTime() {
        year = 2018;
        month = 2;
        day = 17;
        hour = 18;
        minute = 56;
        second= 19;
    }

    @Test
    public void TestDateTimeUUIDIntIntIntIntIntInt() throws Exception {
        DateTime dateTime = new DateTime(uuid, year, month, day, hour, minute, second);

        assertEquals("The year as string was not correct", dateTime.getYear(), "2018");

        assertEquals("The long month as string was not correct",
                dateTime.getLongMonth(),
                "February");

        assertEquals("The short month as string was not correct",
                dateTime.getShortMonth(),
                "Feb");

        assertEquals("The two digit month as string was not correct",
                dateTime.getTwoDigitMonth(),
                "02");

        assertEquals("The day as string was not correct",
                dateTime.getDay(),
                "17");

        assertEquals("The hour as string was not correct",
                dateTime.getHour(),
                "18");

        assertEquals("The hour as string was not correct",
                dateTime.getAMPMHour(),
                "06");

        assertEquals("The minute as string was not correct",
                dateTime.getMinute(),
                "56");

        assertEquals("The second as string was not correct",
                dateTime.getSecond(),
                "19");

        assertEquals("The year as integer was not correct",
                dateTime.getIntYear(),
                2018);

        assertEquals("The month as integer was not correct",
                dateTime.getIntMonth(),
                2);


        assertEquals("The day as integer was not correct",
                dateTime.getIntDay(),
                17);

        assertEquals("The hour as integer was not correct",
                dateTime.getIntHour(),
                18);

        assertEquals("The hour as integer was not correct",
                dateTime.getIntAMPMHour(),
                6);

        assertEquals("The minute as integer was not correct",
                dateTime.getIntMinute(),
                56);

        assertEquals("The second as integer was not correct",
                dateTime.getIntSecond(),
                19);

        assertEquals("The AM-PM was not correct",
                dateTime.getAMPM(),
                "PM");

        assertEquals("The string representation was not correct",
                dateTime.toString(),
                "02-17-2018 at 06:56:19 PM");
    }

    @Test
    public void TestDateTimeUUIDIntIntInt() {
        DateTime dateTime = new DateTime(uuid, year, month, day);

        assertEquals("The year as string was not correct", dateTime.getYear(), "2018");

        assertEquals("The long month as string was not correct",
                dateTime.getLongMonth(),
                "February");

        assertEquals("The short month as string was not correct",
                dateTime.getShortMonth(),
                "Feb");

        assertEquals("The two digit month as string was not correct",
                dateTime.getTwoDigitMonth(),
                "02");

        assertEquals("The day as string was not correct",
                dateTime.getDay(),
                "17");

        assertEquals("The hour as string was not correct",
                dateTime.getHour(),
                "00");

        assertEquals("The hour as string was not correct",
                dateTime.getAMPMHour(),
                "12");

        assertEquals("The minute as string was not correct",
                dateTime.getMinute(),
                "00");

        assertEquals("The second as string was not correct",
                dateTime.getSecond(),
                "00");

        assertEquals("The year as integer was not correct",
                dateTime.getIntYear(),
                2018);

        assertEquals("The month as integer was not correct",
                dateTime.getIntMonth(),
                2);


        assertEquals("The day as integer was not correct",
                dateTime.getIntDay(),
                17);

        assertEquals("The hour as integer was not correct",
                dateTime.getIntHour(),
                0);

        assertEquals("The hour as integer was not correct",
                dateTime.getIntAMPMHour(),
                12);

        assertEquals("The minute as integer was not correct",
                dateTime.getIntMinute(),
                0);

        assertEquals("The second as integer was not correct",
                dateTime.getIntSecond(),
                0);

        assertEquals("The AM-PM was not correct",
                dateTime.getAMPM(),
                "AM");

        assertEquals("The string representation was not correct",
                dateTime.toString(),
                "02-17-2018 at 12:00:00 AM");
    }

    @Test
    public void TestSetDateTimeDate() {
        DateTime dateTime = new DateTime(uuid);

        // 9-10-1989
        // 4:30:08AM
        Date newDateTime = new Date(
                89,
                9 - 1,
                10,
                4,
                30,
                8
        );

        dateTime.setDateTime(newDateTime);

        assertEquals("The year as string was not correct", dateTime.getYear(), "1989");

        assertEquals("The long month as string was not correct",
                dateTime.getLongMonth(),
                "September");

        assertEquals("The short month as string was not correct",
                dateTime.getShortMonth(),
                "Sep");

        assertEquals("The two digit month as string was not correct",
                dateTime.getTwoDigitMonth(),
                "09");

        assertEquals("The day as string was not correct",
                dateTime.getDay(),
                "10");

        assertEquals("The hour as string was not correct",
                dateTime.getHour(),
                "04");

        assertEquals("The hour as string was not correct",
                dateTime.getAMPMHour(),
                "04");

        assertEquals("The minute as string was not correct",
                dateTime.getMinute(),
                "30");

        assertEquals("The second as string was not correct",
                dateTime.getSecond(),
                "08");

        assertEquals("The year as integer was not correct",
                dateTime.getIntYear(),
                1989);

        assertEquals("The month as integer was not correct",
                dateTime.getIntMonth(),
                9);


        assertEquals("The day as integer was not correct",
                dateTime.getIntDay(),
                10);

        assertEquals("The hour as integer was not correct",
                dateTime.getIntHour(),
                4);

        assertEquals("The hour as integer was not correct",
                dateTime.getIntAMPMHour(),
                4);

        assertEquals("The minute as integer was not correct",
                dateTime.getIntMinute(),
                30);

        assertEquals("The second as integer was not correct",
                dateTime.getIntSecond(),
                8);

        assertEquals("The AM-PM was not correct",
                dateTime.getAMPM(),
                "AM");

        assertEquals("The string representation was not correct",
                dateTime.toString(),
                "09-10-1989 at 04:30:08 AM");
    }

    @Test
    public void TestSetDateTimeIntIntIntIntIntInt() {
        DateTime dateTime = new DateTime(uuid);

        // 9-10-1989
        // 4:30:08AM
        dateTime.setDateTime(
                1989,
                9,
                10,
                4,
                30,
                8
        );

        assertEquals("The year as string was not correct", dateTime.getYear(), "1989");

        assertEquals("The long month as string was not correct",
                dateTime.getLongMonth(),
                "September");

        assertEquals("The short month as string was not correct",
                dateTime.getShortMonth(),
                "Sep");

        assertEquals("The two digit month as string was not correct",
                dateTime.getTwoDigitMonth(),
                "09");

        assertEquals("The day as string was not correct",
                dateTime.getDay(),
                "10");

        assertEquals("The hour as string was not correct",
                dateTime.getHour(),
                "04");

        assertEquals("The hour as string was not correct",
                dateTime.getAMPMHour(),
                "04");

        assertEquals("The minute as string was not correct",
                dateTime.getMinute(),
                "30");

        assertEquals("The second as string was not correct",
                dateTime.getSecond(),
                "08");

        assertEquals("The year as integer was not correct",
                dateTime.getIntYear(),
                1989);

        assertEquals("The month as integer was not correct",
                dateTime.getIntMonth(),
                9);


        assertEquals("The day as integer was not correct",
                dateTime.getIntDay(),
                10);

        assertEquals("The hour as integer was not correct",
                dateTime.getIntHour(),
                4);

        assertEquals("The hour as integer was not correct",
                dateTime.getIntAMPMHour(),
                4);

        assertEquals("The minute as integer was not correct",
                dateTime.getIntMinute(),
                30);

        assertEquals("The second as integer was not correct",
                dateTime.getIntSecond(),
                8);

        assertEquals("The AM-PM was not correct",
                dateTime.getAMPM(),
                "AM");

        assertEquals("The string representation was not correct",
                dateTime.toString(),
                "09-10-1989 at 04:30:08 AM");
    }

    @Test
    public void TestSetDateTimeIntIntInt() {
        DateTime dateTime = new DateTime(uuid);

        dateTime.setDateTime(
                1900,
                7,
                1,
                4,
                30,
                8
        );

        // 9-10-1989
        // 4:30:08AM
        dateTime.setDateTime(
                1989,
                9,
                10
        );

        assertEquals("The year as string was not correct", dateTime.getYear(), "1989");

        assertEquals("The long month as string was not correct",
                dateTime.getLongMonth(),
                "September");

        assertEquals("The short month as string was not correct",
                dateTime.getShortMonth(),
                "Sep");

        assertEquals("The two digit month as string was not correct",
                dateTime.getTwoDigitMonth(),
                "09");

        assertEquals("The day as string was not correct",
                dateTime.getDay(),
                "10");

        assertEquals("The hour as string was not correct",
                dateTime.getHour(),
                "04");

        assertEquals("The hour as string was not correct",
                dateTime.getAMPMHour(),
                "04");

        assertEquals("The minute as string was not correct",
                dateTime.getMinute(),
                "30");

        assertEquals("The second as string was not correct",
                dateTime.getSecond(),
                "08");

        assertEquals("The year as integer was not correct",
                dateTime.getIntYear(),
                1989);

        assertEquals("The month as integer was not correct",
                dateTime.getIntMonth(),
                9);


        assertEquals("The day as integer was not correct",
                dateTime.getIntDay(),
                10);

        assertEquals("The hour as integer was not correct",
                dateTime.getIntHour(),
                4);

        assertEquals("The hour as integer was not correct",
                dateTime.getIntAMPMHour(),
                4);

        assertEquals("The minute as integer was not correct",
                dateTime.getIntMinute(),
                30);

        assertEquals("The second as integer was not correct",
                dateTime.getIntSecond(),
                8);

        assertEquals("The AM-PM was not correct",
                dateTime.getAMPM(),
                "AM");

        assertEquals("The string representation was not correct",
                dateTime.toString(),
                "09-10-1989 at 04:30:08 AM");
    }
}