package com.d3.duy.citipocket.adapter;

import com.d3.duy.citipocket.core.store.StatisticsStore;
import com.d3.duy.citipocket.model.MonthlyStatistics;
import com.d3.duy.citipocket.model.enums.MessageType;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by daoducduy0511 on 3/15/16.
 */
public class GraphDataSetAdapter {

    private List<MonthlyStatistics> monthlyStatisticsList;
    private ArrayList<Entry> monthlySpendingList;
    private Map<MessageType, ArrayList<Entry>> monthlySpendingPerType;
    private ArrayList<String> monthInXVals;

    public GraphDataSetAdapter() {
        monthlyStatisticsList = new ArrayList<>();
        monthlyStatisticsList.addAll(StatisticsStore.getInstance().getMonthlyStatistics());

        // sort to make sure xvals in order
        Collections.sort(monthlyStatisticsList, new Comparator<MonthlyStatistics>() {
            @Override
            public int compare(MonthlyStatistics lhs, MonthlyStatistics rhs) {
                if (lhs.getYear() == rhs.getYear()) {
                    return lhs.getMonth().getValue() - rhs.getMonth().getValue();
                } else {
                    return lhs.getYear() - rhs.getYear();
                }
            }
        });

        int xIndex = 0;
        monthlySpendingList = new ArrayList<>();
        monthInXVals = new ArrayList<>();
        monthlySpendingPerType = new HashMap<>();

        for (MessageType type : MessageType.values()) {
            monthlySpendingPerType.put(type, new ArrayList<Entry>());
        }

        for (MonthlyStatistics stat : monthlyStatisticsList) {
            monthlySpendingList.add(new Entry((float)stat.getTotalSpending().getAmount(), xIndex));
            monthInXVals.add(stat.getMonthYear().toShortString());

            // setup spending per type
            // make sure we don't miss out any type
            for (MessageType type : MessageType.values()) {
                boolean isTypeFound = false;
                for (MonthlyStatistics.SubtypeStatistics subStat : stat.getSubStatList()) {
                    if (subStat.getType() == type) {
                        monthlySpendingPerType.get(subStat.getType()).add(new Entry(
                                (float) subStat.getTotalAmount().getAmount(), xIndex));
                        isTypeFound = true;
                        break;
                    }
                }

                if (!isTypeFound) {
                    monthlySpendingPerType.get(type).add(new Entry(0, xIndex));
                }
            }

            xIndex++;
        }
    }

    public List<MonthlyStatistics> getMonthlyStatisticsList() {
        return monthlyStatisticsList;
    }

    public ArrayList<String> getMonthInXVals() {

        return monthInXVals;
    }

    public ArrayList<Entry> getMonthlySpendingList() {
        return monthlySpendingList;
    }

    public Map<MessageType, ArrayList<Entry>> getMonthlySpendingPerType() {
        return monthlySpendingPerType;
    }
}
