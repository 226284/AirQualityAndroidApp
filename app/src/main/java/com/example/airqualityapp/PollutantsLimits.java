package com.example.airqualityapp;

import com.example.airqualityapp.models.MeasureTuple;

import java.util.ArrayList;

public class PollutantsLimits {
    public static ArrayList<MeasureTuple> array =
            new ArrayList<MeasureTuple>() {{
                add(new MeasureTuple("SO2", 100));
                add(new MeasureTuple("C6H6", 11));
                add(new MeasureTuple("CO", 7000));
                add(new MeasureTuple("NO2", 100));
                add(new MeasureTuple("O3", 120));
                add(new MeasureTuple("PM2.5", 35));
                add(new MeasureTuple("PM10", 50));
            }};
}
