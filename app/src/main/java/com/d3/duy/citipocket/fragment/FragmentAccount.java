package com.d3.duy.citipocket.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.d3.duy.citipocket.R;
import com.d3.duy.citipocket.adapter.StatisticAdapter;
import com.d3.duy.citipocket.model.MonthYear;

/**
 * Created by daoducduy0511 on 3/3/16.
 */
public class FragmentAccount extends Fragment implements CustomFragment {

    private static final String TITLE = "Account";

    private StatisticAdapter statAdapter;
    private GridView grid;
    private TextView textView;

    public FragmentAccount() {
        super();
        statAdapter = null;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    // To be used by remote Async task
    public void reloadStatAdapter() {
        statAdapter = new StatisticAdapter(getContext());
        grid.setAdapter(statAdapter);
        statAdapter.loadDataAndNotify();

        // update current month year text
        MonthYear currentMonthYear = MonthYear.getCurrent();
        textView.setText(currentMonthYear.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        textView = (TextView) rootView.findViewById(R.id.label_this_month);
        textView.setText("Loading...");

        grid = (GridView) rootView.findViewById(R.id.grid_this_month);
//        grid.setAdapter(statAdapter);

        return rootView;
    }

}
