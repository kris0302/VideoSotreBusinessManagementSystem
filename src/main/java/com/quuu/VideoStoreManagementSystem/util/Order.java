package com.quuu.VideoStoreManagementSystem.util;

public class Order {
    private String id;
    private String title;
    private String orderdate;
    private String amount;
    private String status;
    private String delivery;
    private String province;
    private String address;
    private String fulladdress;
    private int latedays;

    public Order(String i, String t, String o, String a, String s, String d, String province, String address, int latedays){
        id = i;
        title = t;
        orderdate = o;
        amount = a;
        status = s;
        delivery = d;
        this.province = province;
        this.address = address;
        fulladdress = address +", "+province;
        this.latedays = latedays;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFulladdress() {
        return fulladdress;
    }

    public void setFulladdress(String fulladdress) {
        this.fulladdress = fulladdress;
    }

    public int getLatedays() {
        return latedays;
    }

    public void setLatedays(int latedays) {
        this.latedays = latedays;
    }
}
