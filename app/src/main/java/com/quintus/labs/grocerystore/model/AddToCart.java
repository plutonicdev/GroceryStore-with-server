package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class AddToCart {

    @SerializedName("id")
    private Integer id;
    @SerializedName("created_date")
   
    private String createdDate;
    @SerializedName("updated_date")
   
    private String updatedDate;
    @SerializedName("created_by")
   
    private Integer createdBy;
    @SerializedName("updated_by")
   
    private Integer updatedBy;
    @SerializedName("count")
   
    private Integer count;
    @SerializedName("subtotal")
   
    private String subtotal;
    @SerializedName("including_tax")
   
    private String includingTax;
    @SerializedName("save_for_later")
   
    private Boolean saveForLater;
    @SerializedName("store")
   
    private Integer store;
    @SerializedName("user")
   
    private Integer user;
    @SerializedName("product")
   
    private Integer product;
    @SerializedName("variation")
   
    private String variation;

    @SerializedName("add_product")
    private boolean addProduct;

    public AddToCart() {
    }

    public AddToCart(Integer count, String subtotal, Integer product, String variation,boolean addProduct) {
        this.count = count;
        this.subtotal = subtotal;
        this.product = product;
        this.variation = variation;
        this.addProduct = addProduct;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
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

    public String getIncludingTax() {
        return includingTax;
    }

    public void setIncludingTax(String includingTax) {
        this.includingTax = includingTax;
    }

    public Boolean getSaveForLater() {
        return saveForLater;
    }

    public void setSaveForLater(Boolean saveForLater) {
        this.saveForLater = saveForLater;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getProduct() {
        return product;
    }

    public void setProduct(Integer product) {
        this.product = product;
    }

    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }

    public boolean isAddProduct() {
        return addProduct;
    }

    public void setAddProduct(boolean addProduct) {
        this.addProduct = addProduct;
    }
}
