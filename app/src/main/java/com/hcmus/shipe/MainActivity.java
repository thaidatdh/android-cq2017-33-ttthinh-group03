package com.hcmus.shipe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnLogout;
    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnLogout=(Button)findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(this);
        userLocalStore=new UserLocalStore(this);
    }
    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btnLogout:
               /* userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);*/
                startActivity(new Intent(this, Login.class));

                break;


        }
    }

}
