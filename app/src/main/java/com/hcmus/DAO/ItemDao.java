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
    public ItemDao() {
    }

    public List<ItemDto> SelectAll() throws SQLException {
        List<ItemDto> item = new ArrayList<>();
        String sql = "select * from Item";
        ResultSet rs = Database.SelectQuery(sql);
        while (rs.next()){
            item.add(new ItemDto(rs.getInt("item_id"), rs.getString("name"), rs.getString("description"), rs.getString("thumnail"), rs.getInt("price"), rs.getInt("category"), rs.getString("status").charAt(0), rs.getString("created_date"), rs.getString("updated_date")));
        }
        return item;
    }

    public boolean Insert(ItemDto item) throws SQLException {
        String sql = "insert into Items(item_id , name , description , thumnail , price , category , status , created_date , updated_date) VALUES ("+item.getId() + ", '" + item.getName() + "', '" + item.getDescription() + "', '" + item.getThumbnail() + "', " + item.getPrice() + ", " + item.getCategory() + ", '" + item.getStatus() + "', '" + item.getCreatedDate() + "', '" + item.getUpdatedDate() +")";

        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean Update(ItemDto item) throws SQLException {
        String sql = "update items set name = '" +item.getName() + "', description = '" + item.getDescription() + "', thumnail = '" + item.getThumbnail() + "', price = " + item.getPrice()
                + ", category = " + item.getCategory() + ", status = '" + item.getStatus() + "', created_date = '" + item.getCreatedDate() + "', updated_date = '" + item.getUpdatedDate() + "' where item_id = " + item.getId(); ;
        if (Database.ExecuteQuery(sql) > 0) {

            return true;
        } else

        return false;
    }

    public boolean Delete(ItemDto item) throws SQLException {
        String sql = "delete from items where item_id = " + item.getId();
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }

    public ItemDto findById(int Id) throws SQLException {
        ItemDto item = new ItemDto();
        String sql = "select * from items where item_id = " + Id;
        ResultSet rs = Database.SelectQuery(sql);
        while (rs.next()){
            item = new ItemDto(rs.getInt("item_id"), rs.getString("name"), rs.getString("description"), rs.getString("thumnail"), rs.getInt("price"), rs.getInt("category"), rs.getString("status").charAt(0), rs.getString("created_date"), rs.getString("updated_date"));
        }
        return item;
    }
}
