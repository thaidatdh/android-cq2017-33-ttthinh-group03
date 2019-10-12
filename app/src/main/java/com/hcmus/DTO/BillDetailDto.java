package com.hcmus.DTO;

public class BillDetailDto {
   private int bill_id;
   private int item_id;
   private int amount;

   public BillDetailDto() {
   }

   public BillDetailDto(int bill_id, int item_id, int amount) {
      this.bill_id = bill_id;
      this.item_id = item_id;
      this.amount = amount;
   }

   public void setBillId(int bill_id) {
      this.bill_id = bill_id;
   }

   public int getBillId() {
      return bill_id;
   }

   public void setItemId(int item_id) {
      this.item_id = item_id;
   }

   public int getItemId() {
      return item_id;
   }

   public int getAmount() {
      return amount;
   }

   public void setAmount(int amount) {
      this.amount = amount;
   }
}
