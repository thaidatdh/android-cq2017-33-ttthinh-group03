package com.hcmus.shipe;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLocalStore {
    public static final String SP_NAME="userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context)
    {
        userLocalDatabase =context.getSharedPreferences(SP_NAME,0);

    }

    public void storeUserData (User user)
    {
        SharedPreferences.Editor spEditor=userLocalDatabase.edit();
        spEditor.putString("name",user.name);
        spEditor.putInt("age",user.age);
        spEditor.putString("username",user.username);
        spEditor.putString("password",user.password);
        spEditor.putString("usertype",user.type);
        spEditor.putString("phone",user.phone);
        spEditor.putString("address",user.address);

        spEditor.commit();


    }

    public User getLoggedInUser(){
        String name=userLocalDatabase.getString("name","");
        String username=userLocalDatabase.getString("username","");
        String password=userLocalDatabase.getString("password","");
        int age=userLocalDatabase.getInt("age",-1);
        String type=userLocalDatabase.getString("usertype","");
        String phone=userLocalDatabase.getString("phone","");
        String address=userLocalDatabase.getString("address","");
        User storedUser=new User(name,username,password,age,type,phone,address);

        return storedUser;

    }

    public void setUserLoggedIn(boolean loggedIn)
    {
        SharedPreferences.Editor spEditor=userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn",loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn()
    {
        if(userLocalDatabase.getBoolean("loggedIn",false)==true)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor=userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }


}
