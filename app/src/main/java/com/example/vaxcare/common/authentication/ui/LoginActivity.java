package com.example.vaxcare.common.authentication.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.vaxcare.R;
import com.example.vaxcare.common.authentication.model.User;
import com.example.vaxcare.common.authentication.viewmodel.LoginViewModel;
import com.example.vaxcare.databinding.ActvityLoginBinding;

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
        loginViewModel.getUserMutableLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (TextUtils.isEmpty(user.getEmail())) {
                    binding.etEmail.setError("Enter an Email Address");
                    binding.etEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()) {
                    binding.etEmail.setError("Enter a valid Email Address");
                    binding.etEmail.requestFocus();
                } else if (user.getPassword().length() < 6) {
                    binding.etPassword.setError("Enter at least 6 digit Password");
                    binding.etPassword.requestFocus();
                } else {
                    Toast.makeText(LoginActivity.this, user.getEmail()+" "+user.getPassword(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}