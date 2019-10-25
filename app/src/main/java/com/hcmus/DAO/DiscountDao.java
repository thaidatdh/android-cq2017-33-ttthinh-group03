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

    public static List<DiscountDto> SelectAll() {
        List<DiscountDto> billDetail = new ArrayList<>();
        String sql = "select * from Discount";
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                billDetail.add(new DiscountDto(rs.getInt("discount_id"), rs.getInt("item_id"), rs.getDouble("percentage"), rs.getString("description"), rs.getString("start_date"), rs.getString("end_date")));
            }
        } catch (Exception ex) {}
        return billDetail;
    }

    public static int Insert(DiscountDto billDetail) {
        String sql = "insert into Discount(item_id, percentage, description, start_date, end_date) values(" + billDetail.getItemId() + ", '" + billDetail.getDescription() + "', '" + billDetail.getStartDate() + "', '" + billDetail.getEndDate() + "')";
        int result = Database.ExecuteQuery(sql);
        if (result == -1)
            return result;
        return Database.GetLatestId("discount","discount_id");
    }

    public static boolean Update(DiscountDto billDetail) {
        String sql = "Update Discount set item_id = "+billDetail.getItemId()+", percentage = " + billDetail.getPercentage()+", description = '" + billDetail.getDescription() +"', start_date = '"+ billDetail.getStartDate() +"', end_date = '"+ billDetail.getEndDate() + "' where discount_id = " + billDetail.getDiscountId() + ")" ;
            if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }

    public static boolean Delete(DiscountDto billDetail) {
        String sql = "delete from Discount where discount_id = " + billDetail.getDiscountId();
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }


    public static DiscountDto findById(int Id) {
        DiscountDto billDetail = new DiscountDto();
        String sql = "select * from Discount where discount_id = " + Id;
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                billDetail = new DiscountDto(rs.getInt("discount_id"), rs.getInt("item_id"), rs.getDouble("percentage"), rs.getString("description"), rs.getString("start_date"), rs.getString("end_date"));
            }
        } catch (Exception ex) {}
        return billDetail;
    }
}
