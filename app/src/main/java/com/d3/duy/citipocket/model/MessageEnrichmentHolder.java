package com.d3.duy.citipocket.model;

import java.io.Serializable;

/**
 * Created by daoducduy0511 on 27/2/16.
 */
public class MessageEnrichmentHolder implements Serializable {
    private MessageFilter.MessageType type;
    private String cardInfo;
    private String amount;
    private String originator;
    private String date;

    public MessageFilter.MessageType getType() {
        return type;
    }

    public String getCardInfo() {
        return cardInfo;
    }

    public String getAmount() {
        return amount;
    }

    public String getOriginator() {
        return originator;
    }

    public String getDate() {
        return date;
    }

    public MessageEnrichmentHolder(MessageFilter.MessageType type, String cardInfo, String amount, String originator, String date) {
        this.type = type;
        this.cardInfo = cardInfo;
        this.amount = amount;
        this.originator = originator.trim().replaceAll(" +", " ");;
        this.date = date;
    }

    public MessageEnrichmentHolder(MessageFilter.MessageType type) {
        this.type = type;
        this.cardInfo = "";
        this.amount = "";
        this.originator = "";
        this.date = "";
    }

    @Override
    public String toString() {
        return "Type: " + type.name() + ", " +
                "Card info: " + cardInfo + ", " +
                "Amount: " + amount + ", " +
                "Originator: " + originator + ", " +
                "Date: " + getDate();
    }
}
