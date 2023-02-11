package com.example.vaxcare.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Appointment implements Serializable {
    @SerializedName("appointment_id")
    @Expose
    private String id;
    @SerializedName("vaccine_name")
    @Expose
    private String name;
    @SerializedName("appointment_date")
    @Expose
    private String date;
    @SerializedName("appointment_time")
    @Expose
    private String time;
    @SerializedName("appointment_user")
    @Expose
    private String user;
    @SerializedName("appointment_team")
    @Expose
    private String team;
    @SerializedName("appointment_status")
    @Expose
    private String status;

    public Appointment() {
    }

    public Appointment(String name, String date, String time, String user, String team, String status) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.user = user;
        this.team = team;
        this.status = status;
    }

    public Appointment(String id, String name, String date, String time, String user, String team, String status) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.user = user;
        this.team = team;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
