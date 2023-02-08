package com.example.vaxcare.user.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.vaxcare.common.network.model.Profile;
import com.example.vaxcare.databinding.FragmentProfileBinding;
import com.example.vaxcare.user.viewmodel.ProfileViewModel;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    ProfileViewModel model;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding.setViewModel(model);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        model.getProfileLiveData().observe(getViewLifecycleOwner(), new Observer<Profile>() {
            @Override
            public void onChanged(Profile profile) {
                if (profile != null) {
                    if (!profile.isSuccess()) {
                        Toast.makeText(requireContext(), profile.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        model.fetchData();
    }
}