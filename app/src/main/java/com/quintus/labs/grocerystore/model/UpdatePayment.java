package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class UpdatePayment {
    @SerializedName("id")
  
    private Integer id;
    @SerializedName("user")
  
    private Integer user;
    @SerializedName("total")
  
    private Double total;
    @SerializedName("status")
  
    private String status;
    @SerializedName("payment_ref_no")
  
    private String paymentRefNo;
    @SerializedName("txnid")
  
    private String txnid;
    @SerializedName("payment_mode")
  
    private String paymentMode;
    @SerializedName("notification_status")
  
    private String notificationStatus;

    public UpdatePayment() {
    }

    public UpdatePayment(String status, String txnid, String paymentMode) {
        this.status = status;
        this.txnid = txnid;
        this.paymentMode = paymentMode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentRefNo() {
        return paymentRefNo;
    }

    public void setPaymentRefNo(String paymentRefNo) {
        this.paymentRefNo = paymentRefNo;
    }

    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }
}
