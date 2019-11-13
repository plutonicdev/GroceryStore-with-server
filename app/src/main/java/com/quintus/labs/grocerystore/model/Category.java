package com.quintus.labs.grocerystore.model;

/**
 * Grocery App
 * https://github.com/quintuslabs/GroceryStore
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */
public class Category {
    String id;
    String categry;
    String cateimg;
    String token;

    public Category(String id, String categry, String cateimg) {
        this.id = id;
        this.categry = categry;
        this.cateimg = cateimg;
    }

    public Category(String categry, String token) {
        this.categry = categry;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategry() {
        return categry;
    }

    public void setCategry(String categry) {
        this.categry = categry;
    }

    public String getCateimg() {
        return cateimg;
    }

    public void setCateimg(String cateimg) {
        this.cateimg = cateimg;
    }
}
