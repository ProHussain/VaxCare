package com.example.vaxcare.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.vaxcare.R;
import com.example.vaxcare.databinding.ActvityLoginBinding;
import com.example.vaxcare.viewmodel.LoginViewModel;
import com.example.vaxcare.model.ApiResponse;

public class LoginActivity extends AppCompatActivity {
    LoginViewModel loginViewModel;
    ActvityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding = DataBindingUtil.setContentView(LoginActivity.this,R.layout.actvity_login);
        binding.setLifecycleOwner(this);
        binding.setLoginModel(loginViewModel);
        loginViewModel.getValidFields().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s) {
                    case "1":
                        binding.etEmail.setError("Please enter your email address");
                        binding.etEmail.requestFocus();
                        break;
                    case "2":
                        binding.etEmail.setError("Please enter a valid email address");
                        binding.etEmail.requestFocus();
                        break;
                    case "3":
                        binding.etPassword.setError("Please enter your Password");
                        binding.etPassword.requestFocus();
                        break;
                    case "4":
                        binding.etPassword.setError("Password length must be greater than 5");
                        binding.etPassword.requestFocus();
                        break;
                    case "5":
                        binding.btnLogin.setEnabled(false);
                        break;
                }
            }
        });

        loginViewModel.getAuthResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {
                binding.btnLogin.setEnabled(true);
                if (apiResponse != null) {
                    if (apiResponse.isSuccess()) {
                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}