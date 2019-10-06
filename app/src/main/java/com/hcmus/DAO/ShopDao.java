package com.hcmus.DAO;

import com.hcmus.DTO.ShopDto;
import com.hcmus.Utils.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ShopDao {
    Database db = new Database();
    Connection conn;

    public ShopDao() {
        conn = db.getConnection();
    }

    public List<ShopDto> SelectAll() throws SQLException {
        List<ShopDto> shop = new ArrayList<>();
        Statement statement = conn.createStatement();
        String sql = "select * from Shop";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            shop.add(new ShopDto(rs.getInt("shopId"), rs.getString("address"), rs.getString("openTime"), rs.getString("closeTime")));
        }
        conn.close();// Đóng kết nối
        return shop;
    }

    public boolean Insert(ShopDto shop) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "insert into Shop(address, openTime, closeTime) values('" + shop.getAddress() + "', '" + shop.getOpenTime() + "', '" + shop.getCloseTime() + "')";
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else {
            conn.close();
            return false;
        }
    }

    public boolean Update(ShopDto shop) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "Update Shop set address = '" + shop.getAddress() + "', openTime = '" + shop.getOpenTime() +"', closeTime = '"+ shop.getCloseTime() + "' where shopId = " + shop.getShopId() + ")" ;
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else
            conn.close();
        return false;
    }

    public boolean Delete(ShopDto shop) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "delete from Shop where ID = " + shop.getShopId();
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else
            conn.close();
        return false;
    }


    public ShopDto findById(int Id) throws SQLException {
        ShopDto shop = new ShopDto();
        Statement statement = conn.createStatement();
        String sql = "select * from Shipper where ID = " + Id;
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            shop = new ShopDto(rs.getInt("shopId"), rs.getString("address"), rs.getString("openTime"), rs.getString("closeTime"));
        }
        conn.close();
        return shop;
    }
}
