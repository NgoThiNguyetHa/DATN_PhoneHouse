package com.example.appkhachhang.Api;

import com.example.appkhachhang.Model.SanPham;
import com.example.appkhachhang.Model.SanPhamHot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ThongKe_API {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();


    ThongKe_API thongKeApi = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8686/thongke/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ThongKe_API.class);

    @GET("getDienThoaiHotNhat")
    Call<List<SanPhamHot>> getSanPhamHot();
}
