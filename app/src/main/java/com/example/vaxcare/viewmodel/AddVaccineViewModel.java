package com.example.vaxcare.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.vaxcare.model.Responses.ApiResponse;
import com.example.vaxcare.network.MyApi;
import com.example.vaxcare.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddVaccineViewModel extends AndroidViewModel {
    public String vaccineName, vaccineImageUrl, vaccineDescription;
    public MutableLiveData<Boolean> isAdded = new MutableLiveData<>();
    public AddVaccineViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getIsAdded() {
        return isAdded;
    }

    public void addVaccine() {
        if (TextUtils.isEmpty(vaccineName) || TextUtils.isEmpty(vaccineImageUrl) || TextUtils.isEmpty(vaccineDescription)) {
            Toast.makeText(getApplication(), "Please enter vaccine info", Toast.LENGTH_SHORT).show();
        } else {
            MyApi api = RetrofitClient.getRetrofitInstance().create(MyApi.class);
            api.addVaccine(vaccineName, vaccineImageUrl, vaccineDescription).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().isSuccess()) {
                                isAdded.setValue(true);
                            } else {
                                Log.e("AddVaccineViewModel", "Failed to add vaccine");
                            }
                        }
                    } else {
                        Log.e("AddVaccineViewModel", "Failed to add vaccine");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                    Log.e("AddVaccineViewModel", "Failed to add vaccine");
                }
            });
        }
    }
}
