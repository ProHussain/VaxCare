package com.example.vaxcare.model.Responses;

import com.example.vaxcare.model.AdminList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdminResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("data")
    @Expose
    private List<AdminList> adminLists;

    public AdminResponse() {
    }

    public AdminResponse(String message, boolean success, List<AdminList> adminLists) {
        this.message = message;
        this.success = success;
        this.adminLists = adminLists;
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

    public List<AdminList> getAdminLists() {
        return adminLists;
    }

    public void setAdminLists(List<AdminList> adminLists) {
        this.adminLists = adminLists;
    }
}
