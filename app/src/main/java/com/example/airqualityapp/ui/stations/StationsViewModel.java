package com.example.airqualityapp.ui.stations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.airqualityapp.interfaces.ApiService;
import com.example.airqualityapp.models.Station;

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

public class StationsViewModel extends ViewModel {

    private MutableLiveData<ArrayList<String>> mStationNames = new MutableLiveData<>();
    private MutableLiveData<Throwable> mError = new MutableLiveData<>();

    private MutableLiveData<ArrayList<Station>> stations = new MutableLiveData<>();
    private CompositeDisposable disposables = new CompositeDisposable();

    public StationsViewModel() {
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
                        ArrayList<String> stationNames = new ArrayList<>();

                        for (Station station:data
                             ) {
                            stationNames.add(station.getStationName());
                        }
                        mStationNames.setValue(stationNames);
                        stations.setValue(data);
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


    public LiveData<ArrayList<String>> getmStationNames() {
        return mStationNames;
    }

    public MutableLiveData<Throwable> getmError() {
        return mError;
    }

    public MutableLiveData<ArrayList<Station>> getStations() {
        return stations;
    }
}