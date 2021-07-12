package com.quintus.labs.grocerystore.payment.instamojo.model;

import com.google.gson.annotations.SerializedName;

public class GetOrderIDRequest {

    @SerializedName("env")
    private String env;

    @SerializedName("buyer_name")
    private String buyerName;

    @SerializedName("buyer_email")
    private String buyerEmail;

    @SerializedName("buyer_phone")
    private String buyerPhone;

    @SerializedName("amount")
    private String amount;

    @SerializedName("description")
    private String description;

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
