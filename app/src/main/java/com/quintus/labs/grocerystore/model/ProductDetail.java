package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class ProductDetail {
    @SerializedName("id")
  
    private Integer id;
    @SerializedName("product")
  
    private ProductData product;
    @SerializedName("variation")
  
    private Object variation;
    @SerializedName("count")
  
    private Integer count;
    @SerializedName("subtotal")
  
    private String subtotal;
    @SerializedName("save_for_later")
  
    private Boolean saveForLater;

    public ProductDetail(Integer id, Integer count) {
        this.id = id;
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductData getProduct() {
        return product;
    }

    public void setProduct(ProductData product) {
        this.product = product;
    }

    public Object getVariation() {
        return variation;
    }

    public void setVariation(Object variation) {
        this.variation = variation;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public Boolean getSaveForLater() {
        return saveForLater;
    }

    public void setSaveForLater(Boolean saveForLater) {
        this.saveForLater = saveForLater;
    }

}
