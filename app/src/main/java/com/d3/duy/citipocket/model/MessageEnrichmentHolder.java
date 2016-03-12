package com.d3.duy.citipocket.model;

import android.util.Log;

import com.d3.duy.citipocket.model.enums.MessageType;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by daoducduy0511 on 27/2/16.
 */
public class MessageEnrichmentHolder implements Serializable {
    private static final String TAG = MessageEnrichmentHolder.class.getSimpleName();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy");

    private String message;
    private MessageType type;
    private String cardInfo;
    private CurrencyAmount amount;
    private String originator;
    private String dateStr;
    private Date dateObj;

    public String getMessage() { return message; }
    public MessageType getType() {
        return type;
    }

    public String getCardInfo() {
        return cardInfo;
    }

    public CurrencyAmount getAmount() {
        return amount;
    }

    public String getOriginator() {
        return originator;
    }

    public String getDateStr() {
        return dateStr;
    }

    public Date getDateObj() {
        return dateObj;
    }

    public MessageEnrichmentHolder(String message, MessageType type, String cardInfo, CurrencyAmount amount, String originator, String date) {
        this.message = message;
        this.type = type;
        this.cardInfo = cardInfo;
        this.amount = amount;
        this.originator = originator.trim().replaceAll(" +", " ");
        this.dateStr = date;
        try {
            this.dateObj = DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            Log.d(TAG, "Current date string: " + date + ", error: " + e);
            this.dateObj = new Date();
        }
    }

    public MessageEnrichmentHolder(String message, MessageType type, String date) {
        this.message = message;
        this.type = type;
        this.cardInfo = "";
        this.amount = new CurrencyAmount("SGD", 0);
        this.originator = "";
        this.dateStr = date;
        try {
            this.dateObj = DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            Log.d(TAG, "Current date string: " + date + ", error: " + e);
            this.dateObj = new Date();
        }
    }

    @Override
    public String toString() {
        return "Type: " + type.name() + ", " +
                "Card info: " + cardInfo + ", " +
                "Amount: " + amount + ", " +
                "Originator: " + originator + ", " +
                "Date: " + dateStr;
    }
}
