package com.d3.duy.citipocket.adapter;

/**
 * Created by daoducduy0511 on 3/3/16.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.d3.duy.citipocket.fragment.CustomFragment;
import com.d3.duy.citipocket.fragment.FragmentAccount;
import com.d3.duy.citipocket.fragment.FragmentGraph;
import com.d3.duy.citipocket.fragment.FragmentMessages;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);

        final Fragment accountFragment = new FragmentAccount();
        final Fragment graphFragment = new FragmentGraph();
        final Fragment messagesFragment = new FragmentMessages();

        fragments = new ArrayList<>();
        fragments.add(0, accountFragment);
        fragments.add(1, graphFragment);
        fragments.add(2, messagesFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ((CustomFragment)getItem(position)).getTitle();
    }

    public Fragment getAccountFragment() { return fragments.get(0); }

    public Fragment getGraphFragment() {
        return fragments.get(1);
    }

    public Fragment getMessagesFragment() {
        return fragments.get(2);
    }

}
