package com.example.vaxcare.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vaxcare.databinding.FragmentAppointmentsBinding;
import com.example.vaxcare.model.Appointment;
import com.example.vaxcare.ui.dialogs.AddAppointmentDialog;
import com.example.vaxcare.adapter.AppointmentAdapter;
import com.example.vaxcare.listeners.OnDialogActionListener;
import com.example.vaxcare.utils.VaxPreference;
import com.example.vaxcare.viewmodel.AppointmentsViewModel;

import java.util.List;

public class AppointmentsFragment extends Fragment implements OnDialogActionListener {

    FragmentAppointmentsBinding binding;
    AppointmentsViewModel model;
    AddAppointmentDialog dialog;
    VaxPreference preferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAppointmentsBinding.inflate(inflater,container,false);
        model = new ViewModelProvider(this).get(AppointmentsViewModel.class);
        binding.setViewModel(model);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.appointmentsRV.setAdapter(new AppointmentAdapter());
        preferences = new VaxPreference(requireContext());
        if (preferences.getUserType().equals("team")) {
            binding.fabAddNew.setVisibility(View.GONE);
        }
        model.getAppointmentsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Appointment>>() {
            @Override
            public void onChanged(List<Appointment> list) {
                UpdateList(list);
            }
        });

        model.getIsDialogVisible().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.e("Click Event", String.valueOf(aBoolean));
                if (aBoolean)
                    ShowAppointmentDialog();
                else
                    HideDialog();
            }
        });
        return binding.getRoot();
    }

    private void HideDialog() {
        dialog.dismiss();
    }

    private void ShowAppointmentDialog() {
        dialog = new AddAppointmentDialog(this);
        dialog.show(requireActivity().getSupportFragmentManager(), "AddAppointmentDialog");
    }

    private void UpdateList(List<Appointment> list) {
        if (list.isEmpty()) {
            binding.imgNotFound.setVisibility(View.VISIBLE);
            binding.appointmentsRV.setVisibility(View.GONE);
        } else {
            binding.imgNotFound.setVisibility(View.GONE);
            binding.appointmentsRV.setVisibility(View.VISIBLE);
            AppointmentAdapter adapter = (AppointmentAdapter) binding.appointmentsRV.getAdapter();
            assert adapter != null;
            adapter.setAppointmentList(list);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestClick() {
        model.hideDialog();
    }
}