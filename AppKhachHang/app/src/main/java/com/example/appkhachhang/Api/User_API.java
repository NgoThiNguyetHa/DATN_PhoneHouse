package com.example.appkhachhang.Api;

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

public interface User_API {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();


    User_API userApi = new Retrofit.Builder()
            .baseUrl("http://192.168.1.82:8686/khachhangs/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(User_API.class);

    @GET("getAllKhachHang")
    Call<List<User>> getAllUser();

    @POST("addKhachHang")
    Call<User> addUserDK(@Body User user);
}
