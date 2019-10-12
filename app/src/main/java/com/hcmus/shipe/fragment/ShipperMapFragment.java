package com.hcmus.shipe.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.hcmus.Utils.MapUtils;
import com.hcmus.Utils.MyCallback;
import com.hcmus.shipe.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShipperMapFragment extends Fragment{

    private GoogleMap mMap;
    private Context mContext;
    public ShipperMapFragment (Context context){
        mContext = context;
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
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (supportMapFragment == null)
            return rootView;
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                // Add a marker in Sydney and move the camera
                final LatLng khtn = new LatLng(10.763082, 106.682129);
                mMap.addMarker(new MarkerOptions().position(khtn).title("KHTN"));
                final LatLng bk = new LatLng(10.771731, 106.658046);
                mMap.addMarker(new MarkerOptions().position(bk).title("BK"));
                final LatLng sg = new LatLng(10.760117, 106.682333);
                mMap.addMarker(new MarkerOptions().position(sg).title("ĐH Sài Gòn"));
                final LatLng vhMall = new LatLng(10.770530, 106.669516);
                mMap.addMarker(new MarkerOptions().position(vhMall).title("Van Hanh Mall"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(khtn, 10));
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("mode", "DRIVING");
                ArrayList<LatLng> waypoints = new ArrayList<LatLng>();
                waypoints.add(khtn);
                waypoints.add(vhMall);
                waypoints.add(bk);
                MapUtils mapUtils = new MapUtils(mContext);
                mapUtils.callDirectionAPIWithWaypoints(sg, waypoints, params, new MyCallback() {
                    @Override
                    public void onComplete(List<List<HashMap<String, String>>> routes, List<Integer> distances) {
                        ArrayList<LatLng> points = null;
                        PolylineOptions polyline = null;
                        ArrayList<LatLng> test = new ArrayList<LatLng>();
                        test.add(khtn);
                        test.add(bk);
                        for (int i = 0; i < routes.size(); i++){
                            points = new ArrayList();
                            polyline = new PolylineOptions();
                            List<HashMap<String, String>> path = routes.get(i);
                            for (int j = 0; j < path.size(); j++){
                                HashMap point = path.get(j);
                                LatLng position = new LatLng(Double.parseDouble((String)point.get("lat")), Double.parseDouble((String)point.get("lng")));
                                points.add(position);
                            }

                            //polyline.addAll(test);
                            polyline.addAll(points);
                            polyline.width(5);
                            polyline.color(Color.RED);

                            mMap.addPolyline(polyline);

                        }
                    }
                });
            }
        });
        return rootView;
    }
}
