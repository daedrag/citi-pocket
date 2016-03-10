package com.d3.duy.citipocket.core.group;

/**
 * Created by daoducduy0511 on 3/11/16.
 */

import com.d3.duy.citipocket.model.MessageEnrichmentHolder;
import com.d3.duy.citipocket.model.MonthYear;
import com.d3.duy.citipocket.model.enums.Month;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by daoducduy0511 on 3/9/16.
 */
public class MessageGroupByMonth implements IMessageGroup<MonthYear> {

    private List<MonthYear> monthYearList;
    private List<MessageEnrichmentHolder> messages;

    public MessageGroupByMonth(List<MessageEnrichmentHolder> messages) {
        monthYearList = new ArrayList<>();
        this.messages = messages;
    }

    @Override
    public List<MonthYear> getKeys() {
        return monthYearList;
    }

    @Override
    public List<MessageEnrichmentHolder> getValues() {
        return messages;
    }

    @Override
    public Map<MonthYear, List<MessageEnrichmentHolder>> groupBy() {
        Map<MonthYear, List<MessageEnrichmentHolder>> tempMapping = new HashMap<>();
        for (MessageEnrichmentHolder message : messages) {
            Date d = message.getDateObj();
            Month m = Month.values()[d.getMonth()];  // Date.getMonth() return Jan starting from 0
            MonthYear monthYear = new MonthYear(m, d.getYear() + 1900);

            if (tempMapping.containsKey(monthYear)) {
                tempMapping.get(monthYear).add(message);
            } else {
                List<MessageEnrichmentHolder> messagesAtCurrentDate = new ArrayList<>();
                messagesAtCurrentDate.add(message);
                tempMapping.put(monthYear, messagesAtCurrentDate);
            }
        }

        return tempMapping;
    }
}

