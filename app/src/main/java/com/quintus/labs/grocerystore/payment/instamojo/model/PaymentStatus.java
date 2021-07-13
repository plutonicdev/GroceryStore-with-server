package com.quintus.labs.grocerystore.payment.instamojo.model;

import com.google.gson.annotations.SerializedName;

public class PaymentStatus {
    @SerializedName("order_id")
    private String orderID;
    @SerializedName("transaction_id")
    private String transactionID;
    @SerializedName("payment_id")
    private String paymentID;
    @SerializedName("payment_status")
    private String paymentStatus;

    public String getOrderID() {
        return orderID;
    }

    public PaymentStatus setOrderID(String orderID) {
        this.orderID = orderID;
        return this;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public PaymentStatus setTransactionID(String transactionID) {
        this.transactionID = transactionID;
        return this;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public PaymentStatus setPaymentID(String paymentID) {
        this.paymentID = paymentID;
        return this;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public PaymentStatus setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }
}
