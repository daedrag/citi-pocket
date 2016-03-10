package com.d3.duy.citipocket.core.loader;

import android.util.Log;

import com.d3.duy.citipocket.core.aggregate.AmountAggregator;
import com.d3.duy.citipocket.core.group.IMessageGroup;
import com.d3.duy.citipocket.core.group.MessageGroupByMonth;
import com.d3.duy.citipocket.core.group.MessageGroupByType;
import com.d3.duy.citipocket.model.CurrencyAmount;
import com.d3.duy.citipocket.model.MessageEnrichmentHolder;
import com.d3.duy.citipocket.model.MonthYear;
import com.d3.duy.citipocket.model.MonthlyStatistics;
import com.d3.duy.citipocket.model.MonthlyStatistics.SubtypeStatistics;
import com.d3.duy.citipocket.model.enums.MessageType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by daoducduy0511 on 3/11/16.
 */
public class StatisticsLoader {
    private static final String TAG = StatisticsLoader.class.getSimpleName();

    private static StatisticsLoader ourInstance = new StatisticsLoader();

    public static StatisticsLoader getInstance() {
        return ourInstance;
    }

    private List<MessageEnrichmentHolder> messages;
    private List<MonthlyStatistics> monthlyStatisticsList;

    private Map<MonthYear, List<MessageEnrichmentHolder>> mapByMonthYear;

    private StatisticsLoader() {
        messages = new ArrayList<>();
        monthlyStatisticsList = new ArrayList<>();
        mapByMonthYear = new HashMap<>();
    }

    public void load() {
        Log.d(TAG, "In load()...");

        // 1. classify by month
        // 2. for each group, classify by type
        // 3. aggregate all messages with the same type
        messages = MessageLoader.getInstance().getEnrichedMessages();

        IMessageGroup<MonthYear> groupByMonth = new MessageGroupByMonth(messages);
        mapByMonthYear = groupByMonth.groupBy();

        Log.d(TAG, "Mapping by MonthYear... found " + mapByMonthYear.size() + " entries!");

        for (Map.Entry<MonthYear, List<MessageEnrichmentHolder>> entry : mapByMonthYear.entrySet()) {
            MonthYear currentMonthYear = entry.getKey();
            List<MessageEnrichmentHolder> messagesPerMonth = entry.getValue();

            Log.d(TAG, "Mapping [" + currentMonthYear + " , " + messagesPerMonth.size() + " messages]");

            // create sub stats to store all aggregations
            List<SubtypeStatistics> subStatList = new ArrayList<>();

            // group by type
            IMessageGroup groupByType = new MessageGroupByType(messagesPerMonth);
            Map<MessageType, List<MessageEnrichmentHolder>> typeMap = groupByType.groupBy();

            // aggregate per type
            for (Map.Entry<MessageType, List<MessageEnrichmentHolder>> typeMapEntry : typeMap.entrySet()) {
                MessageType currentType = typeMapEntry.getKey();
                CurrencyAmount ccyAmount = AmountAggregator.aggregate(typeMapEntry.getValue());
                int count = typeMapEntry.getValue().size();

                Log.d(TAG, "Mapping [" + currentMonthYear + " , " + currentType + " , " + ccyAmount + " , " + count + "]");

                // add to sub stats
                subStatList.add(new SubtypeStatistics(count, ccyAmount, currentType));
            }

            MonthlyStatistics statistics = new MonthlyStatistics(currentMonthYear.getMonth(),
                    currentMonthYear.getYear(), subStatList);

            monthlyStatisticsList.add(statistics);
        }
    }

    public List<MonthlyStatistics> getMonthlyStatistics() { return this.monthlyStatisticsList; }
}
