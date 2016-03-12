package com.d3.duy.citipocket.core.enrich;

import com.d3.duy.citipocket.model.CurrencyAmount;

/**
 * Created by daoducduy0511 on 3/10/16.
 */
public class CurrencyEnrichment {

    public static final String BASE_CURRENCY_CODE = "SGD";

    public static CurrencyAmount fromCurrencyAmountString(String s) {
        // default case
        String ccyCode = BASE_CURRENCY_CODE;
        double amount = 0.0;
        
        // assume the first 3 chars contain currency code
        if (s.length() >= 4) {
            if (s.charAt(0) == '-') {
                // negative amount
                ccyCode = s.substring(1, 4); 
                amount = (-1) * Double.parseDouble(s.substring(4));  // negative amount
            } else {
                ccyCode = s.substring(0, 3);
                amount = Double.parseDouble(s.substring(3));    
            }
        }
        
        return new CurrencyAmount(ccyCode, amount);
    }

    // exchange rate as of the day this file created
    public static final double SGD_TO_USD = 137.89 / 100;
    public static final double SGD_TO_VND = 0.006185 / 100;
    public static final double SGD_TO_MYR = 33.64 / 100;
    public static final double SGD_TO_IDR = 0.01048 / 100;
    public static final double SGD_TO_INR = 2.055 / 100;
    public static final double SGD_TO_AUD = 103.71 / 100;

    /**
     * Convert some basic currency codes to SGD
     *
     * @return CurrencyAmount in SGD
     */
    public static CurrencyAmount convertToSgd(CurrencyAmount original) {
        double sgdAmt = 0;
        switch (original.getCurrencyCode()) {
            case "SGD":
                sgdAmt = original.getAmount();
                break;
            case "USD":
                sgdAmt = original.getAmount() * SGD_TO_USD;
                break;
            case "VND":
                sgdAmt = original.getAmount() * SGD_TO_VND;
                break;
            case "MYR":
                sgdAmt = original.getAmount() * SGD_TO_MYR;
                break;
            case "IDR":
                sgdAmt = original.getAmount() * SGD_TO_IDR;
                break;
            case "INR":
                sgdAmt = original.getAmount() * SGD_TO_INR;
                break;
            case "AUD":
                sgdAmt = original.getAmount() * SGD_TO_AUD;
                break;
            default:
                sgdAmt = 0;
        }
        return new CurrencyAmount("SGD", sgdAmt);
    }
}
