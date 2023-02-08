package com.example.vaxcare.user.viewmodel;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.vaxcare.common.network.MyApi;
import com.example.vaxcare.common.network.RetrofitClient;
import com.example.vaxcare.common.network.model.Profile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {
    MutableLiveData<Profile> profileLiveData;

    public ProfileViewModel() {
        profileLiveData = new MutableLiveData<>();
        fetchData();
    }

    public MutableLiveData<Profile> getProfileLiveData(){
        return profileLiveData;
    }

    private void fetchData() {
        MyApi api = RetrofitClient.getRetrofitInstance().create(MyApi.class);
        api.getUserProfile("ali@gmail.com").enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    Profile profile = response.body();
                    profileLiveData.setValue(profile);
                } else {
                    Log.e("User Response", String.valueOf(response.errorBody()));
                    profileLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.e("User Response", String.valueOf(t.getMessage()));
                profileLiveData.setValue(null);
            }
        });
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView)
                .load(url)
                .into(imageView);
    }

    public void onSettingClick(View view) {

    }
}
