package com.hcmus.DAO;

import com.hcmus.DTO.UserDto;
import com.hcmus.Utils.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public static List<UserDto> SelectAll() {
        List<UserDto> user = new ArrayList<>();
        String sql = "select * from Users";
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                user.add(new UserDto(rs.getInt("user_id"), rs.getString("type"), rs.getString("username"), rs.getString("password"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("birth_date"), rs.getString("address"), rs.getString("phone"), rs.getString("created_date")));
            }
        } catch (Exception ex) {}
        return user;
    }

    public static int Insert(UserDto user) {
        String sql = "INSERT INTO Users(username, password, firstname, lastname, birth_date, type, address, phone, created_date) VALUES('"
                + user.getUsername() + "', '" + user.getPassword() + "', '" + user.getFirstName() + "', '" + user.getLastName() + "', '" + user.getBirthDate() + "', '" + user.getUserType() + "', '" + user.getAddress() + "', '" + user.getPhone() + "', '" + user.getCreatedDate() + "')";
        sql = sql.replace("null","");
        int result = Database.ExecuteQuery(sql);
        if (result == -1)
            return result;
        return Database.GetLastestId("users","user_id");
    }

    public static boolean Update(UserDto user) {
        String sql = "UPDATE Users set username = '" + user.getUsername() + "',  password = '" + user.getPassword() + "',  firstname = '"
                + user.getFirstName() + "',  lastname = '" + user.getLastName() + "',  birth_date = '" + user.getBirthDate() + "',  type = '"
                + user.getUserType() + "',  address = '" + user.getAddress() + "',  phone = '" + user.getPhone() + "',  created_date = '" + user.getCreatedDate() + "' WHERE user_id = " + user.getUserId();
        sql = sql.replace("null","");
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }

    public static boolean Delete(UserDto user) {
        String sql = "delete from Users where user_id = " + user.getUserId();
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }


    public static UserDto findById(int Id) {
        UserDto user = null;
        String sql = "select * from users where user_id=" + Id;
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                user = new UserDto(rs.getInt("user_id"), rs.getString("type"), rs.getString("username"), rs.getString("password"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("birth_date"), rs.getString("address"), rs.getString("phone"), rs.getString("created_date"));
            }
        } catch (Exception ex) {}
        return user;
    }
    public static List<UserDto> GetAllCustomer() {
        List<UserDto> user = new ArrayList<>();
        String sql = "select * from users where type='CUSTOMER'";
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                user.add(new UserDto(rs.getInt("user_id"), rs.getString("type"), rs.getString("username"), rs.getString("password"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("birth_date"), rs.getString("address"), rs.getString("phone"), rs.getString("created_date")));
            }
        } catch (Exception ex) {}
        return user;
    }
    public static boolean CheckLogin(String username, String password) {
        String sql = "Select * from Users where username = '" + username +"' AND password = '" + password+"'";
        ResultSet rs = Database.SelectQuery(sql);
        try {
            if (rs.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
