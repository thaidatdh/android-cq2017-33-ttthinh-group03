package com.hcmus.DAO;

import com.hcmus.DTO.ShipperDto;
import com.hcmus.Utils.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ShipperDao  {


    public ShipperDao() {
    }

    public static List<ShipperDto> GetAll(){
        List<ShipperDto> shipper = new ArrayList<>();
        try {
            String sql = "select * from Shipper";
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                shipper.add(new ShipperDto(rs.getInt("user_id"), rs.getString("plate_number"), rs.getString("vehicle"), rs.getString("vehicle_color"), rs.getString("is_active")));
            }
        } catch (Exception ex) {}
        return shipper;
    }
    public static List<ShipperDto> GetUnConfirmed(){
        List<ShipperDto> shipper = new ArrayList<>();
        try {
            String sql = "select * from shipper where is_active='NULL'";
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                shipper.add(new ShipperDto(rs.getInt("user_id"), rs.getString("plate_number"), rs.getString("vehicle"), rs.getString("vehicle_color"), rs.getString("is_active")));
            }
        } catch (Exception ex) {}
        return shipper;
    }
    public static List<ShipperDto> GetActive(){
        List<ShipperDto> shipper = new ArrayList<>();
        try {
            String sql = "select * from shipper where is_active='TRUE'";
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                shipper.add(new ShipperDto(rs.getInt("user_id"), rs.getString("plate_number"), rs.getString("vehicle"), rs.getString("vehicle_color"), rs.getString("is_active")));
            }
        } catch (Exception ex) {}
        return shipper;
    }
    public static List<ShipperDto> GetInactive(){
        List<ShipperDto> shipper = new ArrayList<>();
        try {
            String sql = "select * from shipper where is_active='FALSE'";
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                shipper.add(new ShipperDto(rs.getInt("user_id"), rs.getString("plate_number"), rs.getString("vehicle"), rs.getString("vehicle_color"), rs.getString("is_active")));
            }
        } catch (Exception ex) {}
        return shipper;
    }
    public static boolean Insert(ShipperDto shipper){
        String sql = "insert into shipper(user_id,plate_number, vehicle, vehicle_color, is_active) values("+shipper.getUserId()+",'" + shipper.getPlateNumber() + "', '" + shipper.getVehicle() + "', '" + shipper.getVehicleColor() + "', '" + shipper.getActive()+ "')";
        sql = sql.replace("null","");
        try {
            if (Database.ExecuteQuery(sql) > 0) {
                return true;
            }
        } catch (Exception ex){}
        return false;
    }

    public static boolean Update(ShipperDto shipper){
        String sql = "Update shipper set plate_number = '" + shipper.getPlateNumber() + "', vehicle = '" + shipper.getVehicle() +"', vehicle_color = '"+ shipper.getVehicle() + "', is_active = '" + shipper.getActive() + "' where user_id = " + shipper.getUserId()+ ")" ;
        sql = sql.replace("null","");
        try {
            if (Database.ExecuteQuery(sql) > 0) {
                return true;
            }
        } catch (Exception ex) {}
        return false;
    }

    public static boolean Delete(ShipperDto shipper) throws SQLException {
        String sql = "delete from Shipper where user_id = " + shipper.getUserId();
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }

    public static ShipperDto findById(int Id){
        ShipperDto shipper =  null;
        String sql = "select * from shipper where user_id = " + Id;
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()) {
                shipper = new ShipperDto(rs.getInt("user_id"), rs.getString("plate_number"), rs.getString("vehicle"), rs.getString("vehicle_color"), rs.getString("active"));
            }
        } catch (Exception ex) {}
        return shipper;
    }
}
