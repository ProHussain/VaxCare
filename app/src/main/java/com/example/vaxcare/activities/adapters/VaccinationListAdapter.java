package com.example.vaxcare.activities.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vaxcare.R;
import com.example.vaxcare.models.VaccinationListModel;

import java.util.ArrayList;
import java.util.List;

public class VaccinationListAdapter extends RecyclerView.Adapter<VaccinationListAdapter.ViewHolder> {
    private List<VaccinationListModel> listt;

    public VaccinationListAdapter(List<VaccinationListModel> listt) {
        this.listt = listt;
    }

    @NonNull
    @Override
    public VaccinationListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.vaccination_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VaccinationListAdapter.ViewHolder holder, int position) {
          VaccinationListModel listModel= listt.get(position);
          holder.vaccination_name.setText(listModel.getlTitle());
          holder.vaccination_img.setImageResource(listModel.getlImageResource());
    }

    @Override
    public int getItemCount() {
        return listt.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView vaccination_name;
        ImageView vaccination_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vaccination_name=itemView.findViewById(R.id.tv_vaccination_name);
            vaccination_img=itemView.findViewById(R.id.iv_vaccination_icon);
            
        }
    }
}
