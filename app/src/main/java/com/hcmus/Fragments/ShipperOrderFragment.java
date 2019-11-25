package com.hcmus.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.hcmus.Adapters.ShipperOrderAdapter;
import com.hcmus.Adapters.ShipperTaskAdapter;
import com.hcmus.DAO.BillDao;
import com.hcmus.Models.Task;
import com.hcmus.Utils.Common;
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

public class ShipperOrderFragment extends Fragment {
    private MapUtils mMapUtils;
    private Context mContext;
    private View mViewRoot;
    private RecyclerView taskList;
    private RecyclerView.Adapter orderAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private LatLng mOrigin;
    private List<Task> mTasks;
    private ProgressBar progress;
    private TextView noTaskMsg;
    public ShipperOrderFragment(Context context) {
        mContext = context;
        mMapUtils = new MapUtils(mContext);
        mTasks = new ArrayList<>();
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
        progress = root.findViewById(R.id.get_order_progress);
        noTaskMsg = root.findViewById(R.id.no_order_message);
        mViewRoot = root;

        layoutManager = new LinearLayoutManager(mContext);
        taskList.setLayoutManager(layoutManager);
        orderAdapter = new ShipperOrderAdapter(mContext, mViewRoot, mTasks);
        taskList.setAdapter(orderAdapter);

        DividerItemDecoration divider = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(mContext,R.drawable.line_divider));
        taskList.addItemDecoration(divider);

        return root;
    }

    public void createTask(LatLng origin, int userId){
        if (origin == null)
            return;
        emptyTask();
        mOrigin = origin;
        showProgress();
        createOrderObservable(userId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(createOrderObserver());
    }

    private Observable<List<Task>> createOrderObservable(final int userId){
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
                return tasks;
            }
        });
    }
    private Observer<List<Task>> createOrderObserver(){
        return new Observer<List<Task>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(final List<Task> tasks) {
                if (tasks.size() > 0){
                    dismissNoTask();
                    List<String> addresses = new ArrayList<String>();
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("mode", "DRIVING");
                    for (Task task : tasks){
                        addresses.add(task.getAddress());
                    }
                    try {
                        mMapUtils.callDistanceMatrixAPI(mOrigin, addresses, params,new MyCallback() {
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
                                showTask();
                                dismissProgress();
                            }
                        });
                    } catch (Exception e){
                        Log.e("Task Create Call Distance Matrix", "Error");
                        e.printStackTrace();
                    }
                }
                else {
                    dismissProgress();
                    showNoTask();
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

    private void showTask(){
        if (orderAdapter != null){
            orderAdapter.notifyDataSetChanged();
        }
    }

    private void emptyTask(){
        mTasks.clear();
        if (orderAdapter != null){
            orderAdapter.notifyDataSetChanged();
        }
    }

    private void showProgress(){
        if (progress != null)
            progress.setVisibility(View.VISIBLE);
    }


    private void dismissProgress(){
        if (progress != null)
            progress.setVisibility(View.GONE);
    }

    private void showNoTask(){
        if (progress != null)
            noTaskMsg.setVisibility(View.VISIBLE);
    }

    private void dismissNoTask(){
        if (progress != null)
            noTaskMsg.setVisibility(View.GONE);
    }
}
