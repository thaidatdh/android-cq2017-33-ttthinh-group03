package com.hcmus.DAO;

import com.hcmus.DTO.ItemDto;
import com.hcmus.Utils.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemDao {
    Database db = new Database();
    Connection conn;

    public ItemDao() {
        conn = db.getConnection();
    }

    public List<ItemDto> SelectAll() throws SQLException {
        List<ItemDto> item = new ArrayList<>();
        Statement statement = conn.createStatement();
        String sql = "select * from Item";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            item.add(new ItemDto());
        }
        conn.close();// Đóng kết nối
        return item;
    }

    public boolean Insert(ItemDto item) throws SQLException {
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

    public boolean Update(ItemDto item) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "" ;
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else
            conn.close();
        return false;
    }

    public boolean Delete(ItemDto item) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "";
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else
            conn.close();
        return false;
    }


    public ItemDto findById(int Id) throws SQLException {
        ItemDto item = new ItemDto();
        Statement statement = conn.createStatement();
        String sql = "" + Id;
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            item = new ItemDto();
        }
        conn.close();
        return item;
    }
}
