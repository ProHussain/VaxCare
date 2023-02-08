package com.example.vaxcare.user.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.vaxcare.common.network.model.Help;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    MutableLiveData<List<Help>> helpLiveData;

    public HomeViewModel(Application application) {
        super(application);
        helpLiveData = new MutableLiveData<>();
        fetchData();
    }

    public MutableLiveData<List<Help>> getHelpLiveData() {
        return helpLiveData;
    }

    private void fetchData() {
        List<Help> helpList = new ArrayList<>();
        helpList.add(new Help("1","Help1","Description"));
        helpList.add(new Help("3","Help2","Description"));
        helpList.add(new Help("3","Help3","Description"));
        helpLiveData.postValue(helpList);
    }
}
