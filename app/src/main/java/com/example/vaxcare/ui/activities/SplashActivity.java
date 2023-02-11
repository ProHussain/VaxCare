package com.example.vaxcare.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vaxcare.R;
import com.example.vaxcare.utils.VaxPreference;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
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
        setContentView(R.layout.activity_splash);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(SPLASH_DURATION);
        mHandler = new Handler();
        preference = new VaxPreference(getApplicationContext());
        mProgressRunnable = new Runnable() {
            @Override
            public void run() {
                mProgress += PROGRESS_INTERVAL;
                progressBar.setProgress(mProgress);
                if (mProgress >= SPLASH_DURATION) {
                    if (preference.getLoginStatus())
                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    else
                        startActivity(new Intent(SplashActivity.this, SelectUserActivity.class));
                    finish();
                } else {
                    mHandler.postDelayed(mProgressRunnable, PROGRESS_INTERVAL);
                }
            }
        };
        mHandler.postDelayed(mProgressRunnable, PROGRESS_INTERVAL);

    }
}
