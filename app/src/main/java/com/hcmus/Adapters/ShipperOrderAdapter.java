package com.hcmus.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hcmus.Activities.ui.BillManagement.BillManagementFragment;
import com.hcmus.DAO.BillDao;
import com.hcmus.DAO.BillDetailDao;
import com.hcmus.Dialog.ShipperOrderDialog;
import com.hcmus.Models.Task;
import com.hcmus.Utils.DialogBtnCallBackInterface;
import com.hcmus.shipe.R;

import java.util.List;

public class ShipperOrderAdapter extends RecyclerView.Adapter<ShipperOrderAdapter.ShipperOrderViewHolder>{
    private List<Task> mDataset;
    private Context mContext;
    private LayoutInflater layoutInflater;

    private View root;


    public class ShipperOrderViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout taskView;
        public TextView id;
        public TextView address;
        public TextView distance;
        public TextView duration;

        public ShipperOrderViewHolder(LinearLayout v) {
            super(v);
            taskView = v;
            id = taskView.findViewById(R.id.shipper_task_id);
            address = taskView.findViewById(R.id.shipper_task_address);
            distance = taskView.findViewById(R.id.shipper_task_distance);
            duration = taskView.findViewById(R.id.shipper_task_duration);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ShipperOrderAdapter(Context context, View parent, List<Task> myDataset) {
        mDataset = myDataset;
        mContext = context;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ShipperOrderAdapter.ShipperOrderViewHolder onCreateViewHolder(ViewGroup parent,
                                                                       int viewType) {
        // create a new view
        root = parent;
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shipper_task_item, parent, false);
        final ShipperOrderAdapter.ShipperOrderViewHolder vh = new ShipperOrderAdapter.ShipperOrderViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int index =  vh.getAdapterPosition();
                ShipperOrderDialog dialog = new ShipperOrderDialog(mContext, mDataset, index, new DialogBtnCallBackInterface(){
                    @Override
                    public void onBtnClick(){
                        cancelTask(mDataset.get(index).getBillId());
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
    public void onBindViewHolder(ShipperOrderAdapter.ShipperOrderViewHolder holder, int position) {
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
    private void cancelTask(int billId){
        BillDao.CancelBill(billId);
    }

    private void showNoTaskMsg(){
        try {
            root.findViewById(R.id.no_task_message).setVisibility(View.VISIBLE);
        } catch(Exception e){

        }
    }
}
