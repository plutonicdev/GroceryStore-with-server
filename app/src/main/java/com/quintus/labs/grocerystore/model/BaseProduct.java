package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class BaseProduct {

    @SerializedName("product_id")
   
    private Integer productId;
    @SerializedName("product_name")
   
    private String productName;
    @SerializedName("product_image")
   
    private String productImage;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
