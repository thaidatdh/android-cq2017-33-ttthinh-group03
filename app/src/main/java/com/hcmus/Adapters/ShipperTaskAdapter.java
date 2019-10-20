package com.hcmus.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hcmus.shipe.R;

import java.util.HashMap;
import java.util.List;

public class ShipperTaskAdapter extends RecyclerView.Adapter<ShipperTaskAdapter.ShipperTaskViewHolder> {
    private List<HashMap<String, String>> mDataset;

    public static class ShipperTaskViewHolder extends RecyclerView.ViewHolder {
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
    public ShipperTaskAdapter(List<HashMap<String, String>> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ShipperTaskAdapter.ShipperTaskViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shipper_task_item, parent, false);

        ShipperTaskViewHolder vh = new ShipperTaskViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ShipperTaskViewHolder holder, int position) {
        holder.id.setText(mDataset.get(position).get("id"));
        holder.address.setText(mDataset.get(position).get("address"));
        holder.distance.setText(mDataset.get(position).get("distance"));
        holder.duration.setText(mDataset.get(position).get("duration"));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
