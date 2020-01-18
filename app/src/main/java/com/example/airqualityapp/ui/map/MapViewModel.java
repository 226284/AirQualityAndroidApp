package com.example.airqualityapp.ui.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.airqualityapp.interfaces.ApiService;
import com.example.airqualityapp.models.LocationModel;
import com.example.airqualityapp.models.Station;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapViewModel extends ViewModel {

    private MutableLiveData<ArrayList<MarkerOptions>> mMarkers = new MutableLiveData<>();
    private MutableLiveData<LocationModel> mLocation = new MutableLiveData<>();
    private MutableLiveData<Throwable> mError = new MutableLiveData<>();
    private MutableLiveData<CameraUpdate> mCameraPosition = new MutableLiveData<>();

    private CompositeDisposable disposables;

    public MapViewModel() {
        disposables = new CompositeDisposable();

        // initialize API adapter
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.gios.gov.pl/pjp-api/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


        ApiService apiService = retrofit.create(ApiService.class);

        // request all stations
        Single<ArrayList<Station>> stationSingle = apiService.getStations();

        stationSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ArrayList<Station>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(ArrayList<Station> data) {
                        // data is ready and we can update the UI
                        ArrayList<MarkerOptions> markers = new ArrayList<>();
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();

                        for (Station station : data
                        ) {
                            LatLng tmp_lat_lng = new LatLng(Double.parseDouble(station.getGegrLat()), Double.parseDouble(station.getGegrLon()));
                            markers.add(new MarkerOptions()
                                    .position(tmp_lat_lng)
                                    .title(station.getStationName()));
                            builder.include(tmp_lat_lng);
                        }
                        mMarkers.setValue(markers);

                        int padding = 5; // offset from edges of the map in pixels
                        LatLngBounds bounds = builder.build();
                        mCameraPosition.setValue(CameraUpdateFactory.newLatLngBounds(bounds, padding));
                    }

                    @Override
                    public void onError(Throwable e) {
                        // oops, we best show some error message

                        mError.setValue(e);
                    }
                });
    }

    @Override
    protected void onCleared() {
        if (disposables != null) {
            disposables.dispose();
        }
        super.onCleared();
    }

    public LiveData<ArrayList<MarkerOptions>> getmMarkers() {
        return mMarkers;
    }

    public LiveData<CameraUpdate> getmCameraPosition() {
        return mCameraPosition;
    }


    public MutableLiveData<Throwable> getmError() {
        return mError;
    }
}