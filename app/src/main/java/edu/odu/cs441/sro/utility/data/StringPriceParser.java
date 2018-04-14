package edu.odu.cs441.sro.utility.data;

import java.math.BigDecimal;
import java.text.DecimalFormat;

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
                return Double.valueOf(stringPrice);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getStringValue() {
        DecimalFormat df2 = new DecimalFormat(".##");
        if(decimalPrice != null) {
            return "$" + df2.format(decimalPrice);
        }
        return null;
    }
}
