package com.hcmus.DTO;

public class ShopDto {
   private int shop_id;
   private String name;
   private String address;
   private String open_time;
   private String close_time;

   public ShopDto() {}

   public ShopDto(int shop_id, String name, String address, String open_time, String close_time) {
      this.shop_id = shop_id;
      this.name = name;
      this.address = address;
      this.open_time = open_time;
      this.close_time = close_time;
   }

   public int getShopId() {
      return shop_id;
   }

   public void setShopId(int shop_id) {
      this.shop_id = shop_id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public String getOpenTime() {
      return open_time;
   }

   public void setOpenTime(String open_time) {
      this.open_time = open_time;
   }

   public String getCloseTime() {
      return close_time;
   }

   public void setCloseTime(String close_time) {
      this.close_time = close_time;
   }
}
