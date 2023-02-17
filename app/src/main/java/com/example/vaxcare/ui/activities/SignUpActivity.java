package com.example.vaxcare.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.vaxcare.R;
import com.example.vaxcare.utils.VaxPreference;
import com.example.vaxcare.viewmodel.SignUpViewModel;
import com.example.vaxcare.model.ApiResponse;
import com.example.vaxcare.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    SignUpViewModel viewModel;
    ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        VaxPreference vaxPreference = new VaxPreference(getApplicationContext());
        viewModel.getValidFields().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s) {
                    case "1":
                        binding.etName.setError("Please enter your name");
                        binding.etName.requestFocus();
                        break;
                    case "2":
                        binding.etEmail.setError("Please enter your email address");
                        binding.etEmail.requestFocus();
                        break;
                    case "3":
                        binding.etEmail.setError("Please enter a valid email address");
                        binding.etEmail.requestFocus();
                        break;
                    case "4":
                        binding.etPhone.setError("Please enter your phone number");
                        binding.etPhone.requestFocus();
                        break;
                    case "5":
                        binding.etPhone.setError("Please enter a valid phone number");
                        binding.etPhone.requestFocus();
                        break;
                    case "6":
                        binding.etPassword.setError("Please enter your Password");
                        binding.etPassword.requestFocus();
                        break;
                    case "7":
                        binding.etPassword.setError("Password length must be greater than 5");
                        binding.etPassword.requestFocus();
                        break;
                    case "8":
                        binding.btnSignUp.setEnabled(false);
                        break;
                }
            }
        });

        viewModel.getAuthResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {
                binding.btnSignUp.setEnabled(false);
                if (apiResponse != null) {
                    if (apiResponse.isSuccess()) {
                        vaxPreference.setLoginStatus(true);
                        vaxPreference.setEmail(viewModel.email);
                        vaxPreference.setUserId(apiResponse.getId());
                        Log.e("TAG", "onChanged: " + apiResponse.getId());
                        Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                        finishAffinity();
                    } else {
                        Toast.makeText(SignUpActivity.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}