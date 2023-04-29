package com.example.vaxcare.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class SettingViewModel extends AndroidViewModel {
    MutableLiveData<String> clickData = new MutableLiveData<>();
    public SettingViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getClickData() {
        return clickData;
    }

    public void onShareAppClicked() {
        clickData.setValue("share");
    }

    public void onRateAppClicked() {
        clickData.setValue("rate");
    }

    public void onMoreAppsClicked() {
        clickData.setValue("more_apps");
    }

    public void onSendFeedbackClicked() {
        clickData.setValue("feedback");
    }

    public void onPrivacyPolicyClicked() {
        clickData.setValue("privacy_policy");
    }
}
