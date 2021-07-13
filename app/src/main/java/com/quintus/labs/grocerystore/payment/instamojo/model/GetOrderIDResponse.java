package com.quintus.labs.grocerystore.payment.instamojo.model;

import com.google.gson.annotations.SerializedName;

public class GetOrderIDResponse {

    @SerializedName("order_id")
    private String orderID;
    private String environment;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }
}
