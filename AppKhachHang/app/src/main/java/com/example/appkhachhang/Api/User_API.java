package com.example.appkhachhang.Api;

import com.example.appkhachhang.Model.ChangePassword;
import com.example.appkhachhang.Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface User_API {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();


    User_API userApi = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8686/khachhangs/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(User_API.class);

    @GET("getAllKhachHang")
    Call<List<User>> getAllUser();

    @POST("addKhachHang")
    Call<User> addUserDK(@Body User user);

    @PUT("doiMatKhau")
    Call<User> updateMatKhau(@Body ChangePassword changePassword);
}
