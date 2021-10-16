package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class Total {

    @SerializedName("total")
    private String total;


 @SerializedName("payment_mode")
    private String paymentMode;

    public Total(String total, String paymentMode) {
        this.total = total;
        this.paymentMode = paymentMode;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Total(String total) {
        this.total = total;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}


