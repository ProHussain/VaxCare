package com.example.vaxcare.user.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vaxcare.databinding.FragmentHomeBinding;
import com.example.vaxcare.user.adapter.HelpAdapter;
import com.example.vaxcare.common.network.model.Help;
import com.example.vaxcare.user.viewmodel.HomeViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    HomeViewModel model;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        model = new ViewModelProvider(this).get(HomeViewModel.class);
        binding.setViewModel(model);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.helpRV.setAdapter(new HelpAdapter());
        model.getHelpLiveData().observe(getViewLifecycleOwner(), new Observer<List<Help>>() {
            @Override
            public void onChanged(List<Help> helps) {
                HelpAdapter helpAdapter = (HelpAdapter) binding.helpRV.getAdapter();
                assert helpAdapter != null;
                helpAdapter.setHelpList(helps);
                helpAdapter.notifyDataSetChanged();
            }
        });
        return binding.getRoot();
    }

}