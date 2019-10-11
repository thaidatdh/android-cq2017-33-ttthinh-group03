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

    public PaymentDao() {
    }

    public static List<PaymentDto> SelectAll() throws SQLException {
        List<PaymentDto> payment = new ArrayList<>();
        String sql = "select * from Payment";
        ResultSet rs = Database.SelectQuery(sql);
        while (rs.next()){
            payment.add(new PaymentDto(rs.getInt("bill_id"), rs.getString("payment_date"), rs.getString("type").charAt(0), rs.getInt("amount"), rs.getString("description"), rs.getInt("user_id"), rs.getInt("receiver_id"), rs.getBoolean("is_complete")));
        }
        return payment;
    }

    public static boolean Insert(PaymentDto payment) throws SQLException {
        String sql = "insert into Payment(bill_id, payment_date, type, amount, description, user_id, receiver_id, is_complete) values(" + payment.getBillId() + ", '" + payment.getPaymentDate() + "', '" + payment.getType() + "', " + payment.getAmount() + ", '" + payment.getDescription() + "', "  + payment.getUserId() + ", '" + payment.getReceiverId() + "', " + payment.isCompleted() + ")";
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean Update(PaymentDto payment) throws SQLException {
        String sql = "UPDATE Payment set payment_date =  '" + payment.getPaymentDate() + "', type =  '" + payment.getType() + "', amount =  " + payment.getAmount() + ", description =  '" + payment.getDescription() +
        "', user_id =  "  + payment.getUserId() + ", receiver_id =  '" + payment.getReceiverId() + "', is_complete =  " + payment.isCompleted() ;
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }

    public static boolean Delete(PaymentDto payment) throws SQLException {
        String sql = "delete from Payment where bill_id = " + payment.getBillId();
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }

    public static PaymentDto findById(int Id) throws SQLException {
        PaymentDto payment = new PaymentDto();
        String sql = "Select * from Payment where bill_id" + Id;
        ResultSet rs = Database.SelectQuery(sql);
        while (rs.next()){
            payment = new PaymentDto(rs.getInt("bill_id"), rs.getString("payment_date"), rs.getString("type").charAt(0), rs.getInt("amount"), rs.getString("description"), rs.getInt("user_id"), rs.getInt("receiver_id"), rs.getBoolean("is_complete"));
        }
        return payment;
    }
}
