package com.hcmus.shipe;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TabHost;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.DAO.BillDao;
import com.hcmus.Models.Task;
import com.hcmus.shipe.fragment.ShipperHomeFragment;
import com.hcmus.shipe.fragment.ShipperMapFragment;
import com.hcmus.shipe.fragment.ShipperOrderFragment;
import com.hcmus.shipe.fragment.ShipperTaskFragment;
import com.hcmus.shipe.tabbed.shipper.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShipperActivity extends AppCompatActivity implements LocationListener {
    private LocationManager mLocationManager;
    private Location mLocation;
    private TabLayout tabLayout;
    private TabItem tabHome;
    private TabItem tabTask;
    private TabItem tabOrder;
    private TabItem tabMap;
    private ViewPager viewPager;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private ShipperHomeFragment shipperHome;
    private ShipperTaskFragment shipperTask;
    private ShipperOrderFragment shipperOrder;
    private ShipperMapFragment shipperMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        }

        final List<Task> tasks = BillDao.GetTaskOfShipper(2);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        tabLayout = findViewById(R.id.tabs);
        tabHome = findViewById(R.id.tab_home);
        tabTask = findViewById(R.id.tab_task);
        tabOrder = findViewById(R.id.tab_order);
        tabMap = findViewById(R.id.tab_map);
        viewPager = findViewById(R.id.view_pager);

        sectionsPagerAdapter =  new SectionsPagerAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(sectionsPagerAdapter);

        shipperHome = (ShipperHomeFragment) sectionsPagerAdapter.getItem(0);
        shipperTask = (ShipperTaskFragment) sectionsPagerAdapter.getItem(1);
        shipperOrder = (ShipperOrderFragment) sectionsPagerAdapter.getItem(2);
        shipperMap = (ShipperMapFragment) sectionsPagerAdapter.getItem(3);


        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();
                switch (index){
                    case 3:
                        if (mLocation != null){
                            viewPager.setCurrentItem(index);
                            List<String> addresses = new ArrayList<String>();
                            /*for (Task task : tasks){
                                addresses.add(task.getAddress());
                            }*/
                            addresses.add("DH KHTN");
                            //addresses.add("DH BK");
                            //addresses.add("ĐH Sài Gòn");
                            //addresses.add("Van Hanh Mall");
                            PagerAdapter viewPagerAdapter = viewPager.getAdapter();
                            shipperMap.createRoute(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()), addresses);
                        }
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
}