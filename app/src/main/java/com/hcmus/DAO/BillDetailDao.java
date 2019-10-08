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

    public List<BillDetailDto> SelectAll() throws SQLException {
        List<BillDetailDto> billDetail = new ArrayList<>();
        String sql = "select * from BillDetail";
        ResultSet rs = Database.SelectQuery(sql);
        while (rs.next()){
            billDetail.add(new BillDetailDto(rs.getInt("billId"), rs.getInt("itemId"), rs.getInt("amount")));
        }
        return billDetail;
    }

    public boolean Insert(BillDetailDto billDetail) throws SQLException {
        String sql = "insert into BillDetail(billId, itemId, amount) values(" + billDetail.getBillId() + ", " + billDetail.getItemId() + ", " + billDetail.getAmount() + ")";
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean Update(BillDetailDto billDetail) throws SQLException {
        String sql = "Update BillDetail set itemId = " + billDetail.getItemId() +", amount = "+ billDetail.getAmount() + " where billId = " + billDetail.getBillId() + ")" ;
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }

    public boolean Delete(BillDetailDto billDetail) throws SQLException {
        String sql = "delete from BillDetail where ID = " + billDetail.getBillId();
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }


    public BillDetailDto findById(int Id) throws SQLException {
        BillDetailDto billDetail = new BillDetailDto();
        String sql = "select * from Shipper where ID = " + Id;
        ResultSet rs = Database.SelectQuery(sql);
        while (rs.next()){
            billDetail = new BillDetailDto(rs.getInt("billId"), rs.getInt("itemId"), rs.getInt("amount"));
        }
        return billDetail;
    }
}
