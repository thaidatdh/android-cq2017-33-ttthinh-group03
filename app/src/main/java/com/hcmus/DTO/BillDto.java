package com.hcmus.DTO;

public class BillDto {
   private int bill_id;
   private int customer_id;
   private String created_date;
   private String description;
   private long total_price;
   private long ship_charge;
   private boolean accepted;
   private char status;
   private int shipper;
   private String deliver_time;
   private boolean completed;

   public BillDto() {}

   public BillDto(int bill_id, int customer_id, String created_date, String description, long total_price, long ship_charge, boolean accepted, char status, int shipper, String deliver_time, boolean completed) {
      this.bill_id = bill_id;
      this.customer_id = customer_id;
      this.created_date = created_date;
      this.description = description;
      this.total_price = total_price;
      this.ship_charge = ship_charge;
      this.accepted = accepted;
      this.status = status;
      this.shipper = shipper;
      this.deliver_time = deliver_time;
      this.completed = completed;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getDescription() {
      return description;
   }

   public void setCreatedDate(String created_date) {
      this.created_date = created_date;
   }

   public String getCreatedDate() {
      return created_date;
   }

   public void setStatus(char status) {
      this.status = status;
   }

   public char getStatus() {
      return status;
   }

   public void setShipperId(int shipper) {
      this.shipper = shipper;
   }

   public boolean isAccepted() {
      return accepted;
   }

   public int getShipperId() {
      return shipper;
   }

   public int getBillId() {
      return bill_id;
   }

   public int getCustomerId() {
      return customer_id;
   }

   public long getShipCharge() {
      return ship_charge;
   }

   public long getTotalPrice() {
      return total_price;
   }

   public String getDeliverTime() {
      return deliver_time;
   }

   public void setAccepted(boolean accepted) {
      this.accepted = accepted;
   }

   public void setBillId(int bill_id) {
      this.bill_id = bill_id;
   }

   public void setCustomerId(int customer_id) {
      this.customer_id = customer_id;
   }

   public void setShipCharge(long ship_charge) {
      this.ship_charge = ship_charge;
   }

   public void setTotalPrice(long total_price) {
      this.total_price = total_price;
   }

   public void setDeliverTime(String deliver_time) {
      this.deliver_time = deliver_time;
   }

   public boolean isCompleted() {
      return completed;
   }

   public void setCompleted(boolean completed) {
      this.completed = completed;
   }
}
