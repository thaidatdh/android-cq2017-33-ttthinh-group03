package com.hcmus.shipe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.DAO.UserDao;
import com.hcmus.DTO.UserDto;


public class Login extends AppCompatActivity  {
    TextView txtvRegister;

    EditText edtUsername,edtPassword;
    Button btnSignIn;
    UserLocalStore userLocalStore;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtvRegister=(TextView)findViewById(R.id.txtvRegister);
        edtUsername=(EditText)findViewById(R.id.edtUsername);
        edtPassword=(EditText)findViewById(R.id.edtPassword);
        btnSignIn=(Button)findViewById(R.id.btnSignIn);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=edtUsername.getText().toString();
                String password=edtPassword.getText().toString();

                boolean test=false;

                test= UserDao.CheckLogin(username,password);

                if(test==true)
                {
                    userLocalStore=new UserLocalStore(Login.this);
                    UserDto user=new UserDto();
                    user.setUsername(username);
                    user.setPassword(password);

                    userLocalStore.storeUserData(user);
                    userLocalStore.setUserLoggedIn(true);


                    android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(Login.this);
                    builder.setMessage("Login success!").create().show();
                    Intent intent=new Intent(Login.this,MainActivity.class);

                    Login.this.startActivity(intent);

                }
                else
                {
                    android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(Login.this);
                    builder.setMessage("Login failed!").setNegativeButton("Retry",null).create().show();

                }
            }
        });
        txtvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));

            }
        });


    }

    private void ClickLogin()
    {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);

        alertDialog.setMessage("Logged in successfully");
        alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.show();
    }
}
