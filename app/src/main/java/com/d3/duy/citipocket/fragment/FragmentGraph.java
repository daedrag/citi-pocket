package com.d3.duy.citipocket.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d3.duy.citipocket.R;
import com.d3.duy.citipocket.adapter.GraphDataSetAdapter;
import com.d3.duy.citipocket.core.store.ColorCodeStore;
import com.d3.duy.citipocket.model.enums.MessageType;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by daoducduy0511 on 3/3/16.
 */
public class FragmentGraph extends Fragment implements CustomFragment {

    public static final String TAG = FragmentGraph.class.getSimpleName();
    private static final String TITLE = "Graph";

    private List<MessageType> typesToDraw;
    private GraphDataSetAdapter graphAdapter;
    private LineChart chart;

    public FragmentGraph() {
        super();
        graphAdapter = null;
        typesToDraw = new ArrayList<>();
        typesToDraw.add(MessageType.GIRO);
        typesToDraw.add(MessageType.DEBIT);
        typesToDraw.add(MessageType.PAYMENT);
        typesToDraw.add(MessageType.WITHDRAW);
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graph, container, false);
        chart = (LineChart) rootView.findViewById(R.id.chart);
        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(true);
        xAxis.setAvoidFirstLastClipping(true);

        YAxis yAxis = chart.getAxisLeft();
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawGridLines(false);
        yAxis.setDrawLabels(false);
        yAxis.setDrawZeroLine(true);

        chart.getAxisRight().setEnabled(false);
        chart.setDescription("");
        chart.getLegend().setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);

        return rootView;
    }

    public void initAdapter(Context context) {
        if (graphAdapter == null) {
            graphAdapter = new GraphDataSetAdapter();

            ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

            // Add total spending
            LineDataSet lineDataSet = new LineDataSet(graphAdapter.getMonthlySpendingList(), "TOTAL");
            int lineColor = Color.rgb(240, 99, 99);
            lineDataSet.setLineWidth(2.5f);
            lineDataSet.setCircleRadius(4.5f);
            lineDataSet.setColor(lineColor);
            lineDataSet.setCircleColor(lineColor);
//            lineDataSet.setHighLightColor(Color.rgb(190, 190, 190));
            lineDataSet.setValueTextSize(12f);
            lineDataSet.setValueTextColor(lineColor);
            lineDataSets.add(lineDataSet);

            // Add spending per type
            for (Map.Entry<MessageType, ArrayList<Entry>> spendingPerType :
                    graphAdapter.getMonthlySpendingPerType().entrySet()) {

                // only add types of interest
                MessageType type = spendingPerType.getKey();
                if (!typesToDraw.contains(type)) continue;

                LineDataSet dataSetPerType = new LineDataSet(spendingPerType.getValue(), type.name());
                int colorInt = ColorCodeStore.getColorMap(context).get(type);
                Log.d(TAG, "Color for type " + type.name() + " : " + colorInt);
                dataSetPerType.setLineWidth(1.5f);
                dataSetPerType.setCircleRadius(2.5f);
                dataSetPerType.setColor(colorInt);
                dataSetPerType.setCircleColor(colorInt);
//                dataSetPerType.setHighLightColor(Color.rgb(190, 190, 190));
                dataSetPerType.setValueTextSize(10f);
                dataSetPerType.setValueTextColor(colorInt);
                lineDataSets.add(dataSetPerType);
            }

            LineData lineData = new LineData(graphAdapter.getMonthInXVals(), lineDataSets);
            chart.setData(lineData);
            chart.invalidate(); // refresh chart

            // Please note that all methods modifying the viewport
            // need to be called on the Chart after setting data.
            // https://github.com/PhilJay/MPAndroidChart/wiki/Modifying-the-Viewport
            //
            chart.setVisibleXRangeMaximum(6);   // maximum values on x-axis viewed without scrolling
            chart.setVisibleXRangeMinimum(1);

            // this automatically refreshes the chart (calls invalidate())
            chart.moveViewTo(chart.getLineData().getXValCount() - 7, 50f, YAxis.AxisDependency.LEFT);
            chart.animateY(1500, Easing.EasingOption.Linear);
        }
    }

}
