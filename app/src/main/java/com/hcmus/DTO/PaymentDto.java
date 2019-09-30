package com.hcmus.DTO;

public class PaymentDto {
   private int billId;
   private String paymentDate;
   private int type;
   private long amount;
   private String description;
   private int userId;
   private int receiverId;
   private boolean completed;

   public PaymentDto() {}

   public void setAmount(long amount) {
      this.amount = amount;
   }

   public int getBillId() {
      return billId;
   }

   public void setBillId(int billId) {
      this.billId = billId;
   }

   public void setCompleted(boolean completed) {
      this.completed = completed;
   }

   public boolean isCompleted() {
      return completed;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public void setUserId(int userId) {
      this.userId = userId;
   }

   public int getUserId() {
      return userId;
   }

   public void setType(int type) {
      this.type = type;
   }

   public int getReceiverId() {
      return receiverId;
   }

   public int getType() {
      return type;
   }

   public long getAmount() {
      return amount;
   }

   public String getPaymentDate() {
      return paymentDate;
   }

   public void setPaymentDate(String paymentDate) {
      this.paymentDate = paymentDate;
   }

   public void setReceiverId(int receiverId) {
      this.receiverId = receiverId;
   }
}
