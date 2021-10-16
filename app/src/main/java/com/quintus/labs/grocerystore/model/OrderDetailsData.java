package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class OrderDetailsData {
    @SerializedName("order_no")
   
    private String orderNo;
    @SerializedName("curr_status")
   
    private String currStatus;

    @SerializedName("payment_mode")
     private String paymentMode;

    @SerializedName("payment_status")
     private String paymentStatus;

    @SerializedName("created_date")
   
    private String createdDate;
    @SerializedName("total_amout")
   
    private Double totalAmout;
    @SerializedName("offer_amount")
   
    private Object offerAmount;
    @SerializedName("payable_amount")
   
    private Double payableAmount;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCurrStatus() {
        return currStatus;
    }

    public void setCurrStatus(String currStatus) {
        this.currStatus = currStatus;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Double getTotalAmout() {
        return totalAmout;
    }

    public void setTotalAmout(Double totalAmout) {
        this.totalAmout = totalAmout;
    }

    public Object getOfferAmount() {
        return offerAmount;
    }

    public void setOfferAmount(Object offerAmount) {
        this.offerAmount = offerAmount;
    }

    public Double getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(Double payableAmount) {
        this.payableAmount = payableAmount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
