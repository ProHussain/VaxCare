package com.example.vaxcare.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.vaxcare.R;
import com.example.vaxcare.databinding.ActivitySettingBinding;
import com.example.vaxcare.viewmodel.SettingViewModel;

/**
 * Let's add a setting screen and functionality
 * Instead of writing code, we will copy it from the previous project
 * SettingActivity  is complete now, Let's test it
 */

public class SettingActivity extends AppCompatActivity {
    ActivitySettingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        binding.setLifecycleOwner(this);
        SettingViewModel viewModel = new ViewModelProvider(this).get(SettingViewModel.class);
        binding.setViewModel(viewModel);
        viewModel.getClickData().observe(this, s -> {
            switch (s) {
                case "share":
                    shareApp();
                    break;
                case "rate":
                    rateApp();
                    break;
                case "more_apps":
                    moreApps();
                    break;
                case "feedback":
                    sendFeedback();
                    break;
                case "privacy_policy":
                    privacyPolicy();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + s);
            }
        });

    }

    private void privacyPolicy() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(android.net.Uri.parse("https://www.google.com"));
        startActivity(intent);
    }

    private void sendFeedback() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.developer_email)});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for " + getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, "Hi " + getString(R.string.developer_name) + ",");
        startActivity(Intent.createChooser(intent, "Send Feedback"));
    }

    private void moreApps() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(android.net.Uri.parse("https://play.google.com/store/apps/developer?id=" + getString(R.string.developer_id)));
        startActivity(intent);
    }

    private void rateApp() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(android.net.Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
        startActivity(intent);
    }

    private void shareApp() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Wallcraft");
        intent.putExtra(Intent.EXTRA_TEXT, "Get " + getString(R.string.app_name) + " to get the best wallpapers for your phone: https://play.google.com/store/apps/details?id=" + getPackageName());
        startActivity(Intent.createChooser(intent, "Share App"));
    }
}