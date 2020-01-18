package com.example.airqualityapp.models;

import com.example.airqualityapp.models.City;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Station {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("stationName")
    @Expose
    private String stationName;
    @SerializedName("gegrLat")
    @Expose
    private String gegrLat;
    @SerializedName("gegrLon")
    @Expose
    private String gegrLon;
    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("addressStreet")
    @Expose
    private Object addressStreet;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getGegrLat() {
        return gegrLat;
    }

    public void setGegrLat(String gegrLat) {
        this.gegrLat = gegrLat;
    }

    public String getGegrLon() {
        return gegrLon;
    }

    public void setGegrLon(String gegrLon) {
        this.gegrLon = gegrLon;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Object getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(Object addressStreet) {
        this.addressStreet = addressStreet;
    }

}