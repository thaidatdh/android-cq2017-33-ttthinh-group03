package com.hcmus.DAO;

import com.hcmus.DTO.ItemDto;
import com.hcmus.Utils.Database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ItemDao {
    public ItemDao() {
    }

    public static List<ItemDto> SelectAll() {
        List<ItemDto> item = new ArrayList<>();
        String sql = "select * from Item";
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                item.add(new ItemDto(rs.getInt("item_id"), rs.getString("name"), rs.getString("description"), rs.getString("thumnail"), rs.getInt("price"), rs.getInt("category"), rs.getString("status").charAt(0), rs.getString("created_date"), rs.getString("updated_date")));
            }
        } catch (Exception ex) {}
        return item;
    }

    public static int Insert(ItemDto item) {
        String sql = "insert into Items(name , description , thumnail , price , category , status , created_date , updated_date) VALUES ('" + item.getName() + "', '" + item.getDescription() + "', '" + item.getThumbnail() + "', " + item.getPrice() + ", " + item.getCategory() + ", '" + item.getStatus() + "', '" + item.getCreatedDate() + "', '" + item.getUpdatedDate() +")";
        int result = Database.ExecuteQuery(sql);
        if (result == -1)
            return result;
        return Database.GetLatestId("items","item_id");
    }

    public static boolean Update(ItemDto item) {
        String sql = "update items set name = '" +item.getName() + "', description = '" + item.getDescription() + "', thumnail = '" + item.getThumbnail() + "', price = " + item.getPrice()
                + ", category = " + item.getCategory() + ", status = '" + item.getStatus() + "', created_date = '" + item.getCreatedDate() + "', updated_date = '" + item.getUpdatedDate() + "' where item_id = " + item.getId(); ;
        if (Database.ExecuteQuery(sql) > 0) {

            return true;
        } else

        return false;
    }

    public static boolean UpdateItem(ItemDto item) {
        String sql = "update items set name = '" +item.getName() + "', description = '" + item.getDescription() + "', thumnail = '" + item.getThumbnail() + "', price = " + item.getPrice()
                + ", category = " + item.getCategory() + ", status = '" + item.getStatus() +  "', updated_date = '" + item.getUpdatedDate() + "' where item_id = " + item.getId(); ;
        if (Database.ExecuteQuery(sql) > 0) {

            return true;
        } else

            return false;
    }
    public static boolean Delete(ItemDto item) {
        String sql = "delete from items where item_id = " + item.getId();
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }

    public static ItemDto findById(int Id) {
        ItemDto item = new ItemDto();
        String sql = "select * from Items where item_id = " + Id;
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                item = new ItemDto(rs.getInt("item_id"), rs.getString("name"), rs.getString("description"), rs.getString("thumnail"), rs.getInt("price"), rs.getInt("category"), rs.getString("status").charAt(0), rs.getString("created_date"), rs.getString("updated_date"));
            }
        } catch (Exception ex) {}
        return item;
    }
    public static List<ItemDto> findByCategory(int category) {
        List<ItemDto> item = new ArrayList<>();
        String sql = "select * from Items where category = " + category;
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                item.add(new ItemDto(rs.getInt("item_id"), rs.getString("name"), rs.getString("description"), rs.getString("thumnail"), rs.getInt("price"), rs.getInt("category"), rs.getString("status").charAt(0), rs.getString("created_date"), rs.getString("updated_date")));
            }
        } catch (Exception ex) {}
        return item;
    }

}
