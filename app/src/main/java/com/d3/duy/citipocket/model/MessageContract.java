package com.d3.duy.citipocket.model;

import android.net.Uri;
import android.provider.Telephony;

/**
 * Created by daoducduy0511 on 3/4/16.
 */
public class MessageContract {
    public static final String SENDER_ADDRESS = "CitibankSG";
    public static final Uri URI = Telephony.Sms.Inbox.CONTENT_URI;
    public static final String[] COLUMNS = {
            Telephony.Sms._ID,
            Telephony.Sms.ADDRESS,
            Telephony.Sms.DATE,
            Telephony.Sms.BODY
    };
    public static final String SELECTION_SENDER_ADDRESS = Telephony.Sms.ADDRESS + " = ?";
}
