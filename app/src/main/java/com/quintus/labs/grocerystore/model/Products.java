package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class Products {
    @SerializedName("product")
    private Integer product;


    public Integer getProduct() {
        return product;
    }

    public Products(Integer product) {
        this.product = product;
    }

    public void setProduct(Integer product) {
        this.product = product;
    }
}
