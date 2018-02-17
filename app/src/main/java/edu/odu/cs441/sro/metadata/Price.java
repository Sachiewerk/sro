package edu.odu.cs441.sro.metadata;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by michael on 2/17/18.
 *
 * Price class represents the price information of the transaction.
 * The default currency is USD and the default locale is US.
 * The currency and the locale can be explicitly set.
 */
public class Price extends Tag implements Serializable {

    private BigDecimal VALUE;
    private Currency CURRENCY;
    private Locale LOCALE;

    /**
     * Default Constructor with the default currency and locale -
     * the amount is set to zero
     */
    public Price(){
        super("price");
        VALUE = new BigDecimal("0.00");
        CURRENCY = Currency.getInstance("USD");
        LOCALE = Locale.US;
    }

    public Price(double value) {
        super("price");
        VALUE = new BigDecimal(String.valueOf(value));
        CURRENCY = Currency.getInstance("USD");
        LOCALE = Locale.US;
    }

    public Price(float value) {
        super("price");
        VALUE = new BigDecimal(String.valueOf(value));
        CURRENCY = Currency.getInstance("USD");
        LOCALE = Locale.US;
    }
    public Price(int value) {
        super("price");
        VALUE = new BigDecimal(String.valueOf(value) + ".00");
        CURRENCY = Currency.getInstance("USD");
        LOCALE = Locale.US;
    }

    public Price(String value) {
        super("price");
        VALUE = new BigDecimal(value);
        CURRENCY = Currency.getInstance("USD");
        LOCALE = Locale.US;
    }

    public void setDefaultCurrency(String currencyCode) {
        CURRENCY = Currency.getInstance(currencyCode);
    }

    public void setDefaultLocale(Locale locale) {
        LOCALE = locale;
    }

    public BigDecimal getValue() {
        return VALUE;
    }

    public void setValue(BigDecimal value) {
        VALUE = value;
    }

    public void setValue(String value) {
        VALUE = new BigDecimal(value);
    }

    public void setValue(double value) {
        VALUE = new BigDecimal(String.valueOf(value));
    }

    public void setValue(float value) {
        VALUE = new BigDecimal(String.valueOf(value));
    }

    public void setValue(int value) {
        VALUE = new BigDecimal(String.valueOf(value) + ".00");
    }

    public String getSymbol() {
        return CURRENCY.getSymbol(LOCALE);
    }

    public String getPrice(){
        BigDecimal displayValue =
                VALUE.setScale(CURRENCY.getDefaultFractionDigits(), RoundingMode.HALF_EVEN);

        return displayValue.toPlainString();
    }

    public String toString() {
        BigDecimal displayValue =
                VALUE.setScale(CURRENCY.getDefaultFractionDigits(), RoundingMode.HALF_EVEN);
        NumberFormat usdCostFormat = NumberFormat.getCurrencyInstance(LOCALE);

        return usdCostFormat.format(displayValue.doubleValue());
    }

}
