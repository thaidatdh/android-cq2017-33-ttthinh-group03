package com.hcmus.DTO;

public class ReviewDto {
   private int review_id;
   private int user_id;
   private char type;
   private int objectid;
   private int item_id;
   private int rating;
   private String comment;
   private String created_date;
   private String updated_date;

   public ReviewDto() {
   }

   public ReviewDto(int review_id, int user_id, char type, int objectid, int item_id, int rating, String comment, String created_date, String updated_date) {
      this.review_id = review_id;
      this.user_id = user_id;
      this.type = type;
      this.objectid = objectid;
      this.item_id = item_id;
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
      return objectid;
   }

   public void setObjectId(int objectid) {
      this.objectid = objectid;
   }

   public int getItemId() {
      return item_id;
   }

   public void setItemId(int item_id) {
      this.item_id = item_id;
   }

   public int getRating() {
      return rating;
   }

   public void setRating(int rating) {
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
