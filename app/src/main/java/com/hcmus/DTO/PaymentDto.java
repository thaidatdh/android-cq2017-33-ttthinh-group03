package com.hcmus.DTO;

public class PaymentDto {
   private int payment_id;
   private int bill_id;
   private String payment_date;
   private int type;
   private long amount;
   private String description;
   private int customer;
   private int receiver;
   private boolean is_completed;

   public PaymentDto() {}

   public PaymentDto(int payment_id, int bill_id, String payment_date, int type, long amount, String description, int customer, int receiver, boolean is_completed) {
      this.payment_id = payment_id;
      this.bill_id = bill_id;
      this.payment_date = payment_date;
      this.type = type;
      this.amount = amount;
      this.description = description;
      this.customer = customer;
      this.receiver = receiver;
      this.is_completed = is_completed;
   }

   public int getPaymentId() {
      return payment_id;
   }

   public void setPaymentId(int payment_id) {
      this.payment_id = payment_id;
   }

   public void setAmount(long amount) {
      this.amount = amount;
   }

   public int getBillId() {
      return bill_id;
   }

   public void setBillId(int bill_id) {
      this.bill_id = bill_id;
   }

   public void setCompleted(boolean is_completed) {
      this.is_completed = is_completed;
   }

   public boolean isCompleted() {
      return is_completed;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public void setUserId(int customer) {
      this.customer = customer;
   }

   public int getUserId() {
      return customer;
   }

   public void setType(int type) {
      this.type = type;
   }

   public int getReceiverId() {
      return receiver;
   }

   public int getType() {
      return type;
   }

   public long getAmount() {
      return amount;
   }

   public String getPaymentDate() {
      return payment_date;
   }

   public void setPaymentDate(String payment_date) {
      this.payment_date = payment_date;
   }

   public void setReceiverId(int receiver) {
      this.receiver = receiver;
   }
}
