package com.d3.duy.citipocket.model;

import com.d3.duy.citipocket.core.aggregate.AmountAggregator;
import com.d3.duy.citipocket.model.enums.MessageType;
import com.d3.duy.citipocket.model.enums.Month;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daoducduy0511 on 3/10/16.
 */
public class MonthlyStatistics {

    public static class SubtypeStatistics {
        private List<MessageEnrichmentHolder> messages;
        private CurrencyAmount totalAmount;
        private MessageType type;

        public SubtypeStatistics(MessageType type, List<MessageEnrichmentHolder> messages) {
            this.messages = messages;
            this.totalAmount = AmountAggregator.aggregate(this.messages);
            this.type = type;
        }

        public SubtypeStatistics(List<MessageEnrichmentHolder> messages) {
            this(messages.get(0).getType(), messages);
        }

        public int getCount() { return messages.size(); }
        public CurrencyAmount getTotalAmount() { return totalAmount; }
        public MessageType getType() { return type; }
    }

    private Month month;
    private int year;
    private List<SubtypeStatistics> subStatList;

    public MonthlyStatistics(Month month, int year, List<SubtypeStatistics> subStatList) {
        this.month = month;
        this.year = year;
        this.subStatList = subStatList;
    }

    public MonthlyStatistics(Month month, int year) {
        this.month = month;
        this.year = year;
        this.subStatList = new ArrayList<>();
    }

    public Month getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public List<SubtypeStatistics> getSubStatList() {
        return subStatList;
    }

}
