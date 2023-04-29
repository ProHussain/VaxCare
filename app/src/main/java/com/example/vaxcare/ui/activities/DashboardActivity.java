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

/**
 * Hello Guys, Welcome to HashMac Android Developer Training
 * In this video we will start Admin Panel
 * We remove Admin Login in our 1st video, Now we will add it again
 * We keep admin separate from team member and user but we have same source code
 * So we need to made some configuration in our code to make it two apps
 * let's start by adding admin login page
 * need to add config for admin app in splash screen too, Solved
 * In Admin Panel we need to add some features
 * 1. Add Vaccine with name and description and image url
 * 2. load Vaccine list in recyclerView from Server
 * 3. View Team Members Details in Activity or Dialog
 * 4. Disable a team member from admin panel
 * 5. Secure Vaccines related API from team members
 * 6. View User Profile
 * I think it's enough for Admin Panel
 * Let's start by loading vaccine list from server
 * Here is a problem, we have same code for admin for all list like vaccine, team, user
 * So we need to make some changes in our code, Works fine for vaccines loading from server
 * Now let's update Vaccine Add Dialog
 * Let's test Add Vaccine Dialog, Works pretty fine
 * It's enough for today, we will continue in next video
 * In our next video we will add team member list and see info in Dialog
 * See you in next video, Thanks for watching HashMac Android Developer Training
 * Bye Bye Have a nice day and Happy Coding :)
 */

public class DashboardActivity extends AppCompatActivity {
    ActivityDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        binding.setLifecycleOwner(this);
        DashboardViewModel viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding.setModel(viewModel);
        viewModel.getClick().observe(this, s -> {
            if (s.equals("logout")) {
                LogoutDialog();
                return;
            }
            Intent intent = new Intent(DashboardActivity.this, AdminListActivity.class);
            intent.putExtra("listName", s);
            startActivity(intent);
        });
    }

    private void LogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            VaxPreference preference = new VaxPreference(DashboardActivity.this);
            preference.setLoginStatus(false);
            Toast.makeText(DashboardActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finishAffinity();
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.show();
    }
}