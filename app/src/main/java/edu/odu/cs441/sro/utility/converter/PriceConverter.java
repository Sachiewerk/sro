package edu.odu.cs441.sro.utility.converter;

import android.arch.persistence.room.TypeConverter;
import java.math.BigDecimal;

public class PriceConverter {
    @TypeConverter
    public BigDecimal fromLong(Long value) {
        return value == null ? null : new BigDecimal(value).divide(new BigDecimal(100));
    }

    @TypeConverter
    public Long toLong(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        } else {
            return bigDecimal.multiply(new BigDecimal(100)).longValue();
        }
    }
}
