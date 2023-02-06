package com.example.vaxcare.models;

public class VaccinationTypeModel {
    private int mImageResource;
    private String mTitle;

    public int getmImageResource() {
        return mImageResource;
    }

    public void setmImageResource(int mImageResource) {
        this.mImageResource = mImageResource;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public VaccinationTypeModel(int mImageResource, String mTitle) {
        this.mImageResource = mImageResource;
        this.mTitle = mTitle;
    }
}
