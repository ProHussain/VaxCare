package com.example.vaxcare.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.vaxcare.model.AdminList;
import com.example.vaxcare.model.Responses.AdminResponse;
import com.example.vaxcare.model.Responses.AllUserResponse;
import com.example.vaxcare.model.User;
import com.example.vaxcare.model.Vaccine;
import com.example.vaxcare.model.Responses.VaccineResponse;
import com.example.vaxcare.network.MyApi;
import com.example.vaxcare.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminListViewModel extends AndroidViewModel {

    MutableLiveData<List<AdminList>> adminList = new MutableLiveData<>();
    MutableLiveData<List<Vaccine>> vaccineList = new MutableLiveData<>();
    MutableLiveData<List<User>> userList = new MutableLiveData<>();
    MutableLiveData<Boolean> addData = new MutableLiveData<>();
    private String listName;
    public String title;

    public AdminListViewModel(@NonNull Application application) {
        super(application);
    }

    public void setListName(String listName) {
        this.listName = listName;
        title = "Here is all\nVaxCare " + listName;
    }

    public MutableLiveData<List<AdminList>> getAdminList() {
        return adminList;
    }

    public MutableLiveData<List<Vaccine>> getVaccineList() {
        return vaccineList;
    }

    public MutableLiveData<List<User>> getUserList() {
        return userList;
    }

    public MutableLiveData<Boolean> getAddData() {
        return addData;
    }

    public void addData() {
        addData.setValue(true);
    }

    public void fetchData() {
        switch (listName) {
            case "vaccine":
                fetchVaccineList();
                break;
            case "worker":
                fetchWorkerList();
                break;
            case "users":
                fetchUserList();
                break;
            case "request":
                fetchRequestList();
                break;
            case "complete":
                fetchCompleteList();
                break;
            default:
                break;
        }
    }

    private void fetchCompleteList() {
        MyApi api = RetrofitClient.getRetrofitInstance().create(MyApi.class);
        api.getCompleted().enqueue(new Callback<AdminResponse>() {
            @Override
            public void onResponse(@NonNull Call<AdminResponse> call, @NonNull Response<AdminResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().isSuccess()) {
                        adminList.setValue(response.body().getAdminLists());
                    } else {
                        Log.e("Complete Response", "Failed");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AdminResponse> call, @NonNull Throwable t) {
                Log.e("Complete Response", String.valueOf(t.getMessage()));
            }
        });
    }

    private void fetchRequestList() {
        MyApi api = RetrofitClient.getRetrofitInstance().create(MyApi.class);
        api.getRequests().enqueue(new Callback<AdminResponse>() {
            @Override
            public void onResponse(@NonNull Call<AdminResponse> call, @NonNull Response<AdminResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().isSuccess()) {
                        adminList.setValue(response.body().getAdminLists());
                    } else {
                        Log.e("Request Response", "Failed");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AdminResponse> call, @NonNull Throwable t) {
                Log.e("Request Response", String.valueOf(t.getMessage()));
            }
        });
    }

    private void fetchUserList() {
        MyApi api = RetrofitClient.getRetrofitInstance().create(MyApi.class);
        api.getUsers().enqueue(new Callback<AllUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<AllUserResponse> call, @NonNull Response<AllUserResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().isSuccess()) {
                        userList.postValue(response.body().getUserList());
                    } else {
                        Log.e("User Response", "Failed");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllUserResponse> call, @NonNull Throwable t) {
                Log.e("User Response", String.valueOf(t.getMessage()));
            }
        });
    }

    private void fetchWorkerList() {
        MyApi api = RetrofitClient.getRetrofitInstance().create(MyApi.class);
        api.getTeam().enqueue(new Callback<AllUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<AllUserResponse> call, @NonNull Response<AllUserResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().isSuccess()) {
                        userList.postValue(response.body().getUserList());
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

    private void fetchVaccineList() {
        MyApi api = RetrofitClient.getRetrofitInstance().create(MyApi.class);
        api.getVaccines().enqueue(new Callback<VaccineResponse>() {
            @Override
            public void onResponse(@NonNull Call<VaccineResponse> call, @NonNull Response<VaccineResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().isSuccess()) {
                            vaccineList.postValue(response.body().getVaccineList());
                        } else {
                            Log.e("Vaccine Response", "Failed");
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<VaccineResponse> call, @NonNull Throwable t) {
                Log.e("Vaccine Response", String.valueOf(t.getMessage()));
            }
        });
    }

    public void setAddData(boolean b) {
        addData.setValue(b);
    }
}
