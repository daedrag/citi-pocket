package com.d3.duy.citipocket.model;

import com.d3.duy.citipocket.model.enums.MessageType;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by daoducduy0511 on 27/2/16.
 */
public class MessageEnrichmentHolder implements Serializable {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy");

    private MessageType type;
    private String cardInfo;
    private CurrencyAmount amount;
    private String originator;
    private String dateStr;
    private Date dateObj;

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

    public MessageEnrichmentHolder(MessageType type, String cardInfo, CurrencyAmount amount, String originator, String date) {
        this.type = type;
        this.cardInfo = cardInfo;
        this.amount = amount;
        this.originator = originator.trim().replaceAll(" +", " ");
        this.dateStr = date;
        try {
            this.dateObj = DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            this.dateObj = new Date();
        }
    }

    public MessageEnrichmentHolder(MessageType type) {
        this.type = type;
        this.cardInfo = "";
        this.amount = new CurrencyAmount("SGD", 0);
        this.originator = "";
        this.dateStr = "";
        this.dateObj = new Date();
    }

    @Override
    public String toString() {
        return "Type: " + type.name() + ", " +
                "Card info: " + cardInfo + ", " +
                "Amount: " + amount + ", " +
                "Originator: " + originator + ", " +
                "Date: " + getDateStr();
    }
}
