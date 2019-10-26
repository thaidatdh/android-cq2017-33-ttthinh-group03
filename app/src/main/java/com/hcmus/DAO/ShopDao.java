package com.hcmus.DAO;

import com.hcmus.DTO.ShopDto;
import com.hcmus.Utils.Database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ShopDao {
    public ShopDao() {
    }

    public static List<ShopDto> SelectAll() {
        List<ShopDto> shop = new ArrayList<>();
        String sql = "select * from Shop";
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                shop.add(new ShopDto(rs.getInt("shop_id"), rs.getString("name"), rs.getString("address"), rs.getString("open_time"), rs.getString("close_time")));
            }
        } catch (Exception ex) {}
        return shop;
    }

    public static int Insert(ShopDto shop) {
        String sql = "insert into Shop(name, address, open_time, close_time) values('" + shop.getName() + "', '" + shop.getAddress() + "', '" + shop.getOpenTime() + "', '" + shop.getCloseTime() + "')";
        sql = sql.replace("null", "");
        int result = Database.ExecuteQuery(sql);
        if (result == -1)
            return result;
        return Database.GetLatestId("shop","shop_id");
    }

    public static boolean Update(ShopDto shop) {
        String sql = "Update Shop set name = '" + shop.getName() + "', address = '" + shop.getAddress() + "', open_time = '" + shop.getOpenTime() +"', close_time = '"+ shop.getCloseTime() + "' where shop_id = " + shop.getShopId() + ")" ;
        sql = sql.replace("null","");
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        }
        return false;
    }

    public static boolean Delete(ShopDto shop){
        String sql = "delete from Shop where id_shop = " + shop.getShopId();
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }


    public static ShopDto findById(int Id) {
        ShopDto shop = null;
        String sql = "select * from Shop where shop_id = " + Id;
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                shop = new ShopDto(rs.getInt("shop_id"), rs.getString("name"), rs.getString("address"), rs.getString("open_time"), rs.getString("close_time"));
            }
        }catch (Exception ex) {}
        return shop;
    }
}
