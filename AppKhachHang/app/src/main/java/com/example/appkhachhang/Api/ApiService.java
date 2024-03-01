package com.example.appkhachhang.Api;

import com.example.appkhachhang.Model.ChiTietGioHang;
import com.example.appkhachhang.Model.GioHang;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    //get list gio hang
    @GET("/chitietgiohangs/getChiTietGioHang")
    Call<List<ChiTietGioHang>> getListGioHang();
}
