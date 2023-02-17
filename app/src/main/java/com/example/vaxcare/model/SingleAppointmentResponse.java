package com.example.vaxcare.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleAppointmentResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("data")
    @Expose
    private Appointment appointment;

    public SingleAppointmentResponse() {
    }

    public SingleAppointmentResponse(String message, boolean success, Appointment appointment) {
        this.message = message;
        this.success = success;
        this.appointment = appointment;
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

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
