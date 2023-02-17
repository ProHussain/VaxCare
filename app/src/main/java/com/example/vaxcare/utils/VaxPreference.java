package com.example.vaxcare.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class VaxPreference {
    private static final String PREF_NAME = "VaxPreference";
    private static final String EMAIL = "email";

    private static final String USER_ID = "user_id";
    private static final String USER_TYPE = "user_type";
    private static final String LOGIN_STATUS = "login_status";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public VaxPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setEmail(String email) {
        editor.putString(EMAIL, email);
        editor.apply();
    }

    public String getEmail() {
        return sharedPreferences.getString(EMAIL, "");
    }

    public void setUserType(String userType) {
        editor.putString(USER_TYPE, userType);
        editor.apply();
    }

    public String getUserType() {
        return sharedPreferences.getString(USER_TYPE, "");
    }

    public void setLoginStatus(boolean loginStatus) {
        editor.putBoolean(LOGIN_STATUS, loginStatus);
        editor.apply();
    }

    public boolean getLoginStatus() {
        return sharedPreferences.getBoolean(LOGIN_STATUS, false);
    }

    public void setUserId(String userId) {
        editor.putString(USER_ID, userId);
        editor.apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(USER_ID, "");
    }
}