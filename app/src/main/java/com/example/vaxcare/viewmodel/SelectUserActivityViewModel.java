package com.example.vaxcare.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SelectUserActivityViewModel extends ViewModel {
    MutableLiveData<String> userTypeLiveData = new MutableLiveData<>();

    public MutableLiveData<String> getUserTypeLiveData() {
        return userTypeLiveData;
    }

    public void setUserClick(){
        userTypeLiveData.setValue("user");
    }

    public void setTeamClick(){
        userTypeLiveData.setValue("team");
    }

    public void setAdminClick(){
        userTypeLiveData.setValue("admin");
    }
}
