package com.example.vaxcare.common.network;

import com.example.vaxcare.common.network.model.ApiResponse;
import com.example.vaxcare.common.network.model.Profile;
import com.example.vaxcare.common.network.model.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface MyApi {
    @FormUrlEncoded
    @POST("login")
    Call<ApiResponse> postLoginStatus(@Field("email") String email,
                                      @Field("password") String password);

    @FormUrlEncoded
    @POST("signup")
    Call<ApiResponse> postSignUpStatus(@Field("name") String name,
                                       @Field("email") String email,
                                       @Field("phone") String phone,
                                       @Field("password") String password,
                                       @Field("status") String status);


    @PUT("update_profile")
    Call<ApiResponse> postUpdateProfileStatus(@Body User user);


    @FormUrlEncoded
    @POST("upload_image")
    Call<ApiResponse> uploadImage( @Field("name") String name,
                                   @Field("image") String image,
                                   @Field("email") String email);

    @GET("profile")
    Call<Profile> getUserProfile(@Query("email") String email);

}