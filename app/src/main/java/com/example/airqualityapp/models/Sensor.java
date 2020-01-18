package com.example.airqualityapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Sensor {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("stationId")
    @Expose
    private Integer stationId;
    @SerializedName("param")
    @Expose
    private Param param;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Param getParam() {
        return param;
    }

    public void setParam(Param param) {
        this.param = param;
    }

}

class Param {

    @SerializedName("paramName")
    @Expose
    private String paramName;
    @SerializedName("paramFormula")
    @Expose
    private String paramFormula;
    @SerializedName("paramCode")
    @Expose
    private String paramCode;
    @SerializedName("idParam")
    @Expose
    private Integer idParam;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamFormula() {
        return paramFormula;
    }

    public void setParamFormula(String paramFormula) {
        this.paramFormula = paramFormula;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public Integer getIdParam() {
        return idParam;
    }

    public void setIdParam(Integer idParam) {
        this.idParam = idParam;
    }

}