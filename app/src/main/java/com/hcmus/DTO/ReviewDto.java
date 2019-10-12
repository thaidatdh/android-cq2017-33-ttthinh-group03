package com.hcmus.DTO;

public class ReviewDto {
   private int reviewId;
   private int userId;
   private char type;
   private int objectId;
   private float rating;
   private String comment;
   private String createdDate;
   private String updatedDate;

   public ReviewDto() {
   }

   public ReviewDto(int reviewId, int userId, char type, int objectId, float rating, String comment, String createdDate, String updatedDate) {
      this.reviewId = reviewId;
      this.userId = userId;
      this.type = type;
      this.objectId = objectId;
      this.rating = rating;
      this.comment = comment;
      this.createdDate = createdDate;
      this.updatedDate = updatedDate;
   }

   public int getReviewId() {
      return reviewId;
   }

   public void setReviewId(int reviewId) {
      this.reviewId = reviewId;
   }

   public int getUserId() {
      return userId;
   }

   public void setUserId(int userId) {
      this.userId = userId;
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

   public void setObjectId(int objectId) {
      this.objectId = objectId;
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
      return createdDate;
   }

   public void setCreatedDate(String createdDate) {
      this.createdDate = createdDate;
   }

   public String getUpdatedDate() {
      return updatedDate;
   }

   public void setUpdatedDate(String updatedDate) {
      this.updatedDate = updatedDate;
   }
}
