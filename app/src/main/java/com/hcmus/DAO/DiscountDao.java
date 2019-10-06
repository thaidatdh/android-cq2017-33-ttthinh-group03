package com.hcmus.DAO;

import com.hcmus.DTO.DiscountDto;
import com.hcmus.Utils.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DiscountDao {
    Database db = new Database();
    Connection conn;

    public DiscountDao() {
        conn = db.getConnection();
    }

    public List<DiscountDto> SelectAll() throws SQLException {
        List<DiscountDto> billDetail = new ArrayList<>();
        Statement statement = conn.createStatement();
        String sql = "select * from Discount";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            billDetail.add(new DiscountDto(rs.getInt("discountId"), rs.getInt("itemId"), rs.getDouble("percentage"), rs.getString("description"), rs.getString("startDate"), rs.getString("endDate")));
        }
        conn.close();// Đóng kết nối
        return billDetail;
    }

    public boolean Insert(DiscountDto billDetail) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "insert into Discount(discountId, itemId, percentage, description, startDate, endDate) values(" + billDetail.getDiscountId() + ", " + billDetail.getItemId() + ", '" + billDetail.getDescription() + "', '" + billDetail.getStartDate() + "', '" + billDetail.getEndDate() + "')";
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else {
            conn.close();
            return false;
        }
    }

    public boolean Update(DiscountDto billDetail) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "Update Discount set itemId = "+billDetail.getItemId()+", percentage = " + billDetail.getPercentage()+", description = '" + billDetail.getDescription() +"', startDate = '"+ billDetail.getStartDate() +"', endDate = '"+ billDetail.getEndDate() + "' where discountId = " + billDetail.getDiscountId() + ")" ;
            if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else
            conn.close();
        return false;
    }

    public boolean Delete(DiscountDto billDetail) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "delete from Discount where discountId = " + billDetail.getDiscountId();
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else
            conn.close();
        return false;
    }


    public DiscountDto findById(int Id) throws SQLException {
        DiscountDto billDetail = new DiscountDto();
        Statement statement = conn.createStatement();
        String sql = "select * from Shipper where discountId = " + Id;
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            billDetail = new DiscountDto(rs.getInt("discountId"), rs.getInt("itemId"), rs.getDouble("percentage"), rs.getString("description"), rs.getString("startDate"), rs.getString("endDate"));
        }
        conn.close();
        return billDetail;
    }
}
