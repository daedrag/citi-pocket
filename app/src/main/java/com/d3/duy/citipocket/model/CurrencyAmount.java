package com.d3.duy.citipocket.model;

import java.text.DecimalFormat;

/**
 * Created by daoducduy0511 on 3/10/16.
 */
public class CurrencyAmount {
    private static final DecimalFormat DECIMAL_FORMAT_ACCOUNTING_STYLE = new DecimalFormat("#,###");

    public static final String BASE_CURRENCY_CODE = "SGD";

    private String currencyCode;
    private double amount;

    public CurrencyAmount(String currencyCode, double amount) {
        this.currencyCode = currencyCode;
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public double getAmount() {
        return amount;
    }

    /**
     * Negate the amount to indicate a withdraw or payment
     */
    public CurrencyAmount negate() {
        this.amount *= -1;
        return this;
    }

    @Override
    public String toString() {
        return getFullAmountWith2Digit();
    }

    public String getFullAmountWith2Digit() {
        return (amount < 0 ? "-" : "") + currencyCode + String.format("%.2f", Math.abs(amount));
    }

    public String getFullAmountWithAccountingStyle() {
        if (amount < 0) {
            return "(" + currencyCode + " " + DECIMAL_FORMAT_ACCOUNTING_STYLE.format(Math.abs(amount)) + ")";
        } else {
            return currencyCode + " " + DECIMAL_FORMAT_ACCOUNTING_STYLE.format(amount);
        }
    }

    public String getAmountWith2Digit() {
        return String.format("%.2f", amount);
    }

    public String getAmountWithAccountingStyle() {
        if (amount < 0) {
            return "(" + DECIMAL_FORMAT_ACCOUNTING_STYLE.format(Math.abs(amount)) + ")";
        } else {
            return DECIMAL_FORMAT_ACCOUNTING_STYLE.format(amount);
        }
    }
}
