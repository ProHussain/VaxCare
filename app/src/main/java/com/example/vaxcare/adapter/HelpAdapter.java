package com.example.vaxcare.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vaxcare.databinding.ItemHelpBinding;
import com.example.vaxcare.model.Help;

import java.util.ArrayList;
import java.util.List;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.HelpViewHolder> {

    List<Help> helpList = new ArrayList<>();
    public void setHelpList(List<Help> helpList) {
        this.helpList = helpList;
    }

    @NonNull
    @Override
    public HelpAdapter.HelpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHelpBinding binding = ItemHelpBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HelpViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpAdapter.HelpViewHolder holder, int position) {
        Help help = helpList.get(position);
        holder.binding.setHelp(help);
        holder.binding.getRoot().setOnClickListener(v -> {
            if (holder.binding.tvDescription.getVisibility() == View.VISIBLE) {
                holder.binding.tvDescription.animate()
                        .alpha(0.0f)
                        .setDuration(500)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                holder.binding.tvDescription.setVisibility(View.GONE);
                            }
                        });
            } else {
                holder.binding.tvDescription.setVisibility(View.VISIBLE);
                holder.binding.tvDescription.animate()
                        .alpha(1.0f)
                        .setDuration(500)
                        .setListener(null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return helpList.size();
    }

    public static class HelpViewHolder extends RecyclerView.ViewHolder {
        ItemHelpBinding binding;

        public HelpViewHolder(@NonNull ItemHelpBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
