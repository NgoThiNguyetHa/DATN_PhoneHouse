package com.example.appkhachhang.Api;

import com.example.appkhachhang.Model.ChiTietDienThoai;
import com.example.appkhachhang.Model.HangSanXuat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ChiTietSanPham_API {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();


    ChiTietSanPham_API chiTietSanPhamApi = new Retrofit.Builder()
            .baseUrl("http://192.168.1.19:8686/chitietdienthoais/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ChiTietSanPham_API.class);

    @GET("getChiTiet")
    Call<List<ChiTietDienThoai>> getChiTiet();
}
