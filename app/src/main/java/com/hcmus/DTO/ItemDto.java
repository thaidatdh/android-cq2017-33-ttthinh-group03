package com.hcmus.DTO;

public class ItemDto {
   private int item_id;
   private String name;
   private String description;
   private String thumbnail;
   private long price;
   private int category;
   private char status;
   private String createdDate;
   private String updatedDate;

   public ItemDto() {}

   public ItemDto(int item_id, String name, String description, String thumbnail, long price, int category, char status, String createdDate, String updatedDate) {
      this.item_id = item_id;
      this.name = name;
      this.description = description;
      this.thumbnail = thumbnail;
      this.price = price;
      this.category = category;
      this.status = status;
      this.createdDate = createdDate;
      this.updatedDate = updatedDate;
   }

   public ItemDto( String name, String description, String thumbnail, long price, int category, char status, String createdDate, String updatedDate) {
      this.name = name;
      this.description = description;
      this.thumbnail = thumbnail;
      this.price = price;
      this.category = category;
      this.status = status;
      this.createdDate = createdDate;
      this.updatedDate = updatedDate;
   }
   //update
   public ItemDto(int id, String name, String description, String thumbnail, long price, int category, char status,  String updatedDate) {
      this.item_id=id;
      this.name = name;
      this.description = description;
      this.thumbnail = thumbnail;
      this.price = price;
      this.category = category;
      this.status = status;
      this.updatedDate = updatedDate;
   }
   public int getId() {
      return item_id;
   }

   public void setId(int item_id) {
      this.item_id = item_id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getThumbnail() {
      return thumbnail;
   }

   public void setThumbnail(String thumbnail) {
      this.thumbnail = thumbnail;
   }

   public long getPrice() {
      return price;
   }

   public void setPrice(long price) {
      this.price = price;
   }

   public int getCategory() {
      return category;
   }

   public void setCategory(int category) {
      this.category = category;
   }

   public char getStatus() {
      return status;
   }

   public void setStatus(char status) {
      this.status = status;
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
