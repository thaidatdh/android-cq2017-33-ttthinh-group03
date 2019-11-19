package com.hcmus.Fragments;

import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.hcmus.DAO.BillDao;
import com.hcmus.Dialog.ShipperOrderDialog;
import com.hcmus.Models.Task;
import com.hcmus.Utils.Common;
import com.hcmus.Utils.DialogBtnCallBackInterface;
import com.hcmus.Utils.MapUtils;
import com.hcmus.Utils.MyCallback;
import com.hcmus.shipe.Login;
import com.hcmus.shipe.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ShipperMapFragment extends Fragment{
    private GoogleMap mMap;
    private int defaultZoom = 15;
    private Context mContext;
    private MapUtils mapUtils;
    private Polyline routePolyline;
    private List<Marker> destinationMarkers;
    private Marker shipperMarker;
    private int polylineDefaultWidth = 10;
    private int polylineDefaultColor = Color.RED;
    private String defaultMode = "DRIVING";
    private List<LatLng> points;
    private ShipperOrderDialog oDialog;

    private LatLng shipperLatLng;
    private List<Task> mTasks;

    private ProgressBar mapLoading;
    public ShipperMapFragment (Context context){
        mContext = context;
        mapUtils = new MapUtils(mContext);
        mTasks = new ArrayList<>();
        points = new ArrayList<LatLng>();
        destinationMarkers = new ArrayList<Marker>();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_shipper_map, container, false);
        mapLoading = rootView.findViewById(R.id.map_loading);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (supportMapFragment == null)
            return rootView;
        showMapLoading();
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
                    @Override
                    public boolean onMarkerClick(final Marker marker) {
                        final int index = destinationMarkers.indexOf(marker);
                        if (index >= 0 && index < destinationMarkers.size()){
                            oDialog = new ShipperOrderDialog(mContext, mTasks, index, new DialogBtnCallBackInterface(){
                                @Override
                                public void onBtnClick(String action){
                                    if (action.toLowerCase().equals("cancel")){
                                        cancelTask(index, mTasks.get(index).getBillId());
                                    }
                                    else if (action.toLowerCase().equals("cancel")){
                                        mTasks.remove(index);
                                        createRoute(shipperLatLng, Login.userLocalStore.GetUserId());
                                    }
                                }
                            });
                            oDialog.show();
                        }
                        return true;
                    }
                });
            }
        });
        return rootView;
    }


    public void createRoute(Object start, int userId){
        if (start == null)
            return;
        emptyTask();
        shipperLatLng = (LatLng) start;
        clearRoutePolyline();
        clearDestinationMarkers();
        createMapObservable(userId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(createMapObserver());
    }

    private Observable<List<Task>> createMapObservable(final int userId){
        return Observable.fromCallable(new Callable<List<Task>>() {
            @Override
            public List<Task> call() throws Exception {
                List<Task> tasks = new ArrayList<>();
                try {
                    tasks = BillDao.GetTaskOfShipper(userId);
                } catch (Exception e){
                    Log.e("Task Create", "Error");
                    e.printStackTrace();
                }
                while(mMap == null);
                return tasks;
            }
        });
    }
    private Observer<List<Task>> createMapObserver(){
        return new Observer<List<Task>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(final List<Task> tasks) {
                try {
                    shipperMarker = mMap.addMarker(createShipperMarkerOptions(shipperLatLng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(shipperLatLng, defaultZoom ));
                } catch(Exception e){
                    Log.e("Error Map", "Convert Object Start Location to LatLng");
                    e.printStackTrace();
                }
                if (tasks.size() > 0){
                    //Shipper has no orders
                    final List<String> addresses = new ArrayList<String>();
                    final HashMap<String, String> params = new HashMap<String, String>();
                    params.put("mode", defaultMode);

                    for (Task task : tasks){
                        addresses.add(task.getAddress());
                    }
                    try {
                        mapUtils.callDistanceMatrixAPI(shipperLatLng, addresses, params,new MyCallback() {
                            @Override
                            public void onCompleteDirection(List<List<HashMap<String, String>>> routes, List<Integer> distances) {

                            }
                            @Override
                            public void onCompleteDistanceMatrix(List<HashMap<String, HashMap<String, String>>> results){
                                for (int i = 0; i < results.size(); i++){
                                    tasks.get(i).setDistance(results.get(i).get("distance"));
                                    tasks.get(i).setDuration(results.get(i).get("duration"));
                                }
                                Common.sortTaskListAsc(tasks);
                                mTasks.addAll(tasks);
                                addresses.clear();
                                for (Task task : mTasks){
                                    addresses.add(task.getAddress());
                                }
                                try {
                                    mapUtils.callDirectionAPIWithWaypoints(shipperLatLng, addresses, params, new MyCallback() {
                                        @Override
                                        public void onCompleteDirection(List<List<HashMap<String, String>>> routes, List<Integer> distances) {
                                            int countAddress = 0;
                                            for (int i = 0; i < routes.size(); i++){
                                                List<HashMap<String, String>> path = routes.get(i);
                                                int addressLatLngIdx = 0;
                                                for (int j = 0; j < path.size(); j++){
                                                    HashMap point = path.get(j);
                                                    if (point.size() == 0){
                                                        addressLatLngIdx = j + 1;
                                                        break;
                                                    }
                                                    LatLng position = new LatLng(Double.parseDouble((String)point.get("lat")), Double.parseDouble((String)point.get("lng")));
                                                    points.add(position);

                                                }
                                                for (int k = addressLatLngIdx + 1; k < path.size(); k++){
                                                    HashMap point = path.get(k);
                                                    LatLng position = new LatLng(Double.parseDouble((String)point.get("lat")), Double.parseDouble((String)point.get("lng")));
                                                    Marker marker = mMap.addMarker(createDestinationMarkerOptions(mTasks.get(countAddress).getBillId(), position));
                                                    destinationMarkers.add(marker);
                                                    countAddress++;

                                                }
                                                routePolyline = mMap.addPolyline(createRoutePolylineOptions(points));

                                                if (points.size() > 0)
                                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(points.get(0), defaultZoom  ));
                                                dismissMapLoading();

                                            }
                                        }
                                        @Override
                                        public void onCompleteDistanceMatrix(List<HashMap<String, HashMap<String, String>>> results){

                                        }
                                    });
                                } catch(Exception e){
                                    Log.e("Map Route ERROR", "Create Route");
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (Exception e){
                        Log.e("Task Create Call Distance Matrix", "Error");
                        e.printStackTrace();
                    }
                }
                else {
                    dismissMapLoading();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private PolylineOptions createRoutePolylineOptions (List<LatLng> points) {
        PolylineOptions routePolylineOptions = new PolylineOptions();
        routePolylineOptions = new PolylineOptions();
        routePolylineOptions.width(polylineDefaultWidth);
        routePolylineOptions.color(polylineDefaultColor);
        routePolylineOptions.addAll(points);
        return routePolylineOptions;
    }

    private MarkerOptions createDestinationMarkerOptions(int billId, LatLng position){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(position)
                .title(String.valueOf(billId))
                .zIndex(2.0f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.customer_map_icon));
        return markerOptions;
    }

    private MarkerOptions createShipperMarkerOptions(LatLng position){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(position)
                .zIndex(1.0f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.shipper_map_icon));
        return markerOptions;
    }

    private void clearRoutePolyline(){
        if (routePolyline != null){
            routePolyline.remove();
        }
        if (points != null){
            points.clear();
        }
    }

    private void clearDestinationMarkers(){
        if (shipperMarker != null){
            shipperMarker.remove();
        }
        for (Marker marker : destinationMarkers){
            marker.remove();
        }
        destinationMarkers.clear();
    }

    private void showMapLoading() {
        if (mapLoading != null){
            mapLoading.setVisibility(View.VISIBLE);
        }
    }

    private void dismissMapLoading() {
        if (mapLoading != null){
            mapLoading.setVisibility(View.GONE);
        }
    }

    private void emptyTask(){
        mTasks.clear();
    }

    @SuppressLint("CheckResult")
    private void cancelTask(final int index, final int billId){
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Are you sure you want to CANCEL this task")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        try {
                            BillDao.CancelBill(billId);
                            if (oDialog != null){
                                oDialog.dismiss();
                            }
                            mTasks.remove(index);
                            createRoute(shipperLatLng, Login.userLocalStore.GetUserId());
                        } catch (Exception e){
                            Log.e("Cancel Task", "Error");
                            e.printStackTrace();
                        }
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
