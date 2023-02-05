package com.example.vaxcare.common.authentication.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vaxcare.common.authentication.model.User;
import com.example.vaxcare.common.authentication.ui.LoginActivity;
import com.example.vaxcare.common.listener.ActivityFinisher;

public class SignUpViewModel extends ViewModel {
    public String name, email, password, phone;
    public MutableLiveData<User> userMutableLiveData;
    public ActivityFinisher finisher;

    public MutableLiveData<User> getUserMutableLiveData() {
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<User>();
        }
        return userMutableLiveData;
    }

    public void onSignUpClick(View view) {
        User user = new User(name,email,password,phone);
        userMutableLiveData.setValue(user);
    }

    public void onLoginClick(View view) {
        Context context = view.getContext();
        finisher = (ActivityFinisher) context;
        finisher.onActivityFinish();
    }
}
