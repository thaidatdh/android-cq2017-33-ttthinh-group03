package com.hcmus.DAO;

import com.hcmus.DTO.PaymentDto;
import com.hcmus.Utils.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PaymentDao {
    Database db = new Database();
    Connection conn;

    public PaymentDao() {
        conn = db.getConnection();
    }

    public List<PaymentDto> SelectAll() throws SQLException {
        List<PaymentDto> payment = new ArrayList<>();
        Statement statement = conn.createStatement();
        String sql = "select * from Payment";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            payment.add(new PaymentDto());
        }
        conn.close();// Đóng kết nối
        return payment;
    }

    public boolean Insert(PaymentDto payment) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "";
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else {
            conn.close();
            return false;
        }
    }

    public boolean Update(PaymentDto payment) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "" ;
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else
            conn.close();
        return false;
    }

    public boolean Delete(PaymentDto payment) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "";
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else
            conn.close();
        return false;
    }


    public PaymentDto findById(int Id) throws SQLException {
        PaymentDto payment = new PaymentDto();
        Statement statement = conn.createStatement();
        String sql = "" + Id;
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            payment = new PaymentDto();
        }
        conn.close();
        return payment;
    }
}
