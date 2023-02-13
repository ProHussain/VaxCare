package com.example.vaxcare.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.vaxcare.R;
import com.example.vaxcare.ui.fragments.AppointmentsFragment;
import com.example.vaxcare.ui.fragments.HomeFragment;
import com.example.vaxcare.ui.fragments.ProfileFragment;
import com.example.vaxcare.utils.VaxPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    VaxPreference preference;
    int selectedItemId = R.id.homeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        preference = new VaxPreference(this);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homeFragment:
                        selectedItemId = R.id.homeFragment;
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_fragment,new HomeFragment()).commit();
                        break;
                    case R.id.appointmentsFragment:
                        selectedItemId = R.id.appointmentsFragment;
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_fragment,new AppointmentsFragment()).commit();
                        break;
                    case R.id.profileFragment:
                        selectedItemId = R.id.profileFragment;
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_fragment,new ProfileFragment()).commit();
                        break;
                    case R.id.logoutUser:
                        logoutUser();
                            break;
                }
                return true;
            }
        });
    }

    private void logoutUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                preference.setLoginStatus(false);
                finishAffinity();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    bottomNavigationView.setSelectedItemId(selectedItemId);
                }
            });
        builder.show();
    }
}