package com.example.appkhachhang.Api;

import com.example.appkhachhang.Model.AddressDelivery;
import com.example.appkhachhang.Model.DanhGia;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FeedbackAPI {
    @POST("danhgias/addDanhGia")
    Call<DanhGia> postDanhGia(@Body DanhGia danhGia);
}
