package com.example.airqualityapp.interfaces;

import com.example.airqualityapp.models.AQIndex;
import com.example.airqualityapp.models.Measurement;
import com.example.airqualityapp.models.Sensor;
import com.example.airqualityapp.models.Station;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("aqindex/getIndex/{stationId}")
    Single<AQIndex> getAQIndex(@Path("stationId") int stationId);

    @GET("station/findAll")
    Single<ArrayList<Station>> getStations();

    @GET("station/sensors/{stationId}")
    Single<ArrayList<Sensor>> getSensors(@Path("stationId") int stationId);

    @GET("data/getData/{sensorId}")
    Observable<Measurement> getMeasurments(@Path("sensorId") int sensorId);
}