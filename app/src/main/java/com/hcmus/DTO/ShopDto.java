package com.hcmus.DTO;

public class ShopDto {
   private int shopId;
   private String name;
   private String address;
   private String openTime;
   private String closeTime;

   public ShopDto() {}

   public ShopDto(int shopId, String name, String address, String openTime, String closeTime) {
      this.shopId = shopId;
      this.name = name;
      this.address = address;
      this.openTime = openTime;
      this.closeTime = closeTime;
   }

   public int getShopId() {
      return shopId;
   }

   public void setShopId(int shopId) {
      this.shopId = shopId;
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
      return openTime;
   }

   public void setOpenTime(String openTime) {
      this.openTime = openTime;
   }

   public String getCloseTime() {
      return closeTime;
   }

   public void setCloseTime(String closeTime) {
      this.closeTime = closeTime;
   }
}
