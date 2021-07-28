package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddAddressListResponse {

    @SerializedName("id")
  
    private Integer id;
    @SerializedName("country")
  
    private Country country;
    @SerializedName("state")
  
    private State state;
    @SerializedName("city")
  
    private City city;
    @SerializedName("pin")
  
    private Pin pin;
    @SerializedName("name")
  
    private String name;
    @SerializedName("phone")
  
    private String phone;
    @SerializedName("email")
  
    private String email;
    @SerializedName("flat_no")
  
    private Object flatNo;
    @SerializedName("room_no")
  
    private Object roomNo;
    @SerializedName("alt_phone")
  
    private Object altPhone;
    @SerializedName("line")
  
    private Object line;
    @SerializedName("land_mark")
  
    private String landMark;
    @SerializedName("address")
  
    private String address;
    @SerializedName("address_type")
  
    private String addressType;
    @SerializedName("is_default")
  
    private Boolean isDefault;
    @SerializedName("user")
  
    private Integer user;
    @SerializedName("society")
  
    private Object society;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Pin getPin() {
        return pin;
    }

    public void setPin(Pin pin) {
        this.pin = pin;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(Object flatNo) {
        this.flatNo = flatNo;
    }

    public Object getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(Object roomNo) {
        this.roomNo = roomNo;
    }

    public Object getAltPhone() {
        return altPhone;
    }

    public void setAltPhone(Object altPhone) {
        this.altPhone = altPhone;
    }

    public Object getLine() {
        return line;
    }

    public void setLine(Object line) {
        this.line = line;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Object getSociety() {
        return society;
    }

    public void setSociety(Object society) {
        this.society = society;
    }
}
