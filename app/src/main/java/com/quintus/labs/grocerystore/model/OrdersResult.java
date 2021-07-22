package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrdersResult {
    @SerializedName("id")
  
    private Integer id;
    @SerializedName("delivery_date")
  
    private String deliveryDate;
    @SerializedName("created_date")
  
    private String createdDate;
    @SerializedName("base_product")
  
    private BaseProduct baseProduct;
    @SerializedName("updated_date")
  
    private String updatedDate;
    @SerializedName("created_by")
  
    private Integer createdBy;
    @SerializedName("updated_by")
  
    private Integer updatedBy;
    @SerializedName("order_no")
  
    private String orderNo;
    @SerializedName("total")
  
    private String total;
    @SerializedName("total_discount")
  
    private String totalDiscount;
    @SerializedName("status")
  
    private String status;
    @SerializedName("payment_status")
  
    private String paymentStatus;
    @SerializedName("voucher_price")
  
    private Object voucherPrice;
    @SerializedName("loyalty_points")
  
    private Integer loyaltyPoints;
    @SerializedName("instruction")
  
    private String instruction;
    @SerializedName("user")
  
    private Integer user;
    @SerializedName("address")
  
    private Integer address;
    @SerializedName("payment")
  
    private Integer payment;
    @SerializedName("voucher")
  
    private Object voucher;
    @SerializedName("delivery_charges")
  
    private List<Object> deliveryCharges = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public BaseProduct getBaseProduct() {
        return baseProduct;
    }

    public void setBaseProduct(BaseProduct baseProduct) {
        this.baseProduct = baseProduct;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(String totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Object getVoucherPrice() {
        return voucherPrice;
    }

    public void setVoucherPrice(Object voucherPrice) {
        this.voucherPrice = voucherPrice;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public Object getVoucher() {
        return voucher;
    }

    public void setVoucher(Object voucher) {
        this.voucher = voucher;
    }

    public List<Object> getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(List<Object> deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

}
