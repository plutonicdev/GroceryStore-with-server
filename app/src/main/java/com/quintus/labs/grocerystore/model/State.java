package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class State {
    @SerializedName("id")
    private Integer id;
    @SerializedName("state")
    private String state;
    @SerializedName("country")
    private Integer country;


    public State(Integer id, String state, Integer country) {
        this.id = id;
        this.state = state;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

}
