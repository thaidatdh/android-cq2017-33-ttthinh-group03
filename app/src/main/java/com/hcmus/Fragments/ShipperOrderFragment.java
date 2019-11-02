package com.hcmus.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.hcmus.Adapters.ShipperOrderAdapter;
import com.hcmus.Models.Task;
import com.hcmus.Utils.MapUtils;
import com.hcmus.Utils.MyCallback;
import com.hcmus.shipe.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShipperOrderFragment extends Fragment {
    private MapUtils mMapUtils;
    private Context mContext;
    private View mViewRoot;
    private RecyclerView taskList;
    private RecyclerView.Adapter orderAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public ShipperOrderFragment(Context context) {
        mContext = context;
        mMapUtils = new MapUtils(mContext);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_shipper_order, container, false);
        taskList = (RecyclerView) root.findViewById(R.id.shipper_order_list);
        layoutManager = new LinearLayoutManager(mContext);
        mViewRoot = root;
        return root;
    }

    public void createTask(LatLng origin, final List<Task> tasks){
        List<String> addresses = new ArrayList<String>();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("mode", "DRIVING");
        //new LatLng(10.774853, 106.641888)
        for (Task task : tasks){
            addresses.add(task.getAddress());
        }
        mMapUtils.callDistanceMatrixAPI(origin, addresses, params,new MyCallback() {
            @Override
            public void onCompleteDirection(List<List<HashMap<String, String>>> routes, List<Integer> distances) {

            }
            @Override
            public void onCompleteDistanceMatrix(List<HashMap<String, HashMap<String, String>>> results){
                for (int i = 0; i < results.size(); i++){
                    tasks.get(i).setDistance(results.get(i).get("distance"));
                    tasks.get(i).setDuration(results.get(i).get("duration"));
                }
                showTask(tasks);
            }
        });
    }
    private void showTask(List<Task> tasks){
        taskList.setLayoutManager(layoutManager);
        orderAdapter = new ShipperOrderAdapter(mContext, mViewRoot, tasks);
        taskList.setAdapter(orderAdapter);
    }
}
