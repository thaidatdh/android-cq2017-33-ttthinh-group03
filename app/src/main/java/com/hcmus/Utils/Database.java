package com.hcmus.Utils;

import android.os.StrictMode;

import com.hcmus.DTO.ShopDto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static Connection connection;
    public static Connection getConnection() {
        String ConnectionString = "jdbc:jtds:sqlserver://" + ConfigUtils.SERVER.SERVER + ":" + ConfigUtils.SERVER.PORT
                + ";databaseName=" + ConfigUtils.SERVER.DBN + ";integratedSecurity=true;"
                + "user=" + ConfigUtils.SERVER.UID + ";password=" + ConfigUtils.SERVER.PWD + ";";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                connection = DriverManager.getConnection(ConnectionString);
                //System.out.println("Connection completed.");
            }
        } catch (Exception ex) {
            //System.out.println(ex.getMessage());
        }
        return connection;
    }

    public static ResultSet SelectQuery(String sql){
        ResultSet rs = null;
        try {
            Connection conn = getConnection();
            Statement statement = conn.createStatement();
            rs = statement.executeQuery(sql);
        } catch (Exception ex) {}
        return rs;
    }

    public static int ExecuteQuery(String sql){
        int result = -1;
        try {
            Connection conn = getConnection();
            Statement statement = conn.createStatement();
            result = statement.executeUpdate(sql);
        } catch (Exception ex) {}
        return result;
    }
    public static int GetLastestId(String table, String id_field) {
        String sql = "SELECT TOP 1 "+id_field+" FROM " + table + " ORDER BY " + id_field +" DESC";
        ResultSet rsRow = SelectQuery(sql);
        int result = -1;
        try {
            while (rsRow.next()){
                result = rsRow.getInt(id_field);
            }
        }catch (Exception ex) {}
        return result;
    }
}
