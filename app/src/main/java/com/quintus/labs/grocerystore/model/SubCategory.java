package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class SubCategory {


    @SerializedName("id")
  
    private Integer id;
    @SerializedName("image")
  
    private String image;
    @SerializedName("created_date")
  
    private String createdDate;
    @SerializedName("updated_date")
  
    private String updatedDate;
    @SerializedName("created_by")
  
    private Integer createdBy;
    @SerializedName("updated_by")
  
    private Integer updatedBy;
    @SerializedName("name")
  
    private String name;
    @SerializedName("slug")
  
    private String slug;
    @SerializedName("description")
  
    private String description;
    @SerializedName("image_alt")
  
    private String imageAlt;
    @SerializedName("status")
  
    private Boolean status;
    @SerializedName("store")
  
    private Integer store;
    @SerializedName("parent")
  
    private Integer parent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageAlt() {
        return imageAlt;
    }

    public void setImageAlt(String imageAlt) {
        this.imageAlt = imageAlt;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

}