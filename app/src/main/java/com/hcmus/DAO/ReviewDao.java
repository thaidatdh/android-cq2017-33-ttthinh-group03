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

    public ReviewDao() {
    }

    public static List<ReviewDto> SelectAll() throws SQLException {
        List<ReviewDto> review = new ArrayList<>();
        String sql = "select * from Review";
        ResultSet rs = Database.SelectQuery(sql);
        while (rs.next()){
            review.add(new ReviewDto(rs.getInt("review_id"), rs.getInt("user_id"), rs.getString("type").charAt(0), rs.getInt("objectid"), rs.getInt("item_id"), rs.getInt("rating"), rs.getString("comment"), rs.getString("created_date"), rs.getString("updated_date")));
        }
        return review;
    }

    public static boolean Insert(ReviewDto review) throws SQLException {
        String sql = "insert into review(review_id, user_id, type, objectid, item_id, rating, comment, created_date, updated_date) values ("
                + review.getReviewId() + ", "+ review.getUserId() + ", '" + review.getType() + "', " + review.getObjectId() + ", " + review.getItemId() + ", " + review.getRating() + ", '" + review.getComment() + "', '" + review.getCreatedDate() + "', '" + review.getUpdatedDate()+ "')";
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean Update(ReviewDto review) throws SQLException {
        String sql = "update review set user_id = '" + review.getUserId() + "', type = '" + review.getType() + "', objectid = '" + review.getObjectId() + "', item_id = '" + review.getItemId()
            + "', rating = '" + review.getRating() + "', comment = '" + review.getComment() + "', created_date = '" + review.getCreatedDate() + "', updated_date = '" + review.getUpdatedDate() + "where review_id = " + review.getReviewId();
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }

    public static boolean Delete(ReviewDto review) throws SQLException {
        String sql = "delete from review where review_id = " + review.getReviewId();
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }


    public static ReviewDto findById(int Id) throws SQLException {
        ReviewDto review = new ReviewDto();
        String sql = "select * from review where review_id = " + Id;
        ResultSet rs = Database.SelectQuery(sql);
        while (rs.next()){
            review = new ReviewDto(rs.getInt("review_id"), rs.getInt("user_id"), rs.getString("type").charAt(0), rs.getInt("objectid"), rs.getInt("item_id"), rs.getInt("rating"), rs.getString("comment"), rs.getString("created_date"), rs.getString("updated_date"));
        }
        return review;
    }
}
