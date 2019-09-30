package com.hcmus.DTO;

public class ShopDto {
   private int shopId;
   private String address;
   private String openTime;
   private String closeTime;

   public ShopDto() {}

   public String getAddress() {
      return address;
   }

   public int getShopId() {
      return shopId;
   }

   public String getCloseTime() {
      return closeTime;
   }

   public String getOpenTime() {
      return openTime;
   }

   public void setClosetime(String closeTime) {
      this.closeTime = closeTime;
   }

   public void setOpenTime(String openTime) {
      this.openTime = openTime;
   }

   public void setShopid(int shopId) {
      this.shopId = shopId;
   }

   public void setAddress(String address) {
      this.address = address;
   }
}
