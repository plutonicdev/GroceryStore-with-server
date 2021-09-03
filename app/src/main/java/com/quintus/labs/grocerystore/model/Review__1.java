package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class Review__1 {
    @SerializedName("id")
  
    private String id;
    @SerializedName("rating")
  
    private Integer rating;
    @SerializedName("review")
  
    private String review;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

}
