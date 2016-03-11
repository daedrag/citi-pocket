package com.d3.duy.citipocket.model;

/**
 * Created by daoducduy0511 on 3/10/16.
 */
public class CurrencyAmount {
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
        return (amount < 0 ? "-" : "") + currencyCode + Math.abs(amount);
    }

    public String toStringWith2Digit() {
        return (amount < 0 ? "-" : "") + currencyCode + String.format("%.2f", Math.abs(amount));
    }
}
