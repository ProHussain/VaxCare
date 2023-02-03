package com.example.vaxcare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mProgress = findViewById(R.id.loadprogress);
          
        new Thread(new Runnable() {
            public void run() {
                doWork();
                //startApp();
                finish();
            }
        }).start();
    }

    private void doWork() {
        for (int progress = 0; progress < 100; progress += 1) {
            try {
                Thread.sleep(30);
                mProgress.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
               // Timber.e(e.getMessage());
            }
        }
    }

   // private void startApp() {
     //   Intent intent = new Intent(SplashActivity.this, SplashActivity.class);
       // startActivity(intent);
    //}
}
