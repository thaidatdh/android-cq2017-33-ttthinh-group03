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
    public static List<ReviewDto> GetAll(){
        List<ReviewDto> review = new ArrayList<>();
        String sql = "select * from Review";
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()) {
                review.add(new ReviewDto(rs.getInt("review_id"), rs.getInt("user_id"), rs.getString("type").charAt(0), rs.getInt("objectid"), rs.getFloat("rating"), rs.getString("comment"), rs.getString("created_date"), rs.getString("updated_date")));
            }
        }catch (Exception ex) {}
        return review;
    }
    public static List<ReviewDto> GetAllShipperReviewForShipper(int shipper_id){
        List<ReviewDto> review = new ArrayList<>();
        String sql = "select * from Review where type='U' and objectid=" + shipper_id;
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()) {
                review.add(new ReviewDto(rs.getInt("review_id"), rs.getInt("user_id"), rs.getString("type").charAt(0), rs.getInt("objectid"), rs.getFloat("rating"), rs.getString("comment"), rs.getString("created_date"), rs.getString("updated_date")));
            }
        }catch (Exception ex) {}
        return review;
    }
    public static List<ReviewDto> GetAllShopReview(){
        List<ReviewDto> review = new ArrayList<>();
        String sql = "select * from Review where type='S'";
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()) {
                review.add(new ReviewDto(rs.getInt("review_id"), rs.getInt("user_id"), rs.getString("type").charAt(0), rs.getInt("objectid"), rs.getFloat("rating"), rs.getString("comment"), rs.getString("created_date"), rs.getString("updated_date")));
            }
        }catch (Exception ex) {}
        return review;
    }

    public static int Insert(ReviewDto review) {
        String sql = "insert into review(review_id, user_id, type, objectid, rating, comment, created_date, updated_date) values ("
                + review.getReviewId() + ", "+ review.getUserId() + ", '" + review.getType() + "', " + review.getObjectId() + ", " + review.getRating() + ", '" + review.getComment() + "', '" + review.getCreatedDate() + "', '" + review.getUpdatedDate()+ "')";
        sql = sql.replace("null","");
        int result = Database.ExecuteQuery(sql);
        if (result == -1)
            return result;
        return Database.GetLatestId("review","review_id");
    }

    public static boolean Update(ReviewDto review) {
        String sql = "update review set user_id = " + review.getUserId() + ", type = '" + review.getType() + "', objectid = " + review.getObjectId()
            + ", rating = " + review.getRating() + ", comment = '" + review.getComment() + "', created_date = '" + review.getCreatedDate() + "', updated_date = '" + review.getUpdatedDate() + "where review_id = " + review.getReviewId();
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }

    public static boolean Delete(ReviewDto review) {
        String sql = "delete from review where review_id = " + review.getReviewId();
        if (Database.ExecuteQuery(sql) > 0) {
            return true;
        } else
        return false;
    }


    public static ReviewDto findById(int Id) {
        ReviewDto review = new ReviewDto();
        String sql = "select * from review where review_id = " + Id;
        try {
            ResultSet rs = Database.SelectQuery(sql);
            while (rs.next()){
                review = new ReviewDto(rs.getInt("review_id"), rs.getInt("user_id"), rs.getString("type").charAt(0), rs.getInt("objectid"), rs.getFloat("rating"), rs.getString("comment"), rs.getString("created_date"), rs.getString("updated_date"));
            }
        } catch (Exception ex) {}
        return review;
    }
}
