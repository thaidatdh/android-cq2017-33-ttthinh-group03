package com.hcmus.shipe;

import android.content.Context;
import android.content.SharedPreferences;

import com.hcmus.DTO.UserDto;

public class UserLocalStore {
    public static final String SP_NAME="CurrentUser";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context)
    {
        userLocalDatabase =context.getSharedPreferences(SP_NAME,0);

    }

    public void storeUserData (UserDto user)
    {
        SharedPreferences.Editor spEditor=userLocalDatabase.edit();
        spEditor.putInt("user_id",user.getUserId());
        spEditor.putString("user_type",user.getUserType());
        spEditor.putString("username",user.getUsername());
        spEditor.apply();
    }
    public String GetUsername() {
        return userLocalDatabase.getString("username","");
    }
    public String GetUserType() {
        return userLocalDatabase.getString("user_type","");
    }
    public int GetUserId() {
        return userLocalDatabase.getInt("id",-1);
    }

    public void setUserLoggedIn(boolean loggedIn)
    {
        SharedPreferences.Editor spEditor=userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.apply();
    }

    public boolean getUserLoggedIn()
    {
        return userLocalDatabase.getBoolean("loggedIn",false);
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor=userLocalDatabase.edit();
        spEditor.clear();
        spEditor.apply();
    }
}
