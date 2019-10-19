package com.hcmus.Activities.ui.ItemManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hcmus.Const.ItemCustomAdapter;
import com.hcmus.DAO.ItemDao;
import com.hcmus.DTO.ItemDto;
import com.hcmus.shipe.R;

import java.util.List;

public class ItemManagement extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_management);

        Intent intent=getIntent();
        int pos = intent.getIntExtra("pos",0);

        listView=(ListView)findViewById(R.id.listView_ItemManagement);
        List<ItemDto> itemDtos= (List<ItemDto>) ItemDao.findByCategory(pos);
        ItemCustomAdapter ca= new ItemCustomAdapter(ItemManagement.this,itemDtos,pos);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogShoppingCart();
            }
        });
    }
    private void dialogShoppingCart(){
        Dialog dialog=new Dialog(ItemManagement.this);
        dialog.setContentView(R.layout.shoppingcart_dialog);
        dialog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar_customer,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_search){
            return true;
        }else if(id==R.id.action_cart){
            return true;
        }
        return false;
    }
}
