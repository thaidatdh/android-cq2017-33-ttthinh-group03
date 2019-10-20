package com.hcmus.shipe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.hcmus.DAO.CategoryDao;
import com.hcmus.DAO.ItemDao;
import com.hcmus.DTO.ItemDto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InsertItems extends AppCompatActivity {

    EditText edtNameItem,edtDescriptionItem,edtPriceItem,edtThumbnail;
    Button btnInsertItem;

    Spinner spnCategoryItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_items);

        edtDescriptionItem=(EditText)findViewById(R.id.edtDescriptionItem);
        edtNameItem=(EditText)findViewById(R.id.edtNameItem);
        edtPriceItem=(EditText)findViewById(R.id.edtPriceItem);
        edtThumbnail=(EditText)findViewById(R.id.edtThumbnailItem);

        btnInsertItem=(Button)findViewById(R.id.btnInsertItem);
        spnCategoryItem=(Spinner)findViewById(R.id.spnCategoryItem);

        //Lay category ra tu database
        List<String> data= new ArrayList<>();

        data= CategoryDao.SelectAllName();
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,data);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnCategoryItem.setAdapter(adapter);
        spnCategoryItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(InsertItems.this, spnCategoryItem.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnInsertItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=edtNameItem.getText().toString();
                String description=edtDescriptionItem.getText().toString();
                String thumbnail=edtThumbnail.getText().toString();
                String priceString=edtPriceItem.getText().toString();
                long price=Long.parseLong(priceString);
                //Category
                String categoryName=spnCategoryItem.getSelectedItem().toString();
                int category=CategoryDao.findByName(categoryName);
                //status
                char status='N';

                //Lay ngay hien tai
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String currentDateTime=simpleDateFormat.format(new Date());
                //Ngay update

                String updatedDate=null;


                ItemDto item=new ItemDto(name,description,thumbnail,price,category,status,currentDateTime,updatedDate);

                ItemDao.Insert(item);
                AlertDialog.Builder builder=new AlertDialog.Builder(InsertItems.this);
                builder.setMessage("Insert item success!").create().show();
            }
        });



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:break;
        }

        return super.onOptionsItemSelected(item);
    }
}
