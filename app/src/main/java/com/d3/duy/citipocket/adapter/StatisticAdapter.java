package com.d3.duy.citipocket.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.d3.duy.citipocket.R;
import com.d3.duy.citipocket.core.store.MessageTypeIconStore;
import com.d3.duy.citipocket.core.store.StatisticsStore;
import com.d3.duy.citipocket.model.CurrencyAmount;
import com.d3.duy.citipocket.model.MonthYear;
import com.d3.duy.citipocket.model.MonthlyStatistics;
import com.d3.duy.citipocket.model.MonthlyStatistics.SubtypeStatistics;
import com.d3.duy.citipocket.model.enums.MessageType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by daoducduy0511 on 3/10/16.
 */
public class StatisticAdapter extends BaseAdapter {
    private static final String TAG = StatisticAdapter.class.getSimpleName();

    private static LayoutInflater inflater = null;
    private static final int LAYOUT_ID = R.layout.grid_item_substats;
    private static final List<SubtypeStatistics> EMPTY_LIST = new ArrayList<>();

    private Context context;
    private List<MonthlyStatistics> stats;
    private MonthYear targetedMonthYear;
    private List<SubtypeStatistics> subStats;
    private boolean isLoaded = false;

    private static Map<MessageType, Bitmap> iconMap;

    public StatisticAdapter(Context context, MonthYear monthYear) {
        this.context = context;
        this.targetedMonthYear = monthYear;

        // IMPORTANT: this depends on Async task triggered by MainActivity
        // to calculate the statistics
        this.stats = new ArrayList<>();
        this.subStats = EMPTY_LIST;

        iconMap = new HashMap<>();
        for (Map.Entry<MessageType, Integer> entry: MessageTypeIconStore.getMessageTypeIconMap().entrySet()) {
            Bitmap bMap = BitmapFactory.decodeResource(context.getResources(), entry.getValue());
            Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 128, 128, true);

            iconMap.put(entry.getKey(), bMapScaled);
        }

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public StatisticAdapter(Context context) {
        this(context, MonthYear.getCurrent());
    }

    @Override
    public int getCount() {
        return subStats.size();
    }

    @Override
    public Object getItem(int position) {
        return subStats.get(position);
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
        ImageView imageView = (ImageView) rowView.findViewById(R.id.grid_item_image);

        SubtypeStatistics subStat = subStats.get(position);
        totalAmountTextView.setText(subStat.getTotalAmount().getAmountWithAccountingStyle());

        typeTextView.setText(subStat.getType().name() + " (" + subStat.getCount() + ")");

        imageView.setImageBitmap(iconMap.get(subStat.getType()));

        return rowView;
    }

    public void setPreviousMonthYear() {
        targetedMonthYear = targetedMonthYear.getPrevious();
    }

    public void setNextMonthYear() {
        targetedMonthYear = targetedMonthYear.getNext();
    }

    public void resetMonthYear() {
        targetedMonthYear = MonthYear.getCurrent();
    }

    public MonthYear getCurrentMonthYear() {
        return targetedMonthYear;
    }

    public CurrencyAmount getTotalBalance() {
        double totalBalance = 0.0;

        for (SubtypeStatistics subStat : subStats) {
            totalBalance += subStat.getTotalAmount().getAmount();
        }

        return new CurrencyAmount(CurrencyAmount.BASE_CURRENCY_CODE, totalBalance);
    }

    public void reloadData() {
        if (!isLoaded) {
            stats = StatisticsStore.getInstance().getMonthlyStatistics();
            isLoaded = true;
        }

        // find the correct list of sub statistics
        subStats = EMPTY_LIST;
        for (MonthlyStatistics stat : stats) {
            if (stat.getMonth() == targetedMonthYear.getMonth() && stat.getYear() == targetedMonthYear.getYear()) {
                subStats = stat.getSubStatList();
                break;
            }
        }
    }
}
