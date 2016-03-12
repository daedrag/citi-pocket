package com.d3.duy.citipocket.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.d3.duy.citipocket.R;
import com.d3.duy.citipocket.adapter.StatisticAdapter;

/**
 * Created by daoducduy0511 on 3/3/16.
 */
public class FragmentAccount extends Fragment implements CustomFragment {

    private static final String TAG = FragmentAccount.class.getSimpleName();
    private static final String TITLE = "Account";

    private boolean isAdapterInitialized = false;
    private StatisticAdapter statAdapter;
    private GridView grid;
    private TextView monthView, balanceView;
    private ImageButton btnPreviousMonth, btnNextMonth;

    public FragmentAccount() {
        super();
        statAdapter = null;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    public void initAdapter() {
        if (statAdapter == null) {
            statAdapter = new StatisticAdapter(getContext());
            grid.setAdapter(statAdapter);
        }

        isAdapterInitialized = true;
        btnNextMonth.setClickable(true);
        btnPreviousMonth.setClickable(true);

        balanceView.setText("0.0");
        balanceView.setVisibility(View.VISIBLE);
    }

    // To be used by remote Async task
    public void reloadAdapter() {
        if (!isAdapterInitialized) initAdapter();

        statAdapter.reloadData();

        monthView.setText(statAdapter.getCurrentMonthYear().toString());
        balanceView.setText(statAdapter.getTotalBalance().getFullAmountWithAccountingStyle());

        statAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        monthView = (TextView) rootView.findViewById(R.id.label_this_month);
        monthView.setText("Loading...");

        balanceView = (TextView) rootView.findViewById(R.id.label_balance);
        balanceView.setVisibility(View.GONE);

        grid = (GridView) rootView.findViewById(R.id.grid_this_month);
//        grid.setAdapter(statAdapter);

        btnPreviousMonth = (ImageButton) rootView.findViewById(R.id.btn_previous_month);
        btnNextMonth = (ImageButton) rootView.findViewById(R.id.btn_next_month);

        // set both unclickable before adapter initialized
        btnPreviousMonth.setClickable(false);
        btnNextMonth.setClickable(false);

        btnPreviousMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdapterInitialized) {
                    statAdapter.setPreviousMonthYear();
                    FragmentAccount.this.reloadAdapter();
                }
            }
        });

        btnNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdapterInitialized) {
                    statAdapter.setNextMonthYear();
                    FragmentAccount.this.reloadAdapter();
                }
            }
        });

        return rootView;
    }

}
