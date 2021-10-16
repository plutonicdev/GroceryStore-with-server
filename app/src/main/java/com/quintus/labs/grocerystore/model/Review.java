package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("total_rating")
   
    private Integer totalRating;
    @SerializedName("no_rating")
   
    private Integer noRating;
    @SerializedName("no_review")
   
    private Integer noReview;
    @SerializedName("star")
   
    private Star star;

    public Integer getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(Integer totalRating) {
        this.totalRating = totalRating;
    }

    public Integer getNoRating() {
        return noRating;
    }

    public void setNoRating(Integer noRating) {
        this.noRating = noRating;
    }

    public Integer getNoReview() {
        return noReview;
    }

    public void setNoReview(Integer noReview) {
        this.noReview = noReview;
    }

    public Star getStar() {
        return star;
    }

    public void setStar(Star star) {
        this.star = star;
    }

}
