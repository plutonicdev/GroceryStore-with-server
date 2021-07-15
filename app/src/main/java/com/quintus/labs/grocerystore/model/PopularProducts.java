package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PopularProducts {

  
    @SerializedName("total_pages")
    
    private Integer totalPages;
    @SerializedName("total_items")
    
    private Integer totalItems;
    @SerializedName("page_size")
    
    private Object pageSize;
    @SerializedName("results")
    
    private List<PopularProductsResult> results = null;
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

    public List<PopularProductsResult> getResults() {
        return results;
    }

    public void setResults(List<PopularProductsResult> results) {
        this.results = results;
    }


}
