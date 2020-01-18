package com.example.airqualityapp.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.airqualityapp.models.ChartPollutionData;
import com.example.airqualityapp.models.LocationModel;
import com.example.airqualityapp.R;

import com.example.airqualityapp.models.Station;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private static final String TAG = "Logger";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 111;

    // drawing
    private BarChart barChart;

    // alerts

    // controls
    private TextView cityName;
    private TextView addressText;
    private TextView coordinatesText;
    private TextView aqiText;
    private SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (!checkPermissions()) {
            Log.i(TAG, "requesting permission when permission is not available");
            requestPermissions();
        } else {
            Log.i(TAG, "getting location when permission is already available");
        }

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // controls
        cityName = root.findViewById(R.id.textViewCityName);
        addressText = root.findViewById(R.id.textViewAddress);
        coordinatesText = root.findViewById(R.id.textViewCoordinates);
        aqiText = root.findViewById(R.id.textViewAQIValue);
        swipeRefreshLayout = root.findViewById(R.id.swiperefresh);
        barChart = root.findViewById(R.id.barChart);


        homeViewModel.getmLocation().observe(this, new Observer<LocationModel>() {
            @Override
            public void onChanged(LocationModel location) {
                if (location != null) {
                    coordinatesText.setText(location.toString());
                }
            }
        });

        homeViewModel.getmCityName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                cityName.setText(s);
            }
        });

        homeViewModel.getmAddress().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                addressText.setText(s);
            }
        });

        homeViewModel.getmAQI().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
//                aqiText.getBackground().setColorFilter(
//                        getColorwithId(integer),
//                        );

                aqiText.setBackgroundColor(getColorwithId(integer));
                aqiText.setText(getAQINamewithId(integer));
            }
        });

        homeViewModel.getmChartPollutionData().observe(this, new Observer<ArrayList<ChartPollutionData>>() {
            @Override
            public void onChanged(ArrayList<ChartPollutionData> chartPollutionData) {
                drawChart(chartPollutionData);
            }
        });

        homeViewModel.getmError().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Error")
                        .setMessage(throwable.getMessage())
                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // nothing
                            }
                        })
                        .show();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                homeViewModel.RefreshLayout(requireContext());

                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bdl = getArguments();
        if (bdl != null) {
            homeViewModel.getmSyntax().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if (integer != 0) {
                        try {
                            int val = bdl.getInt("StationId");
                            homeViewModel.ListLayoutInvoke(val);

                            homeViewModel.getmCityName().setValue(bdl.getString("StationCity"));
                            homeViewModel.getmAddress().setValue(bdl.getString("StationAddress"));

                            Log.i(TAG, Integer.toString(val));
                        } catch (Throwable t) {
                            Log.i(TAG, t.getMessage());
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "FragmentA.onDestroyView() has been called.");
        //compositeDisposable.dispose();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    //Request permission from user
    private void requestPermissions() {
        Log.i(TAG, "Inside requestPermissions function");
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        //Log an additional rationale to the user. This would happen if the user denied the
        //request previously, but didn't check the "Don't ask again" checkbox.
        // In case you want, you can also show snackbar. Here, we used Log just to clear the concept.
        if (shouldProvideRationale) {
            Log.i(TAG, "****Inside requestPermissions function when shouldProvideRationale = true");
            startLocationPermissionRequest();
        } else {
            Log.i(TAG, "****Inside requestPermissions function when shouldProvideRationale = false");
            startLocationPermissionRequest();
        }
    }

    //Start the permission request dialog
    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(requireActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }


    private void drawChart(ArrayList<ChartPollutionData> chartPollutionDataArrayList) {
        ArrayList<BarEntry> barEntryArrayList = new ArrayList<>();
        ArrayList<String> labelNames = new ArrayList<>();

        Log.i(TAG, "See if this is working");
        for (int i = 0; i < chartPollutionDataArrayList.size(); i++) {
            barEntryArrayList.add(new BarEntry(i, chartPollutionDataArrayList.get(i).getValue()));
            labelNames.add(chartPollutionDataArrayList.get(i).getFactor());
        }

        BarDataSet barDataSet = new BarDataSet(barEntryArrayList, "Air Quality Index");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        Description description = new Description();
        description.setText("Factors");
        barChart.setDescription(description);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.setPinchZoom(false);


        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelNames));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labelNames.size());
        barChart.animateY(2000);
        barChart.invalidate();
    }

    private int getColorwithId(int id) {
        int tmp_color = R.color.colorAQIN;

        if (id == 0) {
            tmp_color = R.color.colorAQI0;
        } else if (id == 1) {
            tmp_color = R.color.colorAQI1;
        } else if (id == 2) {
            tmp_color = R.color.colorAQI2;
        } else if (id == 3) {
            tmp_color = R.color.colorAQI3;
        } else if (id == 4) {
            tmp_color = R.color.colorAQI4;
        } else if (id == 5) {
            tmp_color = R.color.colorAQI5;
        }

        return ResourcesCompat.getColor(getResources(), tmp_color, null);
    }

    private int getAQINamewithId(int id) {
        int tmp_string = R.string.stringAQIN;

        if (id == 0) {
            tmp_string = R.string.stringAQI0;
        } else if (id == 1) {
            tmp_string = R.string.stringAQI1;
        } else if (id == 2) {
            tmp_string = R.string.stringAQI2;
        } else if (id == 3) {
            tmp_string = R.string.stringAQI3;
        } else if (id == 4) {
            tmp_string = R.string.stringAQI4;
        } else if (id == 5) {
            tmp_string = R.string.stringAQI5;
        }

        return tmp_string;
    }
}