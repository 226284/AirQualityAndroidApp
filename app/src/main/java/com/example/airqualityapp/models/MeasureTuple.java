package com.example.airqualityapp.models;

public class MeasureTuple {
    private String Name;
    private double Value;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getValue() {
        return Value;
    }

    public void setValue(double value) {
        Value = value;
    }

    public MeasureTuple(String name, double value) {
        Name = name;
        Value = value;
    }
}
