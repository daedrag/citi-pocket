package com.d3.duy.citipocket.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.d3.duy.citipocket.R;

/**
 * Created by daoducduy0511 on 3/3/16.
 */
public class FragmentGraph extends Fragment implements CustomFragment {

    private static final String TITLE = "Graph";

    public FragmentGraph() { super(); }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graph, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label_graph);
        textView.setText(TITLE);
        return rootView;
    }

}
