package com.hcmus.DAO;

import com.hcmus.DTO.BillDetailDto;
import com.hcmus.Utils.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BillDetailDao {
    public BillDetailDao() {
    }

    public static List<BillDetailDto> SelectAll() {
        List<BillDetailDto> billDetail = new ArrayList<>();
        String sql = "select * from bill_detail";
        ResultSet rs = Database.SelectQuery(sql);
        try {
            while (rs.next()){
                billDetail.add(new BillDetailDto(rs.getInt("bill_id"), rs.getInt("item"), rs.getInt("amount")));
            }
        } catch (Exception ex) {}
        return billDetail;
    }

    public static boolean Insert(BillDetailDto billDetail) {
        String sql = "insert into bill_detail(bill_id, item, amount) values(" + billDetail.getBillId() + ", " + billDetail.getItemId() + ", " + billDetail.getAmount() + ")";
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean Update(BillDetailDto billDetail) {
        String sql = "Update bill_detail set item = " + billDetail.getItemId() +", amount = "+ billDetail.getAmount() + " where bill_id = " + billDetail.getBillId() + ")" ;
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }

    public static boolean Delete(BillDetailDto billDetail) {
        String sql = "delete from bill_detail where bill_id = " + billDetail.getBillId();
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }


    public static List<BillDetailDto> findById(int Id) {
        List<BillDetailDto> billDetail = new ArrayList<>();
        String sql = "select * from bill_detail where bill_id = " + Id;
        ResultSet rs = Database.SelectQuery(sql);
        try {
            while (rs.next()){
                billDetail.add(new BillDetailDto(rs.getInt("bill_id"), rs.getInt("item"), rs.getInt("amount")));
            }
        } catch (Exception ex) {}
        return billDetail;
    }
}
