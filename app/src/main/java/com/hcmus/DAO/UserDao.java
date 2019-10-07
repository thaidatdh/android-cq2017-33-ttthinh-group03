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
    Database db = new Database();
    Connection conn;

    public UserDao() {
        conn = db.getConnection();
    }

    public List<UserDto> SelectAll() throws SQLException {
        List<UserDto> user = new ArrayList<>();
        Statement statement = conn.createStatement();
        String sql = "select * from User";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            user.add(new UserDto());
        }
        conn.close();// Đóng kết nối
        return user;
    }

    public boolean Insert(UserDto user) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "";
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else {
            conn.close();
            return false;
        }
    }

    public boolean Update(UserDto user) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "" ;
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else
            conn.close();
        return false;
    }

    public boolean Delete(UserDto user) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "";
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else
            conn.close();
        return false;
    }


    public UserDto findById(int Id) throws SQLException {
        UserDto user = new UserDto();
        Statement statement = conn.createStatement();
        String sql = "" + Id;
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            user = new UserDto();
        }
        conn.close();
        return user;
    }
}
