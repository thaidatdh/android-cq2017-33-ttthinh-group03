package com.hcmus.shipe;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.DAO.BillDao;
import com.hcmus.DAO.UserDao;
import com.hcmus.DTO.UserDto;
import com.hcmus.Models.Task;
import com.hcmus.Fragments.ShipperHomeFragment;
import com.hcmus.Fragments.ShipperMapFragment;
import com.hcmus.Fragments.ShipperOrderFragment;
import com.hcmus.Fragments.ShipperTaskFragment;
import com.hcmus.Adapters.SectionsPagerAdapter;
import com.hcmus.Utils.MapUtils;
import com.hcmus.Utils.MyCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShipperActivity extends AppCompatActivity implements LocationListener {
    private String username;
    private UserDto user;
    private LocationManager mLocationManager;
    private Location mLocation;
    private MapUtils mMapUtils;
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

        username = Login.userLocalStore.GetUsername();
        user = UserDao.findByUsername(username);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        mMapUtils = new MapUtils(this);

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
                    case 1:
                        try {
                            final List<Task> tasks = BillDao.GetAllAvailableTask();
                            if (tasks.size() > 0 && mLocation != null){
                                shipperTask.createTask(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()), tasks);
                            }

                        } catch (Exception e){
                            Log.e("Task Create", "Error");
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        try {
                            final List<Task> orders = BillDao.GetTaskOfShipper(user.getUserId());
                            if (orders.size() > 0 && mLocation != null){
                                shipperOrder.createTask(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()), orders);
                            }
                        } catch (Exception e){
                            Log.e("Orders Create", "Error");
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        //viewPager.setCurrentItem(index);
                        try {
                            final List<Task> ordersMap = BillDao.GetTaskOfShipper(user.getUserId());
                            if (mLocation != null){
                                shipperMap.setInputRoute(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()), ordersMap);
                            }
                        } catch (Exception e){
                            Log.e("Orders Map Create", "Error");
                            e.printStackTrace();
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
    @Override
    public void onBackPressed() {
        //Login.Logout(getApplicationContext());
        //finishAndRemoveTask();
    }
}