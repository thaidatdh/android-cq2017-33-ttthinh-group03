package com.hcmus.Activities.ui.ShoppingCartManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hcmus.Activities.ui.Category.CustomerCategory;
import com.hcmus.Activities.ui.ItemManagement.CartDomain;
import com.hcmus.Const.BillDetailCustomAdapter;
import com.hcmus.DAO.BillDao;
import com.hcmus.DAO.BillDetailDao;
import com.hcmus.DTO.BillDetailDto;
import com.hcmus.DTO.BillDto;
import com.hcmus.shipe.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShoppingCartManagement extends AppCompatActivity {

    ListView listSelectedItem;
    TextView textCartItemCount;
    Button btnBuy;
    long totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart_management);

        Intent intent=getIntent();
        listSelectedItem=(ListView)findViewById(R.id.list_selected_item);
        final BillDetailCustomAdapter ca=new BillDetailCustomAdapter(ShoppingCartManagement.this,CartDomain.ListItemInCart);
        listSelectedItem.setAdapter(ca);

        btnBuy=(Button)findViewById(R.id.button_buy);
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CartDomain.ListItemInCart.size() > 0) {
                    BillDto bill = new BillDto();
                    int bill_id = BillDao.Insert(bill);
                    for (int i = 0; i < CartDomain.ListItemInCart.size(); i++) {
                        BillDetailDto bill_detail = CartDomain.ListItemInCart.get(i);
                        bill_detail.setBillId(bill_id);
                        totalPrice += bill_detail.getAmount();
                        BillDetailDao.Insert(bill_detail);
                    }
                    bill = BillDao.findById(bill_id);
                    bill.setTotalPrice(totalPrice);

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String currentDateTime = simpleDateFormat.format(new Date());
                    bill.setCreatedDate(currentDateTime);

                    CartDomain.ListItemInCart.clear();
                    ca.notifyDataSetChanged();
                    BillDao.Update(bill);
                    Toast.makeText(ShoppingCartManagement.this, "Buy success", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
