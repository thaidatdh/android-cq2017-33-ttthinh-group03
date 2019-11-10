package com.hcmus.Activities.ui.ItemManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hcmus.Activities.ui.Category.CustomerCategory;
import com.hcmus.Activities.ui.ShoppingCartManagement.ShoppingCartManagement;
import com.hcmus.Adapters.HistoryBillAdapter;
import com.hcmus.Adapters.ListItemBillAdapter;
import com.hcmus.Const.BillDetailCustomAdapter;
import com.hcmus.DAO.BillDao;
import com.hcmus.DAO.BillDetailDao;
import com.hcmus.DTO.BillDetailDto;
import com.hcmus.DTO.BillDto;
import com.hcmus.shipe.Login;
import com.hcmus.shipe.R;
import com.hcmus.shipe.User;
import com.hcmus.shipe.UserLocalStore;

import java.util.ArrayList;
import java.util.List;

public class Customer_History extends AppCompatActivity {
    TextView textCartItemCount;
    TextView txtvCate;
    TextView txtvHome;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__history);

        txtvCate=(TextView)findViewById(R.id.txtvCategory);
        txtvHome=(TextView)findViewById(R.id.txtvHome);
        listView=(ListView)findViewById(R.id.list_history);

        Context context=getApplicationContext();
        final List<BillDto> listBill= BillDao.FindByCustomer(Login.userLocalStore.GetUserId());
        HistoryBillAdapter adapter=new HistoryBillAdapter(getApplicationContext(),listBill);
        listView.setAdapter(adapter);

        txtvCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CustomerCategory.class));
            }
        });
        txtvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CustomerCategory.class));
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogShoppingCart(listBill.get(position).getBillId());
            }
        });
    }
    private void dialogShoppingCart(int billid){
        final Dialog dialog=new Dialog(Customer_History.this);
        dialog.setContentView(R.layout.list_item_info_popup);
        ListView listItem=(ListView)dialog.findViewById(R.id.list_item_info_popup);
        ImageView close=(ImageView)dialog.findViewById(R.id.close);

        List<BillDetailDto> listBillItem= BillDetailDao.findById(billid);
        ListItemBillAdapter billDetailCustomAdapter=new ListItemBillAdapter(getApplicationContext(),listBillItem);
        listItem.setAdapter(billDetailCustomAdapter);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            Intent intent = new Intent(Customer_History.this, ShoppingCartManagement.class);
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
