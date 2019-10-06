package com.hcmus.shipe;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.*;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;

import java.util.*;

import com.hcmus.Utils.*;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView distanceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        distanceView = (TextView)findViewById(R.id.distance);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
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
        MapUtils mapUtils = new MapUtils(this);
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

                    distanceView.setText(distances.get(0).toString());
                }
            }
        });
    }
}
