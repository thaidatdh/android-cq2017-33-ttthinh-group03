package com.hcmus.shipe;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.shipe.tabbed.shipper.SectionsPagerAdapter;

public class ShipperActivity extends AppCompatActivity {
    TabLayout tabLayout;
    TabItem tabHome;
    TabItem tabTask;
    TabItem tabOrder;
    TabItem tabMap;
    ViewPager viewPager;
    SectionsPagerAdapter sectionsPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper);

        tabLayout = findViewById(R.id.tabs);
        tabHome = findViewById(R.id.tab_home);
        tabTask = findViewById(R.id.tab_task);
        tabOrder = findViewById(R.id.tab_order);
        tabMap = findViewById(R.id.tab_map);
        viewPager = findViewById(R.id.view_pager);

        sectionsPagerAdapter =  new SectionsPagerAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(sectionsPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }
}