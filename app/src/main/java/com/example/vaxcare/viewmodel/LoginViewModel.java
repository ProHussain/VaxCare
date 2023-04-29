package com.example.vaxcare.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.vaxcare.VaxCare;
import com.example.vaxcare.ui.activities.SignUpActivity;
import com.example.vaxcare.network.RetrofitClient;
import com.example.vaxcare.network.MyApi;
import com.example.vaxcare.model.Responses.ApiResponse;
import com.example.vaxcare.utils.VaxPreference;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginViewModel extends AndroidViewModel {
    public String email, password,userType;
    MutableLiveData<String> validFields;
    MutableLiveData<ApiResponse> authResponse;

    VaxPreference preference;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        preference = new VaxPreference(application);
        if (VaxCare.getAppType().equals("admin")) {
            userType = "admin";
        } else {
            userType = "user";
        }
    }


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

    public void onLoginClick() {
        if (isValid()) {
            preference.setUserType(userType);
            MyApi loginApi = RetrofitClient.getRetrofitInstance().create(MyApi.class);
            Call<ApiResponse> userCall = loginApi.postLoginStatus(email, password,userType);
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

    public void onSignUpClick(View view) {
        Context context = view.getContext();
        context.startActivity(new Intent(context, SignUpActivity.class));
    }

    public void onTypeChanged(boolean isChecked) {
        if (isChecked) {
            userType = "user";
        } else {
            userType = "team";
        }
    }

    private boolean isValid() {
        if (TextUtils.isEmpty(email)) {
            validFields.postValue("1");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            validFields.postValue("2");
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
