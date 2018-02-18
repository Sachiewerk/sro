package edu.odu.cs441.sro.metadata;

import java.io.Serializable;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by michael on 2/16/18.
 * DateTime class represents the created date of any transactions.
 */
public class DateTime extends Tag implements Serializable, Comparable<DateTime>, Cloneable {

    private LocalDateTime DATETIME;

    /**
     * Default Constructor. The date is set to the time when this DateTime object
     * is instantiated.
     * @param uuid UUID
     */
    public DateTime(UUID uuid) {
        // Create this date with the current date and time.
        super(uuid);
        DATETIME = LocalDateTime.now();
    }

    /**
     * Copy Constructor
     * @param toCopy DateTime
     */
    public DateTime(DateTime toCopy) {
        super(toCopy.getUUID());
        DATETIME = LocalDateTime.of(
                toCopy.getIntYear(),
                toCopy.getIntMonth(),
                toCopy.getIntDay(),
                toCopy.getIntHour(),
                toCopy.getIntMinute(),
                toCopy.getIntSecond()
        );
    }

    /**
     * Create this DateTime object with the specified date and time.
     * @param uuid UUID
     * @param year int
     * @param month int
     * @param dayOfMonth int
     * @param hour int
     * @param minute int
     * @param second int
     */
    public DateTime(
            UUID uuid,
            int year,
            int month,
            int dayOfMonth,
            int hour,
            int minute,
            int second){
        super(uuid);
        DATETIME = LocalDateTime.of(year, Month.of(month), dayOfMonth, hour, minute, second);
    }

    /**
     * Create this DateTime object with the specified date and time.
     * @param uuid UUID
     * @param year int
     * @param month String
     * @param dayOfMonth int
     * @param hour int
     * @param minute int
     * @param second int
     */
    public DateTime(
            UUID uuid,
            int year,
            String month,
            int dayOfMonth,
            int hour,
            int minute,
            int second){
        super(uuid);
        month = month.toUpperCase();
        DATETIME = LocalDateTime.of(year, Month.valueOf(month), dayOfMonth, hour, minute, second);
    }

    /**
     * Create this DateTime object with just the dates and set the time to midnight
     * @param uuid UUID
     * @param year int
     * @param month int
     * @param dayOfMonth int
     */
    public DateTime(UUID uuid, int year, int month, int dayOfMonth) {
        super(uuid);
        DATETIME = LocalDateTime.of(year, Month.of(month), dayOfMonth, 0, 0);
    }

    /**
     * Create this DateTime object with just the dates and set the time to midnight
     * @param uuid UUID
     * @param year int
     * @param month String
     * @param dayOfMonth int
     */
    public DateTime(UUID uuid, int year, String month, int dayOfMonth) {
        super(uuid);
        month = month.toUpperCase();
        DATETIME = LocalDateTime.of(year, Month.valueOf(month), dayOfMonth, 0, 0);
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
     * Return the two-digit month as string
     * @return String two-digit month
     */
    public String getTwoDigitMonth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
        return DATETIME.format(formatter);
    }

    /**
     * Return the day as string
     * @return String day
     */
    public String getDay() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
        return DATETIME.format(formatter);
    }

    /**
     * Return the hour as string (Military)
     * @return String hour
     */
    public String getHour() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
        return DATETIME.format(formatter);
    }

    /**
     * Return AMPM Hour as string
     * @return String hour
     */
    public String getAMPMHour() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh");
        return DATETIME.format(formatter);
    }
    /**
     * Return the minute as string
     * @return String minute
     */
    public String getMinute() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm");
        return DATETIME.format(formatter);
    }

    /**
     * Return the second as string
     * @return String second
     */
    public String getSecond() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ss");
        return DATETIME.format(formatter);
    }

    /**
     * Get AM or PM
     * @return String AM or PM
     */
    public String getAMPM() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("a");
        return DATETIME.format(formatter);
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
     * Return the hour as integer (Military)
     * @return int hour
     */
    public int getIntHour() {
        return DATETIME.getHour();
    }

    /**
     * Return the AMPM hour as integer
     * @return int hour
     */
    public int getIntAMPMHour() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh");
        return Integer.parseInt(DATETIME.format(formatter));
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
     * which include full date and time
     * @param year int
     * @param month String
     * @param dayOfMonth int
     * @param hour int
     * @param minute int
     * @param second int
     */
    public void setDateTime(
            int year,
            String month,
            int dayOfMonth,
            int hour,
            int minute,
            int second) {
        month = month.toUpperCase();
        DATETIME = LocalDateTime.of(year, Month.valueOf(month), dayOfMonth, hour, minute, second);
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
     * Change this datetime to the specified datetime parameters
     * which include only date
     * @param year int
     * @param month String
     * @param dayOfMonth int
     */
    public void setDateTime(
            int year,
            String month,
            int dayOfMonth
    ) {
        month = month.toUpperCase();
        DATETIME = LocalDateTime.of(year, Month.valueOf(month), dayOfMonth,
                DATETIME.getHour(), DATETIME.getMinute(), DATETIME.getSecond());
    }

    /**
     * Compare this DateTime to another DateTime -
     * must have the same UUID and same Local Date and Time
     * @param obj Object
     * @return boolean true if both objects are equal
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof DateTime)) {
            return false;
        }

        DateTime dateTime = (DateTime) obj;

        return this.getUUID().equals(dateTime.getUUID()) &&
                this.DATETIME.equals(dateTime.DATETIME);
    }

    /**
     * Override hashCode
     * @return int hashCode of this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(getUUID(), DATETIME);
    }

    /**
     * Clone this object
     * @return Object deep copy of this object
     */
    @Override
    public Object clone() {
        return new DateTime(this);
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
