package com.example.vaxcare.viewmodel;

import static com.example.vaxcare.ui.activities.EditProfileActivity.REQUEST_CODE_PICK_IMAGE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.vaxcare.network.MyApi;
import com.example.vaxcare.network.RetrofitClient;
import com.example.vaxcare.model.ApiResponse;
import com.example.vaxcare.model.Profile;
import com.example.vaxcare.model.User;
import com.example.vaxcare.utils.VaxPreference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileViewModel extends AndroidViewModel {

    MutableLiveData<User> userLiveData = new MutableLiveData<>();
    MutableLiveData<ApiResponse> apiResponse = new MutableLiveData<>();
    MutableLiveData<Boolean> onBack = new MutableLiveData<>();
    private final MutableLiveData<Bitmap> selectedImage = new MutableLiveData<>();
    VaxPreference preference;

    public EditProfileViewModel(@NonNull android.app.Application application) {
        super(application);
        preference = new VaxPreference(application.getApplicationContext());
        fetchData();
    }

    public LiveData<Bitmap> getSelectedImage() {
        return selectedImage;
    }

    public void setSelectedImage(Bitmap bitmap) {
        selectedImage.setValue(bitmap);
    }

    public MutableLiveData<ApiResponse> getApiResponse() {
        if (apiResponse == null)
            apiResponse = new MutableLiveData<>();
        return apiResponse;
    }

    public MutableLiveData<User> getUserLiveData() {
        if (userLiveData == null)
            userLiveData = new MutableLiveData<>();
        return userLiveData;
    }

    public MutableLiveData<Boolean> getOnBack() {
        return onBack;
    }

    public void fetchData() {
        MyApi api = RetrofitClient.getRetrofitInstance().create(MyApi.class);
        api.getUserProfile(preference.getUserId()).enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(@NonNull Call<Profile> call, @NonNull Response<Profile> response) {
                if (response.isSuccessful()) {
                    Profile profile = response.body();
                    assert profile != null;
                    userLiveData.setValue(profile.getUser());
                } else {
                    Log.e("User Response", String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Profile> call, @NonNull Throwable t) {
                Log.e("User Response", String.valueOf(t.getMessage()));
            }
        });
    }

    public void onImageCaptureClick(View view) {
        pickImageFromGallery(view.getContext());
    }

    public void pickImageFromGallery(Context context) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        ((Activity) context).startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_PICK_IMAGE);
    }

    public void uploadImage() {
        Bitmap bitmap = selectedImage.getValue();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            String imgname = String.valueOf(Calendar.getInstance().getTimeInMillis());
            MyApi myApi = RetrofitClient.getRetrofitInstance().create(MyApi.class);
            Call<ApiResponse> call = myApi.uploadImage(imgname,encodedImage, Objects.requireNonNull(userLiveData.getValue()).getEmail());
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        apiResponse.postValue(response.body());
                        Log.e("Response Success", Objects.requireNonNull(response.body()).getMessage());
                    } else {
                        try {
                            Log.e("Response Success", Objects.requireNonNull(response.errorBody()).string());
                        } catch (IOException e) {
                            Log.e("Response Exception", e.getMessage());
                            throw new RuntimeException(e);
                        }
                        apiResponse.setValue(new ApiResponse("Something went wrong", false));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                    ApiResponse response = new ApiResponse(t.getMessage(), false);
                    apiResponse.setValue(response);
                    Log.e("Error on Failure", t.getMessage());
                }
            });
        }


    }

    public void onBackClick() {
        onBack.setValue(true);
    }

    public void onSaveClick() {
        User user = userLiveData.getValue();
        assert user != null;
        MyApi myApi = RetrofitClient.getRetrofitInstance().create(MyApi.class);
        Call<ApiResponse> responseCall = myApi.postUpdateProfileStatus(user);
        responseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull retrofit2.Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    apiResponse.postValue(response.body());
                } else {
                    apiResponse.setValue(new ApiResponse("Something went wrong", false));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                ApiResponse response = new ApiResponse(t.getMessage(), false);
                apiResponse.setValue(response);
            }
        });

    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView)
                .load(url)
                .into(imageView);
    }
}
