package com.example.vaxcare.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.vaxcare.model.Help;

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
        helpList.add(new Help("1","Help1","An Expandable RecyclerView is a RecyclerView that can expand and collapse its items to show or hide their child views. Each item in the RecyclerView can contain an expandable section, which can display additional information when expanded. When the user taps on an item, the item can expand or collapse to show or hide its child views. Implementing an Expandable RecyclerView in Android can be done by creating a custom RecyclerView adapter and implementing the expand/collapse functionality in the adapter class."));
        helpList.add(new Help("3","Help2","Description"));
        helpList.add(new Help("3","Help3","Description"));
        helpLiveData.postValue(helpList);
    }
}
