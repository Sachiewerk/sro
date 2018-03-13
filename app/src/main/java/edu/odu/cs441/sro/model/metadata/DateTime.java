package edu.odu.cs441.sro.model.metadata;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by michael on 2/16/18.
 * DateTime class represents the created date of any transactions.
 */
public class DateTime extends Tag implements Serializable, Comparable<DateTime>, Cloneable {

    private Date DATE;


    /**
     * Default Constructor. The date is set to the time when this DateTime object
     * is instantiated.
     * @param uuid UUID
     */
    public DateTime(UUID uuid) {
        // Create this date with the current date and time.
        super(uuid);
        DATE = new Date();
    }

    /**
     * Copy Constructor
     * @param toCopy DateTime
     */
    public DateTime(DateTime toCopy) {
        super(toCopy.getUUID());
        DATE = new Date(
                toCopy.getIntYear(),
                toCopy.getIntMonth(),
                toCopy.getIntDay(),
                toCopy.getIntHour(),
                toCopy.getIntMinute(),
                toCopy.getIntSecond()
        );
    }

    /**
     * Create this DateTime object with the given Date
     * @param uuid
     * @param date
     */
    public DateTime(UUID uuid, Date date) {
        super(uuid);
        DATE = date;
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
        DATE = new Date(year - 1900, month - 1, dayOfMonth, hour, minute, second);
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
        DATE = new Date(year - 1900, month - 1, dayOfMonth, 0, 0);
    }

    /**
     * Return a deep copy of this date object
     * @return Date date
     */
    public Date getDateTime() {
        return (Date)DATE.clone();
    }

    /**
     * Return the year as string
     * @return String year
     */
    public String getYear(){
        SimpleDateFormat ft =
                new SimpleDateFormat ("yyyy");
        return ft.format(DATE);
    }

    /**
     * Return the long month as string: January instead of Jan
     * @return String long month
     */
    public String getLongMonth() {
        if(DATE.getMonth() + 1 == 1) {
            return "January";
        }
        else if(DATE.getMonth() + 1 == 2) {
            return "February";
        }
        else if(DATE.getMonth() + 1 == 3) {
            return "March";
        }
        else if(DATE.getMonth() + 1 == 4) {
            return "April";
        }
        else if(DATE.getMonth() + 1 == 5) {
            return "May";
        }
        else if(DATE.getMonth() + 1 == 6) {
            return "June";
        }
        else if(DATE.getMonth() + 1 == 7) {
            return "July";
        }
        else if(DATE.getMonth() + 1 == 8) {
            return "August";
        }
        else if(DATE.getMonth() + 1 == 9) {
            return "September";
        }
        else if(DATE.getMonth() + 1 == 10) {
            return "October";
        }
        else if(DATE.getMonth() + 1 == 11) {
            return "November";
        }
        else if(DATE.getMonth() + 1 == 12) {
            return "December";
        }
        else {
            throw new IllegalArgumentException("The month is out of range.");
        }
    }

    /**
     * Return the short month as string: Jan instead of January
     * @return String short month
     */
    public String getShortMonth(){
        if(DATE.getMonth() + 1 == 1) {
            return "Jan";
        }
        else if(DATE.getMonth() + 1 == 2) {
            return "Feb";
        }
        else if(DATE.getMonth() + 1 == 3) {
            return "Mar";
        }
        else if(DATE.getMonth() + 1 == 4) {
            return "Apr";
        }
        else if(DATE.getMonth() + 1 == 5) {
            return "May";
        }
        else if(DATE.getMonth() + 1 == 6) {
            return "Jun";
        }
        else if(DATE.getMonth() + 1 == 7) {
            return "Jul";
        }
        else if(DATE.getMonth() + 1 == 8) {
            return "Aug";
        }
        else if(DATE.getMonth() + 1 == 9) {
            return "Sep";
        }
        else if(DATE.getMonth() + 1 == 10) {
            return "Oct";
        }
        else if(DATE.getMonth() + 1 == 11) {
            return "Nov";
        }
        else if(DATE.getMonth() + 1 == 12) {
            return "Dec";
        }
        else {
            throw new IllegalArgumentException("The month is out of range.");
        }
    }

