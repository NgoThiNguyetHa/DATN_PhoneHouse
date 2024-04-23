package com.example.appkhachhang.Api;

import com.example.appkhachhang.Model.AddressDelivery;
import com.example.appkhachhang.Model.DanhGia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FeedbackAPI {
    @POST("danhgias/addDanhGia")
    Call<DanhGia> postDanhGia(@Body DanhGia danhGia);

    @GET("danhgias/getDanhGiaTheoKhachHang/{id}")
    Call<List<DanhGia>> getDanhGia(@Path("id") String id);
}
