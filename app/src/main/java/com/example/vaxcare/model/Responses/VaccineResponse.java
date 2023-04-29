package com.example.vaxcare.model.Responses;

import com.example.vaxcare.model.Vaccine;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VaccineResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("data")
    @Expose
    private List<Vaccine> vaccineList;

    public VaccineResponse() {
    }

    public VaccineResponse(String message, boolean success, List<Vaccine> vaccineList) {
        this.message = message;
        this.success = success;
        this.vaccineList = vaccineList;
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

    public List<Vaccine> getVaccineList() {
        return vaccineList;
    }

    public void setVaccineList(List<Vaccine> vaccineList) {
        this.vaccineList = vaccineList;
    }
}
