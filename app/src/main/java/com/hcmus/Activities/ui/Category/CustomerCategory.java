package com.hcmus.Activities.ui.Category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hcmus.Activities.ui.ItemManagement.ItemManagement;
import com.hcmus.Const.CategoryCustomAdapter;
import com.hcmus.DAO.CategoryDao;
import com.hcmus.DTO.CategoryDto;
import com.hcmus.shipe.R;

import java.util.List;

public class CustomerCategory extends AppCompatActivity {

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_category);

        gridView=(GridView) findViewById(R.id.gridView);
        List<CategoryDto> categoryDtos= CategoryDao.SelectAll();
        CategoryCustomAdapter ca= new CategoryCustomAdapter(CustomerCategory.this,categoryDtos);
        gridView.setAdapter(ca);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CustomerCategory.this, ItemManagement.class);
                intent.putExtra("pos",i);
                startActivity(intent);
            }
        });
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
