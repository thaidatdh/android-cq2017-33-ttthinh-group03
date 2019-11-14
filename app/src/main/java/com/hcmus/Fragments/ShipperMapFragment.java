package com.hcmus.Fragments;

import androidx.fragment.app.Fragment;

import android.content.Context;
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
import com.hcmus.Models.Task;
import com.hcmus.Utils.MapUtils;
import com.hcmus.Utils.MyCallback;
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

    private LatLng shipperLatLng;
    private List<Task> mTasks;

    private ProgressBar mapLoading;
    private boolean isInputReady;
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
            }
        });
        return rootView;
    }


    public void createRoute(Object start, int userId){
        if (start == null)
            return;
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
                    mTasks.addAll(tasks);

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
                    if (mTasks.size() == 0)
                        return;
                    List<String> addresses = new ArrayList<String>();
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("mode", defaultMode);

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
                                    for (int j = 0; j < path.size(); j++){
                                        HashMap point = path.get(j);
                                        LatLng position = new LatLng(Double.parseDouble((String)point.get("lat")), Double.parseDouble((String)point.get("lng")));
                                        points.add(position);

                                        String address = (String)point.get("address");
                                        if (address != null && j != 0){
                                            destinationMarkers.add(mMap.addMarker(createDestinationMarkerOptions(mTasks.get(countAddress).getBillId(), position)));
                                            countAddress++;
                                        }

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
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.customer_map_icon));
        return markerOptions;
    }

    private MarkerOptions createShipperMarkerOptions(LatLng position){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(position)
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
}
