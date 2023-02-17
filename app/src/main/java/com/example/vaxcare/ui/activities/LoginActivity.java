package com.example.vaxcare.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.vaxcare.R;
import com.example.vaxcare.databinding.ActvityLoginBinding;
import com.example.vaxcare.utils.VaxPreference;
import com.example.vaxcare.viewmodel.LoginViewModel;
import com.example.vaxcare.model.ApiResponse;

public class LoginActivity extends AppCompatActivity {
    LoginViewModel loginViewModel;
    ActvityLoginBinding binding;
    VaxPreference vaxPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding = DataBindingUtil.setContentView(LoginActivity.this,R.layout.actvity_login);
        binding.setLifecycleOwner(this);
        binding.setLoginModel(loginViewModel);
        vaxPreference = new VaxPreference(getApplicationContext());
        loginViewModel.setUserType(vaxPreference.getUserType());
        if (vaxPreference.getUserType().equalsIgnoreCase("admin") || vaxPreference.getUserType().equalsIgnoreCase("team")) {
            binding.TvSignup.setVisibility(View.GONE);
        }
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
                        Log.e("LoginActivity", "onChanged: " + apiResponse.getId());
                        vaxPreference.setLoginStatus(true);
                        vaxPreference.setEmail(loginViewModel.email);
                        vaxPreference.setUserId(apiResponse.getId());
                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        if (vaxPreference.getUserType().equalsIgnoreCase("admin"))
                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        else
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