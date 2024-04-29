package com.example.appkhachhang.Api;

import com.example.appkhachhang.Model.ChiTietDienThoai;
import com.example.appkhachhang.Model.DanhGia;
import com.example.appkhachhang.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FeedbackAPI {
    @POST("danhgias/addDanhGia")
    Call<DanhGia> postDanhGia(@Body DanhGia danhGia);

    @GET("danhgias/getDanhGiaTheoKhachHang/{id}")
    Call<List<DanhGia>> getDanhGia(@Path("id") String id);

    @PUT("danhgias/updateDanhGia/{id}")
    Call<DanhGia> update(@Path("id") String id,
                            @Body DanhGia danhGia);
}
