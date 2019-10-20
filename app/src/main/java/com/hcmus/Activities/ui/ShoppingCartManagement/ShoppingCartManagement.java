package com.hcmus.Activities.ui.ShoppingCartManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hcmus.Activities.ui.Category.CustomerCategory;
import com.hcmus.Activities.ui.ItemManagement.CartDomain;
import com.hcmus.Const.BillDetailCustomAdapter;
import com.hcmus.shipe.R;

public class ShoppingCartManagement extends AppCompatActivity {

    ListView listSelectedItem;
    TextView textCartItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart_management);

        Intent intent=getIntent();
        listSelectedItem=(ListView)findViewById(R.id.list_selected_item);
        BillDetailCustomAdapter ca=new BillDetailCustomAdapter(ShoppingCartManagement.this,CartDomain.ListItemInCart);
        listSelectedItem.setAdapter(ca);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar_shopping_cart,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_search){
            return true;
        }
        return false;
    }

}
