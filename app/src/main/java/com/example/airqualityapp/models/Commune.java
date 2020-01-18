package com.example.airqualityapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Commune {

    @SerializedName("communeName")
    @Expose
    private String communeName;
    @SerializedName("districtName")
    @Expose
    private String districtName;
    @SerializedName("provinceName")
    @Expose
    private String provinceName;

    public String getCommuneName() {
        return communeName;
    }

    public void setCommuneName(String communeName) {
        this.communeName = communeName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

}