package com.example.vaxcare.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class DashboardViewModel extends AndroidViewModel {
    MutableLiveData<String> mClick = new MutableLiveData<>();
    public DashboardViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getClick() {
        return mClick;
    }

    public void setClick(View click) {
        mClick.setValue(click.getTag().toString());
    }
}
