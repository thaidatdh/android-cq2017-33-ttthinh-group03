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
    Database db = new Database();
    Connection conn;

    public ShipperDao() {
        conn = db.getConnection();
    }

    public List<ShipperDto> SelectAll() throws SQLException {
        List<ShipperDto> shipper = new ArrayList<>();
        Statement statement = conn.createStatement();
        String sql = "select * from Shipper";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            shipper.add(new ShipperDto(rs.getInt("userId"), rs.getString("plateNumber"), rs.getString("vehicle"), rs.getString("vehicleColor"), rs.getString("active")));
        }
        conn.close();// Đóng kết nối
        return shipper;
    }

    public boolean Insert(ShipperDto shipper) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "insert into Shipper(plateNumber, vehicle, vehicleColor, active) values('" + shipper.getPlateNumber() + "', '" + shipper.getVehicle() + "', '" + shipper.getVehicleColor() + "', '" + shipper.getActive()+ "')";
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else {
            conn.close();
            return false;
        }
    }

    public boolean Update(ShipperDto shipper) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "Update Shipper set plateNumber = '" + shipper.getPlateNumber() + "', vehicle = '" + shipper.getVehicle() +"', vehicleColor = '"+ shipper.getVehicle() + "', active = '" + shipper.getActive() + "' where userId = " + shipper.getUserId()+ ")" ;
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else
            conn.close();
        return false;
    }

    public boolean Delete(ShipperDto shipper) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "delete from Shipper where ID = " + shipper.getUserId();
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else
            conn.close();
        return false;
    }


    public ShipperDto findById(int Id) throws SQLException {
        ShipperDto shipper = new ShipperDto();
        Statement statement = conn.createStatement();
        String sql = "select * from Shipper where ID = " + Id;
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            shipper = new ShipperDto(rs.getInt("userId"), rs.getString("plateNumber"), rs.getString("vehicle"), rs.getString("vehicleColor"), rs.getString("active"));
        }
        conn.close();
        return shipper;
    }

}
