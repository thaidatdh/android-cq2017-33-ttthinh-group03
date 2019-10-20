package com.hcmus.DTO;

public class UserDto {
   private int user_id;
   private String user_type;
   private String username;
   private String password;
   private String first_name;
   private String last_name;
   private String birth_date;
   private String address;
   private String phone;
   private String created_date;

   public UserDto() {}

   public UserDto(int user_id, String user_type, String username, String password, String first_name, String last_name, String birth_date, String address, String phone, String created_date) {
      this.user_id = user_id;
      this.user_type = user_type;
      this.username = username;
      this.password = password;
      this.first_name = first_name;
      this.last_name = last_name;
      this.birth_date = birth_date;
      this.address = address;
      this.phone = phone;
      this.created_date = created_date;
   }
   public UserDto( String username, String password, String firstName, String lastName, String birthDate, String address, String phone,String userType,String created_date) {
      this.user_type = userType;
      this.username = username;
      this.password = password;
      this.first_name = firstName;
      this.last_name = lastName;
      this.birth_date = birthDate;
      this.address = address;
      this.phone = phone;
      this.created_date = created_date;

   }
   public int getUserId() {
      return user_id;
   }

   public void setUserId(int user_id) {
      this.user_id = user_id;
   }

   public String getUserType() {
      return user_type;
   }

   public void setUserType(String user_type) {
      this.user_type = user_type;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getFirstName() {
      return first_name;
   }

   public void setFirstName(String first_name) {
      this.first_name = first_name;
   }

   public String getLastName() {
      return last_name;
   }

   public void setLastName(String last_name) {
      this.last_name = last_name;
   }

   public String getBirthDate() {
      return birth_date;
   }

   public void setBirthDate(String birth_date) {
      this.birth_date = birth_date;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getCreatedDate() {
      return created_date;
   }

   public void setCreatedDate(String created_date) {
      this.created_date = created_date;
   }
}
