package com.quintus.labs.grocerystore.model;

public class OrderItem {
    String id;
    String orderid;
    String itemname;
    String itemquantity;
    String attribute;
    String currency;
    String itemImage;
    String itemprice;
    String itemtotal;
    String token;
    String order_id;

    public OrderItem() {
    }

    public OrderItem(String itemname, String itemquantity, String attribute, String currency, String itemImage, String itemprice, String itemtotal) {
        this.itemname = itemname;
        this.itemquantity = itemquantity;
        this.attribute = attribute;
        this.currency = currency;
        this.itemImage = itemImage;
        this.itemprice = itemprice;
        this.itemtotal = itemtotal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemquantity() {
        return itemquantity;
    }

    public void setItemquantity(String itemquantity) {
        this.itemquantity = itemquantity;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getitemImage() {
        return itemImage;
    }

    public void setitemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemprice() {
        return itemprice;
    }

    public void setItemprice(String itemprice) {
        this.itemprice = itemprice;
    }

    public String getItemtotal() {
        return itemtotal;
    }

    public void setItemtotal(String itemtotal) {
        this.itemtotal = itemtotal;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
