package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class AddAddressListResponse {

        @SerializedName("name")
        private String name;
        @SerializedName("phone")
        private String phone;
        @SerializedName("alt_phone")
        private String altPhone;
        @SerializedName("email")
        private String email;
        @SerializedName("line")
        private String line;
        @SerializedName("land_mark")
        private String landMark;
        @SerializedName("address")
        private String address;
        @SerializedName("address_type")
        private String addressType;
        @SerializedName("country")
        private Integer country;
        @SerializedName("city")
        private Integer city;
        @SerializedName("pin")
        private Integer pin;
        @SerializedName("state")
        private Integer state;
        @SerializedName("is_default")
        private Boolean isDefault;

    public AddAddressListResponse(String name, String phone, String email, String address, String addressType, Integer country, Integer city, Integer pin, Integer state, Boolean isDefault) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.addressType = addressType;
        this.country = country;
        this.city = city;
        this.pin = pin;
        this.state = state;
        this.isDefault = isDefault;
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

        public String getAltPhone() {
            return altPhone;
        }

        public void setAltPhone(String altPhone) {
            this.altPhone = altPhone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getLine() {
            return line;
        }

        public void setLine(String line) {
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

        public Integer getCountry() {
            return country;
        }

        public void setCountry(Integer country) {
            this.country = country;
        }

        public Integer getCity() {
            return city;
        }

        public void setCity(Integer city) {
            this.city = city;
        }

        public Integer getPin() {
            return pin;
        }

        public void setPin(Integer pin) {
            this.pin = pin;
        }

        public Integer getState() {
            return state;
        }

        public void setState(Integer state) {
            this.state = state;
        }

        public Boolean getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(Boolean isDefault) {
            this.isDefault = isDefault;
        }

    }
