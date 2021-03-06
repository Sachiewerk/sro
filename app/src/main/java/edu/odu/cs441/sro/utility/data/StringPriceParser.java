package edu.odu.cs441.sro.utility.data;

import java.text.NumberFormat;
import java.util.Locale;

public class StringPriceParser {

    private String stringPrice;
    private Double decimalPrice;

    public StringPriceParser(String stringPrice) {
        this.stringPrice = stringPrice;
    }
    public StringPriceParser(Double decimalPrice) {
        this.decimalPrice = decimalPrice;
    }

    public Double getDecimalValue() {
        if(stringPrice != null) {
            try {
                stringPrice = stringPrice.replace("$", "").trim();
                stringPrice = stringPrice.replaceAll(",", "");
                return Double.valueOf(stringPrice);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getStringValue() {
        if(decimalPrice != null) {
            Locale locale = Locale.ENGLISH;
            NumberFormat nf = NumberFormat.getNumberInstance(locale);
            nf.setMinimumFractionDigits(2);
            nf.setMaximumFractionDigits(2);
            return "$" + nf.format(decimalPrice);
        }
        return null;
    }

    public static String toTwoDigitDecimal(Double value) {
        if(value != null) {
            Locale locale = Locale.ENGLISH;
            NumberFormat nf = NumberFormat.getNumberInstance(locale);
            nf.setMinimumFractionDigits(2);
            nf.setMaximumFractionDigits(2);
            return nf.format(value);
        }
        return "";
    }
}
