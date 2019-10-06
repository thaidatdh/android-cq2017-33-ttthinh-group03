package com.hcmus.DTO;

public class ItemDto {
   private int id;
   private String name;
   private String description;
   private String thumbnail;
   private long price;
   private int category;
   private char status;
   private String createdDate;
   private String updatedDate;

   public ItemDto() {}

   public ItemDto(int id, String name, String description, String thumbnail, long price, int category, char status, String createdDate, String updatedDate) {
      this.id = id;
      this.name = name;
      this.description = description;
      this.thumbnail = thumbnail;
      this.price = price;
      this.category = category;
      this.status = status;
      this.createdDate = createdDate;
      this.updatedDate = updatedDate;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getName() {
      return name;
   }

   public String getDescription() {
      return description;
   }

   public int getCategory() {
      return category;
   }

   public char getStatus() {
      return status;
   }

   public int getId() {
      return id;
   }

   public long getPrice() {
      return price;
   }

   public String getThumbnail() {
      return thumbnail;
   }

   public void setCategory(int category) {
      this.category = category;
   }

   public void setId(int id) {
      this.id = id;
   }

   public void setPrice(long price) {
      this.price = price;
   }

   public void setStatus(char status) {
      this.status = status;
   }

   public void setThumbnail(String thumbnail) {
      this.thumbnail = thumbnail;
   }

   public void setUpdatedDate(String updatedDate) {
      this.updatedDate = updatedDate;
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
}
