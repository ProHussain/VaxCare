package com.example.vaxcare.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.vaxcare.R;
import com.example.vaxcare.databinding.ActivityEditProfileBinding;
import com.example.vaxcare.model.Responses.ApiResponse;
import com.example.vaxcare.viewmodel.EditProfileViewModel;

import java.io.IOException;

public class EditProfileActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_PICK_IMAGE = 121;
    ActivityEditProfileBinding binding;
    EditProfileViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_profile);
        model = new ViewModelProvider(this).get(EditProfileViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(model);

        model.getOnBack().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                    finish();
            }
        });

        model.getApiResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {
                Toast.makeText(EditProfileActivity.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        model.getSelectedImage().observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                if (bitmap != null) {
                    model.uploadImage();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                model.setSelectedImage(bitmap);
                binding.profileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}