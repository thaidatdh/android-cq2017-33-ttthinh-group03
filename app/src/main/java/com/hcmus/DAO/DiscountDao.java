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

    public DiscountDao() {
    }

    public List<DiscountDto> SelectAll() throws SQLException {
        List<DiscountDto> billDetail = new ArrayList<>();
        
        String sql = "select * from Discount";
        ResultSet rs = Database.SelectQuery(sql);
        while (rs.next()){
            billDetail.add(new DiscountDto(rs.getInt("id"), rs.getInt("item_id"), rs.getDouble("percentage"), rs.getString("description"), rs.getString("start_date"), rs.getString("end_date")));
        }
        
        return billDetail;
    }

    public boolean Insert(DiscountDto billDetail) throws SQLException {
        String sql = "insert into Discount(id, item_id, percentage, description, start_date, end_date) values(" + billDetail.getDiscountId() + ", " + billDetail.getItemId() + ", '" + billDetail.getDescription() + "', '" + billDetail.getStartDate() + "', '" + billDetail.getEndDate() + "')";
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean Update(DiscountDto billDetail) throws SQLException {
        String sql = "Update Discount set item_id = "+billDetail.getItemId()+", percentage = " + billDetail.getPercentage()+", description = '" + billDetail.getDescription() +"', start_date = '"+ billDetail.getStartDate() +"', end_date = '"+ billDetail.getEndDate() + "' where id = " + billDetail.getDiscountId() + ")" ;
            if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }

    public boolean Delete(DiscountDto billDetail) throws SQLException {
        String sql = "delete from Discount where id = " + billDetail.getDiscountId();
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }


    public DiscountDto findById(int Id) throws SQLException {
        DiscountDto billDetail = new DiscountDto();
        String sql = "select * from Shipper where id = " + Id;
        ResultSet rs = Database.SelectQuery(sql);
        while (rs.next()){
            billDetail = new DiscountDto(rs.getInt("id"), rs.getInt("item_id"), rs.getDouble("percentage"), rs.getString("description"), rs.getString("start_date"), rs.getString("end_date"));
        }
        return billDetail;
    }
}
