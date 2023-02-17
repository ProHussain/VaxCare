package com.example.vaxcare.viewmodel;

import android.app.Application;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.vaxcare.model.ApiResponse;
import com.example.vaxcare.network.MyApi;
import com.example.vaxcare.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTeamViewModel extends AndroidViewModel {

    public String name,email, password;
    public MutableLiveData<Boolean> isAdded = new MutableLiveData<>();

    public AddTeamViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getIsAdded() {
        return isAdded;
    }

    public void addTeam() {
        if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
            Toast.makeText(getApplication(), "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplication(), "Please enter valid email", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Toast.makeText(getApplication(), "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
        } else {
            MyApi api = RetrofitClient.getRetrofitInstance().create(MyApi.class);
            api.addTeam(name,email, password).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        if (response.body().isSuccess()) {
                            isAdded.setValue(true);
                        } else {
                            Log.e("AddTeamViewModel", response.body().getMessage());
                        }
                    } else {
                        Log.e("AddTeamViewModel", response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                    Log.e("AddTeamViewModel", t.getMessage());
                }
            });
        }
    }
}
