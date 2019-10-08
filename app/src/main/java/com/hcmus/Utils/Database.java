package com.hcmus.Utils;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    public static Connection getConnection() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection con = null;
        String url = "jdbc:jtds:sqlserver://shipe.database.windows.net:1433;databaseName=Shipe;integratedSecurity=true;user=shipe;password=android1733@;";
        try {

            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            con = DriverManager.getConnection(url);
            System.out.println("Connection completed.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        finally {
        }
        return con;
    }

    public static ResultSet SelectQuery(String sql) throws SQLException {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        conn.close();
        return rs;
    }

    public static int ExecuteQuery(String sql) throws SQLException {
        int result = -1;
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        result = statement.executeUpdate(sql);
        return result;
    }
}
