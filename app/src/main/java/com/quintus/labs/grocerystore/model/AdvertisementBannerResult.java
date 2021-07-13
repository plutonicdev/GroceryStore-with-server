package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class AdvertisementBannerResult {
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
    @SerializedName("title")
  
    private String title;
    @SerializedName("description")
  
    private String description;
    @SerializedName("image")
  
    private String image;
    @SerializedName("image_alt")
  
    private String imageAlt;
    @SerializedName("action_type")
  
    private String actionType;
    @SerializedName("action_url")
  
    private String actionUrl;
    @SerializedName("is_active")
  
    private Boolean isActive;
    @SerializedName("store")
  
    private Integer store;
    @SerializedName("action_product")
  
    private Object actionProduct;
    @SerializedName("action_category")
  
    private Object actionCategory;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageAlt() {
        return imageAlt;
    }

    public void setImageAlt(String imageAlt) {
        this.imageAlt = imageAlt;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public Object getActionProduct() {
        return actionProduct;
    }

    public void setActionProduct(Object actionProduct) {
        this.actionProduct = actionProduct;
    }

    public Object getActionCategory() {
        return actionCategory;
    }

    public void setActionCategory(Object actionCategory) {
        this.actionCategory = actionCategory;
    }

}
