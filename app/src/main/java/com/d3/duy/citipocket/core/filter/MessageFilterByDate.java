package com.d3.duy.citipocket.core.filter;

import com.d3.duy.citipocket.model.MessageEnrichmentHolder;
import com.d3.duy.citipocket.model.enums.Month;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by daoducduy0511 on 3/9/16.
 */
public class MessageFilterByDate implements IMessageFilter {
    private long fromTime = -1;
    private long toTime = -1;

    public MessageFilterByDate(Date date) {
        this.fromTime = date.getTime();
    }

    public MessageFilterByDate(Date fromDate, Date toDate) {
        this.fromTime = fromDate.getTime();
        this.toTime = toDate.getTime();
    }

    public MessageFilterByDate(Month fromMonth, int fromYear, Month toMonth, int toYear) {
        Date fromDate = new Date(fromYear, fromMonth.getValue(), 1);
        this.fromTime = fromDate.getTime();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(toYear, toMonth.getValue(), 1));  // different month & year
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        this.toTime = calendar.getTimeInMillis();
    }

    public MessageFilterByDate(Month month, int year) {
        Date fromDate = new Date(year, month.getValue(), 1);
        this.fromTime = fromDate.getTime();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fromDate); // same month
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        this.toTime = calendar.getTimeInMillis();
    }

    @Override
    public boolean isQualified(MessageEnrichmentHolder enrichedMessage) {
        long msgTime = enrichedMessage.getDateObj().getTime();
        if (toTime == -1) {
            // just find message at exact this date
            return msgTime == fromTime;
        } else {
            // find message sent within date range
            return msgTime >= fromTime && msgTime <= toTime;
        }

    }
}
