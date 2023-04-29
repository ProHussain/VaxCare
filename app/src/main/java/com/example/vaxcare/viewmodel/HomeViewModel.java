package com.example.vaxcare.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.vaxcare.model.Responses.AdminResponse;
import com.example.vaxcare.model.Help;
import com.example.vaxcare.model.Responses.HelpResponse;
import com.example.vaxcare.model.Vaccine;
import com.example.vaxcare.model.Responses.VaccineResponse;
import com.example.vaxcare.network.MyApi;
import com.example.vaxcare.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends AndroidViewModel {
    MutableLiveData<List<Help>> helpLiveData = new MutableLiveData<>();
    MutableLiveData<List<Vaccine>> vaccineLiveData = new MutableLiveData<>();
    MutableLiveData<String> requestLiveData = new MutableLiveData<>();
    MutableLiveData<String> approvedLiveData = new MutableLiveData<>();
    MutableLiveData<String> onClickLiveData = new MutableLiveData<>();

    public HomeViewModel(Application application) {
        super(application);
        fetchData();
    }

    public MutableLiveData<List<Help>> getHelpLiveData() {
        return helpLiveData;
    }

    public MutableLiveData<List<Vaccine>> getVaccineLiveData() {
        return vaccineLiveData;
    }

    public MutableLiveData<String> getRequestLiveData() {
        return requestLiveData;
    }

    public MutableLiveData<String> getApprovedLiveData() {
        return approvedLiveData;
    }

    public MutableLiveData<String> getOnClickLiveData() {
        return onClickLiveData;
    }

    public void onSettingsClicked() {
        onClickLiveData.postValue("settings");
    }

    private void fetchData() {
        // here we can fetch data from server
        MyApi api = RetrofitClient.getRetrofitInstance().create(MyApi.class);
        api.getRequests().enqueue(new Callback<AdminResponse>() {
            @Override
            public void onResponse(@NonNull Call<AdminResponse> call, @NonNull Response<AdminResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().isSuccess()) {
                            requestLiveData.postValue(response.body().getAdminLists().size() + "");
                        } else {
                            Toast.makeText(getApplication(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplication(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AdminResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        api.getCompleted().enqueue(new Callback<AdminResponse>() {
            @Override
            public void onResponse(@NonNull Call<AdminResponse> call, @NonNull Response<AdminResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().isSuccess()) {
                            approvedLiveData.postValue(response.body().getAdminLists().size() + "");
                        } else {
                            Toast.makeText(getApplication(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplication(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AdminResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Vaccine Date here
        // Let's load them from server
        api.getVaccines().enqueue(new Callback<VaccineResponse>() {
            @Override
            public void onResponse(@NonNull Call<VaccineResponse> call, @NonNull Response<VaccineResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().isSuccess()) {
                            vaccineLiveData.postValue(response.body().getVaccineList());
                        } else {
                            Toast.makeText(getApplication(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplication(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<VaccineResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        // Help Data here
        // Let's load them from server
        // 1st create a help data table in Database
        // 2nd create a help api in server
        // 3rd create a help model class
        // Let's load them from server
        api.getHelps().enqueue(new Callback<HelpResponse>() {
            @Override
            public void onResponse(@NonNull Call<HelpResponse> call, @NonNull Response<HelpResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().isSuccess()) {
                            helpLiveData.postValue(response.body().getHelpList());
                        } else {
                            Toast.makeText(getApplication(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplication(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<HelpResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
