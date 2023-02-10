package com.example.vaxcare.model;

public class Help {
    private String position, title, description;

    public Help(String position, String title, String description) {
        this.position = position;
        this.title = title;
        this.description = description;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
