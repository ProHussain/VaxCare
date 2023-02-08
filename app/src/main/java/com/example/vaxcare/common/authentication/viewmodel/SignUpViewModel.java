package com.example.vaxcare.common.authentication.viewmodel;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vaxcare.common.network.MyApi;
import com.example.vaxcare.common.network.RetrofitClient;
import com.example.vaxcare.common.network.model.AuthResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpViewModel extends ViewModel {
    public String name, email, password, phone;
    MutableLiveData<String> validFields;
    MutableLiveData<AuthResponse> authResponse;

    public MutableLiveData<String> getValidFields() {
        if (validFields == null) {
            validFields = new MutableLiveData<>();
        }
        return validFields;
    }

    public MutableLiveData<AuthResponse> getAuthResponse() {
        if (authResponse == null) {
            authResponse = new MutableLiveData<>();
        }
        return authResponse;
    }

    private boolean isValid() {
        if (TextUtils.isEmpty(name)) {
            validFields.postValue("1");
        } else if (TextUtils.isEmpty(email)) {
            validFields.postValue("2");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            validFields.setValue("3");
        } else if (TextUtils.isEmpty(phone)) {
            validFields.postValue("4");
        } else if (!Patterns.PHONE.matcher(phone).matches()) {
            validFields.setValue("5");
        } else if (TextUtils.isEmpty(password)) {
            validFields.postValue("6");
        } else if (password.length() < 6) {
            validFields.postValue("7");
        } else {
            return true;
        }
        return false;
    }

    public void onSignUpClick(View view) {
        if (isValid()) {
            validFields.postValue("8");
            MyApi loginApi = RetrofitClient.getRetrofitInstance().create(MyApi.class);
            Call<AuthResponse> responseCall = loginApi.postSignUpStatus(name, email, phone, password);
            responseCall.enqueue(new Callback<AuthResponse>() {
                @Override
                public void onResponse(@NonNull Call<AuthResponse> call, @NonNull Response<AuthResponse> response) {
                    if (response.isSuccessful()) {
                        authResponse.postValue(response.body());
                    } else {
                        authResponse.postValue(null);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<AuthResponse> call, @NonNull Throwable t) {
                    authResponse.postValue(null);
                }
            });
        }
    }

    public void onLoginClick(View view) {
    }
}
