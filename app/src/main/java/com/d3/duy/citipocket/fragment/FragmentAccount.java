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
public class FragmentAccount extends Fragment implements CustomFragment {

    private static final String TITLE = "Account";

    public FragmentAccount() {
        super();
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        final TextView textView = (TextView) rootView.findViewById(R.id.section_label_account);
        textView.setText(TITLE);
        return rootView;
    }

}
