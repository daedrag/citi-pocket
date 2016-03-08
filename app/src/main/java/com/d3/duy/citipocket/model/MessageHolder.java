package com.d3.duy.citipocket.model;

import java.sql.Timestamp;

/**
 * Created by daoducduy0511 on 3/4/16.
 */
public class MessageHolder {

    // Sms id
    private int id;

    // Number from which the sms was send
    private String number;

    // SMS text body
    private String body;

    // Date sent
    private String date;

    // Timestamp
    private Timestamp timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Timestamp getTimestamp() { return timestamp; }

    public String getTimestampString() {
        return String.format("%1$TD %1$TT", this.timestamp);
    }

    public MessageHolder(int id, String number, String body, String date) {
        this.id = id;
        this.number = number;
        this.body = body;
        this.date = date;
        this.timestamp = new Timestamp(Long.valueOf(this.date).longValue());
    }

    @Override
    public String toString() {
        return "Id: " + id + ", " +
                "Sender: " + number + ", " +
                "Body: " + body + ", " +
                "Date: " + date;
    }
}
