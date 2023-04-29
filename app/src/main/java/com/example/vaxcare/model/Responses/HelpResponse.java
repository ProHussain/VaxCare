package com.example.vaxcare.model.Responses;

import com.example.vaxcare.model.Help;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HelpResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("data")
    @Expose
    private List<Help> helpList;

    public HelpResponse() {
    }

    public HelpResponse(String message, boolean success, List<Help> helpList) {
        this.message = message;
        this.success = success;
        this.helpList = helpList;
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

    public List<Help> getHelpList() {
        return helpList;
    }

    public void setHelpList(List<Help> helpList) {
        this.helpList = helpList;
    }
}
