package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class City {

    @SerializedName("id")
    private Integer id;
    @SerializedName("city")
    private String city;
    @SerializedName("state")
    private Integer state;

    public City(Integer id, String city, Integer state) {
        this.id = id;
        this.city = city;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
