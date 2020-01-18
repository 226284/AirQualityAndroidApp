package com.example.airqualityapp.models;

import androidx.annotation.NonNull;
import java.util.Locale;

public class LocationModel {
    private double Longitude;
    private double Latitude;

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    @Override @NonNull
    public String toString() {
        String lat_label = "N";
        String long_label = "E";

        if (Latitude < 0) lat_label = "S";
        if (Longitude < 0) long_label = "W";

        return String.format(
                Locale.ENGLISH, "%f %s, %f %s",
                Latitude, lat_label,
                Longitude, long_label);
    }
}