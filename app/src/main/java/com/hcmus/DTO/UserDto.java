package com.hcmus.DTO;

public class UserDto {
   private int userId;
   private char userType;
   private String username;
   private String password;
   private String firstName;
   private String lastName;
   private String birthDate;
   private String address;
   private String phone;
   private String createdDate;

   public UserDto() {}

   public UserDto(int userId, char userType, String username, String password, String firstName, String lastName, String birthDate, String address, String phone, String createdDate) {
      this.userId = userId;
      this.userType = userType;
      this.username = username;
      this.password = password;
      this.firstName = firstName;
      this.lastName = lastName;
      this.birthDate = birthDate;
      this.address = address;
      this.phone = phone;
      this.createdDate = createdDate;
   }

   public int getUserId() {
      return userId;
   }

   public void setUserId(int userId) {
      this.userId = userId;
   }

   public String getFirstName() {
      return firstName;
   }

   public char getUserType() {
      return userType;
   }

   public String getLastName() {
      return lastName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getBirthDate() {
      return birthDate;
   }

   public void setBirthDate(String birthDate) {
      this.birthDate = birthDate;
   }

   public void setUserType(char userType) {
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
