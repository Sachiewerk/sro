package edu.odu.cs441.sro.metadata;

import java.io.Serializable;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Created by michael on 2/16/18.
 * DateTime class represents the created date of any transactions.
 */
public class DateTime extends Tag implements Serializable, Comparable<DateTime>{

    private LocalDateTime DATETIME;

    /**
     * Default Constructor. The date is set to the time when this DateTime object
     * is instantiated.
     */
    public DateTime() {
        // Create this date with the current date and time.
        super("datetime");
        DATETIME = LocalDateTime.now();
    }

    /**
     * Create this DateTime object with the specified date and time.
     * @param year int
     * @param month Month
     * @param dayOfMonth int
     * @param hour int
     * @param minute int
     * @param second int
     */
    public DateTime(int year, int month, int dayOfMonth, int hour, int minute, int second){
        super("datetime");
        DATETIME = LocalDateTime.of(year, Month.of(month), dayOfMonth, hour, minute, second);
    }

    /**
     * Create this DateTime object with just the dates and set the time to midnight
     * @param year int
     * @param month int
     * @param dayOfMonth int
     */
    public DateTime(int year, int month, int dayOfMonth) {
        super("datetime");
        DATETIME = LocalDateTime.of(year, Month.of(month), dayOfMonth, 0, 0);
    }

    /**
     * Return the year as string
     * @return String year
     */
    public String getYear(){
        return String.valueOf(DATETIME.getYear());
    }

    /**
     * Return the long month as string: January instead of Jan
     * @return String long month
     */
    public String getLongMonth() {
        return DATETIME.getMonth().getDisplayName(TextStyle.FULL, Locale.US);
    }

    /**
     * Return the short month as string: Jan instead of January
     * @return String short month
     */
    public String getShortMonth(){
        return DATETIME.getMonth().getDisplayName(TextStyle.SHORT, Locale.US);
    }

    /**
     * Return the day as string
     * @return String day
     */
    public String getDay() {
        return String.valueOf(DATETIME.getDayOfMonth());
    }

    /**
     * Return the hour as string
     * @return String hour
     */
    public String getHour() {
        return String.valueOf(DATETIME.getHour());
    }

    /**
     * Return the minute as string
     * @return String minute
     */
    public String getMinute() {
        return String.valueOf(DATETIME.getMinute());
    }

    /**
     * Return the second as string
     * @return String second
     */
    public String getSecond() {
        return String.valueOf(DATETIME.getSecond());
    }

    /**
     * Return the year as integer
     * @return int year
     */
    public int getIntYear(){
        return DATETIME.getYear();
    }

    /**
     * Return the month as integer
     * @return int month
     */
    public int getIntMonth() {
        return DATETIME.getMonth().getValue();
    }

    /**
     * Return the day as integer
     * @return int day
     */
    public int getIntDay() {
        return DATETIME.getDayOfMonth();
    }

    /**
     * Return the hour as integer
     * @return int hour
     */
    public int getIntHour() {
        return DATETIME.getHour();
    }

    /**
     * Return the minute as integer
     * @return int minute
     */
    public int getIntMinute() {
        return DATETIME.getMinute();
    }

    /**
     * Return the second as integer
     * @return int second
     */
    public int getIntSecond() {
        return DATETIME.getSecond();
    }

    /**
     * Change this datetime to the specified datetime
     * @param newDateTime LocalDateTime
     */
    public void setDateTime(LocalDateTime newDateTime){
        DATETIME = newDateTime;
    }

    /**
     * Change this datetime to the specified datetime parameters
     * which include full date and time
     * @param year int
     * @param month int
     * @param dayOfMonth int
     * @param hour int
     * @param minute int
     * @param second int
     */
    public void setDateTime(
            int year,
            int month,
            int dayOfMonth,
            int hour,
            int minute,
            int second) {

        DATETIME = LocalDateTime.of(year, Month.of(month), dayOfMonth, hour, minute, second);
    }

    /**
     * Change this datetime to the specified datetime parameters
     * which include only date
     * @param year int
     * @param month int
     * @param dayOfMonth int
     */
    public void setDateTime(
            int year,
            int month,
            int dayOfMonth
            ) {

        DATETIME = LocalDateTime.of(year, Month.of(month), dayOfMonth,
                DATETIME.getHour(), DATETIME.getMinute(), DATETIME.getSecond());
    }

    /**
     * Compare this datetime to another datetime from earliest to latest.
     * Returns positive integer if this datetime is later than the given datetime,
     * negative integer if this datetime is earlier than the given datetime,
     * zero if this datetime and the given datetime are equal.
     * @param dateTime
     * @return
     */
    @Override
    public int compareTo(DateTime dateTime) {
        return this.DATETIME.compareTo(dateTime.DATETIME);
    }

    /**
     * Returns the string representation of this DateTime as
     * "MM-dd-yyyy 'at' hh:mm:ss a"
     * @return String DateTime
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy 'at' hh:mm:ss a");
        return DATETIME.format(formatter);
    }
}
