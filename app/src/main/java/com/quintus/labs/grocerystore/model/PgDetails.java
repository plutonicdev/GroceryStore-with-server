package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class PgDetails {
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
    @SerializedName("name")
  
    private String name;
    @SerializedName("default")
  
    private Boolean _default;
    @SerializedName("settings")
  
    private Object settings;
    @SerializedName("key")
  
    private String key;
    @SerializedName("salt")
  
    private String salt;
    @SerializedName("mode")
  
    private String mode;
    @SerializedName("store")
  
    private Integer store;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDefault() {
        return _default;
    }

    public void setDefault(Boolean _default) {
        this._default = _default;
    }

    public Object getSettings() {
        return settings;
    }

    public void setSettings(Object settings) {
        this.settings = settings;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

}


