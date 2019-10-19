package com.hcmus.Const;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hcmus.Activities.ui.ItemManagement.CartDomain;
import com.hcmus.Activities.ui.ShoppingCartManagement.ShoppingCartManagement;
import com.hcmus.DAO.ItemDao;
import com.hcmus.DTO.BillDetailDto;
import com.hcmus.DTO.CategoryDto;
import com.hcmus.DTO.ItemDto;
import com.hcmus.shipe.R;

import java.util.List;

public class BillDetailCustomAdapter extends BaseAdapter {

    List<BillDetailDto> myList;
    private Context context;

    public BillDetailCustomAdapter(Context context, List<BillDetailDto> myList){
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
        view = LayoutInflater.from(context).inflate(R.layout.customlayout_billdetail, null);
        TextView id=(TextView)view.findViewById(R.id.billdetail_ID);
        TextView name=(TextView)view.findViewById(R.id.billdetail_Name);
        TextView ammount=(TextView)view.findViewById(R.id.billdetail_Price);
        ImageView close=(ImageView)view.findViewById(R.id.billdetail_close);

        BillDetailDto bill=myList.get(i);
        ItemDto selectedItem= (ItemDto) ItemDao.findById(bill.getItemId());
        if(selectedItem!=null){
            id.setText(Integer.toString(selectedItem.getId()));
            name.setText(selectedItem.getName());
            ammount.setText(Long.toString(selectedItem.getPrice()));
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Delete Item", Toast.LENGTH_SHORT).show();
                    CartDomain.ListItemInCart.remove(i);
                    BillDetailCustomAdapter.this.notifyDataSetChanged();
                }
            });
        }
        return view;
    }
}
