package com.example.vaxcare.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.vaxcare.network.MyApi;
import com.example.vaxcare.network.RetrofitClient;
import com.example.vaxcare.model.Profile;
import com.example.vaxcare.ui.activities.EditProfileActivity;
import com.example.vaxcare.utils.VaxPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends AndroidViewModel {
    MutableLiveData<Profile> profileLiveData;
    VaxPreference preference;

    public ProfileViewModel(android.app.Application application) {
        super(application);
        preference = new VaxPreference(application.getApplicationContext());
    }
    public MutableLiveData<Profile> getProfileLiveData(){
        if (profileLiveData == null)
            profileLiveData = new MutableLiveData<>();
        return profileLiveData;
    }

    public void fetchData() {
        MyApi api = RetrofitClient.getRetrofitInstance().create(MyApi.class);
        api.getUserProfile(preference.getEmail()).enqueue(new Callback<Profile>() {
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
        Context context = view.getContext();
        context.startActivity(new Intent(context, EditProfileActivity.class));
    }
}
