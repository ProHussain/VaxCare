package com.example.vaxcare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vaxcare.databinding.ItemVaccineBinding;
import com.example.vaxcare.model.Vaccine;

import java.util.ArrayList;
import java.util.List;

public class HorizontalVaccineAdapter extends RecyclerView.Adapter<HorizontalVaccineAdapter.HorizontalVaccineViewHolder>{
    List<Vaccine> vaccineList = new ArrayList<>();
    @NonNull
    @Override
    public HorizontalVaccineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemVaccineBinding binding = ItemVaccineBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HorizontalVaccineViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalVaccineViewHolder holder, int position) {
        Vaccine vaccine = vaccineList.get(position);
        holder.binding.setVaccine(vaccine);
        holder.binding.getRoot().setOnClickListener(view -> {
            showDialog(vaccine,holder.binding.getRoot().getContext());
        });
    }

    // My Plan is to show a simple dialog with vaccine details in order to manage our work within time and client budget
    // Works fine, we just need to add description in the vaccine item on Server
    private void showDialog(Vaccine vaccine, Context context) {
        new AlertDialog.Builder(context)
                .setTitle(vaccine.getName())
                .setMessage(vaccine.getDescription())
                .show();
    }

    @Override
    public int getItemCount() {
        return vaccineList.size();
    }

    public void setVaccineList(List<Vaccine> vaccines) {
        this.vaccineList = vaccines;
    }

    public static class HorizontalVaccineViewHolder extends RecyclerView.ViewHolder {
        ItemVaccineBinding binding;
        public HorizontalVaccineViewHolder(@NonNull ItemVaccineBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
