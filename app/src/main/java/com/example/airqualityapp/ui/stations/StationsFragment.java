package com.example.airqualityapp.ui.stations;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.airqualityapp.R;
import com.example.airqualityapp.interfaces.InvokeStation;
import com.example.airqualityapp.models.Station;
import com.example.airqualityapp.ui.home.HomeFragment;

import java.util.ArrayList;

public class StationsFragment extends Fragment {

    private StationsViewModel stationsViewModel;
    private ArrayAdapter<String> adapter;
    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        stationsViewModel =
                ViewModelProviders.of(this).get(StationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stations, container, false);
        listView = root.findViewById(R.id.listViewStations);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                Station station = stationsViewModel.getStations().getValue().get(position);
                bundle.putInt("StationId", station.getId());
                bundle.putString("StationCity", station.getCity().getName());
                bundle.putString("StationAddress", station.getAddressStreet().toString());
                Navigation.findNavController(root).navigate(R.id.navigation_home, bundle);
            }
        });

        stationsViewModel.getmStationNames().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> stations) {
                if (stations != null) {
                    adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_expandable_list_item_1, stations);
                    listView.setAdapter(adapter);
                }
            }
        });

        stationsViewModel.getmError().observe(this, new Observer<Throwable>() {
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
}