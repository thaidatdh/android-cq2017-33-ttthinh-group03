package com.hcmus.Const;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hcmus.DTO.ItemDto;
import com.hcmus.shipe.R;

import java.util.List;

public class ItemCustomAdapter extends BaseAdapter {
    private List<ItemDto> myList;
    private Context context;

    public ItemCustomAdapter(Context context,List<ItemDto> myList){
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
        view = LayoutInflater.from(context).inflate(R.layout.customlayout_item, null);
        TextView id=(TextView)view.findViewById(R.id.items_ID);
        TextView name=(TextView)view.findViewById(R.id.item_Name);
        TextView price=(TextView)view.findViewById(R.id.items_Price);

        ItemDto  itemDto = myList.get(i);

        id.setText("ID: "+Integer.toString(itemDto.getId()));
        name.setText("Product: "+itemDto.getName());
        price.setText("Price: "+Long.toString(itemDto.getPrice())+" VND");

        return view;
    }
}
