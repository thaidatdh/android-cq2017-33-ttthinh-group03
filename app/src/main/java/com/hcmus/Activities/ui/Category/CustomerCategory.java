package com.hcmus.Activities.ui.Category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.hcmus.Activities.ui.ItemManagement.CartDomain;
import com.hcmus.Activities.ui.ItemManagement.CustomerHome;
import com.hcmus.Activities.ui.ItemManagement.CustomerInfo;
import com.hcmus.Activities.ui.ItemManagement.Customer_History;
import com.hcmus.Activities.ui.ItemManagement.ItemManagement;
import com.hcmus.Activities.ui.ShoppingCartManagement.ShoppingCartManagement;
import com.hcmus.Const.CategoryCustomAdapter;
import com.hcmus.DAO.CategoryDao;
import com.hcmus.DTO.CategoryDto;
import com.hcmus.shipe.Login;
import com.hcmus.shipe.R;

import java.util.List;

public class CustomerCategory extends AppCompatActivity {

    GridView gridView;
    TextView textCartItemCount;
    TextView txtvHome;
    TextView txtvHis;
    TextView txtvAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_category);

        Intent intent=getIntent();
        gridView=(GridView) findViewById(R.id.gridView);
        final List<CategoryDto> categoryDtos= CategoryDao.SelectAll();
        CategoryCustomAdapter ca= new CategoryCustomAdapter(CustomerCategory.this,categoryDtos);
        gridView.setAdapter(ca);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CustomerCategory.this, ItemManagement.class);
                int pos=categoryDtos.get(i).getCategoryId();
                intent.putExtra("pos",pos);
                startActivity(intent);
            }
        });

        txtvHis=(TextView)findViewById(R.id.txtvHistory);
        txtvHome=(TextView)findViewById(R.id.txtvHome);
        txtvAcc=(TextView)findViewById(R.id.txtvAccount);
        txtvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CustomerHome.class));
            }
        });
        txtvHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Customer_History.class));
            }
        });
        txtvAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CustomerInfo.class));
            }
        });
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
        //Login.Logout();
        //finishAndRemoveTask();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_search){
            return true;
        }else if(id==R.id.action_cart){
            Intent intent = new Intent(CustomerCategory.this, ShoppingCartManagement.class);
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
