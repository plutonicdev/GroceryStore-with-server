package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class MessageResponse {
    @SerializedName("message")

    private Messages message;

    public Messages getMessage() {
        return message;
    }

    public void setMessage(Messages message) {
        this.message = message;
    }
}
