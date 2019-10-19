package com.hcmus.Activities.ui.ShoppingCartManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hcmus.Activities.ui.ItemManagement.CartDomain;
import com.hcmus.Const.BillDetailCustomAdapter;
import com.hcmus.shipe.R;

public class ShoppingCartManagement extends AppCompatActivity {

    ListView listSelectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart_management);

        Intent intent=getIntent();
        listSelectedItem=(ListView)findViewById(R.id.list_selected_item);
        final BillDetailCustomAdapter ca=new BillDetailCustomAdapter(ShoppingCartManagement.this,CartDomain.ListItemInCart);
        listSelectedItem.setAdapter(ca);
        listSelectedItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ca.notifyDataSetChanged();
            }
        });
    }
}
