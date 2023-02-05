package com.example.vaxcare.common.authentication.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vaxcare.common.activities.ForgotPasswordActivity;
import com.example.vaxcare.common.authentication.ui.SignUpActivity;
import com.example.vaxcare.common.authentication.model.User;
import com.example.vaxcare.common.listener.ActivityFinisher;

public class LoginViewModel extends ViewModel {
    ActivityFinisher finisher;
    public String email, password;
    MutableLiveData<User> userMutableLiveData;
            ;
    public MutableLiveData<User> getUserMutableLiveData() {
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;
    }
    public void onLoginClick(View view) {
        User user = new User(email,password);
        userMutableLiveData.setValue(user);
    }

    public void onForgotPasswordClick(View view) {
        Context context = view.getContext();
        context.startActivity(new Intent(context, ForgotPasswordActivity.class));
    }

    public void onSignUpClick(View view) {
        Context context = view.getContext();
        context.startActivity(new Intent(context, SignUpActivity.class));
    }

}
