package com.example.appkhachhang.Api;

import com.example.appkhachhang.Model.HangSanXuat;
import com.example.appkhachhang.Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface HangSanXuat_API {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();


    HangSanXuat_API hangSXApi = new Retrofit.Builder()
            .baseUrl("http://192.168.1.18:8686/hangsanxuats/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(HangSanXuat_API.class);

    @GET("getHangSanXuat")
    Call<List<HangSanXuat>> getHangSanXuat();

}
