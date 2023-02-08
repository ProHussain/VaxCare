package com.example.vaxcare.common.network;

import com.example.vaxcare.common.network.model.AuthResponse;
import com.example.vaxcare.common.network.model.Profile;
import com.example.vaxcare.common.network.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MyApi {
    @FormUrlEncoded
    @POST("login")
    Call<AuthResponse> postLoginStatus(@Field("email") String email,
                                       @Field("password") String password);

    @FormUrlEncoded
    @POST("signup")
    Call<AuthResponse> postSignUpStatus(@Field("name") String name,
                                        @Field("email") String email,
                                        @Field("phone") String phone,
                                        @Field("password") String password);

    @GET("profile")
    Call<Profile> getUserProfile(@Query("email") String email);
}