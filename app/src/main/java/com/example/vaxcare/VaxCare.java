package com.example.vaxcare;

import android.app.Application;

public class VaxCare extends Application {
    // We will use this App Type to check if user is admin or team member,
    private static String appType;
    @Override
    public void onCreate() {
        super.onCreate();
        appType = "user";
    }

    public static String getAppType() {
        return appType;
    }

    public static void setAppType(String appType) {
        VaxCare.appType = appType;
    }
}
