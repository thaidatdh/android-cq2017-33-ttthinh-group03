package com.hcmus.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.Activities.ui.ItemManagement.CartDomain;
import com.hcmus.DAO.ItemDao;
import com.hcmus.DTO.BillDetailDto;
import com.hcmus.DTO.ItemDto;
import com.hcmus.shipe.R;

import java.util.List;

public class ListItemBillAdapter extends BaseAdapter {

    List<BillDetailDto> myList;
    private Context context;

    public ListItemBillAdapter(Context context, List<BillDetailDto> myList){
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.customlayout_bill_list_item, null);
        TextView id=(TextView)view.findViewById(R.id.billdetail_ID);
        TextView name=(TextView)view.findViewById(R.id.billdetail_Name);
        TextView ammount=(TextView)view.findViewById(R.id.billdetail_Price);
        ImageView close=(ImageView)view.findViewById(R.id.billdetail_close);

        BillDetailDto bill=myList.get(i);
        ItemDto selectedItem= (ItemDto) ItemDao.findById(bill.getItemId());

        id.setText("ID: "+Integer.toString(selectedItem.getId()));
        name.setText("Product: "+selectedItem.getName());
        ammount.setText("Price: "+Long.toString(selectedItem.getPrice())+" VND");

        return view;
    }
}