package com.hcmus.Activities.ui.ProductManagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hcmus.DTO.ItemDto;
import com.hcmus.shipe.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryListItem extends Fragment {


    public static class CustomListAdapter extends ArrayAdapter<ItemDto> {
        Context context;
        int layout;
        List<ItemDto> list;
        public CustomListAdapter(Context context,int layout, ArrayList<ItemDto> list)
        {
            super(context,layout,list);
            this.context=context;
            this.layout=layout;
            this.list=list;
        }
        @Override
        public int getCount() {
            if (list != null)
                return list.size();
            else
                return 0;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.category_list_item, null);

            TextView name=convertView.findViewById(R.id.txtvName);
            TextView price=convertView.findViewById(R.id.txtvPrice);
            ImageView img=convertView.findViewById(R.id.imgItem);

            ItemDto item=list.get(position);
            name.setText(item.getName());
            price.setText(Long.toString(item.getPrice()));
            loadImageFromUrl(item.getThumbnail(),img);
            return convertView;
        }
        public void loadImageFromUrl(String url,ImageView img)
        {
            Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(img,new com.squareup.picasso.Callback(){
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                        }
                    });

        }
    }
}
