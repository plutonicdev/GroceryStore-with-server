package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class Messages {

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
