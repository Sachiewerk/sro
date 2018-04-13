package edu.odu.cs441.sro.converter;

import android.arch.persistence.room.TypeConverter;

import org.joda.time.DateTime;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static DateTime fromTimestamp(Long value) {
        return value == null ? null : new DateTime(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(DateTime date) {
        return date == null ? null : date.getMillis();
    }
}