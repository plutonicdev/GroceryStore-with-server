package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class ServerResp {
    @SerializedName("id")
  
    private Integer id;
    @SerializedName("total")
  
    private Double total;
    @SerializedName("created_date")
  
    private String createdDate;
    @SerializedName("updated_date")
  
    private String updatedDate;
    @SerializedName("created_by")
  
    private Integer createdBy;
    @SerializedName("updated_by")
  
    private Integer updatedBy;
    @SerializedName("tax")
  
    private Double tax;
    @SerializedName("status")
  
    private String status;
    @SerializedName("payment_ref_no")
  
    private String paymentRefNo;
    @SerializedName("note")
  
    private Object note;
    @SerializedName("txnid")
  
    private Object txnid;
    @SerializedName("payment_mode")
  
    private String paymentMode;
    @SerializedName("user")
  
    private Integer user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
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

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
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

    public Object getNote() {
        return note;
    }

    public void setNote(Object note) {
        this.note = note;
    }

    public Object getTxnid() {
        return txnid;
    }

    public void setTxnid(Object txnid) {
        this.txnid = txnid;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }
    
}
