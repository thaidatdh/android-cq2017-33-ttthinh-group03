package com.hcmus.Const;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.hcmus.DTO.ItemDto;
import com.hcmus.shipe.R;

import java.util.List;

public class ItemCustomAdapter extends BaseAdapter {
    private List<ItemDto> myList;
    private Context context;
    private int pos;

    public ItemCustomAdapter(Context context,List<ItemDto> myList,int pos){
        this.context=context;
        this.myList=myList;
        this.pos=pos;
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
        ImageView thumbnail=(ImageView)view.findViewById(R.id.imageView_thumbnail);
        TextView id=(TextView)view.findViewById(R.id.textView_ID);
        TextView name=(TextView)view.findViewById(R.id.textView2_Name);
        TextView price=(TextView)view.findViewById(R.id.textView3_Price);

        ItemDto  itemDto = myList.get(i);

        thumbnail.setImageResource(Integer.parseInt(itemDto.getThumbnail()));
        id.setText(itemDto.getId());
        name.setText(itemDto.getName());
        price.setText(Long.toString(itemDto.getPrice()));
        return view;
    }
}
