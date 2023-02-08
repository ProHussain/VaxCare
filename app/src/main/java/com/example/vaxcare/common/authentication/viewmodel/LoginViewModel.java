package com.example.vaxcare.common.authentication.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vaxcare.common.activities.ForgotPasswordActivity;
import com.example.vaxcare.common.authentication.ui.SignUpActivity;
import com.example.vaxcare.common.network.RetrofitClient;
import com.example.vaxcare.common.network.MyApi;
import com.example.vaxcare.common.network.model.ApiResponse;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginViewModel extends ViewModel {
    public String email, password;
    MutableLiveData<String> validFields;
    MutableLiveData<ApiResponse> authResponse;

    public MutableLiveData<String> getValidFields() {
        if (validFields == null) {
            validFields = new MutableLiveData<>();
        }
        return validFields;
    }

    public MutableLiveData<ApiResponse> getAuthResponse() {
        if (authResponse == null) {
            authResponse = new MutableLiveData<>();
        }
        return authResponse;
    }

    public void onLoginClick(View view) {
        if (isValid()) {
            MyApi loginApi = RetrofitClient.getRetrofitInstance().create(MyApi.class);
            Call<ApiResponse> userCall = loginApi.postLoginStatus(email, password);
            userCall.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<ApiResponse> call, @NonNull retrofit2.Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        authResponse.postValue(response.body());
                    } else {
                        authResponse.postValue(null);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                    authResponse.postValue(null);
                }
            });
        }
    }

    public void onForgotPasswordClick(View view) {
        Context context = view.getContext();
        context.startActivity(new Intent(context, ForgotPasswordActivity.class));
    }

    public void onSignUpClick(View view) {
        Context context = view.getContext();
        context.startActivity(new Intent(context, SignUpActivity.class));
    }

    private boolean isValid() {
        if (TextUtils.isEmpty(email)) {
            validFields.postValue("1");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            validFields.setValue("2");
        } else if (TextUtils.isEmpty(password)) {
            validFields.postValue("3");
        } else if (password.length() < 6) {
            validFields.postValue("4");
        } else {
            return true;
        }
        return false;
    }
}
