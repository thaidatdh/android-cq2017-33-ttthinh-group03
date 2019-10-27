package com.hcmus.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hcmus.Activities.ui.BillManagement.BillManagementFragment;
import com.hcmus.DAO.BillDao;
import com.hcmus.DAO.BillDetailDao;
import com.hcmus.Models.Task;
import com.hcmus.shipe.R;

import java.util.HashMap;
import java.util.List;

public class ShipperTaskAdapter extends RecyclerView.Adapter<ShipperTaskAdapter.ShipperTaskViewHolder> {
    private List<Task> mDataset;
    private Context mContext;
    private LayoutInflater layoutInflater;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private View dialogView;
    private Button acceptBtn;
    private Button closeBtn;

    private ListView taskListItem;
    private TextView customerName;
    private TextView customerAddress;
    private TextView customerPhone;
    private TextView totalPice;
    private TextView description;
    private TextView deliveryDate;

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

        settingTaskDialog();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ShipperTaskAdapter.ShipperTaskViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shipper_task_item, parent, false);

        final ShipperTaskViewHolder vh = new ShipperTaskViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialogValue(vh.getAdapterPosition());
                showDialog();
            }
        });
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int removeIndex = vh.getAdapterPosition();
                acceptTask(mDataset.get(removeIndex).getBillId());
                mDataset.remove(removeIndex);
                notifyItemRemoved(removeIndex);
                closeDialog();
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
        holder.distance.setText(task.getDistance().get("text"));
        holder.duration.setText(task.getDuration().get("text"));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void settingTaskDialog(){
        dialogBuilder = new AlertDialog.Builder(mContext);
        dialogView = layoutInflater.inflate(R.layout.shipper_task_dialog, null);
        dialogBuilder.setView(dialogView);
        dialog = dialogBuilder.create();
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        acceptBtn = (Button) dialogView.findViewById(R.id.task_accept_btn);
        closeBtn = (Button) dialogView.findViewById(R.id.task_close_btn);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDialog();
            }
        });
        //Value
        taskListItem = (ListView) dialogView.findViewById(R.id.task_list_item);
        customerName = (TextView) dialogView.findViewById(R.id.task_customer_name);
        customerAddress = (TextView)dialogView.findViewById(R.id.task_customer_address);
        customerPhone = (TextView) dialogView.findViewById(R.id.task_customer_phone);
        totalPice = (TextView)dialogView.findViewById(R.id.bill_total_price);
        description = (TextView)dialogView.findViewById(R.id.task_bill_description);
        deliveryDate = (TextView)dialogView.findViewById(R.id.task_delivery_date);
    }
    private void setDialogValue(int index){
        Task task = mDataset.get(index);
        BillManagementFragment.BillListAdapter listItemAdapter = new BillManagementFragment.BillListAdapter(mContext, R.layout.bill_detail_list_item, BillDetailDao.findById(task.getBillId()));
        taskListItem.setAdapter(listItemAdapter);
        String name = task.getFirstName() + " " + task.getLastName();
        customerName.setText(name);
        customerAddress.setText(task.getAddress());
        customerPhone.setText(task.getPhone());
        totalPice.setText(String.valueOf(task.getTotalPrice()));
        description.setText(task.getDescription());
        deliveryDate.setText(task.getDeliverTime());
    }

    private void showDialog(){
        if (dialog != null){
            dialog.show();
            dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    private void closeDialog(){
        if (dialog != null){
            dialog.dismiss();
        }
    }
    private void acceptTask(int billId){
        BillDao.AssignShipper(billId, 2);
    }
}
