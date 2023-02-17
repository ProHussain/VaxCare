package com.example.vaxcare.viewmodel;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vaxcare.model.ApiResponse;
import com.example.vaxcare.model.Appointment;
import com.example.vaxcare.model.Profile;
import com.example.vaxcare.model.SingleAppointmentResponse;
import com.example.vaxcare.model.User;
import com.example.vaxcare.network.MyApi;
import com.example.vaxcare.network.RetrofitClient;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentDetailsViewModel extends ViewModel {
    MutableLiveData<Appointment> appointmentMutableLiveData = new MutableLiveData<>();
    MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    MutableLiveData<User> teamMutableLiveData = new MutableLiveData<>();
    String appointmentId;

    MutableLiveData<Boolean> isDialogVisible = new MutableLiveData<>();

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
        fetchAppointmentData();
    }

    public void fetchAppointmentData() {
        MyApi api = RetrofitClient.getRetrofitInstance().create(MyApi.class);
        api.getSingleAppointment(appointmentId).enqueue(new Callback<SingleAppointmentResponse>() {
            @Override
            public void onResponse(@NonNull Call<SingleAppointmentResponse> call, @NonNull Response<SingleAppointmentResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body()!= null;
                    if (response.body().isSuccess()) {
                        appointmentMutableLiveData.setValue(response.body().getAppointment());
                        fetchUserData();
                        fetchTeamData();
                    }
                } else {
                    Log.e("Appointment Response", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<SingleAppointmentResponse> call, @NonNull Throwable t) {
                Log.e("Appointment Response", String.valueOf(t.getMessage()));
            }
        });
    }

    private void fetchUserData() {
        MyApi api = RetrofitClient.getRetrofitInstance().create(MyApi.class);
        api.getUserProfile(Objects.requireNonNull(appointmentMutableLiveData.getValue()).getUser()).enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(@NonNull Call<Profile> call, @NonNull Response<Profile> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().isSuccess()) {
                        userMutableLiveData.setValue(response.body().getUser());
                    }
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.e("User Response", String.valueOf(t.getMessage()));
            }
        });
    }

    private void fetchTeamData() {
        MyApi api = RetrofitClient.getRetrofitInstance().create(MyApi.class);
        api.getUserProfile(Objects.requireNonNull(appointmentMutableLiveData.getValue()).getTeam()).enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(@NonNull Call<Profile> call, @NonNull Response<Profile> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().isSuccess()) {
                        teamMutableLiveData.setValue(response.body().getUser());
                    }
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.e("User Response", String.valueOf(t.getMessage()));
            }
        });
    }

    public MutableLiveData<Appointment> getAppointmentMutableLiveData() {
        return appointmentMutableLiveData;
    }

    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<User> getTeamMutableLiveData() {
        return teamMutableLiveData;
    }

    public void onUpdateStatusClick(View view) {
        Appointment appointment = appointmentMutableLiveData.getValue();
        assert appointment != null;
        appointment.setStatus("Completed");
        MyApi myApi = RetrofitClient.getRetrofitInstance().create(MyApi.class);
        myApi.updateAppointmentStatus(appointment).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().isSuccess()){
                        appointmentMutableLiveData.setValue(appointment);
                    }
                    Log.e("Response Success", Objects.requireNonNull(response.body()).getMessage());
                } else {
                    assert response.errorBody() != null;
                    Log.e("Response Success", Objects.requireNonNull(response.errorBody().toString()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                Log.e("Response Success", t.getMessage());
            }
        });
    }

    public void onAssignClick(View view) {
        isDialogVisible.setValue(true);
    }

    public void hideDialog() {
        isDialogVisible.setValue(false);
    }

    public MutableLiveData<Boolean> getIsDialogVisible() {
        return isDialogVisible;
    }
}
