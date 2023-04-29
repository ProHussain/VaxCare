package com.example.vaxcare.model.Responses;

import com.example.vaxcare.model.AdminList;
import com.example.vaxcare.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllUserResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("data")
    @Expose
    private List<User> userList;

    public AllUserResponse() {
    }

    public AllUserResponse(String message, boolean success, List<User> userList) {
        this.message = message;
        this.success = success;
        this.userList = userList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
