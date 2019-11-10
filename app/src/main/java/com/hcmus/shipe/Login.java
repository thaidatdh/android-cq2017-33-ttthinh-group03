package com.hcmus.shipe;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.Activities.ManagerActivity;
import com.hcmus.Activities.ui.ItemManagement.CustomerHome;
import com.hcmus.DAO.UserDao;
import com.hcmus.DTO.UserDto;
import com.hcmus.Utils.ConversionUtils;


public class Login extends AppCompatActivity  {
    TextView txtvRegister;
    TextView txtvSignUp;
    EditText edtUsername, edtPassword;
    Button btnSignIn;
    public static UserLocalStore userLocalStore;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtvSignUp = (TextView)findViewById(R.id.txtvSignUp);
        txtvRegister=(TextView)findViewById(R.id.txtvRegister);
        edtUsername=(EditText)findViewById(R.id.edtUsername);
        edtPassword=(EditText)findViewById(R.id.edtPassword);
        btnSignIn=(Button)findViewById(R.id.btnSignIn);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=edtUsername.getText().toString();
                String password= ConversionUtils.User.EncryptPassword(edtPassword.getText().toString());

                boolean test = UserDao.CheckLogin(username,password);

                if(test)
                {
                    Authorize(getApplicationContext(), username);
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
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
        txtvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

    }
    public static void Authorize(Context context, String username) {
        userLocalStore=new UserLocalStore(context);
        UserDto currentUser = UserDao.findByUsername(username);
        userLocalStore.storeUserData(currentUser);
        userLocalStore.setUserLoggedIn(true);

        /*android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(Login.this);
        builder.setMessage("Login success!").create().show();*/
        Intent intent = null;
        switch (currentUser.getUserType().toUpperCase()) {
            case "ADMIN":
                intent =new Intent(context, ManagerActivity.class);
                break;
            case "CUSTOMER":
                intent =new Intent(context, CustomerHome.class);
                break;
            case "SHIPPER":
                intent=new Intent(context, ShipperActivity.class);
                break;
        }
        if (intent!= null){
            try {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch(Exception e){
                Log.e("Start Intent", "Login");
                e.printStackTrace();
            }
        }

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
    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
    }
    public static void Logout() {
        Login.userLocalStore.clearUserData();
    }
}
