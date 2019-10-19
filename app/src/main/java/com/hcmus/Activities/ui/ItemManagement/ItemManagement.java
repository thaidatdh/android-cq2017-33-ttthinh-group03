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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hcmus.Activities.ui.ShoppingCartManagement.ShoppingCartManagement;
import com.hcmus.Const.ItemCustomAdapter;
import com.hcmus.DAO.BillDao;
import com.hcmus.DAO.BillDetailDao;
import com.hcmus.DAO.ItemDao;
import com.hcmus.DTO.BillDetailDto;
import com.hcmus.DTO.BillDto;
import com.hcmus.DTO.ItemDto;
import com.hcmus.shipe.R;

import java.util.List;

public class ItemManagement extends AppCompatActivity {

    ListView listView;
    ItemDto selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_management);

        Intent intent=getIntent();
        int pos = intent.getIntExtra("pos",0);

        listView=(ListView)findViewById(R.id.listView_ItemManagement);
        final List<ItemDto> itemDtos=ItemDao.findByCategory(pos);
        ItemCustomAdapter ca= new ItemCustomAdapter(ItemManagement.this,itemDtos);
        listView.setAdapter(ca);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem=itemDtos.get(position);
                dialogShoppingCart();
            }
        });
    }
    private void dialogShoppingCart(){
        final Dialog dialog=new Dialog(ItemManagement.this);
        dialog.setContentView(R.layout.item_info_popup);
        TextView name=(TextView)dialog.findViewById(R.id.Name_item);
        TextView price=(TextView)dialog.findViewById(R.id.price_item);
        TextView des=(TextView)dialog.findViewById(R.id.description);
        ImageView closeBtn=(ImageView)dialog.findViewById(R.id.close);
        ImageView thumbnailItem=(ImageView)dialog.findViewById(R.id.thumbnail_item);
        Button addBtn=(Button)dialog.findViewById(R.id.button_add);

        name.setText(selectedItem.getName());
        price.setText(Long.toString(selectedItem.getPrice()));
        des.setText(selectedItem.getDescription());

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(ItemManagement.this, "add success", Toast.LENGTH_SHORT).show();
                //add item
                BillDetailDto bill = new BillDetailDto();
                bill.setItemId(selectedItem.getId());
                bill.setAmount((int)selectedItem.getPrice());
                CartDomain.ListItemInCart.add(bill);

                Intent intent = new Intent(ItemManagement.this, ShoppingCartManagement.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
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
