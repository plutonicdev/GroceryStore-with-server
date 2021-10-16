package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Country {
    @SerializedName("id")
    private Integer id;
    @SerializedName("country")
    private String country;


    public Country(Integer id, String country) {
        this.id = id;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}

