package com.example.vaxcare.viewmodel;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vaxcare.network.MyApi;
import com.example.vaxcare.network.RetrofitClient;
import com.example.vaxcare.model.ApiResponse;

import retrofit2.Call;
import retrofit2.Callback;

public class SignUpViewModel extends ViewModel {
    public String name, email, password, phone,status;
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
            status = "user";
            MyApi loginApi = RetrofitClient.getRetrofitInstance().create(MyApi.class);
            Call<ApiResponse> responseCall = loginApi.postSignUpStatus(name, email, phone, password,status);
            responseCall.enqueue(new Callback<ApiResponse>() {
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

    public void onLoginClick(View view) {
    }
}
