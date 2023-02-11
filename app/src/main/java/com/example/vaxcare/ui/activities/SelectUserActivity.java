package com.example.vaxcare.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.example.vaxcare.R;
import com.example.vaxcare.databinding.ActivitySelectUserBinding;
import com.example.vaxcare.utils.VaxPreference;
import com.example.vaxcare.viewmodel.SelectUserActivityViewModel;

public class SelectUserActivity extends AppCompatActivity {
    ActivitySelectUserBinding binding;
    VaxPreference preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_select_user);
        binding.setLifecycleOwner(this);
        SelectUserActivityViewModel model = new ViewModelProvider(this).get(SelectUserActivityViewModel.class);
        binding.setModel(model);
        preference = new VaxPreference(getApplicationContext());
        model.getUserTypeLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                preference.setUserType(s);
                startActivity(new Intent(SelectUserActivity.this,LoginActivity.class));
            }
        });
    }
}