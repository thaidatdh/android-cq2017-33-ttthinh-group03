package com.hcmus.DTO;

public class CategoryDto {
   private int categoryId;
   private String name;
   private String description;

   public CategoryDto() {}

   public int getCategoryId() {
      return categoryId;
   }

   public String getDescription() {
      return description;
   }

   public String getName() {
      return name;
   }

   public void setCategoryId(int categoryId) {
      this.categoryId = categoryId;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public void setName(String name) {
      this.name = name;
   }
}
