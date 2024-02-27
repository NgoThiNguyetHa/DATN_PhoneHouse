package com.example.appkhachhang.Api;

import com.example.appkhachhang.Model.SanPham;
import com.example.appkhachhang.Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface SanPham_API {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();


    SanPham_API sanPhamApi = new Retrofit.Builder()
            .baseUrl("http://192.168.1.82:8686/dienthoais/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(SanPham_API.class);

    @GET("getDienThoai")
    Call<List<SanPham>> getAllSanPham();

}
