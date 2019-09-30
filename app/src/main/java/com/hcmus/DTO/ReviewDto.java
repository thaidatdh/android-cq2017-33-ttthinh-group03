package com.hcmus.DTO;

public class ReviewDto {
   private int userId;
   private char type;
   private int shipperId;
   private int rating;
   private String comment;
   private String createdDate;
   private String updatedDate;

   public ReviewDto() {}

   public int getUserId() {
      return userId;
   }

   public int getShipperId() {
      return shipperId;
   }

   public char getType() {
      return type;
   }

   public void setType(char type) {
      this.type = type;
   }

   public int getRating() {
      return rating;
   }

   public void setRating(int rating) {
      this.rating = rating;
   }

   public void setShipperId(int shipperId) {
      this.shipperId = shipperId;
   }

   public void setUserId(int userId) {
      this.userId = userId;
   }

   public String getComment() {
      return comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public void setCreatedDate(String createdDate) {
      this.createdDate = createdDate;
   }

   public String getCreatedDate() {
      return createdDate;
   }

   public String getUpdatedDate() {
      return updatedDate;
   }

   public void setUpdatedDate(String updatedDate) {
      this.updatedDate = updatedDate;
   }
}
