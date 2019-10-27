package com.hcmus.Models;

import java.util.HashMap;

public class Task {
    //Bill
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

    //Customer
    private String user_type;
    private String username;
    private String first_name;
    private String last_name;
    private String birth_date;
    private String address;
    private String phone;

    //Google Map
    private HashMap<String, String> distance;
    private HashMap<String, String> duration;

    public Task() {}

    public Task(int bill_id, int customer_id, String created_date, String description, long total_price, long ship_charge, boolean accepted, char status, int shipper, String deliver_time, boolean completed,  String user_type, String username, String first_name, String last_name, String birth_date, String address, String phone) {
        //Bill
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

        //Customer
        this.user_type = user_type;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birth_date = birth_date;
        this.address = address;
        this.phone = phone;

        //Google map
        this.distance = null;
        this.duration = null;
    }

    //Bill
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

    //Customer
    public String getUserType() {
        return user_type;
    }

    public void setUserType(String user_type) {
        this.user_type = user_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getBirthDate() {
        return birth_date;
    }

    public void setBirthDate(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public HashMap<String, String> getDistance(){ return distance;}

    public void setDistance(HashMap<String, String> distance){ this.distance = distance;}

    public HashMap<String, String> getDuration(){ return duration;}

    public void setDuration(HashMap<String, String> duration){ this.duration = duration;}
}