    /**
     * Return the two-digit month as string
     * @return String two-digit month
     */
    public String getTwoDigitMonth() {
        SimpleDateFormat ft =
                new SimpleDateFormat ("MM");
        return ft.format(DATE);
    }

    /**
     * Return the day as string
     * @return String day
     */
    public String getDay() {
        SimpleDateFormat ft =
                new SimpleDateFormat ("dd");
        return ft.format(DATE);
    }

    /**
     * Return the hour as string (Military)
     * @return String hour
     */
    public String getHour() {
        SimpleDateFormat ft =
                new SimpleDateFormat ("HH");
        return ft.format(DATE);
    }

    /**
     * Return AMPM Hour as string
     * @return String hour
     */
    public String getAMPMHour() {
        SimpleDateFormat ft =
                new SimpleDateFormat ("hh");
        return ft.format(DATE);
    }
    /**
     * Return the minute as string
     * @return String minute
     */
    public String getMinute() {
        SimpleDateFormat ft =
                new SimpleDateFormat ("mm");
        return ft.format(DATE);
    }

    /**
     * Return the second as string
     * @return String second
     */
    public String getSecond() {
        SimpleDateFormat ft =
                new SimpleDateFormat ("ss");
        return ft.format(DATE);
    }

    /**
     * Get AM or PM
     * @return String AM or PM
     */
    public String getAMPM() {
        SimpleDateFormat ft =
                new SimpleDateFormat ("a");
        return ft.format(DATE);
    }

    /**
     * Return the year as integer
     * @return int year
     */
    public int getIntYear(){
        SimpleDateFormat ft =
                new SimpleDateFormat ("yyyy");
        return Integer.parseInt(ft.format(DATE));
    }

    /**
     * Return the month as integer
     * @return int month
     */
    public int getIntMonth() {
        return DATE.getMonth() + 1;
    }

    /**
     * Return the day as integer
     * @return int day
     */
    public int getIntDay() {
        SimpleDateFormat ft =
                new SimpleDateFormat ("dd");
        return Integer.parseInt(ft.format(DATE));
    }

    /**
     * Return the hour as integer (Military)
     * @return int hour
     */
    public int getIntHour() {
        return DATE.getHours();
    }

    /**
     * Return the AMPM hour as integer
     * @return int hour
     */
    public int getIntAMPMHour() {
        SimpleDateFormat ft =
                new SimpleDateFormat ("hh");
        return Integer.parseInt(ft.format(DATE));
    }

    /**
     * Return the minute as integer
     * @return int minute
     */
    public int getIntMinute() {
        return DATE.getMinutes();
    }

    /**
     * Return the second as integer
     * @return int second
     */
    public int getIntSecond() {
        return DATE.getSeconds();
    }

    /**
     * Change this datetime to the specified datetime
     * @param newDate LocalDateTime
     */
    public void setDateTime(Date newDate){
        DATE = newDate;
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

        DATE = new Date(year - 1900, month - 1, dayOfMonth, hour, minute, second);
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

        DATE = new Date(year - 1900, month - 1, dayOfMonth,
                DATE.getHours(), DATE.getMinutes(), DATE.getSeconds());
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
                this.DATE.equals(dateTime.DATE);
    }

    /**
     * Override hashCode
     * @return int hashCode of this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(getUUID(), DATE);
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
        return this.DATE.compareTo(dateTime.DATE);
    }

    /**
     * Returns the string representation of this DateTime as
     * "MM-dd-yyyy 'at' hh:mm:ss a"
     * @return String DateTime
     */
    @Override
    public String toString() {
        SimpleDateFormat ft =
                new SimpleDateFormat ("MM-dd-yyyy 'at' hh:mm:ss a");
        return ft.format(DATE);
    }
}
