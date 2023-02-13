package com.example.vaxcare.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import com.example.vaxcare.R;
import com.example.vaxcare.adapter.AdminListAdapter;
import com.example.vaxcare.databinding.ActivityAdminListBinding;
import com.example.vaxcare.viewmodel.AdminListViewModel;

public class AdminListActivity extends AppCompatActivity {
    ActivityAdminListBinding binding;
    AdminListViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin_list);
        viewModel = new ViewModelProvider(this).get(AdminListViewModel.class);
        viewModel.setListName(getIntent().getStringExtra("listName"));
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        binding.adminListRV.setAdapter(new AdminListAdapter());

        viewModel.getAdminList().observe(this, adminLists -> {
            if (adminLists != null) {
                AdminListAdapter adapter = (AdminListAdapter) binding.adminListRV.getAdapter();
                adapter.setAdminList(adminLists);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void DeleteVaccineDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Delete Vaccine");
        alertDialog.setMessage("Are you sure you want to delete this vaccine?");
        alertDialog.setPositiveButton("Yes", (dialog, which) -> {
            // Delete Vaccine
        });
        alertDialog.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }
}