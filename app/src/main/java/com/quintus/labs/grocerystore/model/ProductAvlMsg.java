package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class ProductAvlMsg {

    @SerializedName("msg")

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
