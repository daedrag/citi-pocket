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

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final Fragment accountFragment = new FragmentAccount();
    private static final Fragment graphFragment = new FragmentGraph();
    private static final Fragment messagesFragment = new FragmentMessages();
    private static Fragment[] fragments = {
            accountFragment,
            graphFragment,
            messagesFragment
    };

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ((CustomFragment)getItem(position)).getTitle();
    }

    public Fragment getAccountFragment() { return accountFragment; }

    public Fragment getGraphFragment() {
        return graphFragment;
    }

    public Fragment getMessagesFragment() {
        return messagesFragment;
    }
}
