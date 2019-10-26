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

    public static List<PaymentDto> SelectAll() {
        List<PaymentDto> payment = new ArrayList<>();
        String sql = "select * from Payment";
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                payment.add(new PaymentDto(rs.getInt("payment_id"), rs.getInt("bill_id"), rs.getString("payment_date"), rs.getString("type").charAt(0), rs.getInt("amount"), rs.getString("description"), rs.getInt("user_id"), rs.getInt("receiver_id"), rs.getBoolean("is_complete")));
            }
        } catch (Exception ex) {}
        return payment;
    }

    public static int Insert(PaymentDto payment) {
        String sql = "insert into Payment(bill_id, payment_date, type, amount, description, user_id, receiver_id, is_complete) values(" + payment.getBillId() + ", '" + payment.getPaymentDate() + "', '" + payment.getType() + "', " + payment.getAmount() + ", '" + payment.getDescription() + "', "  + payment.getUserId() + ", '" + payment.getReceiverId() + "', " + payment.isCompleted() + ")";
        int result = Database.ExecuteQuery(sql);
        if (result == -1)
            return result;
        return Database.GetLatestId("payment","payment_id");
    }

    public static boolean Update(PaymentDto payment) {
        String sql = "UPDATE Payment set bill_id = " + payment.getBillId() + ", payment_date =  '" + payment.getPaymentDate() + "', type =  '" + payment.getType() + "', amount =  " + payment.getAmount() + ", description =  '" + payment.getDescription() +
        "', user_id =  "  + payment.getUserId() + ", receiver_id =  '" + payment.getReceiverId() + "', is_complete =  " + payment.isCompleted() +" where payment_id = " + payment.getPaymentId() ;
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }

    public static boolean Delete(PaymentDto payment) {
        String sql = "delete from Payment where payment_id = " + payment.getPaymentId();
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }

    public static PaymentDto findById(int Id) {
        PaymentDto payment = new PaymentDto();
        String sql = "Select * from Payment where payment_id" + Id;
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                payment = new PaymentDto(rs.getInt("payment_id"), rs.getInt("bill_id"), rs.getString("payment_date"), rs.getString("type").charAt(0), rs.getInt("amount"), rs.getString("description"), rs.getInt("user_id"), rs.getInt("receiver_id"), rs.getBoolean("is_complete"));
            }
        } catch (Exception ex) {}
        return payment;
    }
}
