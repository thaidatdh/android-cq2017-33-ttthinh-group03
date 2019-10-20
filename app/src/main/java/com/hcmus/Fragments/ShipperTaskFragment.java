package com.hcmus.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.hcmus.Adapters.ShipperTaskAdapter;
import com.hcmus.Models.Task;
import com.hcmus.Utils.MapUtils;
import com.hcmus.Utils.MyCallback;
import com.hcmus.shipe.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShipperTaskFragment extends Fragment {
    private MapUtils mMapUtils;
    private Context mContext;
    private RecyclerView taskList;
    private RecyclerView.Adapter taskAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public ShipperTaskFragment(Context context) {
        // Required empty public constructor
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapUtils = new MapUtils(mContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_shipper_task, container, false);
        taskList = (RecyclerView) root.findViewById(R.id.shipper_task_list);
        layoutManager = new LinearLayoutManager(mContext);
        return root;
    }
    public void createTask(LatLng origin, final List<Task> tasks){
        List<String> addresses = new ArrayList<String>();
        addresses.add("DH KHTN");
        addresses.add("DH BK TP HCM");
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("mode", "DRIVING");
        //new LatLng(10.774853, 106.641888)
        /*for (Task task : tasks){
            addresses.add(task.getAddress());
        }*/
        mMapUtils.callDistanceMatrixAPI(origin, addresses, params,new MyCallback() {
            @Override
            public void onCompleteDirection(List<List<HashMap<String, String>>> routes, List<Integer> distances) {

            }
            @Override
            public void onCompleteDistanceMatrix(List<HashMap<String, HashMap<String, String>>> results){
                List<String> distances = new ArrayList<>();
                List<String> durations = new ArrayList<>();
                for (HashMap<String, HashMap<String, String>> result : results){
                    distances.add(result.get("distance").get("text"));
                    durations.add(result.get("duration").get("text"));
                }
                showTask(tasks, distances, durations);
            }
        });
    }
    private void showTask(List<Task> tasks, List<String> distances, List<String> durations){
        List<HashMap<String, String>> dataSet = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < tasks.size(); i++){
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("id", String.valueOf(tasks.get(i).getBillId()));
            hm.put("address", String.valueOf(tasks.get(i).getAddress()));
            hm.put("distance", distances.get(i));
            hm.put("duration", durations.get(i));
            dataSet.add(hm);
        }
        taskList.setLayoutManager(layoutManager);
        taskAdapter = new ShipperTaskAdapter(dataSet);
        taskList.setAdapter(taskAdapter);
    }
}
