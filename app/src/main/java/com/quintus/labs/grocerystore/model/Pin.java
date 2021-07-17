package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class Pin {
    @SerializedName("id")
    private Integer id;
    @SerializedName("area")
    private String area;
    @SerializedName("pin_code")
    private String pinCode;
    @SerializedName("city")
    private Integer city;


    public Pin(Integer id, String area, String pinCode, Integer city) {
        this.id = id;
        this.area = area;
        this.pinCode = pinCode;
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

}

