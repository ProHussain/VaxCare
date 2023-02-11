package com.example.vaxcare.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.example.vaxcare.R;
import com.example.vaxcare.databinding.ActivityAppointmentDetailsBinding;
import com.example.vaxcare.model.Appointment;
import com.example.vaxcare.utils.VaxPreference;
import com.example.vaxcare.viewmodel.AppointmentDetailsViewModel;
import com.example.vaxcare.viewmodel.EditProfileViewModel;

public class AppointmentDetailsActivity extends AppCompatActivity {
    ActivityAppointmentDetailsBinding binding;
    AppointmentDetailsViewModel model;
    VaxPreference vaxPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_appointment_details);
        model = new ViewModelProvider(this).get(AppointmentDetailsViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setAppointment(model);
        Appointment appointment = (Appointment) getIntent().getSerializableExtra("data");
        if (appointment.getStatus().equalsIgnoreCase("completed")) {
            binding.btnUpdateStatus.setVisibility(View.GONE);
        }
        model.setAppointmentMutableLiveData(appointment);

        model.getAppointmentMutableLiveData().observe(this, new Observer<Appointment>() {
            @Override
            public void onChanged(Appointment appointment) {
                if (appointment.getStatus().equalsIgnoreCase("completed")) {
                    binding.btnUpdateStatus.setVisibility(View.GONE);
                }
            }
        });

        vaxPreference = new VaxPreference(getApplicationContext());
        if (vaxPreference.getUserType().equalsIgnoreCase("user")) {
            binding.btnUpdateStatus.setVisibility(View.GONE);
        }

    }
}