package com.hcmus.DAO;

import com.hcmus.DTO.UserDto;
import com.hcmus.Utils.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public UserDao() {
    }

    public static List<UserDto> SelectAll() throws SQLException {
        List<UserDto> user = new ArrayList<>();
        String sql = "select * from Users";
        ResultSet rs = Database.SelectQuery(sql);
        while (rs.next()){
            user.add(new UserDto(rs.getInt("user_id"), rs.getString("user_type").charAt(0), rs.getString("username"), rs.getString("password"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("birth_date"), rs.getString("address"), rs.getString("phone"), rs.getString("created_date")));
        }
        return user;
    }

    public static boolean Insert(UserDto user) throws SQLException {
        String sql = "INSERT INTO Users(username, password, firstname, lastname, birth_date, user_type, address, phone, created_date) VALUES('"
                + user.getUsername() + "', '" + user.getPassword() + "', '" + user.getFirstName() + "', '" + user.getLastName() + "', '" + user.getBirthDate() + "', '" + user.getUserType() + "', '" + user.getAddress() + "', '" + user.getPhone() + "', '" + user.getCreatedDate() + "')";
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean Update(UserDto user) throws SQLException {
        String sql = "UPDATE Users set username = '" + user.getUsername() + "',  password = '" + user.getPassword() + "',  firstname = '"
                + user.getFirstName() + "',  lastname = '" + user.getLastName() + "',  birth_date = '" + user.getBirthDate() + "',  user_type = '"
                + user.getUserType() + "',  address = '" + user.getAddress() + "',  phone = '" + user.getPhone() + "',  created_date = '" + user.getCreatedDate() + "' WHERE user_id = " + user.getUserId();
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }

    public static boolean Delete(UserDto user) throws SQLException {
        String sql = "delete from Users where user_id = " + user.getUserId();
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }


    public static UserDto findById(int Id) throws SQLException {
        UserDto user = new UserDto();
        String sql = "Select * from Users WHERE use_id = " + Id;
        ResultSet rs = Database.SelectQuery(sql);
        while (rs.next()){
            user = new UserDto(rs.getInt("user_id"), rs.getString("user_type").charAt(0), rs.getString("username"), rs.getString("password"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("birth_date"), rs.getString("address"), rs.getString("phone"), rs.getString("created_date"));
        }
        return user;
    }
}
