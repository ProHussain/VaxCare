package com.example.vaxcare.user.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.example.vaxcare.R;
import com.example.vaxcare.common.network.model.ApiResponse;
import com.example.vaxcare.databinding.ActivityEditProfileBinding;
import com.example.vaxcare.user.viewmodel.EditProfileViewModel;

public class EditProfileActivity extends AppCompatActivity {
    ActivityEditProfileBinding binding;
    EditProfileViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_profile);
        model = new ViewModelProvider(this).get(EditProfileViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(model);

        model.getOnBack().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                    finish();
            }
        });

        model.getApiResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {
                Toast.makeText(EditProfileActivity.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}