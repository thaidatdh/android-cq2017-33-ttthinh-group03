package com.hcmus.shipe;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.Activities.ManagerActivity;
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
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper);

        //username = Login.userLocalStore.GetUsername();
        //user = UserDao.findByUsername(username);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        else {
            finish();
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
                        if (mLocation != null){
                            shipperTask.createTask(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()));
                        }
                        break;
                    case 2:
                        if (mLocation != null){
                            shipperOrder.createTask(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()), Login.userLocalStore.GetUserId());
                        }
                        break;
                    case 3:
                        //viewPager.setCurrentItem(index);
                        if (mLocation != null){
                            shipperMap.createRoute(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()), Login.userLocalStore.GetUserId());
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
        checkLocationPermission();
        if (!mLocationManager.isProviderEnabled (LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
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

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        mLocationManager.requestLocationUpdates(provider, 400, 1, this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}