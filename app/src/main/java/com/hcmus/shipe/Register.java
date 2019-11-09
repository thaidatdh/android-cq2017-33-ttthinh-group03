package com.hcmus.shipe;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.DAO.UserDao;
import com.hcmus.DTO.UserDto;
import com.hcmus.Utils.ConversionUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Register extends AppCompatActivity implements View.OnClickListener{

    TextView txtvLogin, txtvSignIn;
    EditText edtFName,edtLName,edtAddress,edtPhone,edtUsername,edtPassword,edtBirthdate;
    RadioGroup radUserType;
    //RadioButton radShop;
    RadioButton radShipper;
    RadioButton radCustomer;
    Button btnSignUp,btnSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        radUserType=(RadioGroup )findViewById(R.id.radioGroupUserType);
        //radShop=(RadioButton)findViewById(R.id.radShop);
        radShipper=(RadioButton)findViewById(R.id.radShipper);
        radCustomer=(RadioButton)findViewById(R.id.radCustomer);
        txtvLogin=(TextView)findViewById(R.id.txtvLogin);
        btnSignUp=(Button)findViewById(R.id.btnSignUp);
        btnSelect=(Button)findViewById(R.id.btnSelect);
        txtvSignIn = (TextView)findViewById(R.id.txtrSignIn);

        edtFName=(EditText)findViewById(R.id.edtFirstName);
        edtLName=(EditText)findViewById(R.id.edtLastName);
        edtAddress=(EditText)findViewById(R.id.edtAddress);
        edtPhone=(EditText)findViewById(R.id.edtPhone);
        edtBirthdate=(EditText)findViewById(R.id.edtBirthdate);
        edtUsername=(EditText)findViewById(R.id.edtUsername);
        edtPassword=(EditText)findViewById(R.id.edtPassword);

        btnSignUp.setOnClickListener(this);
        txtvLogin.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
        txtvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
    @Override
    public void onClick(View view){
        switch(view.getId()) {
            case R.id.btnSignUp:
                String fname=edtFName.getText().toString();
                String lname=edtLName.getText().toString();
                String address=edtAddress.getText().toString();
                String phone=edtPhone.getText().toString();
                String birthdate=edtBirthdate.getText().toString();
                String username=edtUsername.getText().toString();
                String pwd = edtPassword.getText().toString();
                String password= ConversionUtils.User.EncryptPassword(pwd);

                int selectedUserType=radUserType.getCheckedRadioButtonId();
                String usertype=((RadioButton)findViewById(selectedUserType)).getText().toString();

                //Lay ngay hien tai
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String currentDateTime=simpleDateFormat.format(new Date());
                if (username.isEmpty()) {
                    android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(Register.this);
                    builder.setMessage("Please enter username!").setNegativeButton("Retry",null).create().show();
                    return;
                }
                if (pwd.isEmpty()) {
                    android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(Register.this);
                    builder.setMessage("Please enter password!").setNegativeButton("Retry",null).create().show();
                    return;
                }
                UserDto existDto = UserDao.findByUsername(username);
                if (existDto != null) {
                    android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(Register.this);
                    builder.setMessage("Username already exists!").setNegativeButton("Retry",null).create().show();
                    return;
                }
                else
                //Tao userDto
                {
                    UserDto userDto = new UserDto();
                    userDto.setUsername(username);
                    userDto.setPassword(password);
                    userDto.setFirstName(fname);
                    userDto.setLastName(lname);
                    userDto.setBirthDate(birthdate);
                    userDto.setAddress(address);
                    userDto.setPhone(phone);
                    userDto.setUserType(usertype);
                    userDto.setCreatedDate(currentDateTime);
                    //Dua thong tin user vao database
                    int result = UserDao.Insert(userDto);
                    if (result != -1)
                        Login.Authorize(getApplicationContext(), username);
                    else {
                        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(Register.this);
                        builder.setMessage("Sign Up failed. Please try again.").setNegativeButton("Retry",null).create().show();
                    }
                }
                break;
            case R.id.txtvSignIn:
            case R.id.txtvLogin:
                startActivity(new Intent(getApplicationContext(), Login.class));
                break;
            case R.id.btnSelect:
                pickDate();
                break;
        }
    }
    private void pickDate(){
        final Calendar calendar=Calendar.getInstance();
        int day=calendar.get(Calendar.DATE);
        int month=calendar.get(Calendar.MONTH);
        int year=calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                edtBirthdate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },year,month,day);
        datePickerDialog.show();
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
