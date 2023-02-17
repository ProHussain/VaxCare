package com.example.vaxcare.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vaxcare.databinding.AdminListItemBinding;
import com.example.vaxcare.model.AdminList;
import com.example.vaxcare.model.ApiResponse;
import com.example.vaxcare.network.MyApi;
import com.example.vaxcare.network.RetrofitClient;
import com.example.vaxcare.ui.activities.AppointmentDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminListAdapter extends RecyclerView.Adapter<AdminListAdapter.ViewHolder> {
    List<AdminList> adminList = new ArrayList<AdminList>();
    private String listType;

    public AdminListAdapter(String listType) {
        this.listType = listType;
    }

    public void setAdminList(List<AdminList> adminList) {
        this.adminList = adminList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(AdminListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("TAG", "onBindViewHolder: " + adminList.get(position).getId()+ " " + adminList.get(position).getName());
        holder.binding.setModel(adminList.get(position));
        holder.binding.getRoot().setOnClickListener(v -> {
            if (listType.equals("vaccine")){
                DeleteVacDialog(v.getContext(), adminList.get(position).getId());
            } if (listType.equals("request") || listType.equals("complete")){
                Intent intent = new Intent(v.getContext(), AppointmentDetailsActivity.class);
                intent.putExtra("admin",true);
                intent.putExtra("data", adminList.get(position).getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    private void DeleteVacDialog(Context context, String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Vaccine");
        builder.setMessage("Are you sure you want to delete this vaccine?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
                    MyApi api = RetrofitClient.getRetrofitInstance().create(MyApi.class);
                    api.deleteVaccine(id).enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                            if (response.isSuccessful()) {
                                assert response.body() != null;
                                if (response.body().isSuccess()) {
                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        adminList.removeIf(vaccine -> vaccine.getId().equals(id));
                                    } else {
                                        for (int i = 0; i < adminList.size(); i++) {
                                            if (adminList.get(i).getId().equals(id)) {
                                                adminList.remove(i);
                                                break;
                                            }
                                        }
                                    }
                                    notifyDataSetChanged();
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                            t.printStackTrace();
                        }
                    });
                });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.show();
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