package com.hcmus.shipe;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.DAO.CategoryDao;
import com.hcmus.DAO.ItemDao;
import com.hcmus.DTO.ItemDto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UpdateItems extends AppCompatActivity {
    EditText edtNameItem,edtDescriptionItem,edtPriceItem,edtThumbnail;
    Button btnUpdateItem;

    Spinner spnCategoryItem,spnStatusItem;
    int id=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_items);

        String name="item 5";
        long price=45;
        int category=2;
        char status='N';
        String date="2019-10-13 17:06:45.193";
        String des=null;
        String thumb=null;
        String update=null;
        ItemDto itemDB=new ItemDto(id,name,des,thumb,price,category,status,date,update);


        edtDescriptionItem=(EditText)findViewById(R.id.edtDescriptionItem);
        edtNameItem=(EditText)findViewById(R.id.edtNameItem);
        edtPriceItem=(EditText)findViewById(R.id.edtPriceItem);
        edtThumbnail=(EditText)findViewById(R.id.edtThumbnailItem);

        btnUpdateItem=(Button)findViewById(R.id.btnInsertItem);
        spnCategoryItem=(Spinner)findViewById(R.id.spnCategoryItem);
        spnStatusItem=(Spinner)findViewById(R.id.spnStatusItem);


        //Hien ra du lieu trong database
        edtNameItem.setText(name);
        String priceS=Long.toString(price);
        edtPriceItem.setText(priceS);
        edtDescriptionItem.setText(des);
        edtThumbnail.setText(thumb);



        //Status
        List<String> listStatus = new ArrayList<>();
        listStatus.add("New");
        listStatus.add("Sold out");
        listStatus.add("Stop selling");

        ArrayAdapter<String> adapterStatus = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listStatus);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnStatusItem.setAdapter(adapterStatus);
        spnStatusItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(UpdateItems.this, spnStatusItem.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });
        //Lay category ra tu database

        List<String> listCategory= new ArrayList<>();

        listCategory= CategoryDao.SelectAllName();

        ArrayAdapter<String> adapterCategory = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listCategory);

        adapterCategory.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spnCategoryItem.setAdapter(adapterCategory);

        spnCategoryItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(UpdateItems.this, spnCategoryItem.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnUpdateItem.setOnClickListener(new View.OnClickListener() {
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
                String statusName=spnStatusItem.getSelectedItem().toString();
                if(statusName=="New")
                    status='N';
                if(statusName=="Sold out")
                    status='S';
                if(statusName=="Stop selling")
                    status='X';


                //Lay ngay hien tai la ngay update
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String currentDateTime=simpleDateFormat.format(new Date());


                //Tao ItemDto
                ItemDto item=new ItemDto(id,name,description,thumbnail,price,category,status,currentDateTime);
                item.setId(id);
                item.setName(name);
                item.setDescription(description);
                item.setThumbnail(thumbnail);
                item.setPrice(price);
                item.setCategory(category);
                item.setStatus(status);
                item.setUpdatedDate(currentDateTime);

                //Dua ItemDto vao database
                ItemDao.UpdateItem(item);

                AlertDialog.Builder builder=new AlertDialog.Builder(UpdateItems.this);
                builder.setMessage("Update item success!").create().show();
            }
        });
    }
}
