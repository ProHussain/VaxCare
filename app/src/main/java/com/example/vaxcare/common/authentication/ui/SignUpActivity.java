package com.example.vaxcare.common.authentication.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vaxcare.R;
import com.example.vaxcare.common.authentication.model.User;
import com.example.vaxcare.common.authentication.viewmodel.SignUpViewModel;
import com.example.vaxcare.common.listener.ActivityFinisher;
import com.example.vaxcare.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity implements ActivityFinisher {
    SignUpViewModel viewModel;
    ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        viewModel.getUserMutableLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                onSignUp(user);
            }
        });
    }

    private void onSignUp(User user) {
        if (TextUtils.isEmpty(user.getName())) {
            binding.etUser.setError("Enter your Name");
            binding.etUser.requestFocus();
        } else if (TextUtils.isEmpty(user.getEmail())) {
            binding.etEmail.setError("Enter an Email Address");
            binding.etEmail.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()) {
            binding.etEmail.setError("Enter a valid Email Address");
            binding.etEmail.requestFocus();
        } else if (TextUtils.isEmpty(user.getPhoneNumber())) {
            binding.etPhone.setError("Enter a Phone Number");
            binding.etPhone.requestFocus();
        } else if (!Patterns.PHONE.matcher(user.getPhoneNumber()).matches()) {
            binding.etPhone.setError("Enter a valid Phone Number");
            binding.etPhone.requestFocus();
        } else if (user.getPassword().length() < 6) {
            binding.etPassword.setError("Enter at least 6 digit Password");
            binding.etPassword.requestFocus();
        } else {
            Toast.makeText(SignUpActivity.this, user.getEmail()+" "+user.getPassword(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityFinish() {
        finish();
    }
}