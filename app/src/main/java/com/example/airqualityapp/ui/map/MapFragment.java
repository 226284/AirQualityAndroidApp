package com.example.airqualityapp.ui.map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.airqualityapp.R;
import com.example.airqualityapp.models.Station;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.patloew.rxlocation.RxLocation;

import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapViewModel mapViewModel;
    private SupportMapFragment mapFragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapViewModel =
                ViewModelProviders.of(this).get(MapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mapViewModel.getmError().observe(this, new Observer<Throwable>() {
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

        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        mapViewModel.getmMarkers().observe(this, new Observer<ArrayList<MarkerOptions>>() {
            @Override
            public void onChanged(ArrayList<MarkerOptions> markers) {
                if (markers != null) {
                    //adapter = new ArrayAdapter<>(requireActivity(), R.layout.fragment_map, markers);
                    for (MarkerOptions marker: markers
                         ) {
                        googleMap.addMarker(marker);
                    }
                }
            }
        });

        mapViewModel.getmCameraPosition().observe(this, new Observer<CameraUpdate>() {
            @Override
            public void onChanged(CameraUpdate cameraUpdate) {
                if (cameraUpdate != null){
                    googleMap.animateCamera(cameraUpdate);
                }
            }
        });
    }
}