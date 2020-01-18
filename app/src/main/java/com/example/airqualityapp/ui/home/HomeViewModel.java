package com.example.airqualityapp.ui.home;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.airqualityapp.models.AQIndex;
import com.example.airqualityapp.models.ChartPollutionData;
import com.example.airqualityapp.models.LocationModel;
import com.example.airqualityapp.models.MeasureTuple;
import com.example.airqualityapp.models.Measurement;
import com.example.airqualityapp.models.Message;
import com.example.airqualityapp.Services.NetworkService;
import com.example.airqualityapp.PollutantsLimits;
import com.example.airqualityapp.models.Sensor;
import com.example.airqualityapp.models.Station;
import com.patloew.rxlocation.RxLocation;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeViewModel extends AndroidViewModel {
    private MutableLiveData<String> mCityName = new MutableLiveData<>();
    private MutableLiveData<String> mAddress = new MutableLiveData<>();
    private MutableLiveData<Integer> mAQI = new MutableLiveData<>();
    private MutableLiveData<LocationModel> mLocation = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ChartPollutionData>> mChartPollutionData = new MutableLiveData<>();
    private MutableLiveData<Throwable> mError = new MutableLiveData<>();
    private MutableLiveData<Message> mMessage = new MutableLiveData<>();
    private MutableLiveData<Integer> mSyntax = new MutableLiveData<>(0);

    private NetworkService networkService;
    private RxLocation rxLocation;
    private Station nearStation;
    private ArrayList<Sensor> nearSensors;
    private CompositeDisposable disposables;

    public HomeViewModel(Application application) {
        super(application);

        disposables = new CompositeDisposable();

        RefreshLayout(application.getApplicationContext());
    }

    @Override
    protected void onCleared() {
        if (disposables != null) {
            disposables.dispose();
        }
        super.onCleared();
    }

    public void RefreshLayout(Context context) {
        networkService = new NetworkService(context);
        RxLocation rxLocation = new RxLocation(context);
        Disposable disposable = rxLocation.location().lastLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(location -> {
                    LocationModel tmp_location = new LocationModel();
                    tmp_location.setLatitude(location.getLatitude());
                    tmp_location.setLongitude(location.getLongitude());

                    mLocation.setValue(tmp_location);

                    getStationsandCalculate();
                });

        disposables.add(disposable);
    }

    public void ListLayoutInvoke(Integer station_id) {
        if (networkService != null) {
            getAQIandSave(station_id);
            getSensorsandSave(station_id);
        }
    }

    public MutableLiveData<String> getmCityName() {
        return mCityName;
    }

    public void setmCityName(MutableLiveData<String> mCityName) {
        this.mCityName = mCityName;
    }

    public MutableLiveData<String> getmAddress() {
        return mAddress;
    }

    public void setmAddress(MutableLiveData<String> mAddress) {
        this.mAddress = mAddress;
    }

    public LiveData<Integer> getmAQI() {
        return mAQI;
    }

    public void setmAQI(MutableLiveData<Integer> mAQI) {
        this.mAQI = mAQI;
    }

    public MutableLiveData<LocationModel> getmLocation() {
        return mLocation;
    }

    public MutableLiveData<ArrayList<ChartPollutionData>> getmChartPollutionData() {
        return mChartPollutionData;
    }

    public MutableLiveData<Throwable> getmError() {
        return mError;
    }

    public MutableLiveData<Integer> getmSyntax() {
        return mSyntax;
    }

    private void getStationsandCalculate() {
        // request all stations
        Single<ArrayList<Station>> stationSingle = networkService.getApiService().getStations();

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
                        ArrayList<Station> stations = new ArrayList<>();
                        stations = data;

                        if (!stations.isEmpty()) {
                            double distance = 100000;
                            double actLat = mLocation.getValue().getLatitude();
                            double actLon = mLocation.getValue().getLongitude();

                            for (Station station : stations
                            ) {
                                double tmpLat = Double.parseDouble(station.getGegrLat());
                                double tmpLon = Double.parseDouble(station.getGegrLon());

                                double new_dist = Math.sqrt(Math.pow(actLat - tmpLat, 2) + Math.pow(actLon - tmpLon, 2));
                                if (new_dist < distance) {
                                    distance = new_dist;
                                    nearStation = station;
                                }
                            }
                        }

                        // update ViewModel
                        mAddress.setValue(nearStation.getAddressStreet().toString());
                        mCityName.setValue(nearStation.getCity().getName());

                        Log.i(TAG, "Address Updated");

                        getAQIandSave(nearStation.getId());
                        getSensorsandSave(nearStation.getId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        // oops, we best show some error message

                        mError.setValue(e);
                    }
                });
    }

    private void getAQIandSave(Integer id) {
        Single<AQIndex> pollutionDataSingle = networkService.getApiService().getAQIndex(id);

        pollutionDataSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<AQIndex>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(AQIndex data) {
                        // data is ready and we can update the UI
                        int aqi_level = data.getStIndexLevel().getId();
                        mAQI.setValue(aqi_level);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // oops, we best show some error message
                        mError.setValue(e);
                    }
                });
    }

    private void getSensorsandSave(int stationId) {
        // request all stations
        Single<ArrayList<Sensor>> sensorSingle = networkService.getApiService().getSensors(stationId);

        sensorSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ArrayList<Sensor>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(ArrayList<Sensor> data) {
                        // data is ready and we can update the UI
                        nearSensors = data;
                        getMeasurements();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mError.setValue(e);
                    }
                });
    }

    private void getMeasurements() {
        ArrayList<Observable<?>> requests = new ArrayList<>();

        for (Sensor d : nearSensors) {
            requests.add(networkService.getApiService().getMeasurments(d.getId()));
        }

        Log.i(TAG, "Sensors Updating");

        Disposable disposable = Observable
                .zip(requests, new Function<Object[], Object>() {
                            @Override
                            public Object apply(Object[] objects) throws Exception {
                                // Objects[] is an array of combined results of completed requests
                                ArrayList<MeasureTuple> tuples = new ArrayList<>();
                                for (Object o : objects) {
                                    Measurement tmp = (Measurement) o;

                                    int i = 0;
                                    while (i < tmp.getValues().size()) {
                                        if (tmp.getValues().get(i).getValue() == null) {
                                            Log.i(TAG, "Null Val");
                                            i++;
                                        } else {
                                            tuples.add(new MeasureTuple(tmp.getKey(), tmp.getValues().get(i).getValue()));
                                            Log.i(TAG, Double.toString(tmp.getValues().get(i).getValue()));
                                            break;
                                        }
                                    }
                                }
                                ArrayList<ChartPollutionData> chartPollutionDataArrayList = new ArrayList<>();
                                for (MeasureTuple t : tuples) {
                                    for (MeasureTuple el : PollutantsLimits.array) {
                                        if (el.getName().equals(t.getName())) {
                                            t.setValue(t.getValue() / el.getValue() * 100);
                                            break;
                                        }
                                    }
                                    chartPollutionDataArrayList.add(new ChartPollutionData(t.getName(), (float) t.getValue()));
                                }

                                mChartPollutionData.postValue(chartPollutionDataArrayList);

                                Log.i(TAG, "See if this is working");

                                return chartPollutionDataArrayList;
                            }
                        }
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                //Do something on successful completion of all requests
                                if (mSyntax.getValue() == 0) {
                                    mSyntax.setValue(1);
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable e) throws Exception {
                                mError.setValue(e);
                            }
                        }
                );

        disposables.add(disposable);
    }
}