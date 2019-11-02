package com.hcmus.Fragments;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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
    private PolylineOptions routePolylineOptions;
    private Polyline routePolyline;
    private int polylineDefaultWidth = 10;
    private int polylineDefaultColor = Color.RED;
    private String defaultMode = "DRIVING";
    private List<LatLng> points;
    private List<Marker> markers;
    public ShipperMapFragment (Context context){
        mContext = context;
        mapUtils = new MapUtils(mContext);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        routePolylineOptions = new PolylineOptions();
        routePolylineOptions.width(polylineDefaultWidth);
        routePolylineOptions.color(polylineDefaultColor);
        points = new ArrayList<LatLng>();
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
                /*final LatLng khtn = new LatLng(10.763082, 106.682129);
                mMap.addMarker(new MarkerOptions().position(khtn).title("KHTN"));*/
            }
        });
        return rootView;
    }
    public void createRoute(Object start, final List<Task> tasks){
        if (routePolyline != null)
            clearRoutePolyline();
        List<String> addresses = new ArrayList<String>();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("mode", defaultMode);
        //new LatLng(10.774853, 106.641888)
        for (Task task : tasks){
            addresses.add(task.getAddress());
        }
        mapUtils.callDirectionAPIWithWaypoints(start, addresses, params, new MyCallback() {
            @Override
            public void onCompleteDirection(List<List<HashMap<String, String>>> routes, List<Integer> distances) {
                for (int i = 0; i < routes.size(); i++){
                    List<HashMap<String, String>> path = routes.get(i);
                    for (int j = 0; j < path.size(); j++){
                        HashMap point = path.get(j);
                        LatLng position = new LatLng(Double.parseDouble((String)point.get("lat")), Double.parseDouble((String)point.get("lng")));
                        points.add(position);

                        String address = (String)point.get("address");
                        if (address != null){
                            mMap.addMarker(new MarkerOptions().position(position));
                        }
                    }

                    routePolylineOptions.addAll(points);
                    routePolyline = mMap.addPolyline(routePolylineOptions);

                    if (points.size() > 0)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(points.get(0), defaultZoom  ));

                }
            }
            @Override
            public void onCompleteDistanceMatrix(List<HashMap<String, HashMap<String, String>>> results){

            }
        });

    }
    private void clearRoutePolyline(){
        routePolyline.remove();
        points.clear();
    }
}
