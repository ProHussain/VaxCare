package com.example.vaxcare.user.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vaxcare.common.network.model.Help;
import com.example.vaxcare.databinding.HelpItemBinding;

import java.util.ArrayList;
import java.util.List;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.HelpViewHolder> {

    List<Help> helpList = new ArrayList<>();

    public HelpAdapter() {

    }

    public void setHelpList(List<Help> helpList) {
        this.helpList = helpList;
    }

    @NonNull
    @Override
    public HelpAdapter.HelpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HelpItemBinding binding = HelpItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HelpViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpAdapter.HelpViewHolder holder, int position) {
        Help help = helpList.get(position);
        Log.e("Help",help.getTitle());
        holder.binding.setHelp(help);
    }

    @Override
    public int getItemCount() {
        return helpList.size();
    }

    public static class HelpViewHolder extends RecyclerView.ViewHolder {
        HelpItemBinding binding;
        public HelpViewHolder(@NonNull HelpItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
