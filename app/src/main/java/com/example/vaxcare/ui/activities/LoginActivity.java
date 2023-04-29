package com.example.vaxcare.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.vaxcare.R;
import com.example.vaxcare.VaxCare;
import com.example.vaxcare.databinding.ActvityLoginBinding;
import com.example.vaxcare.utils.VaxPreference;
import com.example.vaxcare.viewmodel.LoginViewModel;

/**
 * I don't like this design, Let's copy Design from the other project instead of write new one
 * Let's test new UI
 * Logcat not working, Let's restart Android Studio
 * Let's our new Login Page
 * Login Page Complete, Let's move on SignUp Page
 * Let's we user and Team app after changes
 * Everything is working fine, Let's push the code
 * But first need to update the appointment automatically
 * Hey Again thanks for watching, if you like this video then please like and subscribe to our channel
 * I hope you will like this series and understand hwo to deal with old projects
 * See you in next Series, Bye Bye and Happy Coding :)
 */

public class LoginActivity extends AppCompatActivity {
    LoginViewModel loginViewModel;
    ActvityLoginBinding binding;
    VaxPreference vaxPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding = DataBindingUtil.setContentView(LoginActivity.this,R.layout.actvity_login);
        binding.setLifecycleOwner(this);
        binding.setLoginModel(loginViewModel);

        if (VaxCare.getAppType().equals("admin")) {
            hideUIForAdmin();
        }

        vaxPreference = new VaxPreference(this);
        loginViewModel.getValidFields().observe(this, s -> {
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
                default:
                    throw new IllegalStateException("Unexpected value: " + s);
            }
        });

        loginViewModel.getAuthResponse().observe(this, apiResponse -> {
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
        });
    }

    private void hideUIForAdmin() {
        binding.radioGroup.setVisibility(View.GONE);
        binding.btnLogin.setText("Login as Admin");
        binding.tvHintSignup.setVisibility(View.GONE);
        binding.tvSignup.setVisibility(View.GONE);
        binding.tvOr.setVisibility(View.GONE);
        binding.view.setVisibility(View.GONE);
        binding.view1.setVisibility(View.GONE);
    }
}