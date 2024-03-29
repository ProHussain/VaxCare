package com.example.vaxcare.network;

import com.example.vaxcare.model.Responses.AllUserResponse;
import com.example.vaxcare.model.Responses.ApiResponse;
import com.example.vaxcare.model.Appointment;
import com.example.vaxcare.model.Responses.AppointmentResponse;
import com.example.vaxcare.model.Responses.HelpResponse;
import com.example.vaxcare.model.Profile;
import com.example.vaxcare.model.Responses.SingleAppointmentResponse;
import com.example.vaxcare.model.User;
import com.example.vaxcare.model.Responses.AdminResponse;
import com.example.vaxcare.model.Responses.VaccineResponse;

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
    Call<Profile> getUserProfile(@Query("id") String id);

    @PUT("set_appointment")
    Call<ApiResponse> postAppointmentStatus(@Body Appointment appointment);

    @GET("get_appointments")
    Call<AppointmentResponse> getAppointments(@Query("email") String email);

    @GET("get_vaccines")
    Call<VaccineResponse> getVaccines();

    @GET("get_all_team")
    Call<AllUserResponse> getTeam();

    @GET("get_all_users")
    Call<AllUserResponse> getUsers();

    @GET("get_all_requests")
    Call<AdminResponse> getRequests();

    @GET("get_all_completed")
    Call<AdminResponse> getCompleted();
    @FormUrlEncoded
    @POST("delete_vaccine")
    Call<ApiResponse> deleteVaccine( @Field("id") String id);

    // We need to update Api too

    @FormUrlEncoded
    @POST("add_vaccine")
    Call<ApiResponse> addVaccine(@Field("name") String name,
                                 @Field("image") String vaccineImageUrl,
                                 @Field("description") String vaccineDescription);

    @FormUrlEncoded
    @POST("add_team")
    Call<ApiResponse> addTeam( @Field("name") String name,
                               @Field("email") String email,
                               @Field("password") String password);

    @PUT("update_appointment")
    Call<ApiResponse> updateAppointmentStatus(@Body Appointment appointment);

    @GET("get_single_appointment")
    Call<SingleAppointmentResponse> getSingleAppointment(@Query("id") String id);

    @GET("get_helps")
    Call<HelpResponse> getHelps();
}