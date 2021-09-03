package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class Voucher {
    @SerializedName("price")
    private String price;

    @SerializedName("voucher_code")
    private String voucher_code;

    public Voucher(String price, String voucher_code) {
        this.price = price;
        this.voucher_code = voucher_code;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVoucher_code() {
        return voucher_code;
    }

    public void setVoucher_code(String voucher_code) {
        this.voucher_code = voucher_code;
    }
}
