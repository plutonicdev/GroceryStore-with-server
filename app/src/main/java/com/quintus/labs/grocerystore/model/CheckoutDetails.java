package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class CheckoutDetails {

    @SerializedName("payment_id")
  
    private String paymentId;
    @SerializedName("payment_status")
  
    private String paymentStatus;
    @SerializedName("address_id")
  
    private String addressId;
    @SerializedName("price")
  
    private String price;
    @SerializedName("voucher")
  
    private String voucher;
    @SerializedName("voucher_price")
  
    private String voucherPrice;
    @SerializedName("loyalty_points")
  
    private String loyaltyPoints;

    public CheckoutDetails(String paymentId, String paymentStatus, String addressId, String price, String voucher, String voucherPrice, String loyaltyPoints) {
        this.paymentId = paymentId;
        this.paymentStatus = paymentStatus;
        this.addressId = addressId;
        this.price = price;
        this.voucher = voucher;
        this.voucherPrice = voucherPrice;
        this.loyaltyPoints = loyaltyPoints;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public String getVoucherPrice() {
        return voucherPrice;
    }

    public void setVoucherPrice(String voucherPrice) {
        this.voucherPrice = voucherPrice;
    }

    public String getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(String loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }
}
