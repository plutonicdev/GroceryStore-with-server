package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class Total {

    @SerializedName("total")
    private String total;

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


