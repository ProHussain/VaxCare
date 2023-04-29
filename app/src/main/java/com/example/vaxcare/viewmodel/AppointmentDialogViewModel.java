package com.example.vaxcare.viewmodel;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.vaxcare.model.Responses.ApiResponse;
import com.example.vaxcare.model.Appointment;
import com.example.vaxcare.model.Responses.VaccineResponse;
import com.example.vaxcare.network.MyApi;
import com.example.vaxcare.network.RetrofitClient;
import com.example.vaxcare.utils.VaxPreference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentDialogViewModel extends AndroidViewModel {
    MutableLiveData<List<String>> vaccineList = new MutableLiveData<>();
    MutableLiveData<ApiResponse> apiResponse = new MutableLiveData<>();
    private String vaccineName;
    VaxPreference preference;

    public AppointmentDialogViewModel(@NonNull android.app.Application application) {
        super(application);
        preference = new VaxPreference(application.getApplicationContext());
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
        Appointment appointment = new Appointment(vaccineName, date,time,preference.getUserId(),"","Pending");
        MyApi myApi = RetrofitClient.getRetrofitInstance().create(MyApi.class);
        myApi.postAppointmentStatus(appointment).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    apiResponse.postValue(response.body());
                    Log.e("Response Success", Objects.requireNonNull(response.body()).getMessage());
                } else {
                    try {
                        Log.e("Response Failure", Objects.requireNonNull(response.errorBody()).string());
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

    // We handle this glitch in our Php code
    // I'm not a master of Php let's Ask GPT to solve our problem
    // Chat GPT is great, let's test new code, but first i will explain you what it did
    // I have added a new field in Appointment table called status
    // Now when we request for appointment, we will send status as Pending
    // When we request for appointment, we will send status as Pending
    // we will check sender id, status and and vaccine name in Appointment table
    // if there is no record, we will insert new record otherwise we will show error message
    // Chat GPT code not work, there is some problem in code
    // In fact chat gpt is great source to handle our problems but it's not working sometimes
    // As a developer we need to handle these problems, we need to debug chat gpt code and use for our purpose
    // let's debug chat gpt code or we just get idea from chat gpt code and write our own code

    public void fetchData() {
        // here we fetch vaccine list from server
        MyApi api = RetrofitClient.getRetrofitInstance().create(MyApi.class);
        api.getVaccines().enqueue(new Callback<VaccineResponse>() {
            @Override
            public void onResponse(@NonNull Call<VaccineResponse> call, @NonNull Response<VaccineResponse> response) {
                if (response.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < Objects.requireNonNull(response.body()).getVaccineList().size(); i++) {
                        list.add(response.body().getVaccineList().get(i).getName());
                    }
                    vaccineList.postValue(list);
                } else {
                    try {
                        Log.e("Response Success", Objects.requireNonNull(response.errorBody()).string());
                    } catch (IOException e) {
                        Log.e("Response Exception", e.getMessage());
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<VaccineResponse> call, @NonNull Throwable t) {
                Log.e("Error on Failure", t.getMessage());
            }
        });

        // Let's test our app now
        // Appointments are working fine but we need to show in list too
    }
}
