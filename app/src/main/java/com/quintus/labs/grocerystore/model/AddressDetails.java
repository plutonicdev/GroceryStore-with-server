package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class AddressDetails {
    @SerializedName("user")
   
    private String user;
    @SerializedName("name")
   
    private String name;
    @SerializedName("phone")
   
    private String phone;
    @SerializedName("line")
   
    private Object line;
    @SerializedName("alt_phone")
   
    private Object altPhone;
    @SerializedName("land_mark")
   
    private String landMark;
    @SerializedName("address")
   
    private String address;
    @SerializedName("address_type")
   
    private String addressType;
    @SerializedName("city")
   
    private String city;
    @SerializedName("pin")
   
    private String pin;
    @SerializedName("state")
   
    private String state;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getLine() {
        return line;
    }

    public void setLine(Object line) {
        this.line = line;
    }

    public Object getAltPhone() {
        return altPhone;
    }

    public void setAltPhone(Object altPhone) {
        this.altPhone = altPhone;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
}
