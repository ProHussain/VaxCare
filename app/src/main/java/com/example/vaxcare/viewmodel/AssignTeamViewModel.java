package com.example.vaxcare.viewmodel;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.vaxcare.model.AdminList;
import com.example.vaxcare.model.Responses.AdminResponse;
import com.example.vaxcare.model.Responses.AllUserResponse;
import com.example.vaxcare.model.Responses.ApiResponse;
import com.example.vaxcare.model.Appointment;
import com.example.vaxcare.model.User;
import com.example.vaxcare.network.MyApi;
import com.example.vaxcare.network.RetrofitClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Hello there!
 * I made few changes in APi and ViewModel
 * Let me show you
 * I secure API too, let me show you and load more data
 * For security, I add a status
 */

public class AssignTeamViewModel extends AndroidViewModel {

    public Appointment appointment;
    MutableLiveData<ApiResponse> apiResponse = new MutableLiveData<>();
    MutableLiveData<List<User>> teamList = new MutableLiveData<>();

    public AssignTeamViewModel(@NonNull Application application) {
        super(application);
        fetchData();
    }

    private void fetchData() {
        MyApi api = RetrofitClient.getRetrofitInstance().create(MyApi.class);
        api.getTeam().enqueue(new Callback<AllUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<AllUserResponse> call, @NonNull Response<AllUserResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().isSuccess()) {
                        teamList.setValue(response.body().getUserList());
                    } else {
                        Log.e("Team Response", "Failed");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllUserResponse> call, @NonNull Throwable t) {
                Log.e("Team Response", String.valueOf(t.getMessage()));
            }
        });
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public MutableLiveData<ApiResponse> getApiResponse() {
        return apiResponse;
    }

    public MutableLiveData<List<User>> getTeamList() {
        return teamList;
    }

    public void onAssignClick(View view) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(calendar.getTime());
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        String time = timeFormat.format(calendar.getTime());
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setStatus("Assigned");
        MyApi myApi = RetrofitClient.getRetrofitInstance().create(MyApi.class);
        myApi.updateAppointmentStatus(appointment).enqueue(new Callback<ApiResponse>() {
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

}
