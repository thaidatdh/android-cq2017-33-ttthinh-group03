package com.hcmus.DAO;

import com.hcmus.DTO.BillDto;
import com.hcmus.Models.Task;
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

    public static List<BillDto> SelectAll(){
        List<BillDto> bill = new ArrayList<>();
        String sql = "select * from Bill";
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                bill.add(new BillDto(rs.getInt("bill_id"), rs.getInt("customer"), rs.getString("created_date"), rs.getString("description"), rs.getLong("total_price"), rs.getLong("ship_charge"), rs.getBoolean("accepted"), rs.getString("status").charAt(0), rs.getInt("shipper"), rs.getString("deliver_time"), rs.getBoolean("is_completed")));
            }
        } catch (Exception ex) {}
        return bill;
    }

    public static int Insert(BillDto bill){
        String accepted = ("" + bill.isAccepted()).toUpperCase().trim();
        String completed = ("" + bill.isCompleted()).toUpperCase().trim();
        String sql = "insert into Bill(customer, created_date, description, total_price, ship_charge, accepted, status, shipper, deliver_time, is_completed) values(" + bill.getCustomerId() + ", '" + bill.getCreatedDate() + "', '" + bill.getDescription() + "', " + bill.getTotalPrice()+ ", " + bill.getShipCharge() + ", '" + accepted + "', '" + bill.getStatus()+ "', " + bill.getShipperId() + ", '" + bill.getDeliverTime() + "', '" + completed+ "')";
        sql = sql.replace("null","");
        int result = Database.ExecuteQuery(sql);
        if (result == -1)
            return result;
        return Database.GetLatestId("bill","bill_id");
    }

    public static boolean Update(BillDto bill){
        String accepted = ("" + bill.isAccepted()).toUpperCase().trim();
        String completed = ("" + bill.isCompleted()).toUpperCase().trim();
        String sql = "Update Bill set customer = " + bill.getCustomerId() + ", created_date = '" + bill.getCreatedDate() + "', description = '" + bill.getDescription() + "', total_price = " + bill.getTotalPrice() + ", ship_charge = " + bill.getShipCharge() + ", accepted = '" + accepted + "', status = '" + bill.getStatus()
                + "', shipper = " + bill.getShipperId() + ", deliver_time = '" + bill.getDeliverTime() + "', is_completed = '" + completed + "' where bill_id = " + bill.getBillId()+ "" ;
        sql = sql.replace("null","");
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }

    public static boolean Delete(BillDto bill){
        String sql = "delete from Bill where bill_id = " + bill.getBillId();
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }


    public static BillDto findById(int Id){
        BillDto bill = new BillDto();
        String sql = "select * from Bill where bill_id = " + Id;
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                bill = new BillDto(rs.getInt("bill_id"), rs.getInt("customer"), rs.getString("created_date"), rs.getString("description"), rs.getLong("total_price"), rs.getLong("ship_charge"), rs.getBoolean("accepted"), rs.getString("status").charAt(0), rs.getInt("shipper"), rs.getString("deliver_time"), rs.getBoolean("is_completed"));
            }
        } catch (Exception ex) {}
        return bill;
    }

    public static List<BillDto> GetNew(){
        List<BillDto> bill = new ArrayList<>();
        String sql = "select * from Bill where status ='N'";
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                bill.add(new BillDto(rs.getInt("bill_id"), rs.getInt("customer"), rs.getString("created_date"), rs.getString("description"), rs.getLong("total_price"), rs.getLong("ship_charge"), rs.getBoolean("accepted"), rs.getString("status").charAt(0), rs.getInt("shipper"), rs.getString("deliver_time"), rs.getBoolean("is_completed")));
            }
        } catch (Exception ex) {}
        return bill;
    }

    public static List<BillDto> GetGetting(){
        List<BillDto> bill = new ArrayList<>();
        String sql = "select * from Bill where status ='G'";
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                bill.add(new BillDto(rs.getInt("bill_id"), rs.getInt("customer"), rs.getString("created_date"), rs.getString("description"), rs.getLong("total_price"), rs.getLong("ship_charge"), rs.getBoolean("accepted"), rs.getString("status").charAt(0), rs.getInt("shipper"), rs.getString("deliver_time"), rs.getBoolean("is_completed")));
            }
        } catch (Exception ex) {}
        return bill;
    }

    public static List<BillDto> GetOnGoing(){
        List<BillDto> bill = new ArrayList<>();
        String sql = "select * from Bill where status ='O'";
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                bill.add(new BillDto(rs.getInt("bill_id"), rs.getInt("customer"), rs.getString("created_date"), rs.getString("description"), rs.getLong("total_price"), rs.getLong("ship_charge"), rs.getBoolean("accepted"), rs.getString("status").charAt(0), rs.getInt("shipper"), rs.getString("deliver_time"), rs.getBoolean("is_completed")));
            }
        } catch (Exception ex) {}
        return bill;
    }

    public static List<BillDto> GetCompleted(){
        List<BillDto> bill = new ArrayList<>();
        String sql = "select * from Bill where status ='C'";
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                bill.add(new BillDto(rs.getInt("bill_id"), rs.getInt("customer"), rs.getString("created_date"), rs.getString("description"), rs.getLong("total_price"), rs.getLong("ship_charge"), rs.getBoolean("accepted"), rs.getString("status").charAt(0), rs.getInt("shipper"), rs.getString("deliver_time"), rs.getBoolean("is_completed")));
            }
        } catch (Exception ex) {}
        return bill;
    }
    public static List<Task> GetAllAvailableTask(){
        List<Task> bill = new ArrayList<>();
        String sql = "select * " +
                "from users us inner join bill " +
                "on us.user_id = bill.customer " +
                "where us.type = 'CUSTOMER' AND bill.shipper is NULL";
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                bill.add(new Task(rs.getInt("bill_id"), rs.getInt("customer"), rs.getString("created_date"), rs.getString("description"), rs.getLong("total_price"), rs.getLong("ship_charge"), rs.getBoolean("accepted"), rs.getString("status").charAt(0), rs.getInt("shipper"), rs.getString("deliver_time"), rs.getBoolean("is_completed"), rs.getString("type"), rs.getString("username"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("birth_date"), rs.getString("address"), rs.getString("phone")));
            }
        } catch (Exception ex) {}
        return bill;
    }
    public static List<Task> GetTaskOfShipper(int shipper){
        List<Task> bill = new ArrayList<>();
        String sql = "select * " +
                "from users us inner join bill " +
                "on us.user_id = bill.customer " +
                "where us.type = 'CUSTOMER' AND bill.shipper = " + shipper;
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                bill.add(new Task(rs.getInt("bill_id"), rs.getInt("customer"), rs.getString("created_date"), rs.getString("description"), rs.getLong("total_price"), rs.getLong("ship_charge"), rs.getBoolean("accepted"), rs.getString("status").charAt(0), rs.getInt("shipper"), rs.getString("deliver_time"), rs.getBoolean("is_completed"), rs.getString("type"), rs.getString("username"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("birth_date"), rs.getString("address"), rs.getString("phone")));
            }
        } catch (Exception ex) {}
        return bill;
    }
    public static void AssignShipper(int billId, int shipperID){
        String sql = "UPDATE bill " +
                "SET shipper = " + shipperID + " " +
                "WHERE " + shipperID + " IN (SELECT user_id FROM users ) AND bill_id = " + billId + " AND shipper is NULL";
        try {
            ResultSet rs = Database.SelectQuery(sql);
        } catch (Exception ex) {}
    }
    public static void CancelBill(int billId){
        String sql = "UPDATE bill " +
                "SET shipper = NULL " +
                "where bill_id = " + billId + " AND is_completed LIKE 'False' COLLATE SQL_Latin1_General_CP1_CI_AS";
        try {
            ResultSet rs = Database.SelectQuery(sql);
        } catch (Exception ex) {}
    }
    public static List<BillDto> FindByCustomer(int customer_id){
        List<BillDto> bill = new ArrayList<>();
        String sql = "select * from Bill where customer=" + customer_id + " order by created_date desc;";
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                bill.add(new BillDto(rs.getInt("bill_id"), rs.getInt("customer"), rs.getString("created_date"), rs.getString("description"), rs.getLong("total_price"), rs.getLong("ship_charge"), rs.getBoolean("accepted"), rs.getString("status").charAt(0), rs.getInt("shipper"), rs.getString("deliver_time"), rs.getBoolean("is_completed")));
            }
        } catch (Exception ex) {}
        return bill;
    }
}
