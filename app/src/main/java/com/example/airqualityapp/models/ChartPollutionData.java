package com.example.airqualityapp.models;

public class ChartPollutionData {
    private String Factor;
    private float Value;

    public ChartPollutionData(String factor, float value) {
        Factor = factor;
        this.Value = value;
    }

    public String getFactor() {
        return Factor;
    }

    public void setFactor(String factor) {
        Factor = factor;
    }

    public float getValue() {
        return Value;
    }

    public void setValue(float value) {
        Value = value;
    }
}
