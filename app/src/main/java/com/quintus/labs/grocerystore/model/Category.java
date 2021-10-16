package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Grocery App
 * https://github.com/quintuslabs/GroceryStore
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */
public class Category {
    @SerializedName("id")
   
    private Integer id;
    @SerializedName("name")
   
    private String name;
    @SerializedName("slug")
   
    private String slug;
    @SerializedName("description")
   
    private String description;
    @SerializedName("parent")
   
    private Object parent;
    @SerializedName("image")
   
    private String image;
    @SerializedName("image_alt")
   
    private String imageAlt;
    @SerializedName("created_date")
   
    private String createdDate;
    @SerializedName("status")
   
    private Boolean status;
    @SerializedName("is_favorite")
   
    private Boolean isFavorite;
    @SerializedName("items")
   
    private Integer items;
    @SerializedName("sub_category")
   
    private List<SubCategory> subCategory = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public Integer getItems() {
        return items;
    }

    public void setItems(Integer items) {
        this.items = items;
    }

    public List<SubCategory> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(List<SubCategory> subCategory) {
        this.subCategory = subCategory;
    }

}
