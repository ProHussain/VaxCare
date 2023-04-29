package com.example.vaxcare.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.vaxcare.R;
import com.example.vaxcare.ui.fragments.AppointmentsFragment;
import com.example.vaxcare.ui.fragments.HomeFragment;
import com.example.vaxcare.ui.fragments.ProfileFragment;
import com.example.vaxcare.utils.VaxPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

/**
 * Date: 29/04/2023
 * Hello Guys, This is the HomeActivity where we are showing the bottom navigation view
 * Design is good but need to improve the UI
 * We need to reduce the text size and also add some more features and about page
 * Let's check Home page first

 -------------------------------------------------------------------------------------------

 * Date: 30/04/2023
 * Hello Guys, Welcome to HashMac Android Developer Training
 * In this video we will move AddAppointmentActivity and check it's functionality and Design
 * let's start

 -------------------------------------------------------------------------------------------

 * Date: 01/05/2023
 * Hello Guys, Welcome to HashMac Android Developer Training
 * last time we miss to reduce the margin between view of AppointmentsFragment
 * let's do it now,
 * Good it's looking better now
 * Now we start Profile Fragment modification
 * Profile fragment is good, Let's check edit profile page
 * need to replace setting icon with edit icon
 * logout exit our app, we need to navigate to login page instead of exit
 * let's do it now
 * Performance and UI is much better now, In our next video we will start team module
 * Thank you for watching, Please like, share and subscribe
 * See you in next video
 * Bye Bye Happy Coding :)

 -------------------------------------------------------------------------------------------

 * Date: 02/05/2023
 * Hello Guys, Welcome to HashMac Android Developer Training
 * In this video we will start team module
 * let's start by login as team member and check the UI
 * We don't need fab for add appointment in team module
 * NO need to modify UI, Let's check the functionality after Admin Panel
 * We need to close the recyclerView for helps
 * Enough for today, Let's continue in next video
 * In next video we will start Admin Panel
 * Thank you for watching, Please like, share and subscribe
 * See you in next video
 * Bye Bye Happy Coding :)

 -------------------------------------------------------------------------------------------

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
 */

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

        bottomNavigationView.setOnItemSelectedListener(item -> {
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
        });
    }

    private void logoutUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (dialog, which) -> {
            preference.setLoginStatus(false);
            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            finishAffinity();
        });

        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
            bottomNavigationView.setSelectedItemId(selectedItemId);
        });
        builder.show();
    }
}