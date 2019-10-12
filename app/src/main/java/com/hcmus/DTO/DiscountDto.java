package com.hcmus.DTO;

public class DiscountDto {
   private int discount_id;
   private int item_id;
   private double percentage;
   private String description;
   private String start_date;
   private String end_date;

   public DiscountDto() {}

   public DiscountDto(int discount_id, int item_id, double percentage, String description, String start_date, String end_date) {
      this.discount_id = discount_id;
      this.item_id = item_id;
      this.percentage = percentage;
      this.description = description;
      this.start_date = start_date;
      this.end_date = end_date;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public double getPercentage() {
      return percentage;
   }

   public int getDiscountId() {
      return discount_id;
   }

   public int getItemId() {
      return item_id;
   }

   public String getEndDate() {
      return end_date;
   }

   public String getStartDate() {
      return start_date;
   }

   public void setDiscountId(int discount_id) {
      this.discount_id = discount_id;
   }

   public void setEndDate(String end_date) {
      this.end_date = end_date;
   }

   public void setItemId(int item_id) {
      this.item_id = item_id;
   }

   public void setPercentage(double percentage) {
      this.percentage = percentage;
   }

   public void setStartDate(String start_date) {
      this.start_date = start_date;
   }
}
