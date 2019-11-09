package com.hcmus.shipe;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.Activities.ui.Category.CustomerCategory;
import com.hcmus.Activities.ui.ItemManagement.ItemManagement;
import com.hcmus.Activities.ui.ShoppingCartManagement.ShoppingCartManagement;
import com.hcmus.DAO.CategoryDao;
import com.hcmus.DTO.CategoryDto;
import com.hcmus.Utils.Database;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean network = isNetworkAvailable();
        boolean db = isDatabaseAvailable();
        if (!network || !db) {
            String errorMessage = "";
            if (!network) {
                errorMessage = "Internet not available. Please connect internet and start app again.";
            }
            if (!db) {
                errorMessage = "Cannot connect to database. Please contact database administrator.";
            }
            android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(getApplicationContext());
            builder.setMessage(errorMessage).setNegativeButton("Got it",null).create().show();
            finishAndRemoveTask();
        }
        else {
            Login.userLocalStore = new UserLocalStore(getApplicationContext());
            if (Login.userLocalStore.getUserLoggedIn()) {
                String username = Login.userLocalStore.GetUsername();
                Login.Authorize(getApplicationContext(), username);
            }
            else
            {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Login.userLocalStore = new UserLocalStore(getApplicationContext());
        if (Login.userLocalStore.getUserLoggedIn()) {
            String username = Login.userLocalStore.GetUsername();
            Login.Authorize(getApplicationContext(), username);
        }
        else
        {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        }
    }
    private boolean isNetworkAvailable() {
        try {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        catch (Exception ex) {
            return false;
        }
    }
    public boolean isDatabaseAvailable() {
        try {
            Connection connection = Database.getConnection();
            return (connection != null);
        }
        catch (Exception ex) {
            return false;
        }
    }
}