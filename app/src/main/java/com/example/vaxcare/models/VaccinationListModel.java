package com.example.vaxcare.models;

public class VaccinationListModel {
    private int lImageResource;
    private String lTitle;

    public int getlImageResource() {
        return lImageResource;
    }

    public void setlImageResource(int lImageResource) {
        this.lImageResource = lImageResource;
    }

    public String getlTitle() {
        return lTitle;
    }

    public void setlTitle(String lTitle) {
        this.lTitle = lTitle;
    }

    public VaccinationListModel(int lImageResource, String lTitle) {
        this.lImageResource = lImageResource;
        this.lTitle = lTitle;
    }
}
