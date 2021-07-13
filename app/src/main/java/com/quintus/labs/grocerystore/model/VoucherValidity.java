package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class VoucherValidity {

    @SerializedName("price")
   
    private String price;
    @SerializedName("voucher_code")
   
    private String voucherCode;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }
}
