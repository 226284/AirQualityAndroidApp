package com.example.airqualityapp.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AQIndex {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("stCalcDate")
    @Expose
    private String stCalcDate;
    @SerializedName("stIndexLevel")
    @Expose
    private StIndexLevel stIndexLevel;
    @SerializedName("stSourceDataDate")
    @Expose
    private String stSourceDataDate;
    @SerializedName("so2CalcDate")
    @Expose
    private String so2CalcDate;
    @SerializedName("so2IndexLevel")
    @Expose
    private Object so2IndexLevel;
    @SerializedName("so2SourceDataDate")
    @Expose
    private String so2SourceDataDate;
    @SerializedName("no2CalcDate")
    @Expose
    private String no2CalcDate;
    @SerializedName("no2IndexLevel")
    @Expose
    private Object no2IndexLevel;
    @SerializedName("no2SourceDataDate")
    @Expose
    private String no2SourceDataDate;
    @SerializedName("coCalcDate")
    @Expose
    private String coCalcDate;
    @SerializedName("coIndexLevel")
    @Expose
    private Object coIndexLevel;
    @SerializedName("coSourceDataDate")
    @Expose
    private String coSourceDataDate;
    @SerializedName("pm10CalcDate")
    @Expose
    private String pm10CalcDate;
    @SerializedName("pm10IndexLevel")
    @Expose
    private Object pm10IndexLevel;
    @SerializedName("pm10SourceDataDate")
    @Expose
    private String pm10SourceDataDate;
    @SerializedName("pm25CalcDate")
    @Expose
    private String pm25CalcDate;
    @SerializedName("pm25IndexLevel")
    @Expose
    private Object pm25IndexLevel;
    @SerializedName("pm25SourceDataDate")
    @Expose
    private Object pm25SourceDataDate;
    @SerializedName("o3CalcDate")
    @Expose
    private String o3CalcDate;
    @SerializedName("o3IndexLevel")
    @Expose
    private Object o3IndexLevel;
    @SerializedName("o3SourceDataDate")
    @Expose
    private String o3SourceDataDate;
    @SerializedName("c6h6CalcDate")
    @Expose
    private String c6h6CalcDate;
    @SerializedName("c6h6IndexLevel")
    @Expose
    private Object c6h6IndexLevel;
    @SerializedName("c6h6SourceDataDate")
    @Expose
    private String c6h6SourceDataDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStCalcDate() {
        return stCalcDate;
    }

    public void setStCalcDate(String stCalcDate) {
        this.stCalcDate = stCalcDate;
    }

    public StIndexLevel getStIndexLevel() {
        return stIndexLevel;
    }

    public void setStIndexLevel(StIndexLevel stIndexLevel) {
        this.stIndexLevel = stIndexLevel;
    }

    public String getStSourceDataDate() {
        return stSourceDataDate;
    }

    public void setStSourceDataDate(String stSourceDataDate) {
        this.stSourceDataDate = stSourceDataDate;
    }

    public String getSo2CalcDate() {
        return so2CalcDate;
    }

    public void setSo2CalcDate(String so2CalcDate) {
        this.so2CalcDate = so2CalcDate;
    }

    public Object getSo2IndexLevel() {
        return so2IndexLevel;
    }

    public void setSo2IndexLevel(Object so2IndexLevel) {
        this.so2IndexLevel = so2IndexLevel;
    }

    public String getSo2SourceDataDate() {
        return so2SourceDataDate;
    }

    public void setSo2SourceDataDate(String so2SourceDataDate) {
        this.so2SourceDataDate = so2SourceDataDate;
    }

    public String getNo2CalcDate() {
        return no2CalcDate;
    }

    public void setNo2CalcDate(String no2CalcDate) {
        this.no2CalcDate = no2CalcDate;
    }

    public Object getNo2IndexLevel() {
        return no2IndexLevel;
    }

    public void setNo2IndexLevel(Object no2IndexLevel) {
        this.no2IndexLevel = no2IndexLevel;
    }

    public String getNo2SourceDataDate() {
        return no2SourceDataDate;
    }

    public void setNo2SourceDataDate(String no2SourceDataDate) {
        this.no2SourceDataDate = no2SourceDataDate;
    }

    public String getCoCalcDate() {
        return coCalcDate;
    }

    public void setCoCalcDate(String coCalcDate) {
        this.coCalcDate = coCalcDate;
    }

    public Object getCoIndexLevel() {
        return coIndexLevel;
    }

    public void setCoIndexLevel(Object coIndexLevel) {
        this.coIndexLevel = coIndexLevel;
    }

    public String getCoSourceDataDate() {
        return coSourceDataDate;
    }

    public void setCoSourceDataDate(String coSourceDataDate) {
        this.coSourceDataDate = coSourceDataDate;
    }

    public String getPm10CalcDate() {
        return pm10CalcDate;
    }

    public void setPm10CalcDate(String pm10CalcDate) {
        this.pm10CalcDate = pm10CalcDate;
    }

    public Object getPm10IndexLevel() {
        return pm10IndexLevel;
    }

    public void setPm10IndexLevel(Object pm10IndexLevel) {
        this.pm10IndexLevel = pm10IndexLevel;
    }

    public String getPm10SourceDataDate() {
        return pm10SourceDataDate;
    }

    public void setPm10SourceDataDate(String pm10SourceDataDate) {
        this.pm10SourceDataDate = pm10SourceDataDate;
    }

    public String getPm25CalcDate() {
        return pm25CalcDate;
    }

    public void setPm25CalcDate(String pm25CalcDate) {
        this.pm25CalcDate = pm25CalcDate;
    }

    public Object getPm25IndexLevel() {
        return pm25IndexLevel;
    }

    public void setPm25IndexLevel(Object pm25IndexLevel) {
        this.pm25IndexLevel = pm25IndexLevel;
    }

    public Object getPm25SourceDataDate() {
        return pm25SourceDataDate;
    }

    public void setPm25SourceDataDate(Object pm25SourceDataDate) {
        this.pm25SourceDataDate = pm25SourceDataDate;
    }

    public String getO3CalcDate() {
        return o3CalcDate;
    }

    public void setO3CalcDate(String o3CalcDate) {
        this.o3CalcDate = o3CalcDate;
    }

    public Object getO3IndexLevel() {
        return o3IndexLevel;
    }

    public void setO3IndexLevel(Object o3IndexLevel) {
        this.o3IndexLevel = o3IndexLevel;
    }

    public String getO3SourceDataDate() {
        return o3SourceDataDate;
    }

    public void setO3SourceDataDate(String o3SourceDataDate) {
        this.o3SourceDataDate = o3SourceDataDate;
    }

    public String getC6h6CalcDate() {
        return c6h6CalcDate;
    }

    public void setC6h6CalcDate(String c6h6CalcDate) {
        this.c6h6CalcDate = c6h6CalcDate;
    }

    public Object getC6h6IndexLevel() {
        return c6h6IndexLevel;
    }

    public void setC6h6IndexLevel(Object c6h6IndexLevel) {
        this.c6h6IndexLevel = c6h6IndexLevel;
    }

    public String getC6h6SourceDataDate() {
        return c6h6SourceDataDate;
    }

    public void setC6h6SourceDataDate(String c6h6SourceDataDate) {
        this.c6h6SourceDataDate = c6h6SourceDataDate;
    }

    @NonNull
    @Override
    public String toString() {
        return getStIndexLevel().getIndexLevelName();
    }
}