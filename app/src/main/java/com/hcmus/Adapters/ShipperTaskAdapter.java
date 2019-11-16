package com.hcmus.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.hcmus.DAO.BillDao;
import com.hcmus.Dialog.ShipperTaskDialog;
import com.hcmus.Models.Task;
import com.hcmus.Utils.DialogBtnCallBackInterface;
import com.hcmus.shipe.Login;
import com.hcmus.shipe.R;

import java.util.List;

public class ShipperTaskAdapter extends RecyclerView.Adapter<ShipperTaskAdapter.ShipperTaskViewHolder> {
    private List<Task> mDataset;
    private Context mContext;
    private LayoutInflater layoutInflater;

    private View root;

    public class ShipperTaskViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout taskView;
        public TextView id;
        public TextView address;
        public TextView distance;
        public TextView duration;

        public ShipperTaskViewHolder(LinearLayout v) {
            super(v);
            taskView = v;
            id = taskView.findViewById(R.id.shipper_task_id);
            address = taskView.findViewById(R.id.shipper_task_address);
            distance = taskView.findViewById(R.id.shipper_task_distance);
            duration = taskView.findViewById(R.id.shipper_task_duration);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ShipperTaskAdapter(Context context, View parent, List<Task> myDataset) {
        mDataset = myDataset;
        mContext = context;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ShipperTaskAdapter.ShipperTaskViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        root = parent;
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shipper_task_item, parent, false);

        final ShipperTaskViewHolder vh = new ShipperTaskViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int index =  vh.getAdapterPosition();
                ShipperTaskDialog dialog = new ShipperTaskDialog(mContext, mDataset, index, new DialogBtnCallBackInterface(){
                    @Override
                    public void onBtnClick(String action){
                        acceptTask(mDataset.get(index).getBillId());
                        mDataset.remove(index);
                        notifyItemRemoved(index);

                        if (mDataset.size() == 0){
                            showNoTaskMsg();
                        }
                    }
                });
                dialog.show();
            }
        });
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ShipperTaskViewHolder holder, int position) {
        Task task = mDataset.get(position);
        holder.id.setText(String.valueOf(task.getBillId()));
        holder.address.setText(task.getAddress());
        if (task.getDistance() != null){
            holder.distance.setText(task.getDistance().get("text"));
        }
        if (task.getDuration() != null){
            holder.duration.setText(task.getDuration().get("text"));
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
    @SuppressLint("CheckResult")
    private void acceptTask(final int billId){
        try {
            BillDao.AssignShipper(billId, Login.userLocalStore.GetUserId());

        } catch (Exception e){
            Log.e("Cancel Task", "Error");
            e.printStackTrace();
        }
    }

    private void showNoTaskMsg(){
        try {
            root.findViewById(R.id.no_task_message).setVisibility(View.VISIBLE);
        } catch(Exception e){

        }
    }
}
