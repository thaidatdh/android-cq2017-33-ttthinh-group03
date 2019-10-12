package com.hcmus.DTO;

public class ReviewDto {
   private int review_id;
   private int user_id;
   private char type;
   private int objectId;
   private float rating;
   private String comment;
   private String created_date;
   private String updated_date;

   public ReviewDto() {
   }

   public ReviewDto(int reviewId, int userId, char type, int objectId, float rating, String comment, String createdDate, String updatedDate) {
      this.review_id = reviewId;
      this.user_id = userId;
      this.type = type;
      this.objectId = objectId;
      this.rating = rating;
      this.comment = comment;
      this.created_date = created_date;
      this.updated_date = updated_date;
   }

   public int getReviewId() {
      return review_id;
   }

   public void setReviewId(int review_id) {
      this.review_id = review_id;
   }

   public int getUserId() {
      return user_id;
   }

   public void setUserId(int user_id) {
      this.user_id = user_id;
   }

   public char getType() {
      return type;
   }

   public void setType(char type) {
      this.type = type;
   }

   public int getObjectId() {
      return objectId;
   }

   public void setObjectId(int objectid) {
      this.objectId = objectid;
   }

   public float getRating() {
      return rating;
   }

   public void setRating(float rating) {
      this.rating = rating;
   }

   public String getComment() {
      return comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public String getCreatedDate() {
      return created_date;
   }

   public void setCreatedDate(String created_date) {
      this.created_date = created_date;
   }

   public String getUpdatedDate() {
      return updated_date;
   }

   public void setUpdatedDate(String updated_date) {
      this.updated_date = updated_date;
   }
}
