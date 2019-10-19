package com.hcmus.shipe.tabbed.shipper;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hcmus.shipe.R;
import com.hcmus.shipe.fragment.ShipperHomeFragment;
//import com.hcmus.shipe.fragment.ShipperMapFragment;
import com.hcmus.shipe.fragment.ShipperOrderFragment;
import com.hcmus.shipe.fragment.ShipperTaskFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_home, R.string.tab_task, R.string.tab_order, R.string.tab_map};
    private final Context mContext;
    private  int numOfTabs;

    public SectionsPagerAdapter(Context context, FragmentManager fm, int num){
        super(fm);
        mContext = context;
        numOfTabs = num;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new ShipperHomeFragment();
            case 1:
                return new ShipperTaskFragment();
            case 2:
                return new ShipperOrderFragment();
            case 3:
               // return new ShipperMapFragment(mContext);
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