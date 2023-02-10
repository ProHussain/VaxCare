package com.example.vaxcare.viewmodel;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vaxcare.model.ApiResponse;
import com.example.vaxcare.model.Appointment;
import com.example.vaxcare.network.MyApi;
import com.example.vaxcare.network.RetrofitClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentDialogViewModel extends ViewModel {
    MutableLiveData<List<String>> vaccineList = new MutableLiveData<>();
    MutableLiveData<ApiResponse> apiResponse = new MutableLiveData<>();
    private String vaccineName;

    public AppointmentDialogViewModel() {
        fetchData();
    }

    public MutableLiveData<List<String>> getVaccineList() {
        return vaccineList;
    }

    public MutableLiveData<ApiResponse> getApiResponse() {
        if (apiResponse == null)
            apiResponse = new MutableLiveData<>();
        return apiResponse;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public void onRequestClick(View view) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(calendar.getTime());
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        String time = timeFormat.format(calendar.getTime());
        Appointment appointment = new Appointment(vaccineName, date,time,"ali@gmail.com","","Pending");
        MyApi myApi = RetrofitClient.getRetrofitInstance().create(MyApi.class);
        myApi.postAppointmentStatus(appointment).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    apiResponse.postValue(response.body());;
                    Log.e("Response Success", Objects.requireNonNull(response.body()).getMessage());
                } else {
                    try {
                        Log.e("Response Success", Objects.requireNonNull(response.errorBody()).string());
                        apiResponse.setValue(new ApiResponse(response.errorBody().string(), false));
                    } catch (IOException e) {
                        Log.e("Response Exception", e.getMessage());
                        apiResponse.setValue(new ApiResponse("Something went wrong", false));
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                ApiResponse response = new ApiResponse(t.getMessage(), false);
                apiResponse.setValue(response);
                Log.e("Error on Failure", t.getMessage());
            }
        });
    }

    public void fetchData() {
        List<String> list = new ArrayList<>();
        list.add("Vax 1");
        list.add("Vax 2");
        list.add("Vax 3");
        list.add("Vax 4");
        vaccineList.setValue(list);
    }
}
