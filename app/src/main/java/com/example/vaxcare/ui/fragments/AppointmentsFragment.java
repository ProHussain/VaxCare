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

/**
 * in AppointmentsFragment we are showing the list of appointments
 * But what if list is empty? We need to show a message to user that list is empty
 * So we will add a image view and show it when list is empty, Let's add image view
 * The image is already added in layout file, we just need to show it when list is empty
 * Works pretty well
 * Now Let's add some Appointment in list, In adding Appointment we will use a dialog
 * Dummy data is already added in AppointmentAdapter, we need to replace it with real data
 * Let's load vaccine list from server
 * Works pretty well, Now we also show a message to user if vaccine appointment is already requested
 * It's prevent user from requesting same vaccine appointment again and again and also prevent entry of duplicate data
 * Duplicate check is done on basis of vaccine name and and user id and status
 * Now we need to auto update the list when new appointment is added
 * Works well and i think it's enough for this fragment
 * In our next video we will work over profile fragment
 * Thank you for watching, if you like this video then please like and subscribe to our channel
 * See you in next video
 * Bye Bye and Happy Coding :)
 */

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
        model.getAppointmentsLiveData().observe(getViewLifecycleOwner(), this::UpdateList);

        model.getIsDialogVisible().observe(getViewLifecycleOwner(), aBoolean -> {
            Log.e("Click Event", String.valueOf(aBoolean));
            if (Boolean.TRUE.equals(aBoolean))
                ShowAppointmentDialog();
            else
                HideDialog();
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
        Log.e("List Size", String.valueOf(list.size()));
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
    public void onResume() {
        super.onResume();
        model.fetchData();
    }

    @Override
    public void onClick() {
        model.hideDialog();
    }
}