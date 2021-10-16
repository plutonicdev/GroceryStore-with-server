package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class ImageData {
    @SerializedName("image")

    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
