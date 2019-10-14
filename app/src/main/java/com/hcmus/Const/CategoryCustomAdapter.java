package com.hcmus.Const;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.DTO.CategoryDto;
import com.hcmus.shipe.R;

import java.util.List;

public class CategoryCustomAdapter extends BaseAdapter {
    List<CategoryDto> myList;
    private Context context;

    public CategoryCustomAdapter(Context context,List<CategoryDto> myList){
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
        view = LayoutInflater.from(context).inflate(R.layout.customlayout_category, null);
        TextView textView=(TextView)view.findViewById(R.id.textView_category);

        CategoryDto categoryDto=myList.get(i);

        textView.setText(categoryDto.getName());
        return view;
    }
}
