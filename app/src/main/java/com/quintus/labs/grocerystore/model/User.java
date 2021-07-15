package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

/**
 * Grocery App
 * https://github.com/quintuslabs/GroceryStore
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */
public class User {
    String id;
    String fname;
    String name;
    String phone;
    String fcm_id;
    String otp;
    String lname;
    String email;
    String password;
    String area;
    String address;
    String address_type;
    String country;
    String state;
    String city;
    String zip;
    String token;
    String reset_code;
    String firebase_token;

    boolean phone_verified;

    public User(String fcm_id) {
        this.fcm_id = fcm_id;
    }

    public User() {
    }

    public User(String id,String name, String phone, String email, String address, String address_type, String country, String state, String city, String zip, String token) {
        this.id= id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.address_type = address_type;
        this.country = country;
        this.state = state;
        this.city = city;
        this.zip = zip;
        this.token = token;
    }

    public User(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public User(String name, String email, String phone, String password, String firebase_token) {
        this.name = name;
        this.email = email;
        // this.lname = lname;
        this.phone = phone;
        this.password = password;
        this.firebase_token = firebase_token;
    }

    public User(String phone, String password, String firebase_token) {
        this.phone = phone;
        this.password = password;
        this.firebase_token = firebase_token;
    }

    public User(String id, String fname, String phone, String email, String address, String state, String city, String zip, String token) {
        this.id = id;
        this.fname = fname;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.state = state;
        this.city = city;
        this.zip = zip;
        this.token = token;
    }

    public String getAddress_type() {
        return address_type;
    }

    public void setAddress_type(String address_type) {
        this.address_type = address_type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getFcm_id() {
        return fcm_id;
    }

    public void setFcm_id(String fcm_id) {
        this.fcm_id = fcm_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        return phone;
    }

    public void setMobile(String phone) {
        this.phone = phone;
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

    public boolean isPhone_verified() {
        return phone_verified;
    }

    public void setPhone_verified(boolean phone_verified) {
        this.phone_verified = phone_verified;
    }
}
