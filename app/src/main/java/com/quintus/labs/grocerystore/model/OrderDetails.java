package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetails {
    @SerializedName("order_details")
   
    private OrderDetailsData orderDetails;
    @SerializedName("del_exec_details")
   
    private DelExecDetails delExecDetails;
    @SerializedName("products")
   
    private List<ProductsData> products = null;
    @SerializedName("address_details")
   
    private AddressDetails addressDetails;

    public OrderDetailsData getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetailsData orderDetails) {
        this.orderDetails = orderDetails;
    }

    public DelExecDetails getDelExecDetails() {
        return delExecDetails;
    }

    public void setDelExecDetails(DelExecDetails delExecDetails) {
        this.delExecDetails = delExecDetails;
    }

    public List<ProductsData> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsData> products) {
        this.products = products;
    }

    public AddressDetails getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(AddressDetails addressDetails) {
        this.addressDetails = addressDetails;
    }
}
