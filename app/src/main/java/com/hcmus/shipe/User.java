package com.hcmus.shipe;

public class User {
    String name,username,password,type,phone,address;
    int age;
    public User(String name,String username,String password,int age, String type,String phone,String address){
        this.name=name;
        this.username=username;
        this.password=password;
        this.age=age;
        this.type=type;
        this.phone=phone;
        this.address=address;
    }
    public User(String username,String password)
    {
        this.username=username;
        this.password=password;
        this.age=-1;
        this.name="";

    }
}
