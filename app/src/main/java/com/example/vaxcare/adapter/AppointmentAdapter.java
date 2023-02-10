package com.example.vaxcare.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vaxcare.databinding.AppointmentItemBinding;
import com.example.vaxcare.model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    List<Appointment> appointmentList = new ArrayList<>();

    public AppointmentAdapter() {
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public AppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AppointmentItemBinding binding = AppointmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AppointmentAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.ViewHolder holder, int position) {
        holder.binding.setModel(appointmentList.get(position));
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AppointmentItemBinding binding;
        public ViewHolder(@NonNull AppointmentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
