package com.hcmus.DTO;

public class ShipperDto {
   private int user_id;
   private String plate_number;
   private String vehicle;
   private String vehicle_color;
   private String is_active;

   public ShipperDto() {}

   public ShipperDto(int user_id, String plate_number, String vehicle, String vehicle_color, String is_active) {
      this.user_id = user_id;
      this.plate_number = plate_number;
      this.vehicle = vehicle;
      this.vehicle_color = vehicle_color;
      this.is_active = is_active;
   }

   public void setUserId(int user_id) {
      this.user_id = user_id;
   }

   public int getUserId() {
      return user_id;
   }

   public String getActive() {
      return is_active;
   }

   public String getPlateNumber() {
      return plate_number;
   }

   public String getVehicle() {
      return vehicle;
   }

   public String getVehicleColor() {
      return vehicle_color;
   }

   public void setActive(String is_active) {
      this.is_active = is_active;
   }

   public void setPlateNumber(String plate_number) {
      this.plate_number = plate_number;
   }

   public void setVehicle(String vehicle) {
      this.vehicle = vehicle;
   }

   public void setVehicleColor(String vehicle_color) {
      this.vehicle_color = vehicle_color;
   }
}
