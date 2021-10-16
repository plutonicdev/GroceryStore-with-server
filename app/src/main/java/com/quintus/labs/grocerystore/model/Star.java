package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class Star {
    @SerializedName("one_star")
   
    private Integer oneStar;
    @SerializedName("two_star")
   
    private Integer twoStar;
    @SerializedName("three_star")
   
    private Integer threeStar;
    @SerializedName("four_star")
   
    private Integer fourStar;
    @SerializedName("five_star")
   
    private Integer fiveStar;

    public Integer getOneStar() {
        return oneStar;
    }

    public void setOneStar(Integer oneStar) {
        this.oneStar = oneStar;
    }

    public Integer getTwoStar() {
        return twoStar;
    }

    public void setTwoStar(Integer twoStar) {
        this.twoStar = twoStar;
    }

    public Integer getThreeStar() {
        return threeStar;
    }

    public void setThreeStar(Integer threeStar) {
        this.threeStar = threeStar;
    }

    public Integer getFourStar() {
        return fourStar;
    }

    public void setFourStar(Integer fourStar) {
        this.fourStar = fourStar;
    }

    public Integer getFiveStar() {
        return fiveStar;
    }

    public void setFiveStar(Integer fiveStar) {
        this.fiveStar = fiveStar;
    }

}
