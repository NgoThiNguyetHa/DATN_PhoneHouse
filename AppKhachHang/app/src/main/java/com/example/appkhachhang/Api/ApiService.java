package com.example.appkhachhang.Api;

import com.example.appkhachhang.Model.ChiTietGioHang;
import com.example.appkhachhang.Model.GioHang;
import com.example.appkhachhang.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    //get list gio hang
    @GET("/chitietgiohangs/getChiTietGioHangTheoKhachHang/{id}")
    Call<List<ChiTietGioHang>> getListGioHang(@Path("id") String id);

    @POST("/chitietgiohangs/addChiTietGioHang/{idKhachHang}")
    Call<ChiTietGioHang> addGioHang(@Body ChiTietGioHang chiTietGioHang, @Path("idKhachHang") String idKhachHang);

    @DELETE("/chitietgiohangs/deleteChiTietGioHang/{id}")
    Call<ChiTietGioHang> deleteGioHang(@Path("id") String id);
}
