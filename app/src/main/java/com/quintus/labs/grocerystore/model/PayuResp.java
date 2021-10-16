package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class PayuResp {
    
    @SerializedName("txnid")
  
    private String txnid;
    @SerializedName("amount")
  
    private String amount;
    @SerializedName("firstname")
  
    private String firstname;
    @SerializedName("email")
  
    private String email;
    @SerializedName("phone")
  
    private String phone;
    @SerializedName("productinfo")
  
    private String productinfo;
    @SerializedName("key")
  
    private String key;
    @SerializedName("hashh")
  
    private String hashh;
    @SerializedName("merchant_key")
  
    private String merchantKey;
    @SerializedName("surl")
  
    private String surl;
    @SerializedName("furl")
  
    private String furl;
    @SerializedName("hash_string")
  
    private String hashString;
    @SerializedName("service_provider")
  
    private String serviceProvider;
    @SerializedName("action")
  
    private String action;

    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProductinfo() {
        return productinfo;
    }

    public void setProductinfo(String productinfo) {
        this.productinfo = productinfo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHashh() {
        return hashh;
    }

    public void setHashh(String hashh) {
        this.hashh = hashh;
    }

    public String getMerchantKey() {
        return merchantKey;
    }

    public void setMerchantKey(String merchantKey) {
        this.merchantKey = merchantKey;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }

    public String getFurl() {
        return furl;
    }

    public void setFurl(String furl) {
        this.furl = furl;
    }

    public String getHashString() {
        return hashString;
    }

    public void setHashString(String hashString) {
        this.hashString = hashString;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}