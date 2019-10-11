package com.hcmus.DAO;

import com.hcmus.DTO.CategoryDto;
import com.hcmus.Utils.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    public CategoryDao() {
    }

    public static List<CategoryDto> SelectAll() throws SQLException {
        List<CategoryDto> billDetail = new ArrayList<>();
        String sql = "select * from Category";
        ResultSet rs = Database.SelectQuery(sql);
        while (rs.next()){
            billDetail.add(new CategoryDto(rs.getInt("categoryId"), rs.getString("name"), rs.getString("description")));
        }
        return billDetail;
    }

    public static boolean Insert(CategoryDto billDetail) throws SQLException {
        String sql = "insert into Category(categoryId, name, description) values(" + billDetail.getCategoryId() + ", '" + billDetail.getName() + "', '" + billDetail.getDescription() + "')";
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean Update(CategoryDto billDetail) throws SQLException {
        String sql = "Update Category set name = '" + billDetail.getName() +"', description = '"+ billDetail.getDescription() + "' where billId = " + billDetail.getCategoryId() + ")" ;
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }

    public static boolean Delete(CategoryDto billDetail) throws SQLException {
        String sql = "delete from Category where ID = " + billDetail.getCategoryId();
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }


    public static CategoryDto findById(int Id) throws SQLException {
        CategoryDto billDetail = new CategoryDto();
        String sql = "select * from Shipper where ID = " + Id;
        ResultSet rs = Database.SelectQuery(sql);
        while (rs.next()){
            billDetail = new CategoryDto(rs.getInt("categoryId"), rs.getString("name"), rs.getString("description"));
        }
        return billDetail;
    }
}
