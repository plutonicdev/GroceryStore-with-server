package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceOrder {
    @SerializedName("token")
    String token;
    @SerializedName("fname")
    String fname;
    @SerializedName("lname")
    String lname;
    @SerializedName("mobile")
    String mobile;
    @SerializedName("area")
    String area;
    @SerializedName("address")
    String address;
    @SerializedName("user_id")
    String user_id;
    @SerializedName("orderitems")
    List<OrderItem> orderitems;

    public PlaceOrder(String token, String fname, String lname, String mobile, String area, String address, String user_id, List<OrderItem> orderitems) {
        this.token = token;
        this.fname = fname;
        this.lname = lname;
        this.mobile = mobile;
        this.area = area;
        this.address = address;
        this.user_id = user_id;
        this.orderitems = orderitems;
    }

    public PlaceOrder() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderItem> getOrderitems() {
        return orderitems;
    }

    public void setOrderitems(List<OrderItem> orderitems) {
        this.orderitems = orderitems;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
