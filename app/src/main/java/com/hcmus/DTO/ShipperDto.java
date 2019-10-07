package com.hcmus.DTO;

public class ShipperDto {
   private int userId;
   private String plateNumber;
   private String vehicle;
   private String vehicleColor;
   private String active;

   public ShipperDto() {}

   public ShipperDto(int userId, String plateNumber, String vehicle, String vehicleColor, String active) {
      this.userId = userId;
      this.plateNumber = plateNumber;
      this.vehicle = vehicle;
      this.vehicleColor = vehicleColor;
      this.active = active;
   }

   public void setUserId(int userId) {
      this.userId = userId;
   }

   public int getUserId() {
      return userId;
   }

   public String getActive() {
      return active;
   }

   public String getPlateNumber() {
      return plateNumber;
   }

   public String getVehicle() {
      return vehicle;
   }

   public String getVehicleColor() {
      return vehicleColor;
   }

   public void setActive(String active) {
      this.active = active;
   }

   public void setPlateNumber(String plateNumber) {
      this.plateNumber = plateNumber;
   }

   public void setVehicle(String vehicle) {
      this.vehicle = vehicle;
   }

   public void setVehicleColor(String vehicleColor) {
      this.vehicleColor = vehicleColor;
   }
}
