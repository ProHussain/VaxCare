package com.example.vaxcare.user.viewmodel;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.vaxcare.common.network.MyApi;
import com.example.vaxcare.common.network.RetrofitClient;
import com.example.vaxcare.common.network.model.ApiResponse;
import com.example.vaxcare.common.network.model.Profile;
import com.example.vaxcare.common.network.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileViewModel extends ViewModel {

    MutableLiveData<User> userLiveData;
    MutableLiveData<ApiResponse> apiResponse;
    MutableLiveData<Boolean> onBack = new MutableLiveData<>();
//    MutableLiveData<Boolean> onSave = new MutableLiveData<>();


    public EditProfileViewModel() {
        userLiveData = new MutableLiveData<>();
        apiResponse = new MutableLiveData<>();
        fetchData();
    }

    private void fetchData() {
        MyApi api = RetrofitClient.getRetrofitInstance().create(MyApi.class);
        api.getUserProfile("ali@gmail.com").enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    Profile profile = response.body();
                    assert profile != null;
                    userLiveData.setValue(profile.getUser());
                } else {
                    Log.e("User Response", String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.e("User Response", String.valueOf(t.getMessage()));
            }
        });
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

    public void onImageCaptureClick(View view) {

    }

    public void onBackClick() {
        onBack.setValue(true);
    }

    public void onSaveClick() {
        String image = getProfileImageURL();
        User user = userLiveData.getValue();
        assert user != null;
        user.setEmail("ali@gmail.com");
        user.setImage(image);
        MyApi myApi = RetrofitClient.getRetrofitInstance().create(MyApi.class);
        Call<ApiResponse> responseCall = myApi.postUpdateProfileStatus(user);
        responseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull retrofit2.Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    apiResponse.postValue(response.body());
                } else {
                    apiResponse.setValue(new ApiResponse("Something went wrong",false));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                ApiResponse response = new ApiResponse(t.getMessage(),false);
                apiResponse.setValue(response);
            }
        });

    }

    private String getProfileImageURL() {
        return "https://images.hdqwalls.com/download/scarlett-johansson-black-widow-5b-1280x2120.jpg";
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView)
                .load(url)
                .into(imageView);
    }
}
