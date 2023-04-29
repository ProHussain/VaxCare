package com.example.vaxcare.model;

import android.widget.ImageView;

import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.vaxcare.R;

public class Vaccine {
    private int id;
    private String name;
    private String description;
    private String image;

    public Vaccine() {
    }

    public Vaccine(int id, String name,String image, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .thumbnail(Glide.with(imageView).load(url).override(25))
                .placeholder(R.drawable.covid)
                .into(imageView);
    }
}
