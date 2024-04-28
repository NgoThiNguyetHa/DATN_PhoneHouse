package com.example.appkhachhang.Api;


import com.example.appkhachhang.Model.Root;
import com.example.appkhachhang.Model.SearchResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ChiTietSanPham_API {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();


    ChiTietSanPham_API chiTietSanPhamApi = new Retrofit.Builder()
            .baseUrl("https://datn-phonehouse.onrender.com/chitietdienthoais/")
//            .baseUrl("http://192.168.1.170:8686/chitietdienthoais/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ChiTietSanPham_API.class);

    @GET("getChiTiet")
    Call<List<Root>> getChiTiet();

    @GET("searchDienThoaiVaCuaHang")
    Call<SearchResponse> searchDienThoaiVaCuaHang (@Query("search") String search,
                                                         @Query("GiaMin") String giaMin,
                                                         @Query("GiaMax") String giaMax,
                                                         @Query("Ram") String ram,
                                                         @Query("boNho") String boNho,
                                                         @Query("sortByPrice") String sortByPrice,
                                                         @Query("uuDaiHot") String uuDaiHot,
                                                         @Query("maHangSanXuat") String maHangSanXuat,
                                                         @Query("sortDanhGia") String sortDanhGia);
    @GET("getChiTietDienThoaiTheoCuaHang/{id}")
    Call<List<Root>> getChiTietDienThoaiTheoCuaHang(@Path("id") String id);
}
