package com.hcmus.DTO;

public class UserDto {
   private int userId;
   private String userType;
   private String username;
   private String password;
   private String name;

   private String address;
   private String phone;
   private String createdDate;
   private int age;

   public UserDto() {}

   public int getUserId() {
      return userId;
   }

   public void setUserId(int userId) {
      this.userId = userId;
   }



   public String getUserType() {
      return userType;
   }


   public int getAge() {
      return age;
   }

   public void setAge(int age) {
      this.age = age;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setUserType(String userType) {
      this.userType = userType;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getCreatedDate() {
      return createdDate;
   }

   public void setCreatedDate(String createdDate) {
      this.createdDate = createdDate;
   }

}
