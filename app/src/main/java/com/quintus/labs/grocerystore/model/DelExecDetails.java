package com.quintus.labs.grocerystore.model;

import com.google.gson.annotations.SerializedName;

public class DelExecDetails {

    @SerializedName("delivered_date")
   
    private Object deliveredDate;
    @SerializedName("delivered_time")
   
    private Object deliveredTime;
    @SerializedName("executive_name")
   
    private Object executiveName;
    @SerializedName("executive_phone")
   
    private Object executivePhone;

    public Object getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(Object deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public Object getDeliveredTime() {
        return deliveredTime;
    }

    public void setDeliveredTime(Object deliveredTime) {
        this.deliveredTime = deliveredTime;
    }

    public Object getExecutiveName() {
        return executiveName;
    }

    public void setExecutiveName(Object executiveName) {
        this.executiveName = executiveName;
    }

    public Object getExecutivePhone() {
        return executivePhone;
    }

    public void setExecutivePhone(Object executivePhone) {
        this.executivePhone = executivePhone;
    }
}
