package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class ProductsData {

    @SerializedName("id")
   
    private Integer id;
    @SerializedName("order")
   
    private Integer order;
    @SerializedName("user")
   
    private Integer user;
    @SerializedName("product")
   
    private Product__1 product;
    @SerializedName("extra_info")
   
    private Object extraInfo;
    @SerializedName("quantity")
   
    private Integer quantity;
    @SerializedName("price")
   
    private String price;
    @SerializedName("delivery_details")
   
    private DeliveryDetails deliveryDetails;
    @SerializedName("review")
   
    private Review__1 review;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Product__1 getProduct() {
        return product;
    }

    public void setProduct(Product__1 product) {
        this.product = product;
    }

    public Object getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(Object extraInfo) {
        this.extraInfo = extraInfo;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public DeliveryDetails getDeliveryDetails() {
        return deliveryDetails;
    }

    public void setDeliveryDetails(DeliveryDetails deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
    }

    public Review__1 getReview() {
        return review;
    }

    public void setReview(Review__1 review) {
        this.review = review;
    }

}
