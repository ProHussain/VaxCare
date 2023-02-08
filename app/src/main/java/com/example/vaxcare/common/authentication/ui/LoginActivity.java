package com.example.vaxcare.common.authentication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.vaxcare.R;
import com.example.vaxcare.common.authentication.viewmodel.LoginViewModel;
import com.example.vaxcare.common.network.model.AuthResponse;
import com.example.vaxcare.databinding.ActvityLoginBinding;
import com.example.vaxcare.user.ui.activities.HomeActivity;

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

        loginViewModel.getAuthResponse().observe(this, new Observer<AuthResponse>() {
            @Override
            public void onChanged(AuthResponse authResponse) {
                binding.btnLogin.setEnabled(true);
                if (authResponse != null) {
                    if (authResponse.isSuccess()) {
                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, authResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}