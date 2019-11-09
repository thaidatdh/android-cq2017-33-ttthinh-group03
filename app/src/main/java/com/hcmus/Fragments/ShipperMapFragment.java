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
import com.hcmus.Models.Task;
import com.hcmus.Utils.MapUtils;
import com.hcmus.Utils.MyCallback;
import com.hcmus.shipe.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    private boolean isInputReady;
    public ShipperMapFragment (Context context){
        mContext = context;
        mapUtils = new MapUtils(mContext);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        points = new ArrayList<LatLng>();
        destinationMarkers = new ArrayList<Marker>();
        isInputReady = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_shipper_map, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (supportMapFragment == null)
            return rootView;
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
            }
        });
        return rootView;
    }
    public void setInputRoute(Object start, final List<Task> tasks){
        shipperLatLng = (LatLng) start;
        mTasks = tasks;
        if (mMap == null){
            //New Thread waiting for map to be ready
            HandlerThread newThread = new HandlerThread("New Thread");
            newThread.start();
            Handler handler = new Handler(newThread.getLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    while (mMap == null);
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            createRoute();
                        }
                    });
                }
            });
        } else {
            createRoute();
        }

    }

    private void createRoute() {
        //Shipper has no orders
        try {
            shipperMarker = mMap.addMarker(createShipperMarkerOptions(shipperLatLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(shipperLatLng, defaultZoom ));
        } catch(Exception e){
            Log.e("Error Map", "Convert Object Start Location to LatLng");
            e.printStackTrace();
        }
        if (routePolyline != null){
            clearRoutePolyline();
            clearDestinationMarkers();
        }
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

                    }
                }
                @Override
                public void onCompleteDistanceMatrix(List<HashMap<String, HashMap<String, String>>> results){

                }
            });
        } catch(Exception e){
            Log.e("Map Direction API ERROR", "Create Route");
            e.printStackTrace();
        }
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
        routePolyline.remove();
        points.clear();
    }

    private void clearDestinationMarkers(){
        shipperMarker.remove();
        for (Marker marker : destinationMarkers){
            marker.remove();
        }
        destinationMarkers.clear();
    }
}
