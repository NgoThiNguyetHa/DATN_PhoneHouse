package com.example.appcuahang.api;

import com.example.appcuahang.model.Brand;
import com.example.appcuahang.model.HoaDon;
import com.example.appcuahang.model.LoginResponse;
import com.example.appcuahang.model.Store;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("hangsanxuats/getHangSanXuat")
    Call<List<Brand>> getHangSanXuat();
    @GET("hangsanxuats/getHangSanXuatTheoCuaHang/{maCuaHang}")
    Call<List<Brand>> getHangSanXuatByCuaHang(@Path("maCuaHang") String id);

    @POST("hangsanxuats/addHangSanXuat")
    Call<Brand> postHangSanXuat(@Body Brand hangSanXuat);

    @PUT("hangsanxuats/updateHangSanXuat/{id}")
    Call<Brand> putHangSanXuat(@Path("id") String id,
                            @Body Brand hangSanXuat);

    @GET("hoadons/getHoaDonTheoTrangThai/{trangThaiNhanHang}")
    Call<List<HoaDon>> getHoaDonByTrangThai(@Path("trangThaiNhanHang") String trangThaiNhanHang);

    @GET("hoadons/getHoaDonTheoTrangThai/{trangThaiNhanHang}/{maCuaHang}")
    Call<List<HoaDon>> getHoaDonTrangThai(@Path("trangThaiNhanHang") String trangThaiNhanHang,
                                            @Path("maCuaHang") String maCuaHang);

    @GET("hoadons/getHoaDonTheoChiTiet")
    Call<List<HoaDon>> getAllHoaDon();

    @FormUrlEncoded
    @POST("cuahangs/dangNhapCuaHang")
    Call<LoginResponse> DangNhap(@Field("email") String email,
                                 @Field("password") String password);

    //thay đổi thông tin - đổi mật khẩu
    @PUT("cuahangs/updateCuaHang/{id}")
    Call<Store> putCuaHang(@Path("id") String id,
                               @Body Store store);
}
