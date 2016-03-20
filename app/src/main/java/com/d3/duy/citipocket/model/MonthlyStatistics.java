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
            this.totalAmount = AmountAggregator.aggregateMessages(this.messages);
            this.type = type;
        }

        public SubtypeStatistics(List<MessageEnrichmentHolder> messages) {
            this(messages.get(0).getType(), messages);
        }

        public int getCount() { return messages.size(); }
        public CurrencyAmount getTotalAmount() { return totalAmount; }
        public MessageType getType() { return type; }
    }

    private MonthYear monthYear;
    private List<SubtypeStatistics> subStatList;
    private CurrencyAmount totalSpending;

    public MonthlyStatistics(MonthYear monthYear, List<SubtypeStatistics> subStatList) {
        this.monthYear = monthYear;
        this.subStatList = subStatList;

        List<CurrencyAmount> spendingPerType = new ArrayList<>();
        for (SubtypeStatistics subStat: subStatList) {
            spendingPerType.add(subStat.getTotalAmount());
        }

        this.totalSpending = AmountAggregator.aggregateAmounts(spendingPerType);
    }

    public MonthlyStatistics(Month month, int year, List<SubtypeStatistics> subStatList) {
        this(new MonthYear(month, year), subStatList);
    }

    public MonthlyStatistics(MonthYear monthYear) {
        this(monthYear, new ArrayList<SubtypeStatistics>());
    }

    public MonthlyStatistics(Month month, int year) {
        this(month, year, new ArrayList<SubtypeStatistics>());
    }

    public MonthYear getMonthYear() {
        return monthYear;
    }

    public Month getMonth() {
        return monthYear.getMonth();
    }

    public int getYear() {
        return monthYear.getYear();
    }

    public List<SubtypeStatistics> getSubStatList() {
        return subStatList;
    }

    public CurrencyAmount getTotalSpending() {
        return totalSpending;
    }
}
