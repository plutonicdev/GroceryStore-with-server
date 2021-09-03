package com.quintus.labs.grocerystore.model;

import android.media.Image;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PopularProductsResult {
    @SerializedName("id")
   
    private Integer id;
    @SerializedName("images")
   
    private List<ImageData> images = null;
    @SerializedName("variations")
   
    private Object variations;
    @SerializedName("currency")
   
    private Currency currency;
    @SerializedName("discount_percentage")
   
    private Double discountPercentage;
    @SerializedName("user_is_favorite")
   
    private Boolean userIsFavorite;
    @SerializedName("review")
   
    private Review review;
    @SerializedName("product_avl_msg")
   
    private ProductAvlMsg productAvlMsg;
    @SerializedName("created_date")
   
    private String createdDate;
    @SerializedName("updated_date")
   
    private String updatedDate;
    @SerializedName("created_by")
   
    private Integer createdBy;
    @SerializedName("updated_by")
   
    private Integer updatedBy;
    @SerializedName("out_of_stock_limit")
   
    private Integer outOfStockLimit;
    @SerializedName("min_order_limit")
   
    private Integer minOrderLimit;
    @SerializedName("max_order_limit")
   
    private Integer maxOrderLimit;
    @SerializedName("mrp")
   
    private String mrp;
    @SerializedName("price")
   
    private String price;
    @SerializedName("available_quantity")
   
    private Integer availableQuantity;
    @SerializedName("status")
   
    private Boolean status;
    @SerializedName("code")
   
    private Integer code;
    @SerializedName("name")
   
    private String name;
    @SerializedName("slug")
   
    private String slug;
    @SerializedName("description")
   
    private String description;
    @SerializedName("charge_taxes")
   
    private Boolean chargeTaxes;
    @SerializedName("published")
   
    private Boolean published;
    @SerializedName("is_favorite")
   
    private Boolean isFavorite;
    @SerializedName("model")
   
    private String model;
    @SerializedName("type")
   
    private String type;
    @SerializedName("tax_percentage")
   
    private Double taxPercentage;
    @SerializedName("show_delivery")
   
    private Boolean showDelivery;
    @SerializedName("delivery_charge")
   
    private String deliveryCharge;
    @SerializedName("is_public")
   
    private Boolean isPublic;
    @SerializedName("has_variation")
   
    private Boolean hasVariation;
    @SerializedName("moq_bulk")
   
    private Integer moqBulk;
    @SerializedName("discount_type")
   
    private String discountType;
    @SerializedName("discount_amount")
   
    private String discountAmount;
    @SerializedName("discount")
   
    private String discount;
    @SerializedName("weight")
   
    private String weight;
    @SerializedName("height")
   
    private String height;
    @SerializedName("width")
   
    private String width;
    @SerializedName("length")
   
    private String length;
    @SerializedName("store")
   
    private Integer store;
    @SerializedName("category")
   
    private Integer category;
    @SerializedName("brand")
   
    private Integer brand;
    @SerializedName("weight_uom")
   
    private Integer weightUom;
    @SerializedName("height_uom")
   
    private Object heightUom;
    @SerializedName("width_uom")
   
    private Object widthUom;

    @SerializedName("length_uom")
    private Object lengthUom;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ImageData> getImages() {
        return images;
    }

    public void setImages(List<ImageData> images) {
        this.images = images;
    }

    public Object getVariations() {
        return variations;
    }

    public void setVariations(Object variations) {
        this.variations = variations;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Boolean getUserIsFavorite() {
        return userIsFavorite;
    }

    public void setUserIsFavorite(Boolean userIsFavorite) {
        this.userIsFavorite = userIsFavorite;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public ProductAvlMsg getProductAvlMsg() {
        return productAvlMsg;
    }

    public void setProductAvlMsg(ProductAvlMsg productAvlMsg) {
        this.productAvlMsg = productAvlMsg;
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

    public Integer getOutOfStockLimit() {
        return outOfStockLimit;
    }

    public void setOutOfStockLimit(Integer outOfStockLimit) {
        this.outOfStockLimit = outOfStockLimit;
    }

    public Integer getMinOrderLimit() {
        return minOrderLimit;
    }

    public void setMinOrderLimit(Integer minOrderLimit) {
        this.minOrderLimit = minOrderLimit;
    }

    public Integer getMaxOrderLimit() {
        return maxOrderLimit;
    }

    public void setMaxOrderLimit(Integer maxOrderLimit) {
        this.maxOrderLimit = maxOrderLimit;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getChargeTaxes() {
        return chargeTaxes;
    }

    public void setChargeTaxes(Boolean chargeTaxes) {
        this.chargeTaxes = chargeTaxes;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(Double taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public Boolean getShowDelivery() {
        return showDelivery;
    }

    public void setShowDelivery(Boolean showDelivery) {
        this.showDelivery = showDelivery;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Boolean getHasVariation() {
        return hasVariation;
    }

    public void setHasVariation(Boolean hasVariation) {
        this.hasVariation = hasVariation;
    }

    public Integer getMoqBulk() {
        return moqBulk;
    }

    public void setMoqBulk(Integer moqBulk) {
        this.moqBulk = moqBulk;
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

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getBrand() {
        return brand;
    }

    public void setBrand(Integer brand) {
        this.brand = brand;
    }

    public Integer getWeightUom() {
        return weightUom;
    }

    public void setWeightUom(Integer weightUom) {
        this.weightUom = weightUom;
    }

    public Object getHeightUom() {
        return heightUom;
    }

    public void setHeightUom(Object heightUom) {
        this.heightUom = heightUom;
    }

    public Object getWidthUom() {
        return widthUom;
    }

    public void setWidthUom(Object widthUom) {
        this.widthUom = widthUom;
    }

    public Object getLengthUom() {
        return lengthUom;
    }

    public void setLengthUom(Object lengthUom) {
        this.lengthUom = lengthUom;
    }

}
