package com.example.vaxcare.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vaxcare.databinding.AdminListItemBinding;
import com.example.vaxcare.model.AdminList;

import java.util.ArrayList;
import java.util.List;

public class AdminListAdapter extends RecyclerView.Adapter<AdminListAdapter.ViewHolder>{
    List<AdminList> adminList = new ArrayList<AdminList>();

    public void setAdminList(List<AdminList> adminList) {
        this.adminList = adminList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdminListItemBinding binding = AdminListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setModel(adminList.get(position));
    }

    @Override
    public int getItemCount() {
        return adminList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdminListItemBinding binding;
        public ViewHolder(@NonNull AdminListItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
