package com.d3.duy.citipocket.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.d3.duy.citipocket.R;
import com.d3.duy.citipocket.core.loader.StatisticsLoader;
import com.d3.duy.citipocket.model.MonthYear;
import com.d3.duy.citipocket.model.MonthlyStatistics;
import com.d3.duy.citipocket.model.MonthlyStatistics.SubtypeStatistics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daoducduy0511 on 3/10/16.
 */
public class StatisticAdapter extends BaseAdapter {
    private static final String TAG = StatisticAdapter.class.getSimpleName();

    private static LayoutInflater inflater = null;
    private static final int LAYOUT_ID = R.layout.grid_item_substats;

    private Context context;
    private final List<MonthlyStatistics> stats;
    private MonthYear targetedMonthYear;
    private final List<SubtypeStatistics> subStats;

    public StatisticAdapter(Context context, MonthYear monthYear) {
        this.context = context;
        this.targetedMonthYear = monthYear;

        // IMPORTANT: this depends on Async task triggered by MainActivity
        // to calculate the statistics
        this.stats = new ArrayList<>();
        this.subStats = new ArrayList<>();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        loadDataAndNotify();
    }

    public StatisticAdapter(Context context) {
        this(context, MonthYear.getCurrent());
    }

    @Override
    public int getCount() {
        return this.subStats.size();
    }

    @Override
    public Object getItem(int position) {
        return this.subStats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = inflater.inflate(LAYOUT_ID, null);

        TextView totalAmountTextView = (TextView) rowView.findViewById(R.id.grid_item_totalAmount);
        TextView typeTextView = (TextView) rowView.findViewById(R.id.grid_item_type);
        TextView countTextView = (TextView) rowView.findViewById(R.id.grid_item_count);

        SubtypeStatistics subStat = this.subStats.get(position);
        totalAmountTextView.setText(subStat.getTotalAmount().toString());
        typeTextView.setText(subStat.getType().name());
        countTextView.setText(subStat.getCount() + " transactions");

        return rowView;
    }


    public void setMonthYear(MonthYear monthYear) {
        this.targetedMonthYear = monthYear;
    }

    public void loadDataAndNotify() {
        Log.d(TAG, "Loading statistics adapter for " + this.targetedMonthYear.toString());

        List<MonthlyStatistics> monthlyStatistics = StatisticsLoader.getInstance().getMonthlyStatistics();
        if (monthlyStatistics.size() == 0) return;

        this.stats.clear();
        this.stats.addAll(monthlyStatistics);

        // find the correct list of sub statistics
        this.subStats.clear();
        for (MonthlyStatistics stat : this.stats) {
            Log.d(TAG, "Current month year: " + stat.getMonth() + " " + stat.getYear());
            if (stat.getMonth() == this.targetedMonthYear.getMonth() &&
                    stat.getYear() == this.targetedMonthYear.getYear()) {

                Log.d(TAG, "Found sub statistics!");

                List<SubtypeStatistics> subStats = stat.getSubStatList();
                this.subStats.addAll(subStats);
                break;
            }
        }

        notifyDataSetChanged();
    }
}
