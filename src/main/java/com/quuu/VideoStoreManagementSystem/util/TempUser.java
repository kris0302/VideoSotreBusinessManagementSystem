package com.quuu.VideoStoreManagementSystem.util;

public class TempUser {
    private String address;
    private String name;
    private String delivery;
    private String province;

    public TempUser(String a, String n, String d, String p){
        address = a;
        name = n;
        delivery = d;
        setProvince(p);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }
}
