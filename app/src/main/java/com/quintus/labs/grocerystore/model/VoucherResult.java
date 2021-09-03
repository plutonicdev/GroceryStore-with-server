package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class VoucherResult {

    @SerializedName("id")
   
    private Integer id;
    @SerializedName("end_date")
   
    private String endDate;
    @SerializedName("voucher_status")
   
    private String voucherStatus;
    @SerializedName("created_date")
   
    private String createdDate;
    @SerializedName("updated_date")
   
    private String updatedDate;
    @SerializedName("created_by")
   
    private Integer createdBy;
    @SerializedName("updated_by")
   
    private Integer updatedBy;
    @SerializedName("discount_category")
   
    private String discountCategory;
    @SerializedName("discount_type")
   
    private String discountType;
    @SerializedName("discount_amount")
   
    private String discountAmount;
    @SerializedName("usage_limit")
   
    private Object usageLimit;
    @SerializedName("min_spent")
   
    private String minSpent;
    @SerializedName("min_checkout_items_quantity")
   
    private Object minCheckoutItemsQuantity;
    @SerializedName("customers")
   
    private String customers;
    @SerializedName("products")
   
    private String products;
    @SerializedName("categories")
   
    private String categories;
    @SerializedName("stores")
   
    private String stores;
    @SerializedName("start_date")
   
    private String startDate;
    @SerializedName("is_active")
   
    private Boolean isActive;
    @SerializedName("name")
   
    private String name;
    @SerializedName("description")
   
    private String description;
    @SerializedName("code")
   
    private String code;
    @SerializedName("used")
   
    private Integer used;
    @SerializedName("store")
   
    private Integer store;
    @SerializedName("cart_price")
   
    private Integer cartPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getVoucherStatus() {
        return voucherStatus;
    }

    public void setVoucherStatus(String voucherStatus) {
        this.voucherStatus = voucherStatus;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getDiscountCategory() {
        return discountCategory;
    }

    public void setDiscountCategory(String discountCategory) {
        this.discountCategory = discountCategory;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Object getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(Object usageLimit) {
        this.usageLimit = usageLimit;
    }

    public String getMinSpent() {
        return minSpent;
    }

    public void setMinSpent(String minSpent) {
        this.minSpent = minSpent;
    }

    public Object getMinCheckoutItemsQuantity() {
        return minCheckoutItemsQuantity;
    }

    public void setMinCheckoutItemsQuantity(Object minCheckoutItemsQuantity) {
        this.minCheckoutItemsQuantity = minCheckoutItemsQuantity;
    }

    public String getCustomers() {
        return customers;
    }

    public void setCustomers(String customers) {
        this.customers = customers;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getStores() {
        return stores;
    }

    public void setStores(String stores) {
        this.stores = stores;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public Integer getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(Integer cartPrice) {
        this.cartPrice = cartPrice;
    }
}
