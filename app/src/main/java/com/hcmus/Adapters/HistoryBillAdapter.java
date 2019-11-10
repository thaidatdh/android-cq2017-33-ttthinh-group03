package com.hcmus.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.DTO.BillDto;
import com.hcmus.DTO.CategoryDto;
import com.hcmus.shipe.R;

import org.w3c.dom.Text;

import java.util.List;

public class HistoryBillAdapter extends BaseAdapter {
    List<BillDto> myList;
    private Context context;

    public HistoryBillAdapter(Context context,List<BillDto> myList){
        this.context=context;
        this.myList=myList;
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.customlayout_billhistory, null);
        TextView id=(TextView)view.findViewById(R.id.billdetail_ID);
        TextView createdDate=(TextView)view.findViewById(R.id.billdetail_CreatedDate);
        TextView status=(TextView)view.findViewById(R.id.billdetail_status);
        ImageView thumbnail=(ImageView)view.findViewById(R.id.billdetail_statusthumbnail);

        BillDto billDto=myList.get(i);

        id.setText("Bill ID: "+Integer.toString(billDto.getBillId()));
        createdDate.setText("Created Date: "+billDto.getCreatedDate());
        switch (billDto.getStatus()){
            case 'N': status.setText("Status: New");thumbnail.setImageResource(R.drawable.new_icon);break;
            case 'G': status.setText("Status: Getting");thumbnail.setImageResource(R.drawable.getting_icon);break;
            case 'O': status.setText("Status: Ongoing");thumbnail.setImageResource(R.drawable.ongoing_icon);break;
            case 'C': status.setText("Status: Complete");thumbnail.setImageResource(R.drawable.complete_icon);break;
        }

        return view;
    }
}