package edu.odu.cs441.sro.utility.data;

import java.math.BigDecimal;

public class StringPriceParser {

    private String stringPrice;
    private BigDecimal decimalPrice;

    public StringPriceParser(String stringPrice) {
        this.stringPrice = stringPrice;
    }

    public StringPriceParser(BigDecimal decimalPrice) {
        this.decimalPrice = decimalPrice;
    }

    public BigDecimal getDecimalValue() {
        BigDecimal bdPrice = null;
        if(stringPrice != null) {
            try {
                stringPrice = stringPrice.replace("$", "").trim();
                Double doublePrice = Double.valueOf(stringPrice);
                bdPrice = BigDecimal.valueOf(doublePrice);
            } catch(Exception e) {
                e.printStackTrace();
                bdPrice = null;
            }
        }
        return bdPrice;
    }

    public String getStringValue() {
        String stringPrice = "";
        if(decimalPrice != null) {
            stringPrice = decimalPrice.setScale(2).toString();
        }
        return "$" + stringPrice;
    }
}
