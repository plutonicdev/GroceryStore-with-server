package com.quintus.labs.grocerystore.payment.instamojo.model;

import com.google.gson.annotations.SerializedName;

public class GatewayOrderStatus {

    @SerializedName("status")
    private String status;

    @SerializedName("amount")
    private String amount;

    @SerializedName("payment_id")
    private String paymentID;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }
}
