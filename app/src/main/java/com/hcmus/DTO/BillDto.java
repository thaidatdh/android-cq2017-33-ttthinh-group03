package com.hcmus.DTO;

public class BillDto {
   private int billId;
   private int customerId;
   private String createdDate;
   private String description;
   private long totalPrice;
   private long shipCharge;
   private boolean accepted;
   private char status;
   private int shipperId;
   private String deliverTime;
   private boolean completed;

   public BillDto() {}

   public BillDto(int billId, int customerId, String createdDate, String description, long totalPrice, long shipCharge, boolean accepted, char status, int shipperId, String deliverTime, boolean completed) {
      this.billId = billId;
      this.customerId = customerId;
      this.createdDate = createdDate;
      this.description = description;
      this.totalPrice = totalPrice;
      this.shipCharge = shipCharge;
      this.accepted = accepted;
      this.status = status;
      this.shipperId = shipperId;
      this.deliverTime = deliverTime;
      this.completed = completed;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getDescription() {
      return description;
   }

   public void setCreatedDate(String createdDate) {
      this.createdDate = createdDate;
   }

   public String getCreatedDate() {
      return createdDate;
   }

   public void setStatus(char status) {
      this.status = status;
   }

   public char getStatus() {
      return status;
   }

   public void setShipperId(int shipperId) {
      this.shipperId = shipperId;
   }

   public boolean isAccepted() {
      return accepted;
   }

   public int getShipperId() {
      return shipperId;
   }

   public int getBillId() {
      return billId;
   }

   public int getCustomerId() {
      return customerId;
   }

   public long getShipCharge() {
      return shipCharge;
   }

   public long getTotalPrice() {
      return totalPrice;
   }

   public String getDeliverTime() {
      return deliverTime;
   }

   public void setAccepted(boolean accepted) {
      this.accepted = accepted;
   }

   public void setBillId(int billId) {
      this.billId = billId;
   }

   public void setCustomerId(int customerId) {
      this.customerId = customerId;
   }

   public void setShipCharge(long shipCharge) {
      this.shipCharge = shipCharge;
   }

   public void setTotalPrice(long totalPrice) {
      this.totalPrice = totalPrice;
   }

   public void setDeliverTime(String deliverTime) {
      this.deliverTime = deliverTime;
   }

   public boolean isCompleted() {
      return completed;
   }

   public void setCompleted(boolean completed) {
      this.completed = completed;
   }
}
