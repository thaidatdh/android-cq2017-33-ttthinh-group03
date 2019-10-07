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

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText edtUsername,edtPassword;
    Button btnLogin;
    TextView txtvRegisterLink;
    UserLocalStore userLocalStore;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername=(EditText)findViewById(R.id.edtUsername);
        edtPassword=(EditText)findViewById(R.id.edtPassword);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        txtvRegisterLink=(TextView)findViewById(R.id.txtvRegisterLink);


        btnLogin.setOnClickListener(this);
        txtvRegisterLink.setOnClickListener(this);
        userLocalStore=new UserLocalStore(this);


    }
    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btnLogin:
                String username= edtUsername.getText().toString();
                String password=edtPassword.getText().toString();
                //encrypt password
               /* String encodePw=com.hcmus.Utils.ConversionUtils.User.EncryptPassword(password);
                User user=new User(username,encodePw);
                userLocalStore.storeUserData(user);
                userLocalStore.setUserLoggedIn(true);*/

                Intent intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
                break;
            case R.id.txtvRegisterLink:
                startActivity(new Intent(this, Register.class));
                break;

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
}
