package com.example.vaxcare.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vaxcare.R;
import com.example.vaxcare.models.VaccinationTypeModel;

import java.util.List;

public class VaccinationTypeAdapter extends RecyclerView.Adapter<VaccinationTypeAdapter.ViewHolder> {
    private List<VaccinationTypeModel> vaccines;

    public VaccinationTypeAdapter(List<VaccinationTypeModel> vaccines) {
        this.vaccines = vaccines;
    }

    @NonNull
    @Override
    public VaccinationTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.vaccination_type_item,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull VaccinationTypeAdapter.ViewHolder holder, int position) {
        VaccinationTypeModel item=vaccines.get(position);
        holder.title.setText(item.getmTitle());
        holder.imageView.setImageResource(item.getmImageResource());
    }

    @Override
    public int getItemCount() {
        return vaccines.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.vaccination_title);
            imageView=itemView.findViewById(R.id.vaccination_image);

        }
    }
}
