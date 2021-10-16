package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class Currency {
    @SerializedName("id")
  
    private Integer id;
    @SerializedName("title")
  
    private String title;
    @SerializedName("code")
  
    private String code;
    @SerializedName("symbol")
  
    private String symbol;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
