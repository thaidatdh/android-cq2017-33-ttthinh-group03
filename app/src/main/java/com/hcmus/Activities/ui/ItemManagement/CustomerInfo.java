package com.hcmus.Activities.ui.ItemManagement;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.Activities.ui.Category.CustomerCategory;
import com.hcmus.Activities.ui.ShoppingCartManagement.ShoppingCartManagement;
import com.hcmus.DTO.BillDetailDto;
import com.hcmus.DTO.ItemDto;
import com.hcmus.shipe.R;

public class CustomerInfo extends AppCompatActivity {
    ItemDto selectedItem;
    TextView textCartItemCount;
    TextView txtvCate;
    TextView txtvHis;
    TextView txtvHome;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);

        txtvCate=(TextView)findViewById(R.id.txtvCategory);
        txtvHis=(TextView)findViewById(R.id.txtvHistory);
        txtvHome=(TextView)findViewById(R.id.txtvHome);

        txtvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CustomerHome.class));
            }
        });
        txtvCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CustomerCategory.class));
            }
        });
        txtvHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Customer_History.class));
            }
        });

    }

    private void dialogShoppingCart(){
        final Dialog dialog=new Dialog(CustomerInfo.this);
        dialog.setContentView(R.layout.item_info_popup);
        TextView name=(TextView)dialog.findViewById(R.id.Name_item);
        TextView price=(TextView)dialog.findViewById(R.id.price_item);
        TextView des=(TextView)dialog.findViewById(R.id.description);
        ImageView closeBtn=(ImageView)dialog.findViewById(R.id.close);
        ImageView thumbnailItem=(ImageView)dialog.findViewById(R.id.thumbnail_item);
        Button addBtn=(Button)dialog.findViewById(R.id.button_add);

        name.setText("Product: "+selectedItem.getName());
        price.setText("Price: "+Long.toString(selectedItem.getPrice())+" VND");
        des.setText("Description: "+selectedItem.getDescription());

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //add item
                BillDetailDto bill = new BillDetailDto();
                bill.setItemId(selectedItem.getId());
                bill.setAmount((int)selectedItem.getPrice());
                CartDomain.ListItemInCart.add(bill);
                setupBadge();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        setupBadge();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar_customer,menu);
        final MenuItem menuItem = menu.findItem(R.id.action_cart);
        View actionView = menuItem.getActionView();
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);
        setupBadge();
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }
    @Override
    public void onBackPressed() {
        //Login.Logout(getApplicationContext());
        //finishAndRemoveTask();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_search){
            return true;
        }else if(id==R.id.action_cart){
            Intent intent = new Intent(CustomerInfo.this, ShoppingCartManagement.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
    private void setupBadge() {
        if (textCartItemCount != null) {
            int mCartItemCount = CartDomain.ListItemInCart.size();
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
