package com.hcmus.Utils;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    public Connection getConnection() {
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
}
