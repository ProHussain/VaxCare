package com.example.vaxcare.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.vaxcare.R;
import com.example.vaxcare.utils.VaxPreference;
import com.example.vaxcare.viewmodel.SignUpViewModel;
import com.example.vaxcare.databinding.ActivitySignUpBinding;

/**
 * Let's test UI and functionality of SignUp Page
 * SignUp Page Complete
 * It's enough for today, Let's continue tomorrow
 * We will redesign the Home Page tomorrow
 * Thank you for watching
 * Good Bye and Happy Coding
 */
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
        viewModel.getValidFields().observe(this, s -> {
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
                    binding.btnSignup.setEnabled(false);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + s);
            }
        });

        viewModel.getAuthResponse().observe(this, apiResponse -> {
            binding.btnSignup.setEnabled(false);
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
        });
    }


}