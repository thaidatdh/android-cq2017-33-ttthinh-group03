package com.hcmus.DTO;

public class CategoryDto {
   private int category_id;
   private String name;
   private String description;

   public CategoryDto() {}

   public CategoryDto(int category_id, String name, String description) {
      this.category_id = category_id;
      this.name = name;
      this.description = description;
   }

   public int getCategoryId() {
      return category_id;
   }

   public String getDescription() {
      return description;
   }

   public String getName() {
      return name;
   }

   public void setCategoryId(int category_id) {
      this.category_id = category_id;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public void setName(String name) {
      this.name = name;
   }
}
