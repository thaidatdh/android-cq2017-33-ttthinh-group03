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

    public static List<ShipperDto> SelectAll() throws SQLException {
        List<ShipperDto> shipper = new ArrayList<>();
        String sql = "select * from Shipper";
        ResultSet rs = Database.SelectQuery(sql);
        while (rs.next()){
            shipper.add(new ShipperDto(rs.getInt("user_id"), rs.getString("plate_number"), rs.getString("vehicle"), rs.getString("vehicle_color"), rs.getString("active")));
        }
        return shipper;
    }

    public static boolean Insert(ShipperDto shipper) throws SQLException {
        String sql = "insert into Shipper(plate_number, vehicle, vehicle_color, active) values('" + shipper.getPlateNumber() + "', '" + shipper.getVehicle() + "', '" + shipper.getVehicleColor() + "', '" + shipper.getActive()+ "')";
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean Update(ShipperDto shipper) throws SQLException {
        String sql = "Update Shipper set plate_number = '" + shipper.getPlateNumber() + "', vehicle = '" + shipper.getVehicle() +"', vehicle_color = '"+ shipper.getVehicle() + "', active = " + shipper.getActive() + " where user_id = " + shipper.getUserId()+ ")" ;
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }

    public static boolean Delete(ShipperDto shipper) throws SQLException {
        String sql = "delete from Shipper where user_id = " + shipper.getUserId();
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }

    public static ShipperDto findById(int Id) throws SQLException {
        ShipperDto shipper = new ShipperDto();
        String sql = "select * from Shipper where user_id = " + Id;
        ResultSet rs = Database.SelectQuery(sql);
        while (rs.next()){
            shipper = new ShipperDto(rs.getInt("user_id"), rs.getString("plate_number"), rs.getString("vehicle"), rs.getString("vehicle_color"), rs.getString("active"));
        }
        return shipper;
    }

}
