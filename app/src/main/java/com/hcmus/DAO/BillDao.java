package com.hcmus.DAO;

import com.hcmus.DTO.BillDto;
import com.hcmus.Utils.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BillDao {
    Database db = new Database();
    Connection conn;

    public BillDao() {
        conn = db.getConnection();
    }

    public List<BillDto> SelectAll() throws SQLException {
        List<BillDto> bill = new ArrayList<>();
        Statement statement = conn.createStatement();
        String sql = "select * from Bill";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            bill.add(new BillDto(rs.getInt("billId"), rs.getInt("customerId"), rs.getString("createdDate"), rs.getString("description"), rs.getLong("totalPrice"), rs.getLong("shipCharge"), rs.getBoolean("accepted"), rs.getString("status").charAt(0), rs.getInt("shipperId"), rs.getString("deliverTime"), rs.getBoolean("completed")));
        }
        conn.close();// Đóng kết nối
        return bill;
    }

    public boolean Insert(BillDto bill) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "insert into Bill(customerId, createdDate, description, totalPrice, shipCharge, accepted, status, shipperId, deliverTime, completed) values(" + bill.getCustomerId() + ", '" + bill.getCreatedDate() + "', '" + bill.getDescription() + "', " + bill.getTotalPrice()+ ", " + bill.getShipCharge() + ", " + bill.isAccepted() + ", '" + bill.getStatus()+ "', " + bill.getShipperId() + ", '" + bill.getDeliverTime() + "', " + bill.isCompleted()+ ")";
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else {
            conn.close();
            return false;
        }
    }

    public boolean Update(BillDto bill) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "Update Bill set billId = " + bill.getBillId() + ", customerId = " + bill.getCustomerId() + ", createdDate = '" + bill.getCreatedDate() + "', description = '" + bill.getDescription() + "', totalPrice = " + bill.getTotalPrice() + ", shipCharge = " + bill.getShipCharge() + ", accepted = " + bill.isAccepted() + ", status = '" + bill.getStatus()
                + "', shipperId = " + bill.getShipperId() + ", deliverTime = '" + bill.getDeliverTime() + "', completed = " + bill.isCompleted() + " where billId = " + bill.getBillId()+ ")" ;
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else
            conn.close();
        return false;
    }

    public boolean Delete(BillDto bill) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "delete from Bill where billId = " + bill.getBillId();
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else
            conn.close();
        return false;
    }


    public BillDto findById(int Id) throws SQLException {
        BillDto bill = new BillDto();
        Statement statement = conn.createStatement();
        String sql = "select * from Bill where ID = " + Id;
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            bill = new BillDto(rs.getInt("billId"), rs.getInt("customerId"), rs.getString("createdDate"), rs.getString("description"), rs.getLong("totalPrice"), rs.getLong("shipCharge"), rs.getBoolean("accepted"), rs.getString("status").charAt(0), rs.getInt("shipperId"), rs.getString("deliverTime"), rs.getBoolean("completed"));
        }
        conn.close();
        return bill;
    }
}
