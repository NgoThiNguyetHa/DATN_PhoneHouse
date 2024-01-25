package com.example.appcuahang.api;

import com.example.appcuahang.model.Brand;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("hangsanxuats/getHangSanXuat")
    Call<List<Brand>> getHangSanXuat();

    @POST("hangsanxuats/getHangSanXuat")
    Call<List<Brand>> postHangSanXuat();

    @GET("hangsanxuats/getHangSanXuat")
    Call<List<Brand>> getHoaDon();
}
