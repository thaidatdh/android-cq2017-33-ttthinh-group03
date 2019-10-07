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
    Database db = new Database();
    Connection conn;

    public BillDetailDao() {
        conn = db.getConnection();
    }

    public List<BillDetailDto> SelectAll() throws SQLException {
        List<BillDetailDto> billDetail = new ArrayList<>();
        Statement statement = conn.createStatement();
        String sql = "select * from BillDetail";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            billDetail.add(new BillDetailDto(rs.getInt("billId"), rs.getInt("itemId"), rs.getInt("amount")));
        }
        conn.close();// Đóng kết nối
        return billDetail;
    }

    public boolean Insert(BillDetailDto billDetail) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "insert into BillDetail(billId, itemId, amount) values(" + billDetail.getBillId() + ", " + billDetail.getItemId() + ", " + billDetail.getAmount() + ")";
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else {
            conn.close();
            return false;
        }
    }

    public boolean Update(BillDetailDto billDetail) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "Update BillDetail set itemId = " + billDetail.getItemId() +", amount = "+ billDetail.getAmount() + " where billId = " + billDetail.getBillId() + ")" ;
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else
            conn.close();
        return false;
    }

    public boolean Delete(BillDetailDto billDetail) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "delete from BillDetail where ID = " + billDetail.getBillId();
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else
            conn.close();
        return false;
    }


    public BillDetailDto findById(int Id) throws SQLException {
        BillDetailDto billDetail = new BillDetailDto();
        Statement statement = conn.createStatement();
        String sql = "select * from Shipper where ID = " + Id;
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            billDetail = new BillDetailDto(rs.getInt("billId"), rs.getInt("itemId"), rs.getInt("amount"));
        }
        conn.close();
        return billDetail;
    }
}
