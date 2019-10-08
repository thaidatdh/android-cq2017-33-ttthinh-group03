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
    public ShopDao() {
    }

    public List<ShopDto> SelectAll() throws SQLException {
        List<ShopDto> shop = new ArrayList<>();
        String sql = "select * from Shop";
        ResultSet rs = Database.SelectQuery(sql);
        while (rs.next()){
            shop.add(new ShopDto(rs.getInt("shop_id"), rs.getString("name"), rs.getString("address"), rs.getString("open_time"), rs.getString("close_time")));
        }
        return shop;
    }

    public boolean Insert(ShopDto shop) throws SQLException {
        String sql = "insert into Shop(name, address, open_time, close_time) values('" + shop.getName() + "', '" + shop.getAddress() + "', '" + shop.getOpenTime() + "', '" + shop.getCloseTime() + "')";
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean Update(ShopDto shop) throws SQLException {
        String sql = "Update Shop set name = '" + shop.getName() + "', address = '" + shop.getAddress() + "', open_time = '" + shop.getOpenTime() +"', close_time = '"+ shop.getCloseTime() + "' where shop_id = " + shop.getShopId() + ")" ;
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }

    public boolean Delete(ShopDto shop) throws SQLException {
        String sql = "delete from Shop where id_shop = " + shop.getShopId();
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }


    public ShopDto findById(int Id) throws SQLException {
        ShopDto shop = new ShopDto();
        String sql = "select * from Shipper where id_shop = " + Id;
        ResultSet rs = Database.SelectQuery(sql);
        while (rs.next()){
            shop = new ShopDto(rs.getInt("shop_id"), rs.getString("name"), rs.getString("address"), rs.getString("open_time"), rs.getString("close_time"));
        }
        return shop;
    }
}
