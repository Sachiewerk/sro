package edu.odu.cs441.sro.metadata;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by michael on 2/17/18.
 *
 * Price class represents the price information of the transaction.
 * The default currency is USD and the default locale is US.
 * The currency and the locale can be explicitly set.
 */
public class Price extends Tag implements Serializable, Comparable<Price> {

    private BigDecimal VALUE;
    private Currency CURRENCY;
    private Locale LOCALE;

    /**
     * Default Constructor with the default currency and locale -
     * the amount is set to zero
     * @param uuid UUID
     */
    public Price(UUID uuid){
        super(uuid);
        VALUE = new BigDecimal("0.00");
        CURRENCY = Currency.getInstance("USD");
        LOCALE = Locale.US;
    }

    /**
     * Constructor that accepts double value
     * @param value double
     * @param uuid UUID
     */
    public Price(UUID uuid, double value) {
        super(uuid);
        VALUE = new BigDecimal(String.valueOf(value));
        CURRENCY = Currency.getInstance("USD");
        LOCALE = Locale.US;
    }

    /**
     * Constructor that accepts floating-point value
     * @param uuid UUID
     * @param value float
     */
    public Price(UUID uuid, float value) {
        super(uuid);
        VALUE = new BigDecimal(String.valueOf(value));
        CURRENCY = Currency.getInstance("USD");
        LOCALE = Locale.US;
    }

    /**
     * Constructor that accepts integer value
     * @param uuid UUID
     * @param value int
     */
    public Price(UUID uuid, int value) {
        super(uuid);
        VALUE = new BigDecimal(String.valueOf(value) + ".00");
        CURRENCY = Currency.getInstance("USD");
        LOCALE = Locale.US;
    }

    /**
     * Constructor that accepts string value
     * @param uuid UUID
     * @param value String
     */
    public Price(UUID uuid, String value) {
        super(uuid);
        VALUE = new BigDecimal(value);
        CURRENCY = Currency.getInstance("USD");
        LOCALE = Locale.US;
    }

    /**
     * Change the default currency
     * @param currencyCode String
     */
    public void setDefaultCurrency(String currencyCode) {
        CURRENCY = Currency.getInstance(currencyCode);
    }

    /**
     * Change the default locale
     * @param locale Locale
     */
    public void setDefaultLocale(Locale locale) {
        LOCALE = locale;
    }

    /**
     * Return the value as BigDecimal
     * @return BigDecimal value in BigDecimal
     */
    public BigDecimal getValue() {
        return VALUE;
    }

    /**
     * Set this to a new value
     * @param value BigDecimal
     */
    public void setValue(BigDecimal value) {
        VALUE = value;
    }

    /**
     * Set this to a new value
     * @param value String
     */
    public void setValue(String value) {
        VALUE = new BigDecimal(value);
    }

    /**
     * Set this to a new value
     * @param value double
     */
    public void setValue(double value) {
        VALUE = new BigDecimal(String.valueOf(value));
    }

    /**
     * Set this to a new value
     * @param value float
     */
    public void setValue(float value) {
        VALUE = new BigDecimal(String.valueOf(value));
    }

    /**
     * Set this to a new value
     * @param value int
     */
    public void setValue(int value) {
        VALUE = new BigDecimal(String.valueOf(value) + ".00");
    }

    /**
     * Return the currency symbol
     * @return String currency symbol
     */
    public String getSymbol() {
        return CURRENCY.getSymbol(LOCALE);
    }

    /**
     * Return the string representation of the price without the currency symbol
     * @return String price as string literal
     */
    public String getPrice(){
        BigDecimal displayValue =
                VALUE.setScale(CURRENCY.getDefaultFractionDigits(), RoundingMode.HALF_EVEN);

        return displayValue.toPlainString();
    }

    /**
     *
     * @param price Price
     * @return int
     */
    @Override
    public int compareTo(Price price) {
        return this.VALUE.compareTo(price.VALUE);
    }

    /**
     * Override toString method to return the price with the currency symbol and specific
     * locale formatting
     * @return String price with locale-specific formatting
     */
    @Override
    public String toString() {
        BigDecimal displayValue =
                VALUE.setScale(CURRENCY.getDefaultFractionDigits(), RoundingMode.HALF_EVEN);
        NumberFormat usdCostFormat = NumberFormat.getCurrencyInstance(LOCALE);

        return usdCostFormat.format(displayValue.doubleValue());
    }

}
