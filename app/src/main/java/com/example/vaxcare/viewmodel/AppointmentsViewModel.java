package com.example.vaxcare.viewmodel;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vaxcare.model.Appointment;
import com.example.vaxcare.model.AppointmentResponse;
import com.example.vaxcare.network.MyApi;
import com.example.vaxcare.network.RetrofitClient;
import com.example.vaxcare.utils.VaxPreference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentsViewModel extends AndroidViewModel {
    MutableLiveData<List<Appointment>> appointmentsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isDialogVisible = new MutableLiveData<>();
    VaxPreference preference;

    public AppointmentsViewModel(@NonNull android.app.Application application) {
        super(application);
        preference = new VaxPreference(application.getApplicationContext());
        fetchData();
    }
    public MutableLiveData<List<Appointment>> getAppointmentsLiveData() {
        if (appointmentsLiveData == null)
            appointmentsLiveData = new MutableLiveData<>();
        return appointmentsLiveData;
    }

    public MutableLiveData<Boolean> getIsDialogVisible() {
        return isDialogVisible;
    }

    public void onAddClick(View view) {
        showDialog();
    }

    public void showDialog() {
        isDialogVisible.setValue(true);
    }

    public void hideDialog() {
        isDialogVisible.setValue(false);
    }

    private void fetchData() {
        List<Appointment> appointmentList = new ArrayList<>();
        MyApi myApi = RetrofitClient.getRetrofitInstance().create(MyApi.class);
        myApi.getAppointments(preference.getUserId()).enqueue(new Callback<AppointmentResponse>() {
            @Override
            public void onResponse(@NonNull Call<AppointmentResponse> call, @NonNull Response<AppointmentResponse> response) {
                if (response.isSuccessful()) {
                    AppointmentResponse appointmentResponse = response.body();
                    assert appointmentResponse != null;
                    if (appointmentResponse.isSuccess()) {
                        appointmentList.addAll(appointmentResponse.getAppointmentList());
                        appointmentsLiveData.setValue(appointmentList);
                    }
                    Log.e("Response Success", Objects.requireNonNull(response.body()).getMessage());
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
            public void onFailure(@NonNull Call<AppointmentResponse> call, @NonNull Throwable t) {
                Log.e("Error on Failure", t.getMessage());
            }
        });
    }
}
