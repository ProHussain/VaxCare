package com.example.vaxcare.ui.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.vaxcare.R;
import com.example.vaxcare.adapter.AdminListAdapter;
import com.example.vaxcare.databinding.ActivityDashboardBinding;
import com.example.vaxcare.utils.VaxPreference;
import com.example.vaxcare.viewmodel.DashboardViewModel;

public class DashboardActivity extends AppCompatActivity {
    ActivityDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        binding.setLifecycleOwner(this);
        DashboardViewModel viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding.setModel(viewModel);
        viewModel.getClick().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("logout")) {
                    LogoutDialog();
                    return;
                }
                Intent intent = new Intent(DashboardActivity.this, AdminListActivity.class);
                intent.putExtra("listName", s);
                startActivity(intent);
            }
        });
    }

    private void LogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                VaxPreference preference = new VaxPreference(DashboardActivity.this);
                preference.setLoginStatus(false);
                Toast.makeText(DashboardActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}