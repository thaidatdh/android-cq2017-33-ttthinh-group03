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
    Database db = new Database();
    Connection conn;

    public CategoryDao() {
        conn = db.getConnection();
    }

    public List<CategoryDto> SelectAll() throws SQLException {
        List<CategoryDto> billDetail = new ArrayList<>();
        Statement statement = conn.createStatement();
        String sql = "select * from Category";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            billDetail.add(new CategoryDto(rs.getInt("categoryId"), rs.getString("name"), rs.getString("description")));
        }
        conn.close();// Đóng kết nối
        return billDetail;
    }

    public boolean Insert(CategoryDto billDetail) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "insert into Category(categoryId, name, description) values(" + billDetail.getCategoryId() + ", '" + billDetail.getName() + "', '" + billDetail.getDescription() + "')";
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else {
            conn.close();
            return false;
        }
    }

    public boolean Update(CategoryDto billDetail) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "Update Category set name = '" + billDetail.getName() +"', description = '"+ billDetail.getDescription() + "' where billId = " + billDetail.getCategoryId() + ")" ;
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else
            conn.close();
        return false;
    }

    public boolean Delete(CategoryDto billDetail) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "delete from Category where ID = " + billDetail.getCategoryId();
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else
            conn.close();
        return false;
    }


    public CategoryDto findById(int Id) throws SQLException {
        CategoryDto billDetail = new CategoryDto();
        Statement statement = conn.createStatement();
        String sql = "select * from Shipper where ID = " + Id;
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            billDetail = new CategoryDto(rs.getInt("categoryId"), rs.getString("name"), rs.getString("description"));
        }
        conn.close();
        return billDetail;
    }
}
