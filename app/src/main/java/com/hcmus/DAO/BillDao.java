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

    public BillDao() {
    }

    public List<BillDto> SelectAll() throws SQLException {
        List<BillDto> bill = new ArrayList<>();
        String sql = "select * from Bill";
        ResultSet rs = Database.SelectQuery(sql);
        while (rs.next()){
            bill.add(new BillDto(rs.getInt("bill_id"), rs.getInt("customer_id"), rs.getString("created_date"), rs.getString("description"), rs.getLong("total_price"), rs.getLong("ship_charge"), rs.getBoolean("accepted"), rs.getString("status").charAt(0), rs.getInt("shipper_id"), rs.getString("deliver_time"), rs.getBoolean("is_completed")));
        }
        return bill;
    }

    public boolean Insert(BillDto bill) throws SQLException {
        String sql = "insert into Bill(customer_id, created_date, description, total_price, ship_charge, accepted, status, shipper_id, deliver_time, is_completed) values(" + bill.getCustomerId() + ", '" + bill.getCreatedDate() + "', '" + bill.getDescription() + "', " + bill.getTotalPrice()+ ", " + bill.getShipCharge() + ", " + bill.isAccepted() + ", '" + bill.getStatus()+ "', " + bill.getShipperId() + ", '" + bill.getDeliverTime() + "', " + bill.isCompleted()+ ")";
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean Update(BillDto bill) throws SQLException {
        String sql = "Update Bill set bill_id = " + bill.getBillId() + ", customer_id = " + bill.getCustomerId() + ", created_date = '" + bill.getCreatedDate() + "', description = '" + bill.getDescription() + "', total_price = " + bill.getTotalPrice() + ", ship_charge = " + bill.getShipCharge() + ", accepted = " + bill.isAccepted() + ", status = '" + bill.getStatus()
                + "', shipper_id = " + bill.getShipperId() + ", deliver_time = '" + bill.getDeliverTime() + "', is_completed = " + bill.isCompleted() + " where bill_id = " + bill.getBillId()+ ")" ;
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }

    public boolean Delete(BillDto bill) throws SQLException {
        String sql = "delete from Bill where bill_id = " + bill.getBillId();
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }


    public BillDto findById(int Id) throws SQLException {
        BillDto bill = new BillDto();
        String sql = "select * from Bill where ID = " + Id;
        ResultSet rs = Database.SelectQuery(sql);
        while (rs.next()){
            bill = new BillDto(rs.getInt("bill_id"), rs.getInt("customer_id"), rs.getString("created_date"), rs.getString("description"), rs.getLong("total_price"), rs.getLong("ship_charge"), rs.getBoolean("accepted"), rs.getString("status").charAt(0), rs.getInt("shipper_id"), rs.getString("deliver_time"), rs.getBoolean("is_completed"));
        }
        return bill;
    }
}
