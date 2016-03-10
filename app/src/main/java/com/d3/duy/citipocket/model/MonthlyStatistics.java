package com.d3.duy.citipocket.model;

import com.d3.duy.citipocket.model.enums.MessageType;
import com.d3.duy.citipocket.model.enums.Month;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daoducduy0511 on 3/10/16.
 */
public class MonthlyStatistics {

    public static class SubtypeStatistics {
        private int count;
        private CurrencyAmount totalAmount;
        private MessageType type;

        public SubtypeStatistics(int count, CurrencyAmount totalAmount, MessageType type) {
            this.count = count;
            this.totalAmount = totalAmount;
            this.type = type;
        }

        public int getCount() { return count; }
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
