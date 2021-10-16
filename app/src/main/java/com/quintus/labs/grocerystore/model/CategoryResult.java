package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResult {
//    @SerializedName("categories")
//    List<Category> categoryList;
//    @SerializedName("code")
//    int code;
//    @SerializedName("status")
//    String status;
//
//    public List<Category> getCategoryList() {
//        return categoryList;
//    }
//
//    public void setCategoryList(List<Category> categoryList) {
//        this.categoryList = categoryList;
//    }
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }

   
    @SerializedName("total_pages")
   
    private Integer totalPages;
    @SerializedName("total_items")
   
    private Integer totalItems;
    @SerializedName("page_size")
   
    private Object pageSize;
    @SerializedName("results")
   
    private List<Category> results = null;
    @SerializedName("extra_data")
   


    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public Object getPageSize() {
        return pageSize;
    }

    public void setPageSize(Object pageSize) {
        this.pageSize = pageSize;
    }

    public List<Category> getResults() {
        return results;
    }

    public void setResults(List<Category> results) {
        this.results = results;
    }



}
