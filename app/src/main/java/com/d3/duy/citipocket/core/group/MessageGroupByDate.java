package com.d3.duy.citipocket.core.group;

import com.d3.duy.citipocket.model.enums.Month;
import com.d3.duy.citipocket.model.MessageEnrichmentHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by daoducduy0511 on 3/9/16.
 */
public class MessageGroupByDate implements IMessageGroup<Date> {

    private List<Date> dates;
    private List<Long> datesInMilis;
    private List<MessageEnrichmentHolder> messages;

    public MessageGroupByDate(Month month, int year, List<MessageEnrichmentHolder> messages) {
        int monthValue = month.getValue();
        Date fromDate = new Date(year, monthValue, 1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fromDate);
        int maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        dates = new ArrayList<>();
        datesInMilis = new ArrayList<>();

        for (int dayOfMonth = 1; dayOfMonth < maxDayOfMonth; dayOfMonth++) {
            Date currentDate = new Date(year, monthValue, dayOfMonth);
            dates.add(currentDate);
            datesInMilis.add(currentDate.getTime());
        }

        this.messages = messages;
    }

    @Override
    public List<Date> getKeys() {
        return dates;
    }

    @Override
    public List<MessageEnrichmentHolder> getValues() {
        return messages;
    }

    @Override
    public Map<Date, List<MessageEnrichmentHolder>> groupBy() {
        Map<Long, List<MessageEnrichmentHolder>> tempMapping = new HashMap<>();
        for (MessageEnrichmentHolder message : messages) {
            long timeInMilis = message.getDateObj().getTime();
            if (tempMapping.containsKey(timeInMilis)) {
                tempMapping.get(timeInMilis).add(message);
            } else {
                List<MessageEnrichmentHolder> messagesAtCurrentDate = new ArrayList<>();
                messagesAtCurrentDate.add(message);
                tempMapping.put(timeInMilis, messagesAtCurrentDate);
            }
        }

        Map<Date, List<MessageEnrichmentHolder>> finalMapping = new HashMap<>();
        for (int indexInDates = 0; indexInDates < dates.size(); indexInDates++) {
            if (tempMapping.containsKey(datesInMilis.get(indexInDates))) {
                finalMapping.put(
                        dates.get(indexInDates),
                        tempMapping.get(datesInMilis.get(indexInDates)));
            } else {
                finalMapping.put(
                        dates.get(indexInDates), new ArrayList<MessageEnrichmentHolder>());
            }
        }
        return finalMapping;
    }
}
