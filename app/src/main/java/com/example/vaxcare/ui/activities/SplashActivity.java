package com.example.vaxcare.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vaxcare.R;
import com.example.vaxcare.utils.VaxPreference;

/**
 * Date: 28/04/2023
 * Hello Guys, Welcome to HashMac Android Developer Tutorials.
 * I'm Hussain and in this series we learn how to upgrade a pre built Android Project
 * Here we have a simple App, Let's visit it first and than we discuss what to do with it.
 * It's UI is too bad, we will change it and make it some what better.
 * Some Functionalities are missing, we will add them.
 * We will also add some new features.
 * Now our 1st task is to Understand the Project Structure.
 * Let's Start.
 * This project is divided into 3 parts. Admin, User and Team
 * Admin can add new users and can see the list of all users.
 * User can see vaccine details and can book an appointment.
 * Team can see the list of all users and can update the vaccine details.
 * Project Development is based on Java and Android Studio with MVVM Architecture.
 * Rest API is developed in PHP and MySQL is used as Database.
 * We will use Retrofit for API Calls.
 * We will use Glide for Image Loading.
 * We will use Gson for JSON Parsing.
 * We will use Room for Local Database.
 * It's enough for now, We will start our upgrade process in next video.
 * Thank You.
 * Happy Coding. :)

 -----------------------------------------------------------------------------------------

 * Date: 29/04/2023
 * Hello Guys, Welcome to HashMac Android Developer Tutorials.
 * I'm Hussain and in this series we learn how to upgrade a pre built Android Project
 * Today we will start our upgrade process.
 * We will start with Splash Screen.
 * We will add a Splash Screen with Progress Bar.
 * need to remove status bar from splash screen.
 * I will do all things on beginner level so that you can understand easily.
 * Like How to search and use stackoverflow.
 * Looks good now, we will add some delay in splash screen.
 * We don't need select user activity now, we will remove it.
 * but first we will check it's status from preference.
 */

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    final int SPLASH_DURATION = 3500;// 3.5 seconds
    final int PROGRESS_INTERVAL = 100; // 100 milliseconds
    private int mProgress = 0;
    private Handler mHandler;
    private Runnable mProgressRunnable;
    private ProgressBar progressBar;
    private VaxPreference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_splash);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(SPLASH_DURATION);
        mHandler = new Handler(Looper.getMainLooper());
        preference = new VaxPreference(getApplicationContext());
        mProgressRunnable = () -> {
            mProgress += PROGRESS_INTERVAL;
            progressBar.setProgress(mProgress);
            if (mProgress >= SPLASH_DURATION) {
                if (preference.getLoginStatus()) {
                    if (preference.getUserType().equalsIgnoreCase("admin")) {
                        startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    }
                }
                else
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            } else {
                mHandler.postDelayed(mProgressRunnable, PROGRESS_INTERVAL);
            }
        };
        mHandler.postDelayed(mProgressRunnable, PROGRESS_INTERVAL);

    }
}
