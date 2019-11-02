package com.hcmus.shipe;

import android.content.Context;
import android.content.SharedPreferences;

import com.hcmus.DTO.UserDto;

public class UserLocalStore {
    public static final String SP_NAME="userDetails";
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
        spEditor.putString("password",user.getPassword());
        spEditor.putString("first_name",user.getFirstName());
        spEditor.putString("last_name",user.getLastName());
        spEditor.putString("birth_date",user.getBirthDate());
        spEditor.putString("address",user.getAddress());
        spEditor.putString("phone",user.getPhone());
        spEditor.putString("created_date",user.getCreatedDate());

        spEditor.commit();


    }

    public UserDto getLoggedInUser(){
        int id=userLocalDatabase.getInt("id",-1);
        String type=userLocalDatabase.getString("user_type","");
        String username=userLocalDatabase.getString("username","");
        String password=userLocalDatabase.getString("password","");
        String fname=userLocalDatabase.getString("first_name","");
        String lname=userLocalDatabase.getString("last_name","");
        String birth_date=userLocalDatabase.getString("birth_date","");
        String address=userLocalDatabase.getString("address","");
        String phone=userLocalDatabase.getString("phone","");
        String created_date=userLocalDatabase.getString("created_date","");


        UserDto storedUser=new UserDto();
        storedUser.setUsername(username);
        storedUser.setPassword(password);
        storedUser.setUserId(id);
        storedUser.setAddress(address);
        storedUser.setBirthDate(birth_date);
        storedUser.setUserType(type);
        storedUser.setFirstName(fname);
        storedUser.setLastName(lname);
        storedUser.setPhone(phone);
        storedUser.setCreatedDate(created_date);

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
