package com.hcmus.shipe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity implements View.OnClickListener{

    Button btnRegister;
    EditText edtName,edtAge,edtUsername,edtPassword,edtPhone,edtAddress;
    RadioGroup radUserType;
    RadioButton radShop;
    RadioButton radShipper;
    RadioButton radCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister=(Button)findViewById(R.id.btnRegister);
        edtAge=(EditText)findViewById(R.id.edtAge);
        edtName=(EditText)findViewById(R.id.edtName);
        edtUsername=(EditText)findViewById(R.id.edtUsername);
        edtPassword=(EditText)findViewById(R.id.edtPassword);
        edtPhone=(EditText)findViewById(R.id.edtPhone);
        edtAddress=(EditText)findViewById(R.id.edtAddress);

        radUserType=(RadioGroup )findViewById(R.id.radioGroupUserType);
        radShop=(RadioButton)findViewById(R.id.radShop);
        radShipper=(RadioButton)findViewById(R.id.radShipper);
        radCustomer=(RadioButton)findViewById(R.id.radShipper);

        btnRegister.setOnClickListener(this);

    }
    @Override
    public void onClick(View view){
        switch(view.getId()) {
            case R.id.btnRegister:
               /* String name= edtName.getText().toString();
                String username=edtUsername.getText().toString();
                String password=edtPassword.getText().toString();
                 //encrypt password

                String ecPs=com.hcmus.Utils.ConversionUtils.User.EncryptPassword(password);
                int age=Integer.parseInt(edtAge.getText().toString());
                String address=edtAddress.getText().toString();
                String phone=edtPhone.getText().toString();
                int type=radUserType.getCheckedRadioButtonId();
                RadioButton radType=(RadioButton)findViewById(type);
                String usertype=radType.getText().toString();

                User registeredData=new User(name,username,ecPs,age,usertype,phone,address);
                UserDto saveRegisteredData=new UserDto();
                saveRegisteredData.setUsername(registeredData.username);
                saveRegisteredData.setPassword(registeredData.password);
                saveRegisteredData.setName(registeredData.name);
                saveRegisteredData.setAddress(registeredData.address);
                saveRegisteredData.setPhone(registeredData.phone);
                saveRegisteredData.setUserType(registeredData.type);
                saveRegisteredData.setAge(registeredData.age);*/



                ClickRegister();

                Intent activitymoi=new Intent(this,MainActivity.class);
                this.startActivity(activitymoi);
                break;

        }
    }
    private void ClickRegister()
    {
        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setTitle("Congratulations!");
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setMessage("You have successfully registered");
        alertDialog.setCancelable(false);

        alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();

    }
}
