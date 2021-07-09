package com.quintus.labs.grocerystore.model;

/**
 * Grocery App
 * https://github.com/quintuslabs/GroceryStore
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */
public class User {
    String id;
    String fname;
    String lname;
    String mobile;
    String email;
    String password;
    String area;
    String address;
    String state;
    String city;
    String zip;
    String token;
    String reset_code;
    String firebase_token;


    public User() {
    }

    public User(String fname, String lname, String mobile, String password, String firebase_token) {
        this.fname = fname;
        this.lname = lname;
        this.mobile = mobile;
        this.password = password;
        this.firebase_token = firebase_token;
    }

    public User(String mobile, String password, String firebase_token) {
        this.mobile = mobile;
        this.password = password;
        this.firebase_token = firebase_token;
    }

    public User(String id, String fname, String mobile, String email, String address, String state, String city, String zip, String token) {
        this.id = id;
        this.fname = fname;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
        this.state = state;
        this.city = city;
        this.zip = zip;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getReset_code() {
        return reset_code;
    }

    public void setReset_code(String reset_code) {
        this.reset_code = reset_code;
    }

    public String getFirebase_token() {
        return firebase_token;
    }

    public void setFirebase_token(String firebase_token) {
        this.firebase_token = firebase_token;
    }
}
