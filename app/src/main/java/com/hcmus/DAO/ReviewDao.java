package com.hcmus.DAO;

import com.hcmus.DTO.ReviewDto;
import com.hcmus.Utils.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao {
    Database db = new Database();
    Connection conn;

    public ReviewDao() {
        conn = db.getConnection();
    }

    public List<ReviewDto> SelectAll() throws SQLException {
        List<ReviewDto> review = new ArrayList<>();
        Statement statement = conn.createStatement();
        String sql = "select * from Review";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            review.add(new ReviewDto());
        }
        conn.close();// Đóng kết nối
        return review;
    }

    public boolean Insert(ReviewDto review) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "";
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else {
            conn.close();
            return false;
        }
    }

    public boolean Update(ReviewDto review) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "" ;
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else
            conn.close();
        return false;
    }

    public boolean Delete(ReviewDto review) throws SQLException {
        Statement statement = conn.createStatement();// Tạo đối tượng Statement.
        String sql = "";
        if (statement.executeUpdate(sql) > 0) {
            conn.close();
            return true;
        } else
            conn.close();
        return false;
    }


    public ReviewDto findById(int Id) throws SQLException {
        ReviewDto review = new ReviewDto();
        Statement statement = conn.createStatement();
        String sql = "" + Id;
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            review = new ReviewDto();
        }
        conn.close();
        return review;
    }
}
