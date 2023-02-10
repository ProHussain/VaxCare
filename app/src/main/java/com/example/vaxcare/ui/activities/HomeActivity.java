package com.example.vaxcare.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.vaxcare.R;
import com.example.vaxcare.ui.fragments.AppointmentsFragment;
import com.example.vaxcare.ui.fragments.HomeFragment;
import com.example.vaxcare.ui.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homeFragment:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_fragment,new HomeFragment()).commit();
                        break;
                    case R.id.appointmentsFragment:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_fragment,new AppointmentsFragment()).commit();
                        break;
                    case R.id.profileFragment:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_fragment,new ProfileFragment()).commit();
                        break;
                }
                return true;
            }
        });
    }
}