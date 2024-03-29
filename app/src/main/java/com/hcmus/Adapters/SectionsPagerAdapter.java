package com.hcmus.Adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hcmus.shipe.R;

import com.hcmus.Fragments.ShipperHomeFragment;
import com.hcmus.Fragments.ShipperMapFragment;
import com.hcmus.Fragments.ShipperOrderFragment;
import com.hcmus.Fragments.ShipperTaskFragment;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_home, R.string.tab_task, R.string.tab_order, R.string.tab_map};
    private final Context mContext;
    private  int numOfTabs;
    private ShipperHomeFragment shipperHome;
    private ShipperTaskFragment shipperTask;
    private ShipperOrderFragment shipperOrder;
    private ShipperMapFragment shipperMap;

    public SectionsPagerAdapter(Context context, FragmentManager fm, int num){
        super(fm);
        mContext = context;
        numOfTabs = num;
        shipperHome = new ShipperHomeFragment(context);
        shipperTask = new ShipperTaskFragment(context);
        shipperOrder = new ShipperOrderFragment(context);
        shipperMap = new ShipperMapFragment(context);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return shipperHome;
            case 1:
                return shipperTask;
            case 2:
                return shipperOrder;
            case 3:
                return shipperMap;
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}