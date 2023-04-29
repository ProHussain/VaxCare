package com.example.vaxcare.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.vaxcare.adapter.HelpAdapter;
import com.example.vaxcare.adapter.HorizontalVaccineAdapter;
import com.example.vaxcare.databinding.FragmentHomeBinding;
import com.example.vaxcare.ui.activities.SettingActivity;
import com.example.vaxcare.viewmodel.HomeViewModel;

/**
 * Let's test new UI changes
 * The Requests and approved requests are dummy data, Let's load it from the server
 * Currently we keep home banner dummy but add a eye catching banner
 * Now we will create vaccine item and adapter
 * We also need to add images and description for vaccine
 * We will add description in the vaccine item later
 * Now juts load vaccines from the server and show on the home screen
 * Much better now, i think our video length is going to be too long
 * We will see other helps data in next video or we can add it in this video
 * Let's add it in this video
 * Let's enough for this video, and Home Screen
 * In our next video we will see Vaccine Details and check other Screens too

 ---------------------------------------------------------------------------------

 * Date: 29/04/2023
 * Hello everyone, welcome to the 3rd video of this series
 * In this video we will see Vaccine Details Screen
 * Let's start
 * Vaccine Dialog complete
 * Now let's add a setting screen and functionality
 * Works fine, We will see other screens in next video
 * Thank you for watching this video
 * Please like, share and subscribe to my channel
 * See you in the next video, Happy Coding :)
 */

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    HomeViewModel model;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        model = new ViewModelProvider(this).get(HomeViewModel.class);
        binding.setViewModel(model);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.rvVaccines.setAdapter(new HorizontalVaccineAdapter());
        binding.rvHelps.setAdapter(new HelpAdapter());
        model.getHelpLiveData().observe(getViewLifecycleOwner(), helps -> {
            HelpAdapter helpAdapter = (HelpAdapter) binding.rvHelps.getAdapter();
            assert helpAdapter != null;
            helpAdapter.setHelpList(helps);
            helpAdapter.notifyDataSetChanged();
        });
        model.getVaccineLiveData().observe(getViewLifecycleOwner(), vaccines -> {
            HorizontalVaccineAdapter vaccineAdapter = (HorizontalVaccineAdapter) binding.rvVaccines.getAdapter();
            assert vaccineAdapter != null;
            vaccineAdapter.setVaccineList(vaccines);
            vaccineAdapter.notifyDataSetChanged();
        });
        model.getOnClickLiveData().observe(getViewLifecycleOwner(), s -> {
            if ("settings".equals(s)) {
                startActivity(new Intent(requireActivity(), SettingActivity.class));
            } else {
                throw new IllegalStateException("Unexpected value: " + s);
            }
        });
        return binding.getRoot();
    }
}