package com.example.vaxcare.network;

import com.example.vaxcare.model.ApiResponse;
import com.example.vaxcare.model.Appointment;
import com.example.vaxcare.model.AppointmentResponse;
import com.example.vaxcare.model.Profile;
import com.example.vaxcare.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface MyApi {
    @FormUrlEncoded
    @POST("login")
    Call<ApiResponse> postLoginStatus(@Field("email") String email,
                                      @Field("password") String password,
                                      @Field("status") String status);

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

    @PUT("set_appointment")
    Call<ApiResponse> postAppointmentStatus(@Body Appointment appointment);

    @GET("get_appointments")
    Call<AppointmentResponse> getAppointments(@Query("email") String email);

    @PUT("update_appointment")
    Call<ApiResponse> updateAppointmentStatus(@Body Appointment appointment);
}