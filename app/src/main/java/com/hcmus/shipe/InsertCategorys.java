package com.hcmus.shipe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.DAO.CategoryDao;
import com.hcmus.DTO.CategoryDto;

public class InsertCategorys extends AppCompatActivity {
    EditText edtName,edtDescription;
    Button btnInsert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_categorys);

        edtName=(EditText)findViewById(R.id.edtNameCategory);
        edtDescription=(EditText)findViewById(R.id.edtDescriptionCategory);
        btnInsert=(Button)findViewById(R.id.btnInsertCategory);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=edtName.getText().toString();
                String des=edtDescription.getText().toString();

                //Tao categoryDto
                CategoryDto categoryDto=new CategoryDto();
                categoryDto.setName(name);
                categoryDto.setDescription(des);

                //Dua categoryDto vao database
                CategoryDao.InsertNoID(categoryDto);
                AlertDialog.Builder builder=new AlertDialog.Builder(InsertCategorys.this);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setMessage("Insert category success!").create().show();

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
